package com.glenrockappv1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
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

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		refreshNewsArticles();
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
	    super.onCreateOptionsMenu(menu,inflater);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.refresh:
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
		//newsArticles = ((MainActivity) getActivity()).getStockNewsArticles();
		// initialize all nids to 0 since theyre unused right now

		adapter = new NewsListAdapter(getActivity(), newsArticles);
		// fill listview
		setListAdapter(adapter);
	}

	void refreshNewsArticles() {
		// NEWS
		newsArticles = new ArrayList<Article>();
		GetJsonTask newsJTask = new GetJsonTask();
		try {
			// this line ensures that the query will execute before moving
			// forward, the variable assignment is responsible
			newsJString = newsJTask.execute("http://10.0.2.2/news.php").get();
			newsJObject = new JSONObject(newsJString);
			newsJArray = newsJObject.getJSONArray("news");

			Log.v("MainActivity_newsJObject", newsJObject.toString());
			Log.v("MainActivity_newsJArray", newsJArray.toString());
			Article temp;
			for (int i = 0; i < newsJArray.length(); i++) {
				temp = new Article(newsJArray.getJSONObject(i).getString(
						"newsID"), newsJArray.getJSONObject(i).getString(
						"newsTitle").replaceAll(
								"\\<.*?\\>", ""), newsJArray.getJSONObject(i).getString(
						"newsDesc").replaceAll(
								"\\<.*?\\>", ""), newsJArray.getJSONObject(i).getString(
						"DateAdded"));
				// Log.i("article title", temp.title);
				newsArticles.add(temp);

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
		
		
		adapter = new NewsListAdapter(getActivity(), newsArticles);
		// fill listview
		setListAdapter(adapter);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

}
