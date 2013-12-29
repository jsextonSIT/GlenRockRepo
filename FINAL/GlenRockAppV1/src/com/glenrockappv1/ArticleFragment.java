package com.glenrockappv1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
/* Fragment for displaying a single news article
 * 
 */
public class ArticleFragment extends SherlockFragment {
	private Article article;
	private TextView date;
	private TextView title;
	private TextView text;
	/* input: article to be displayed
	 * output: ArticleFragment with the article attached as an argument
	 */
	static ArticleFragment newInstance(Article item){
		ArticleFragment fragment = new ArticleFragment();
		Bundle args = new Bundle();
		args.putParcelable("article", item);
		fragment.setArguments(args);
		return fragment;
	}
	/* 
	 * input: inflater(inflates layout), viewgroup, bundle
	 * output: A view from the inflated fragment_article layout
	 * oncreateview is a generic android function called once to create the view of the fragment
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_article,container, false);
		//get the article to be displayed from the arguments
		article = (Article) getArguments().getParcelable("article");
		//save all parts of data from the article
		title = (TextView)view.findViewById(R.id.article_title);
		text = (TextView)view.findViewById(R.id.article_text);
		if((date = (TextView)view.findViewById(R.id.article_date)) != null){
			date.setText(article.dateAdded);
		}
		title.setText(article.title);
		text.setText(article.text);
		return view;
	}

}

