package com.glenrockappv1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
/* Fragment for displaying the list of news articles
 * Displays title and a small snippet of text from article
 */

public class NewsFragment extends SherlockListFragment {
	// future handle fragment navigation
	public ArrayList<Article> newsArticles;
	public ArrayList<Integer> nid;
	public ArrayList<String> titles;
	public NewsListAdapter adapter;
	// News Json
	private String newsJString;
	private JSONObject newsJObject;
	private JSONArray newsJArray;
	/* Generic android func
	 * input: bundle for information possibly saved from last run
	 * output: void
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//has the mainactivity show the loading screen
	}
	/* Generic android func for inflating the fragment_news layout
	 * input: inflater, viewgroup, bundle
	 * output: a inflated view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, container, false);
		//allows the refresh button to be displayed at top
		setHasOptionsMenu(true);
		return view;
	}
	/* Generic android func for inflating menu (inflates the refresh button)
	 * input: fragment's menu, inflater
	 * output: void
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.news, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	/* Generic android func for handling selection of menu items
	 * Only needed for refresh button
	 * input: menu item that was pressed
	 * output: boolean whether it was handled or not
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.refresh:
			((MainActivity)getActivity()).refreshNewsArticles();
			newsArticles = ((MainActivity) getActivity()).getNewsArticles();
			adapter = new NewsListAdapter(getActivity(), newsArticles);
			// fill listview
			setListAdapter(adapter);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	/* Generic android func: called once when fragment created and again when paused/resumed
	 * input/output: void
	 */
	@Override
	public void onStart() {
		super.onStart();
		newsArticles = ((MainActivity) getActivity()).getNewsArticles();
//		//if the most recent refresh of articles was successful, displays list of articles
		adapter = new NewsListAdapter(getActivity(), newsArticles);
		// fill listview
		setListAdapter(adapter);
	}

}
