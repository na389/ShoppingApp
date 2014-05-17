package com.cnmc.shoppingbuddy.app;

import com.cnmc.shoppingbuddy.db.ListData;
import com.cnmc.shoppingbuddy.db.LocationData;



import android.app.Application;

public class ShoppingBuddyApplication extends Application{
	
	private LocationData locationData;
	private ListData listData;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.locationData = new LocationData(this);
		this.listData = new ListData(this);
		System.out.println("list object created");
	}
	public LocationData getLocationData() {
		return locationData;
	}
	public void setLocationData(LocationData locationData) {
		this.locationData = locationData;
	}
	
	public ListData getListData() {
		return listData;
	}
	public void setListData(ListData listData) {
		this.listData = listData;
	}
	
	
}
