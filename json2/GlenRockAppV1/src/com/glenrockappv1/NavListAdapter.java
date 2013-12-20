
package com.glenrockappv1;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
//fragment for displaying the navigation drawer rows
public class NavListAdapter extends ArrayAdapter<String> {
	ArrayList<String> titles;
	Context context;
	/* constructor for navlistadapter
	 * input:context and array of titles for the rows
	 */
	public NavListAdapter(Context context, ArrayList<String> titles) {
		super(context, R.layout.drawer_list_item, titles);
		this.titles = titles;
		this.context = context;
	}
	/* Creates each individual view for the rows inside the navigation drawer and sets the title
	 * input: index(position) of drawer item, view, viewgroup,
	 * output: view for the row
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.drawer_list_item, parent, false);
		TextView nameTextView = (TextView) convertView.findViewById(android.R.id.text1);
		//ImageView image = (ImageView) convertView.findViewById(R.id.image);
		nameTextView.setText(titles.get(position));
		//for adding images to icons in future
//		switch(position){
//		case (0):
//			image.setImageResource(R.drawable.);
//		break;
//		case (1):
//			image.setImageResource(R.drawable.);
//		break;
//		case (2):
//			image.setImageResource(R.drawable.);
//		break;
//		}
		return convertView;
	}
}