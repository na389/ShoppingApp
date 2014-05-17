package com.cnmc.shoppingbuddy;

import javax.xml.namespace.QName;

import com.cnmc.shoppingbuddy.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FragmentTwo   extends Fragment {
 
     
      TextView tvItemName;
      Button fav;
  
      public static final String ITEM_NAME = "itemName";
 
      public FragmentTwo()
      {
 
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
 
            View view=inflater.inflate(R.layout.fragment_layout_two,container, false);
 
          
            tvItemName=(TextView)view.findViewById(R.id.frag2_text);
            
            tvItemName.setText(getArguments().getString(ITEM_NAME));
            
            
            
            
            return view;
      }
 
}