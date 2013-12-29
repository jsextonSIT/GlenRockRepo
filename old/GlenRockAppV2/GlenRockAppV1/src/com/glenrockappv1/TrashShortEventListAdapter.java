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
	
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.calendar_short_event_item, parent, false);
		
		TextView title, desc, time;
		
		title = (TextView) convertView.findViewById(R.id.calendar_short_event_title);
		time = (TextView) convertView.findViewById(R.id.calendar_short_event_time);
		desc = (TextView) convertView.findViewById(R.id.calendar_short_event_desc);
		
		Log.i("ett", position + "");
		
		TrashEventArticle tea = teas.get(position);
		title.setText(tea.title);
		time.setText(tea.time);
		desc.setText(tea.desc);
		
		LinearLayout wholeView = (LinearLayout) convertView.findViewById(R.id.calendar_short_event_item_ll);
		wholeView.setOnClickListener(new openTrashArticle(context, position));
		return convertView;
	}
	
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
				articleListener.onArticleSelected(teas.get(p));
			}
		}
	}
}
