package com.glenrockappv1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
//Comments for each function required
//Document 	-What it does
//			-What each paramter represents/does
//			-What each function call does
//Function names must be reasonable

public class MainActivity extends SherlockFragmentActivity implements
		NewsListAdapter.ArticleSelectedListener {
	private final static String TAG_NEWS_FRAGMENT = "NEWS_FRAGMENT";
	private final static String TAG_CONTACT_BUTTONS_FRAGMENT = "CONTACT_BUTTONS_FRAGMENT";
	private ListView navDrawerList;
	private ArrayList<String> contactButtonNames;
	private DrawerLayout navDrawerLayout;
	private ArrayList<String> fragTitles;
	private ActionBarDrawerToggle navDrawerToggle;
	private FragmentManager fragmentManager;
	private int cFragment; // current fragment index, starting at 0, opens to
							// 1(news)
	private String jsonResult;
	
	private JSONParser jParser;
	private String bankingUrl = "http://localhost/banking.php";
	private JSONObject bankingJson;
	private JSONArray bankingArray = null;
	
	// Delete these later
	private ArrayList<String> stockNewsTitles;
	private ArrayList<String> stockNewsSnipps;
	private ArrayList<Article> stockNewsArticles;
	private String url = "http://www.glenrocknj.net/";
	private String testurl = "jdbc:MySql://10.0.2.2:3306/databaseName";

	/*
	 * Where activity is initialized. Takes a bundle which contains saved data.
	 * Called on the creation of a activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		jParser = new JSONParser();
		bankingJson = jParser.getJSONFromUrl(bankingUrl);
		
		stockNewsArticles = new ArrayList<Article>();
		// titles of pages in nav drawer
		fragTitles = new ArrayList(Arrays.asList(getResources().getStringArray(
				R.array.navigation_array)));

		// stock news titles and text used for example (DELETE THESE LATER)
		stockNewsTitles = new ArrayList(Arrays.asList(getResources()
				.getStringArray(R.array.stock_news_headers)));
		stockNewsSnipps = new ArrayList(Arrays.asList(getResources()
				.getStringArray(R.array.stock_news_fillers)));
		// populate a stock article arraylist with data from arrays
		for (int i = 0; i < 5; i++) {
			Article temp = new Article(i, stockNewsTitles.get(i),
					stockNewsSnipps.get(i));
			stockNewsArticles.add(temp);
		}

		contactButtonNames = new ArrayList(Arrays.asList(getResources()
				.getStringArray(R.array.contact_button_names)));
		navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navDrawerList = (ListView) findViewById(R.id.left_drawer);
		navDrawerList.setAdapter(new NavListAdapter(this, fragTitles));
		navDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		getSupportActionBar().setHomeButtonEnabled(true);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null) {
			cFragment = -1; // set fragment as -1 to avoid check

		} else {
			// get fragment number from savedinstancestate to display saved page
			cFragment = savedInstanceState.getInt("cFragment");
		}
		navDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		navDrawerLayout, /* DrawerLayout object */
		R.drawable.menuicon, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		);
		navDrawerLayout.setDrawerListener(navDrawerToggle);
	}

	// Accessor method for returning an array of news articles
	public ArrayList<Article> getStockNewsArticles() {
		return stockNewsArticles;
	}

	// Accessor method for returning array of contact button names hard coded
	// into resources
	public ArrayList<String> getContactButtonNames() {
		return contactButtonNames;
	}

	// Inflates the menu; this adds items to the action bar if it is present.
	// Currently unused
	// Takes a menu object to inflate
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// getSupportMenuInflater().inflate(R.menu.home, menu);
		return false;
	}

	// Method used to start activity on creation and after being stopped
	@Override
	public void onStart() {
		super.onStart();
		navPage(1);// news
		// possibly get from website to set to 0 (emergency)
		// consider adding additional loading screen fragment
	}

	// Takes the selected menu item as a argument
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		switch (item.getItemId()) {
		case android.R.id.home:
			if (navDrawerLayout.isDrawerOpen(navDrawerList)) {
				navDrawerLayout.closeDrawer(navDrawerList);
			} else {
				navDrawerLayout.openDrawer(navDrawerList);
			}
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		// Called when a item is pressed in the navigation drawer
		// Takes a adapterview, view and position and id
		// position is the index of the
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			navPage(position);
		}
	}

	// navigates to the particular page chosen in the navigation drawer
	// takes a integer which is the index of the page chosen in the nav drawer
	// array
	private void navPage(int position) {
		if (cFragment != position) {
			navDrawerList.setItemChecked(position, true);
			fragmentManager = getSupportFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			switch (position) {
			case 0:
				// Emergency
				break;
			case 1:
				// News
				NewsFragment hfragment = new NewsFragment();
				transaction.replace(R.id.fragment_container, hfragment,
						TAG_NEWS_FRAGMENT);
				transaction.commit();
				break;
			case 5:
				// Contact
				ContactFragment contact_fragment = new ContactFragment();
				transaction.replace(R.id.fragment_container, contact_fragment,
						TAG_CONTACT_BUTTONS_FRAGMENT);
				transaction.commit();
				break;
			}
			this.setTitle(fragTitles.get(position));
			cFragment = position;
		}
		navDrawerLayout.closeDrawer(navDrawerList);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("cFragment", cFragment);
	}

	// Handle creation of article fragments from news fragment
	// Takes a article as a argument to be displayed in a new fragment
	// Called from the news fragment
	@Override
	public void onArticleSelected(Article item) {
		Log.i("article title", item.title);
		Log.i("article text", item.text);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		SherlockFragment fragment = ArticleFragment.newInstance(item);
		ft.replace(R.id.fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}

	// Asynchronous Task that gets data from glenrock mysql database
	private class NewsAsyncTask extends AsyncTask<String, Void, String> {
		//get
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}
		
		public void accessDatabase() {
			NewsAsyncTask task = new NewsAsyncTask();
			  // passes values for the urls string array
			  task.execute(new String[] { url });
			 }

		@Override
		protected void onPostExecute(String result) {
			// ListDrwaer();
		}
	}// end async task
}
