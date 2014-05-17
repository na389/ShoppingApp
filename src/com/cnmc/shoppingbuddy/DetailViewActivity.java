package com.cnmc.shoppingbuddy;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_view);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detail_view,
					container, false);
			TextView tv_listName = (TextView) rootView.findViewById(R.id.textViewName);
			TextView tv_priority = (TextView) rootView.findViewById(R.id.textViewPriority);
			TextView tv_itemshashmap = (TextView) rootView.findViewById(R.id.textViewHashMap);
			
			String hashMapText = getActivity().getIntent().getExtras().getSerializable("itemshashmap").toString();
			String priorityText = getActivity().getIntent().getExtras().getString("priority").toString();
			String listNameText = getActivity().getIntent().getExtras().getString("name").toString();
			System.out.println("Hehehe"+hashMapText);
			
			tv_itemshashmap.setText(hashMapText);
			tv_priority.setText(priorityText);
			tv_listName.setText(listNameText);
			
			return rootView;
		}
	}

}
