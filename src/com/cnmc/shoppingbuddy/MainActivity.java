package com.cnmc.shoppingbuddy;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.cnmc.shoppingbuddy.TrackingService;

public class MainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private CustomDrawerAdapter adapter;
	private List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("On create bundle");
		Log.i("Bundle", "on create");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_activity);

		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);

		dataList.add(new DrawerItem("Home"));
		dataList.add(new DrawerItem("MyList"));
		dataList.add(new DrawerItem("Preferred Locations"));
		dataList.add(new DrawerItem("Maps"));
		dataList.add(new DrawerItem("Settings"));
		dataList.add(new DrawerItem("Feedback"));
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}

		android.support.v4.app.FragmentManager myFragmentManager = getSupportFragmentManager();
		FragmentTransaction tx = myFragmentManager.beginTransaction();
		tx.replace(R.id.content_frame, Fragment.instantiate(MainActivity.this,
				"com.cnmc.shoppingbuddy.UserHomeFragment"));
		tx.commit();

		
		//startTracker();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_action, menu);
		return true;
	}

	public void selectItem(int possition) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {
		case 0:
			fragment = new UserHomeFragment();
			args.putString(UserHomeFragment.TAG, dataList.get(possition)
					.getItemName());
			break;
		case 1:
			fragment = new AddedListDisplayFragment();
			args.putString(AddedListDisplayFragment.TAG, dataList
				.get(possition).getItemName());

			break;
		case 2:
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());

			break;
		case 3:
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());

			break;
		case 4:
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());

			break;
		case 5:
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());

			break;
		case 6:
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());

			break;

		default:
			break;
		}

		fragment.setArguments(args);
		FragmentManager frgManager = getSupportFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);

		}
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.listDialogTitle)
				.setPositiveButton(R.string.listDialogDone,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(UserHomeFragment.TAG, "Menu item clicked");
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event

		// Handle your other action bar items...
		if (item.getItemId() == R.id.add_list) {
			createDialog().show();
			System.out.println("add an item");
			return true;
		}
		if( item.getItemId()== R.id.search){
			Intent intent=new Intent(MainActivity.this,SearchListActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	
	private Dialog createDialog() {

		View view = LayoutInflater.from(this).inflate(
				R.layout.list_name_dialog, null);
		final EditText editText = (EditText) view.findViewById(R.id.editText1);
		final Spinner prioritySpinner = (Spinner) view.findViewById(R.id.prioritySpinner);
		prioritySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				
		        String selectedPriority = (parent.getItemAtPosition(pos)).toString();
				System.out.println("User selected the priority: "+selectedPriority);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Set the dialog title
		builder.setTitle("Create a New List")
				// Specify the list array, the items to be selected by default
				// (null for none),
				// and the listener through which to receive callbacks when
				// items are selected
				.setView(view)
				// Set the action buttons
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// User clicked OK, so save the mSelectedItems
								// results somewhere
								// or return them to the component that opened
								// the dialog
								
								String selectedListName = editText.getText().toString();
								String selectedPriority = prioritySpinner.getSelectedItem().toString();
								
								System.out.println("The user entered list name and selected a priority");
								System.out.println("Printing Edit Text:"
										+ editText.getText().toString());
								System.out.println("Printing the priority "+prioritySpinner.getSelectedItem().toString());
				          	  	System.out.println("starting new activity");
				          	  	
				          	  	Intent intent=new Intent(MainActivity.this,AddItemToListActivity.class);
				          	  	Bundle extras = new Bundle();
				          	  	extras.putString("priority",selectedPriority);
				          	  	extras.putString("name",selectedListName);
				         	  	System.out.println("Added extras successfully");
				         	  	intent.putExtras(extras);
				          	  	startActivity(intent);
								
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								Log.i("dialog","cancel");
				            	System.out.println("User clicked cancel on dialog");
								dialog.dismiss();
							}
						});

		return builder.create();
	}
	 

}