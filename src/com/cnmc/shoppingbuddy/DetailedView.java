package com.cnmc.shoppingbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DetailedView extends Activity {
	
	PlaceList nearPlaces;
	// TextView tvItemName;
	TextView placeTextView;
	TextView addressTextView;
	TextView typeTextView;
	ToggleButton favourite;

	public static final String ITEM_NAME = "itemName";

	public DetailedView() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailedview);
		Intent intent = getIntent();
		
		String placeTitle = intent.getStringExtra("place_title");
		nearPlaces = (PlaceList) intent.getSerializableExtra("near_places");
		
		
		
		/*Intent intent = getIntent();
		String c_name = intent.getStringExtra("name").toString();
		
		placeTextView =(TextView)view.findViewById(R.id.textView1);
		placeTextView.setText(c_name);
		
		addressTextView =(TextView)view.findViewById(R.id.address);
		addressTextView.setText(intent.getStringExtra("address"));
		
				
		
		typeTextView =(TextView)view.findViewById(R.id.type);
		
		typeTextView.setText(intent.getStringExtra("type"));*/
		
		// tvItemName.setText(getArguments().getString(ITEM_NAME));

		//favourite = (ToggleButton) view.findViewById();
		//favourite.setPressed(true);
		favourite.setOnClickListener(favButtonHandler);
		
	}

	View.OnClickListener favButtonHandler = new View.OnClickListener() {

		public void onClick(View v) {

			if (favourite.isPressed()) {
				//favourite.setImageResource(R.drawable.toecu);
				//favourite.setPressed(false);
				//favourite.setEnabled(false);
			} else {
				//favourite.setImageResource(R.drawable.btn_rating_star_off_normal);
				//favourite.setPressed(true);
			}
			// Resource(R.drawable.toecu);

		}
	};

}