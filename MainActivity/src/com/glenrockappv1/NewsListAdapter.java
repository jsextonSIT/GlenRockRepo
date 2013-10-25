
package com.glenrockappv1;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsListAdapter extends ArrayAdapter<String> {
	ArrayList<String> titles;
	ArrayList<String> snipps;
	ArrayList<Integer> nid;
	Context context;
	public NewsListAdapter(Context context, ArrayList<String> titles, ArrayList<String> snipps, ArrayList<Integer> nid) {
		super(context, R.layout.news_list_item, titles);
		this.titles = titles;
		this.snipps = snipps;
		this.nid = nid;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.news_list_item, parent, false);
		TextView titleTextView = (TextView) convertView.findViewById(android.R.id.text1);
		TextView snippTextView = (TextView) convertView.findViewById(android.R.id.text2);
		//Button readon = (Button) convertView.findViewById(R.id.button_read_on);
		LinearLayout card = (LinearLayout) convertView.findViewById(R.id.news_card);
		titleTextView.setText(titles.get(position));
		snippTextView.setText(snipps.get(position));
		card.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View view) {
				//pass nid to method in mainactivity to handle showing snippet pages
			}
		});
		return convertView;
	}
}