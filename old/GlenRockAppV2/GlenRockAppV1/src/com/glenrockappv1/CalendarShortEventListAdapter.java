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
	
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.calendar_short_event_item, parent, false);
		
		TextView title, desc, time;
		
		title = (TextView) convertView.findViewById(R.id.calendar_short_event_title);
		time = (TextView) convertView.findViewById(R.id.calendar_short_event_time);
		desc = (TextView) convertView.findViewById(R.id.calendar_short_event_desc);
		
		Log.i("ett", position + "");
		
		CalendarEventArticle cea = ceas.get(position);
		title.setText(cea.title);
		time.setText(cea.time);
		desc.setText(cea.desc);
		
		LinearLayout wholeView = (LinearLayout) convertView.findViewById(R.id.calendar_short_event_item_ll);
		wholeView.setOnClickListener(new openArticle(context, position));
		return convertView;
	}
	
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
				articleListener.onArticleSelected(ceas.get(p));
			}
		}
	}
}
