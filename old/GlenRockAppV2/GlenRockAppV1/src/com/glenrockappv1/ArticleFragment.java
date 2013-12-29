package com.glenrockappv1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class ArticleFragment extends SherlockFragment {
	private Article article;
	private TextView date;
	private TextView title;
	private TextView text;
	static ArticleFragment newInstance(Article item){
		ArticleFragment fragment = new ArticleFragment();
		Bundle args = new Bundle();
		args.putParcelable("article", item);
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_article,container, false);
		article = (Article) getArguments().getParcelable("article");
		title = (TextView)view.findViewById(R.id.article_title);
		text = (TextView)view.findViewById(R.id.article_text);
		if((date = (TextView)view.findViewById(R.id.article_date)) != null){
			date.setText(article.dateAdded);
		}
		title.setText(article.title);
		text.setText(article.text);
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

