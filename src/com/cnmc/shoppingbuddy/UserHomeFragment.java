package com.cnmc.shoppingbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cnmc.shoppingbuddy.app.ShoppingBuddyApplication;
import com.cnmc.shoppingbuddy.db.ListData;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserHomeFragment extends Fragment {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	public static String TAG = "UserHomeFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View userHomeLayout = inflater.inflate(
				R.layout.user_home_fragment_layout, container, false);
		// get the listview
		expListView = (ExpandableListView) userHomeLayout
				.findViewById(R.id.lvExp);

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				Toast.makeText(
						getActivity(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
										listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getActivity(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collapsed listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getActivity(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});

		Button startService = (Button) userHomeLayout
				.findViewById(R.id.startButton);
		startService.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// startTracker();
				Toast.makeText(getActivity(), "nsfskfhskbks",
						Toast.LENGTH_SHORT).show();
				doBindService();
				startTracker();
			}
		});
		Button stopService = (Button) userHomeLayout
				.findViewById(R.id.stopButton);
		stopService.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// stopTracker();
				doUnbindService();
			}
		});

		// preparing list data
		int size = prepareListData();
		if (size == 0) {
			ImageView emptyCart = (ImageView)userHomeLayout.findViewById(R.id.emptyList);
			emptyCart.setVisibility(View.VISIBLE);
			TextView emptyCartText = (TextView)userHomeLayout.findViewById(R.id.emptyText);
			emptyCartText.setVisibility(View.VISIBLE);
		} else {
			listAdapter = new ExpandableListAdapter(getActivity(),
					listDataHeader, listDataChild, "home");

			// setting list adapter
			expListView.setAdapter(listAdapter);
		}
		return userHomeLayout;
	}

	/*
	 * Preparing the list data
	 */
	private int prepareListData() {

		ShoppingBuddyApplication application = (ShoppingBuddyApplication) getActivity()
				.getApplication();
		ListData db = application.getListData();
		listDataHeader = db.getListNames();

		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		for (String listName : listDataHeader) {
			listDataChild
					.put(listName, db.getListData(listName).getListItems());

		}

		return listDataHeader.size();

	}

	// functions to start and stop the service
	public void startTracker() {
		System.out.println("in start tracker");
		Intent i = new Intent(getActivity(), TrackingService.class);
		ShoppingBuddyApplication application = (ShoppingBuddyApplication) getActivity()
				.getApplication();
		ListData db = application.getListData();
		if(db.getListNames()!=null && db.getListNames().size() == 0){
			Toast.makeText(getActivity(), "No List Added", Toast.LENGTH_SHORT).show();
			return;
		}
		
		i.putExtra("list_types_string", db.getListData(db.getListNames().get(0)).getTypeItemString());

		getActivity().startService(i);
	}

	public void stopTracker() {
		getActivity().stopService(
				new Intent(getActivity(), TrackingService.class));
	}

	private TrackingService mBoundService;
	private boolean mIsBound = false;
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			mBoundService = ((TrackingService.LocalBinder) service)
					.getService();

			// Tell the user about this for our demo.
			Toast.makeText(getActivity(), "Binding service", Toast.LENGTH_SHORT)
					.show();
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			// Because it is running in our same process, we should never
			// see this happen.
			mBoundService = null;
			Toast.makeText(getActivity(), "Service Disconnected",
					Toast.LENGTH_SHORT).show();
		}
	};

	void doBindService() {
		// Establish a connection with the service. We use an explicit
		// class name because we want a specific service implementation that
		// we know will be running in our own process (and thus won't be
		// supporting component replacement by other applications).

		getActivity().bindService(
				new Intent(getActivity(), TrackingService.class), mConnection,
				Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			getActivity().unbindService(mConnection);
			mIsBound = false;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}
}
