/**********
 * This class is for displaying information about a given trash event. This is the individual page for that event.
 * It is shown when the user clicks on the title of an event.
 */
package com.glenrockappv1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class TrashEventArticleFragment extends SherlockFragment {
	private TrashEventArticle article;
	private TextView time;
	private TextView title;
	private TextView desc;
	private TextView location;
	private TextView contact;
	private TextView date;
	static TrashEventArticleFragment newInstance(TrashEventArticle item){
		TrashEventArticleFragment fragment = new TrashEventArticleFragment();
		Bundle args = new Bundle();
		args.putParcelable("article", item);
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	//sets all the fields in the event layout
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.calendar_article_fragment,container, false);
		article = (TrashEventArticle) getArguments().getParcelable("article");
		
		//gets all the textview ids from the layouts
		title = (TextView)view.findViewById(R.id.calendar_article_title);
		desc = (TextView)view.findViewById(R.id.calendar_article_desc);
		time = (TextView)view.findViewById(R.id.calendar_article_time);
		location = (TextView)view.findViewById(R.id.calendar_article_location);
		contact = (TextView)view.findViewById(R.id.calendar_article_contact);
		date = (TextView)view.findViewById(R.id.calendar_article_date);
		
		//sets all the text in the textviews
		title.setText(article.title);
		desc.setText(article.desc);
		time.setText(article.time);
		location.setText(article.location);
		contact.setText(article.contact);
		date.setText(article.date);
		return view;
	}

	@Override
	public void onStart(){
		super.onStart();	
		
	}

	@Override
	public void onStop() {
		super.onStop();
	}

}

