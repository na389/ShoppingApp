package com.cnmc.shoppingbuddy;

import java.util.ArrayList;
import java.util.List;

import com.cnmc.shoppingbuddy.app.ShoppingBuddyApplication;
import com.cnmc.shoppingbuddy.db.ListItemsPriority;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.os.DropBoxManager.Entry;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class DetailedViewList extends Activity {

	ListView myListView;
	Button save;
	String list_name;
	String checkedStatus;

	private List<String> items = new ArrayList<String>();


	  	
	  	
	
	MyArrayAdapter myArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_view_list);

		Intent intent = getIntent();
	  
		 list_name = intent.getStringExtra("name");
		 System.out.println(list_name);
		 
	ShoppingBuddyApplication application = (ShoppingBuddyApplication)getApplication();
	ListItemsPriority list_obj = application.getListData().getListData(list_name);
	checkedStatus = list_obj.getStatus();
	 System.out.println("checkedStatus " +checkedStatus);
	
		items = list_obj.getListItems();
		
		myListView = (ListView) findViewById(R.id.list);

		//String[] array = items.toArray(new String[items.size()]);
		
		myArrayAdapter = new MyArrayAdapter(this, R.layout.row,
				android.R.id.text1, items);
		
		myListView.setAdapter(myArrayAdapter);
		myListView.setOnItemClickListener(myOnItemClickListener);

		save = (Button) findViewById(R.id.save_changes);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// get all items and save it in database
				ContentValues content = new ContentValues();
				content.put(ListData.C_CHECKED, checkedStatus);
				
				ShoppingBuddyApplication application = (ShoppingBuddyApplication)getApplication();
          	  	application.getListData().updateList(content, list_name);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed_view_list, menu);
		return true;
	}

	OnItemClickListener myOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			myArrayAdapter.toggleChecked(position);

			char[] statusArr = checkedStatus.toCharArray();
			statusArr[position] = (statusArr[position]=='1')?'0':'1';
			checkedStatus = String.valueOf(statusArr);

		}
	};

	private class MyArrayAdapter extends ArrayAdapter<String> {

		private HashMap<Integer, Boolean> myChecked = new HashMap<Integer, Boolean>();

		public MyArrayAdapter(Context context, int resource,
				int textViewResourceId, List<String> objects) {
			super(context, resource, textViewResourceId, objects);

			for (int i = 0; i < objects.size(); i++) {
				// get from database
				Boolean st = false;
				if(checkedStatus.charAt(i)=='1')
					st = true;
				else
					st = false;
				
				myChecked.put(i, st);
			}
		}

		public void toggleChecked(int position) {
			if (myChecked.get(position)) {
				myChecked.put(position, false);
			} else {
				myChecked.put(position, true);
			}

			notifyDataSetChanged();
		}

		public List<Integer> getCheckedItemPositions() {
			List<Integer> checkedItemPositions = new ArrayList<Integer>();

			for (int i = 0; i < myChecked.size(); i++) {
				if (myChecked.get(i)) {
					(checkedItemPositions).add(i);
				}
			}

			return checkedItemPositions;
		}

		public List<String> getCheckedItems() {
			List<String> checkedItems = new ArrayList<String>();

			for (int i = 0; i < myChecked.size(); i++) {
				if (myChecked.get(i)) {
					(checkedItems).add(items.get(i));
				}
			}

			return checkedItems;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
			}

			CheckedTextView checkedTextView = (CheckedTextView) row
					.findViewById(R.id.text1);
			checkedTextView.setText(items.get(position));

			Boolean checked = myChecked.get(position);
			if (checked != null) {
				checkedTextView.setChecked(checked);
			}

			return row;
		}

	}


}
