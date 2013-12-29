/****
 * This class is designed to show the events with a "Trash" tag in a list underneath the calendar grid. It has code for displaying a single event
 * but applies this code for every event
 */

package com.glenrockappv1;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrashShortEventListAdapter extends ArrayAdapter<TrashEventArticle>{
	public interface TrashArticleSelectedListener {
		public void onArticleSelected(TrashEventArticle item);
	}
	TrashArticleSelectedListener articleListener;
	private Context context;
	private ArrayList<TrashEventArticle> teas;

	public TrashShortEventListAdapter(Context c, ArrayList<TrashEventArticle> teas){
		super(c, R.layout.calendar_short_event_item, teas);
		this.teas = teas;
		context = c;
		articleListener = (MainActivity) context;
	}
	
	/**
	 * 
	 * Precondition: Given the position of the item in the list to display, called by Android system
	 * Postcondition: This returns the "view", which is the display content of a given item in the list.
	 * 
	 */
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.calendar_short_event_item, parent, false); //decides which xml layout to use
		
		TextView title, desc, time;
		
		title = (TextView) convertView.findViewById(R.id.calendar_short_event_title); // this text view sets the title of a given event
		time = (TextView) convertView.findViewById(R.id.calendar_short_event_time);//sets the time of a given event
		desc = (TextView) convertView.findViewById(R.id.calendar_short_event_desc); // sets the description of a given event
		
		//Log.i("ett", position + "");
		
		TrashEventArticle tea = teas.get(position); //gets the event at the position to display
		title.setText(tea.title); //sets the title
		time.setText(tea.time);//sets the time
		desc.setText(tea.desc);//sets the description
		
		LinearLayout wholeView = (LinearLayout) convertView.findViewById(R.id.calendar_short_event_item_ll);
		wholeView.setOnClickListener(new openTrashArticle(context, position)); //when the item is clicked, it will open the individual event page
		return convertView;
	}
	
	/***
	 * 
	 * This class is for describing what happens upon clicking the individual event.
	 *
	 */
	private class openTrashArticle implements View.OnClickListener{
		private Context mContext;
		private int p;
		public openTrashArticle(Context c, int position){
			mContext = c;
			p = position;
		}
		@Override
		public void onClick(View view) {
			if (articleListener != null){
				articleListener.onArticleSelected(teas.get(p)); //The onArticleSelected method is in the MainActivity class, which selects the correct event to display on its own page. This is done for efficiency.
			}
		}
	}
}
