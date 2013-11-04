
package com.glenrockappv1;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsListAdapter extends ArrayAdapter<Article> {
	public interface ArticleSelectedListener {
		public void onArticleSelected(Article item);
	}
	ArticleSelectedListener articleListener;
	ArrayList<Article> articles;
	Context context;
	public NewsListAdapter(Context context, ArrayList<Article> articles) {
		super(context, R.layout.news_list_item, articles);
		this.articles = articles;
		this.context = context;
		articleListener = (MainActivity) context;
		/*for (int i = 0; i < 5; i++){
			Log.i("article title", articles.get(i).title);
		}*/
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.news_list_item, parent, false);
		TextView titleTextView = (TextView) convertView.findViewById(android.R.id.text1);
		TextView snippTextView = (TextView) convertView.findViewById(android.R.id.text2);
		//Button readon = (Button) convertView.findViewById(R.id.button_read_on);
		LinearLayout card = (LinearLayout) convertView.findViewById(R.id.news_card);
		titleTextView.setText(articles.get(position).title);
		//Log.i("article title", articles.get(position).title);
		snippTextView.setText(articles.get(position).text);
		card.setTag(position);
		card.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View view){
				if (articleListener != null)articleListener.onArticleSelected(articles.get((Integer)view.getTag()));
			}
		});
		return convertView;
	}
}