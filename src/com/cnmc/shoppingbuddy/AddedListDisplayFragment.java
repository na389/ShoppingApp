package com.cnmc.shoppingbuddy;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cnmc.shoppingbuddy.R;
import com.cnmc.shoppingbuddy.app.ShoppingBuddyApplication;

public class AddedListDisplayFragment extends Fragment {
	private ArrayList<AddedListNameStatus> listNameStatus;
	List<String> list_item;
	private EfficientAdapter listAdapter;
	public static String TAG = "AddedListDisplayFragment";
	public AddedListDisplayFragment(){
		listNameStatus = new ArrayList<AddedListDisplayFragment.AddedListNameStatus>();		
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View listDisplayLayout = inflater.inflate(R.layout.added_list_display, container, false);
		//Adding Items in the list
		ShoppingBuddyApplication application = (ShoppingBuddyApplication)getActivity().getApplication();
		list_item=application.getListData().getListNames();
		
		for(String entryString : list_item)
		{
			listNameStatus.add(new AddedListNameStatus(entryString, true));			
		}
		// Create the adapter to convert the array to views
		
		// Attach the adapter to a ListView

		listAdapter = new EfficientAdapter(getActivity(), listNameStatus);	
		ListView list = (ListView)listDisplayLayout.findViewById(android.R.id.list);
		list.setAdapter(listAdapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), DetailedViewList.class);
				Bundle extras = new Bundle();
	      	  	extras.putString("name",listNameStatus.get(position).getListName());
				//Intent intent=new Intent(AddedListDisplayFragment.this,SearchListActivity.class);
				intent.putExtras(extras);
				startActivity(intent);
			}
			});
		
		
		//list.setOnItemClickListener((OnItemClickListener)this);
		return listDisplayLayout;
			

	}
	
	public static final class AddedListNameStatus{
		private String listName;
		private boolean status;
		public AddedListNameStatus(String listName, boolean status) {
			this.listName = listName;
			this.status = status;
		}
		public String getListName() {
			return listName;
		}
		public void setListName(String listName) {
			this.listName = listName;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}		
	}

	
	
	/*public void onItemClick(AdapterView<?> parent, View view,int position, long id)
	{		  
			Intent intent = new Intent(getActivity(), DetailedViewList.class);
			Bundle extras = new Bundle();
      	  	extras.putString("list_name",listNameStatus.get(position).getListName());
			//Intent intent=new Intent(AddedListDisplayFragment.this,SearchListActivity.class);
			intent.putExtras(extras);
			startActivity(intent);
		}
	 
	*/
	
}
