package com.glenrockappv1;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;

public class NewsFragment extends SherlockListFragment {
	//future handle fragment navigation
	public ArrayList<Article> stockNewsArticles;
	public ArrayList<Integer> nid;
	public ArrayList<String> titles;
	public NewsListAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news,container, false);
		return view;
	}

	@Override
	public void onStart(){
		super.onStart();
		//Very hackish way to exchange this data, will find better solution including data from mysql database
		stockNewsArticles = ((MainActivity)getActivity()).getStockNewsArticles();
		//initialize all nids to 0 since theyre unused right now
		
		adapter = new NewsListAdapter(getActivity(), stockNewsArticles);
		//fill listview
		setListAdapter(adapter);
	}
	

	@Override
	public void onStop() {
		super.onStop();
	}

}

