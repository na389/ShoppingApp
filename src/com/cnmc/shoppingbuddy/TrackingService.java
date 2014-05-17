package com.cnmc.shoppingbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TrackingService extends Service{
	
	// flag for Internet connection status
    Boolean isInternetPresent = false;
 
    // Connection detector class
    ConnectionDetector cd;
     
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
 
    // Google Places
    GooglePlaces googlePlaces;
 
    // Places List
    PlaceList nearPlaces;
 
    // GPS Location
    GPSTracker gps;
 
    // Button
    Button btnShowOnMap;
 
    // Progress dialog
    //ProgressDialog pDialog;
     
    // Places Listview
    ListView lv;
     
    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
     
     
    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name
    public static String KEY_TYPE = "types";
    // Map view
    MapView mapView;
 
    // Map overlay items
    List<Overlay> mapOverlays;
 
    AddItemizedOverlay itemizedOverlay;
 
    GeoPoint geoPoint;
    // Map controllers
    MapController mc;
     
    double latitude;
    double longitude;
    OverlayItem overlayitem;
	
	
	Context mContext;
	private final IBinder mBinder = new LocalBinder();
	TimerTask scanTask;
	final Handler handler = new Handler();
	Timer t = new Timer();
	public static String GPS = "GPS";
	public String typesString;
	
	public TrackingService(){		
	}
	
	public TrackingService(Context mContext) {
		this.mContext = mContext;
		
		
	}
	
	public class LocalBinder extends Binder {
        TrackingService getService() {
            return TrackingService.this;
        }
    }

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("onStartCommand");
		gps = new GPSTracker(getApplicationContext());
		typesString= intent.getStringExtra("list_types_string");
		
		
//		Toast.makeText(mContext, "Service Called", Toast.LENGTH_SHORT).show();
		startThread();
		return Service.START_STICKY;
	}
	
	private void startThread(){

		System.out.println("in start thread");
		scanTask = new TimerTask() {
	        public void run() {
	                handler.post(new Runnable() {
	                	
	                        public void run() {
	                        new LoadPlaces().execute();
	                         Log.d("TIMER", "Timer set off");
	                        }
	               });
	        }};

	        t.schedule(scanTask, 300, 900000); // repeated every 300 seconds
	
	}
	
	
	

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	  /**
     * Background Async Task to Load Google places
     * */
    class LoadPlaces extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
 
        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new GooglePlaces();
            
            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                // 
            	//Toast.makeText(mContext, "Thread Called", Toast.LENGTH_SHORT).show();
                String types = typesString.substring(0, typesString.length()-1); // Listing places only cafes, restaurants
                 
                // Radius in meters - increase this value if you don't find any places
                double radius = 1000; // 1000 meters 
                 
                // get nearest places
                nearPlaces = googlePlaces.search(gps.getLatitude(),
                        gps.getLongitude(), radius, types);
                
 
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
           // pDialog.dismiss();
            if(nearPlaces == null){
            	createIntent(0);
            	System.out.println("Near Places not found");
            	return;
            }

           
                    /**
                     * Updating parsed Places into LISTVIEW
                     * */
                    // Get json response status
                    String status = nearPlaces.status;
                     
                    // Check for all possible status
                    if(status.equals("OK")){
                        // Successfully got places details
                        if (nearPlaces.results != null) {
                            // loop through each place
                            for (Place p : nearPlaces.results) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                 
                                // Place reference won't display in listview - it will be hidden
                                // Place reference is used to get "place full details"
                                map.put(KEY_REFERENCE, p.reference);
                                 
                                // Place name
                                map.put(KEY_NAME, p.name);
                                 
                                // adding HashMap to ArrayList
                                placesListItems.add(map);
                            }
                            
                           createIntent(nearPlaces.results.size());
                          
                        }
                    }
             
 
        }
 
    }
    
    void createIntent(int numResults){
    	if(numResults  == 0){
    		System.out.println("No results");
    		return;
    	}
    	
    	NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)  
    			.setSmallIcon(R.drawable.shopping_cart)
    	        .setContentTitle("Shopping Buddy")
    	        .setContentText("Number of Results Found:"+numResults);
    	Intent resultIntent = new Intent(getApplicationContext(),
				PlacesMapActivity.class);
    	resultIntent.setAction("com.cnmc.shoppingbuddy.Notification");
    	int notifyID = 1;
    	// The stack builder object will contain an artificial back stack for the
    	// started Activity.
    	// This ensures that navigating backward from the Activity leads out of
    	// your application to the Home screen.
    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    	// Adds the back stack for the Intent (but not the Intent itself)
    	stackBuilder.addParentStack(PlacesMapActivity.class);
    	// Adds the Intent that starts the Activity to the top of the stack
    	stackBuilder.addNextIntent(resultIntent);
    	resultIntent.putExtra("user_latitude", Double.toString(gps.getLatitude()));
		resultIntent.putExtra("user_longitude", Double.toString(gps.getLongitude()));
		
		// passing near places to map activity
		resultIntent.putExtra("near_places", nearPlaces);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP  | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    	/*PendingIntent resultPendingIntent =
    	        stackBuilder.getPendingIntent(
    	            0,
    	            PendingIntent.FLAG_UPDATE_CURRENT
    	        );*/
    	mBuilder.setContentIntent(contentIntent);
    	NotificationManager mNotificationManager =
    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	// Sending user current geo location
    				
    				// staring activity
    				//startActivity(resultIntent);
    	// mId allows you to update the notification later on.
    	mNotificationManager.notify(notifyID, mBuilder.getNotification());
    	
    	 
			
    
    }
}
    


