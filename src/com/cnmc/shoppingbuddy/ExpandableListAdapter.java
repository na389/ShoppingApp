package com.cnmc.shoppingbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
	public HashMap<String,ArrayList<String>> glob_item_hmap=new HashMap<String,ArrayList<String>>();
	
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    
    private HashMap<String, List<String>> _listDataChild;
    private String callIdentifier;
    private String customItem;
	private String k=new String();

    
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData, String callIdentifier) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.callIdentifier = callIdentifier;
    }
    
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
    	final int grpPos = groupPosition;
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // convertView = infalInflater.inflate(R.layout.list_item, null);
            //use switch case instead of if's
            if ( "home".equals(callIdentifier)){
            	convertView = infalInflater.inflate(R.layout.list_item, null);
            }
            if ("addItem".equals(callIdentifier)){
            	convertView = infalInflater.inflate(R.layout.listitem_layout,null);
            }
        }
 
       // TextView txtListChild = (TextView) convertView
         //       .findViewById(R.id.lblListItem);
 
        //txtListChild.setText(childText);
        //use switch case instead of if's
        if ( "home".equals(callIdentifier)){
        	TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        	 txtListChild.setText(childText);
        }
        
        if ( "addItem".equals(callIdentifier)){
        	TextView txtListChild = (TextView) convertView
                .findViewById(R.id.expListItem);
        	 txtListChild.setText(childText);
        	 
        	ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.addItemButton);
        	imageButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					int expListIndex=grpPos;

				    ArrayList<String> tmp = new ArrayList<String>();
					switch(expListIndex){

						case 0:
							k ="grocery_or_supermarket";
							break;

						case 1:
							k = "clothing_store";
							break;

						case 2:
							k = "electronics_store";
							break;

						case 3:
							k = "convenience_store";
							break;

						case 4:
							k = "department_store";
							break;

						case 5:
							k = "furniture_store";
							break;

						case 6:
							k = "health";
							break;

						case 7:
							k ="liquor_store";
							break;

						case 8:
							k = "shoe_store";
							break;

						case 9:
							k = "shopping_mall";
							break;

						case 10:
							k = "veterinary_care";
							break;

						case 11:
							k = "florist";
							break;

						default:
							k = "unknown item";
							break;

					}


					if(glob_item_hmap.containsKey(k) && childPosition !=0){
						tmp = glob_item_hmap.get(k);
						tmp.add(childText);
					}

					else{
						if (childPosition !=0){
							tmp.add(childText);
						}
					}
					glob_item_hmap.put(k,tmp);

					if(childPosition == 0){

						createDialog().show();
					}
					System.out.println("Current Contents"+tmp);
					//Debugging code
					System.out.println(_listDataHeader.get(grpPos));					
	                System.out.println(childText);
					System.out.println("Contents of hash map:");
					System.out.println(glob_item_hmap.toString());

				}


			});

        }
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
    private Dialog createDialog(){


		final View view = LayoutInflater.from(_context).inflate(
				R.layout.custom_item_dialog, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(_context);
		// Set the dialog title
		builder.setTitle("Custom Item")
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
								EditText item_name_editText = (EditText) view.findViewById(R.id.customItemName);
								customItem = item_name_editText.getText().toString();
								System.out.println("Inserting custom item "+customItem);
							    ArrayList<String> tmp2 = new ArrayList<String>();


								if(glob_item_hmap.containsKey(k) ){
									tmp2 = glob_item_hmap.get(k);
									tmp2.add(customItem);
								}

								else{
										tmp2.add(customItem);

								}
								glob_item_hmap.put(k,tmp2);

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