package com.cnmc.shoppingbuddy;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

 
public class PlacesMapActivity extends FragmentActivity implements OnMarkerClickListener{
    // Nearest places
    PlaceList nearPlaces;
 
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

	private GoogleMap mMap;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);               
        setContentView(R.layout.map_places);        
        onNewIntent(getIntent());
 }
 
    @Override
    public void onNewIntent(Intent i) {
    	if(i.getExtras() == null){
    		Toast.makeText(this, "Intent", Toast.LENGTH_SHORT).show();	
    		return;
    	}
    	AlertDialogManager alert = new AlertDialogManager();   
         // Users current geo location        
        String user_latitude = i.getStringExtra("user_latitude");
        String user_longitude = i.getStringExtra("user_longitude");
         
        // Nearplaces list
        nearPlaces = (PlaceList) i.getSerializableExtra("near_places");
 
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("MAP");
        mMap = ((SupportMapFragment)fragment).getMap();
        if(mMap == null){
        	alert.showAlertDialog(this, "Error!!", "Error Loading Map", false);
        	return;
        }
        mMap.setOnMarkerClickListener(this);
        mMap.setMyLocationEnabled(true);   
        mMap.addMarker(new MarkerOptions()
		.position(new LatLng((int) (Double.parseDouble(user_latitude) * 1E6),
                (int) (Double.parseDouble(user_longitude) * 1E6)))
		.title("my position")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
		new LatLng((int) (Double.parseDouble(user_latitude) * 1E6),
                (int) (Double.parseDouble(user_longitude) * 1E6)), 15.0f));

        
        mMap.getUiSettings().setZoomControlsEnabled(true);
        final List<Place> placeList = nearPlaces.results;
        
	    final View mapView = fragment.getView();
	    if (mapView.getViewTreeObserver().isAlive()) {
	        mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	            
	            // We check which build version we are using.
	            @SuppressWarnings("deprecation")
				@Override
	            public void onGlobalLayout() {
	            	
	            	LatLngBounds.Builder bld = new LatLngBounds.Builder();
	            	for (int i = 0; i < placeList.size(); i++) {         
	            		double lati = placeList.get(i).geometry.location.lat;
	            		double longLat = placeList.get(i).geometry.location.lng;
	            		LatLng ll = new LatLng(lati,longLat);
	            		bld.include(ll);            
	            		mMap.addMarker(new MarkerOptions()
						.position(new LatLng(lati, longLat))
						.title(placeList.get(i).name)
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue))
						.snippet(placeList.get(i).vicinity)						
						);
	            	}
	            	LatLngBounds bounds = bld.build();          
	            	mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 40));
	            	mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

	            }
	        });
	    }
	        
    }

	@Override
	public boolean onMarkerClick(Marker marker) {
		Intent intent = new Intent(this, DetailedView.class);
		intent.putExtra("near_places", nearPlaces);	
		intent.putExtra("place_title", marker.getTitle());
		startActivity(intent);		
		return true;
	}

}
