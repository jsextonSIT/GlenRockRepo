/****
 * This class is designed to show the events in the calendar in a list underneath the calendar grid. 
 * It has code for displaying a single event but applies this code for every event
 */


package com.glenrockappv1;

import java.util.ArrayList;

import com.glenrockappv1.NewsListAdapter.ArticleSelectedListener;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalendarShortEventListAdapter extends ArrayAdapter<CalendarEventArticle>{
	public interface CalendarArticleSelectedListener {
		public void onArticleSelected(CalendarEventArticle item);
	}
	CalendarArticleSelectedListener articleListener;
	private Context context;
	private ArrayList<CalendarEventArticle> ceas;

	public CalendarShortEventListAdapter(Context c, ArrayList<CalendarEventArticle> ceas){
		super(c, R.layout.calendar_short_event_item, ceas);
		this.ceas = ceas;
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
		convertView = inflater.inflate(R.layout.calendar_short_event_item, parent, false);
		
		TextView title, desc, time;
		
		//sets the layouts
		title = (TextView) convertView.findViewById(R.id.calendar_short_event_title);
		time = (TextView) convertView.findViewById(R.id.calendar_short_event_time);
		desc = (TextView) convertView.findViewById(R.id.calendar_short_event_desc);
		
		Log.i("ett", position + "");
		
		CalendarEventArticle cea = ceas.get(position); //gets the event at a position to display
		title.setText(cea.title); //displays the title
		time.setText(cea.time);//displays the time 
		desc.setText(cea.desc);//displays the description
		
		LinearLayout wholeView = (LinearLayout) convertView.findViewById(R.id.calendar_short_event_item_ll);
		wholeView.setOnClickListener(new openArticle(context, position));
		return convertView;
	}
	/************
	 * This class describes the clicking action on the name of an event in the list. It will open the 
	 * individual page for the event.
	 *
	 */
	private class openArticle implements View.OnClickListener{
		private Context mContext;
		private int p;
		public openArticle(Context c, int position){
			mContext = c;
			p = position;
		}
		@Override
		public void onClick(View view) {
			if (articleListener != null){
				articleListener.onArticleSelected(ceas.get(p));//The onArticleSelected method is in the MainActivity class, which selects the correct event to display on its own page. This is done for efficiency.
				
			}
		}
	}
}
