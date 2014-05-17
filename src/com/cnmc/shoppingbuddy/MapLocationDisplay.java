package com.cnmc.shoppingbuddy;

import java.util.ArrayList;
import java.util.HashMap;
 
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
 
 
public class MapLocationDisplay extends MapActivity {
 
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
    ProgressDialog pDialog;
     
    // Places Listview
    ListView lv;
     
    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
     
     
    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name
 
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
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_places);
 
        cd = new ConnectionDetector(getApplicationContext());
 
        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet Connection is not present
            alert.showAlertDialog(MapLocationDisplay.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
 
        // creating GPS Class object
        gps = new GPSTracker(this);
 
        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            // Can't get user's current location
            alert.showAlertDialog(MapLocationDisplay.this, "GPS Status",
                    "Couldn't get location information. Please enable GPS",
                    false);
            // stop executing code by return
            return;
        }
 
        new LoadPlaces().execute();
        
      //  mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
 
        mapOverlays = mapView.getOverlays();
         
        // Geopoint to place on map
        geoPoint = new GeoPoint((int) (gps.getLatitude() * 1E6),
                (int) (gps.getLongitude() * 1E6));
         
        // Drawable marker icon
        Drawable drawable_user = this.getResources()
                .getDrawable(R.drawable.mark_red);
         
        itemizedOverlay = new AddItemizedOverlay(drawable_user, this);
         
        // Map overlay item
        overlayitem = new OverlayItem(geoPoint, "Your Location",
                "That is you!");
 
        itemizedOverlay.addOverlay(overlayitem);
         
        mapOverlays.add(itemizedOverlay);
        itemizedOverlay.populateNow();
         
        // Drawable marker icon
        Drawable drawable = this.getResources()
                .getDrawable(R.drawable.mark_blue);
         
        itemizedOverlay = new AddItemizedOverlay(drawable, this);
 
        mc = mapView.getController();
 

 
    }

	private void addMap(int minLat, int minLong, int maxLat, int maxLong) {
		if (nearPlaces.results != null) {
            // loop through all the places
            for (Place place : nearPlaces.results) {
                latitude = place.geometry.location.lat; // latitude
                longitude = place.geometry.location.lng; // longitude
                 
                // Geopoint to place on map
                geoPoint = new GeoPoint((int) (latitude * 1E6),
                        (int) (longitude * 1E6));
                 
                // Map overlay item
                overlayitem = new OverlayItem(geoPoint, place.name,
                        place.vicinity);
 
                itemizedOverlay.addOverlay(overlayitem);
                 
                 
                // calculating map boundary area
                minLat  = (int) Math.min( geoPoint.getLatitudeE6(), minLat );
                minLong = (int) Math.min( geoPoint.getLongitudeE6(), minLong);
                maxLat  = (int) Math.max( geoPoint.getLatitudeE6(), maxLat );
                maxLong = (int) Math.max( geoPoint.getLongitudeE6(), maxLong );
            }
            mapOverlays.add(itemizedOverlay);
             
            // showing all overlay items
            itemizedOverlay.populateNow();
        }
         
        // Adjusting the zoom level so that you can see all the markers on map
        mapView.getController().zoomToSpan(Math.abs( minLat - maxLat ), Math.abs( minLong - maxLong ));
         
        // Showing the center of the map
        mc.animateTo(new GeoPoint((maxLat + minLat)/2, (maxLong + minLong)/2 ));
        mapView.postInvalidate();
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
            pDialog = new ProgressDialog(MapLocationDisplay.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
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
                String types = "cafe|restaurant"; // Listing places only cafes, restaurants
                 
                // Radius in meters - increase this value if you don't find any places
                double radius = 1000; // 1000 meters 
                 
                // get nearest places
                nearPlaces = googlePlaces.search(gps.getLatitude(),
                        gps.getLongitude(), radius, types);
                 
 
            } catch (Exception e) {
                e.printStackTrace();
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
            pDialog.dismiss();
            
            // These values are used to get map boundary area
            // The area where you can see all the markers on screen
            int minLat = Integer.MAX_VALUE;
            int minLong = Integer.MAX_VALUE;
            int maxLat = Integer.MIN_VALUE;
            int maxLong = Integer.MIN_VALUE;
     
            // check for null in case it is null
            addMap(minLat, minLong, maxLat, maxLong);
            
            
            
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
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
                          
                        }
                    }
                    else if(status.equals("ZERO_RESULTS")){
                        // Zero results found
                        alert.showAlertDialog(MapLocationDisplay.this, "Near Places",
                                "Sorry no places found. Try to change the types of places",
                                false);
                    }
                    else if(status.equals("UNKNOWN_ERROR"))
                    {
                        alert.showAlertDialog(MapLocationDisplay.this, "Places Error",
                                "Sorry unknown error occured.",
                                false);
                    }
                    else if(status.equals("OVER_QUERY_LIMIT"))
                    {
                        alert.showAlertDialog(MapLocationDisplay.this, "Places Error",
                                "Sorry query limit to google places is reached",
                                false);
                    }
                    else if(status.equals("REQUEST_DENIED"))
                    {
                        alert.showAlertDialog(MapLocationDisplay.this, "Places Error",
                                "Sorry error occured. Request is denied",
                                false);
                    }
                    else if(status.equals("INVALID_REQUEST"))
                    {
                        alert.showAlertDialog(MapLocationDisplay.this, "Places Error",
                                "Sorry error occured. Invalid Request",
                                false);
                    }
                    else
                    {
                        alert.showAlertDialog(MapLocationDisplay.this, "Places Error",
                                "Sorry error occured.",
                                false);
                    }
                }
            });
 
        }
 
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
 
 
}