package com.cnmc.shoppingbuddy;

import java.util.ArrayList;

import com.cnmc.shoppingbuddy.AddedListDisplayFragment.AddedListNameStatus;
import com.cnmc.shoppingbuddy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EfficientAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<AddedListNameStatus> listNameStatus;
	private LayoutInflater inflater;
	public EfficientAdapter(Context context, ArrayList<AddedListNameStatus> listNameStatus){
		this.context = context;
		this.listNameStatus = listNameStatus;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return listNameStatus.size();
	}

	@Override
	public Object getItem(int itemPosition) {
		return listNameStatus.get(itemPosition);
	}

	@Override
	public long getItemId(int itemPosition) {
		return itemPosition;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid unneccessary calls to findViewById() on each row.
		ViewHolder holder = null;
		// When convertView is not null, we can reuse it directly, there is
		// no need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.added_list_item, null);

			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.listName = (TextView) convertView
					.findViewById(R.id.listName);
			holder.status= (ImageView) convertView
					.findViewById(R.id.statusImage);

			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		holder.listName.setText(listNameStatus.get(position)
				.getListName());
		if(listNameStatus.get(position).isStatus()){
			holder.status.setImageResource(R.drawable.right);
		}else{
			holder.status.setImageResource(R.drawable.wrong);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView listName;
		ImageView status;		
	}
}
