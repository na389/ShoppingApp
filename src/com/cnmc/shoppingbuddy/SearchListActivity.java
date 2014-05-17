package com.cnmc.shoppingbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cnmc.shoppingbuddy.app.ShoppingBuddyApplication;
 
public class SearchListActivity extends Activity {
     
    // List view
    private ListView lv;
     
    // Listview Adapter
    ArrayAdapter<String> adapter;
     
    // Search EditText
    EditText inputSearch;
    
    SQLiteDatabase db;
    // ArrayList for Listview
   // ArrayList<HashMap<String, String>> productList;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);
         
        lv = (ListView) findViewById(R.id.search_list_view);
        
        
    	ShoppingBuddyApplication application = (ShoppingBuddyApplication)getApplication();

	  	List<String> listNames=application.getListData().getListNames();
       
 /*
        myDB =hello.this.openOrCreateDatabase("CUED", MODE_PRIVATE, null); 
        Cursor crs = myDB.rawQuery("SELECT * FROM HELLO", null);


        List<String> array = new ArrayList<String>();
		while(crs.moveToNext()){
    	String uname = crs.getString(crs.getColumnIndex("NAME"));
    	array.add(uname);
}
*/	
        // Listview Data
        
       // ListData dataObj = new ListData();
        
       // String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
         //                       "iPhone 4S", "Samsung Galaxy Note 800",
           //                     "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
         
 
        
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        
    //listener for list item     
        // Adding items to listview
        String[] array = listNames.toArray(new String[listNames.size()]);
        
        
        adapter = new ArrayAdapter<String>(this, R.layout.search_list_item, R.id.list_name, array);
        lv.setAdapter(adapter);       
     

        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
        	    {
        	      String selectedFromList = (String) ((TextView)view.findViewById(R.id.list_name)).getText();
        	      Intent intent=new Intent();
        	      intent.setClass(getApplicationContext(), DetailedViewList.class);
					//intent.setAction("com.cnmc.shoppingbuddy.search");
	          	  	intent.putExtra("name", selectedFromList);
	          	  	startActivity(intent);
        	      
        	    }});
       
        inputSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchListActivity.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
    }
     
}