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
	private boolean show;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((MainActivity)getActivity()).showLoading();
		show = refreshNewsArticles();
		SystemClock.sleep(7000);
		if (show == true){
			((MainActivity)getActivity()).hideLoading();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, container, false);
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.news, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.refresh:
			((MainActivity) getActivity()).showLoading();
			refreshNewsArticles();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// Very hackish way to exchange this data, will find better solution
		// including data from mysql database
		// newsArticles = ((MainActivity) getActivity()).getStockNewsArticles();
		// initialize all nids to 0 since theyre unused right now
		if (show == true){
		adapter = new NewsListAdapter(getActivity(), newsArticles);
		// fill listview
		setListAdapter(adapter);
		}
	}
	//get the most up to date news from the glen rock website
	//return true and update list if retrieval successful
	//return false if unsuccessful
	public boolean refreshNewsArticles() {
		// NEWS
		newsArticles = new ArrayList<Article>();
		GetJsonTask newsJTask = new GetJsonTask();

		try {
			// this line ensures that the query will execute before moving
			// forward, the variable assignment is responsible
			newsJString = newsJTask.execute("http://10.0.2.2/news.php").get();
			if (newsJString != null) {
				// not null so connection was made, hide loading screen
				

				newsJObject = new JSONObject(newsJString);
				newsJArray = newsJObject.getJSONArray("news");

				// Log.v("MainActivity_newsJObject", newsJObject.toString());
				// Log.v("MainActivity_newsJArray", newsJArray.toString());
				Article temp;
				for (int i = 0; i < newsJArray.length(); i++) {
					temp = new Article(newsJArray.getJSONObject(i).getString(
							"newsID"),
							newsJArray.getJSONObject(i).getString("newsTitle")
									.replaceAll("\\<.*?\\>", ""), newsJArray
									.getJSONObject(i).getString("newsDesc")
									.replaceAll("\\<.*?\\>", ""), newsJArray
									.getJSONObject(i).getString("DateAdded"));
					// Log.i("article title", temp.title);
					newsArticles.add(temp);
				}
			} else {
				// is null so connection not made return false
				return false;
			}
		} catch (Exception e) {
			Log.e("MainActivity_jTask", e.toString());
			if (newsJArray == null) {
				Log.v("MainActivity_jTask", "jArray is null");
			} else if (newsJObject == null) {
				Log.v("MainActivity_jTask", "jObject is null");
			} else if (newsJString == null) {
				Log.v("MainActivity_jTask", "jString is null");
			} else if (newsArticles == null) {
				Log.v("MainActivity_jTask", "arraylist is null");
			}
		}
		((MainActivity) getActivity()).hideLoading();
		adapter = new NewsListAdapter(getActivity(), newsArticles);
		// fill listview
		setListAdapter(adapter);
		return true;
	}

	@Override
	public void onStop() {
		super.onStop();
	}

}
