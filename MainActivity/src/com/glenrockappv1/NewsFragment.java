package com.glenrockappv1;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;

public class NewsFragment extends SherlockListFragment {
	//future handle fragment navigation
	//	public interface EntitySelectedListener {
	//		public void onEntitySelected(String id, boolean type);
	//	}
	//EntitySelectedListener mListener;
	public ArrayList<String> stockNewsTitles;
	public ArrayList<String> stockNewsSnipps;
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
		//Very hackish way to exchange this data, will find better solution later
		stockNewsTitles = ((MainActivity)getActivity()).stockNewsTitles;
		stockNewsSnipps = ((MainActivity)getActivity()).stockNewsSnipps;
		//initialize all nids to 0 since theyre unused right now
		for (int i = 0; i < 6; i++){
			//nid.set(0,  0);
			Log.i("titles", stockNewsTitles.get(i));
		}
		adapter = new NewsListAdapter(getActivity(), stockNewsTitles, stockNewsSnipps, nid);
		//fill listview
		setListAdapter(adapter);
	}


	@Override
	public void onStop() {
		super.onStop();
	}

}

