/***********
 * This class is for communicating information between all the fragments.
 */
package com.glenrockappv1;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity implements
NewsListAdapter.ArticleSelectedListener,
CalendarShortEventListAdapter.CalendarArticleSelectedListener,
TrashShortEventListAdapter.TrashArticleSelectedListener {
	private final static String TAG_NEWS_FRAGMENT = "NEWS_FRAGMENT";
	private final static String TAG_CALENDAR_FRAGMENT = "CALENDAR_FRAGMENT";
	private final static String TAG_TRASH_FRAGMENT = "TRASH_FRAGMENT";
	private final static String TAG_CONTACT_BUTTONS_FRAGMENT = "CONTACT_BUTTONS_FRAGMENT";
	private final static String TAG_GO_LOCAL_BUTTONS_FRAGMENT = "GO_LOCAL_BUTTONS_FRAGMENT";
	private final static String TAG_GO_LOCAL_DIRECTORY_FRAGMENT = "GO_LOCAL_DIRECTORY_FRAGMENT";
	private final static String TAG_CONTACT_FORM_FRAGMENT = "CONTACT_FORM_FRAGMENT";
	private ListView navDrawerList;
	Context context;
	private ArrayList<String> goLocalButtonNames;
	private DrawerLayout navDrawerLayout;
	private ArrayList<String> fragTitles;
	private ActionBarDrawerToggle navDrawerToggle;
	private FragmentManager fragmentManager;
	private int cFragment; // current fragment index, starting at 0, opens to
	// 1(news)

	// JSON
	private String jString;
	private JSONObject jObject;
	private JSONArray jArray;

	// News Json
	private String newsJString;
	private JSONObject newsJObject;
	private JSONArray newsJArray;
	private NewsFragment nFragment;
	// Calendar:
	private ArrayList<CalendarEventArticle> calendarArticles;

	// Trash:
	private ArrayList<TrashEventArticle> trashArticles;

	// Go Local
	private ArrayList<String> goLocalBusinessNames;
	private ArrayList<String> goLocalPhoneNumbers;
	private ArrayList<String> goLocalAddresses;
	private ArrayList<String> goLocalWebsites;

	// Contact page
	private ArrayList<String> contactButtonNames;
	private ArrayList<String> emailList;
	private ArrayList<String> phoneList;
	private int[] NoneOrPhoneOrEmailOrBoth;

	private SharedPreferences.Editor spe;
	private SharedPreferences sp;
	private String SP_NAME;
	private Resources res;

	// Debug for developers
	private boolean debug;
	// public view for loading
	private LinearLayout loadingLayout;
	private ArrayList<Article> newsArticles;
	private FrameLayout container;
	private ListView drawer;
	private TextView welcome;

	/* Generic android func called when app is started - sets up everything
	 * input: Bundle of previous state of app if there was any
	 * output: void
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		debug = true;

		setContentView(R.layout.activity_main);


		//if (debug) {
		//loadingLayout = (LinearLayout) findViewById(R.id.loading_layout);
		//loadingLayout.setVisibility(LinearLayout.VISIBLE);

		//welcome = (TextView) findViewById(R.id.welcome_text);
		//welcome.setVisibility(View.VISIBLE);
		//		} else {
		//			
		//		}
		// RESOURCES AND SHARED PREFERENCES
		res = getResources();
		SP_NAME = res.getString(R.string.SP_NAME);
		sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		spe = sp.edit();

		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null) {
			cFragment = 0; // set fragment as news
		} else {
			// get fragment number from savedinstancestate to display saved page
			cFragment = savedInstanceState.getInt("cFragment");
		}
		// SHERLOCK
		fragTitles = new ArrayList<String>(Arrays.asList(res
				.getStringArray(R.array.navigation_array)));
		navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navDrawerList = (ListView) findViewById(R.id.left_drawer);
		navDrawerList.setAdapter(new NavListAdapter(this, fragTitles));
		navDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		getSupportActionBar().setHomeButtonEnabled(true);
		navDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
				navDrawerLayout, /* DrawerLayout object */
				R.drawable.menuicon, /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open, /* "open drawer" description for accessibility */
				R.string.drawer_close /* "close drawer" description for accessibility */
				);
		navDrawerLayout.setDrawerListener(navDrawerToggle);
		//welcome.setVisibility(TextView.INVISIBLE);

	}

	/*************
	 * Accessor methods
	 *****/
	public ArrayList<Article> getNewsArticles() {
		return newsArticles;
	}

	public ArrayList<CalendarEventArticle> getCalendarEventArticles() {
		return calendarArticles;
	}

	public ArrayList<TrashEventArticle> getTrashEventArticles() {
		return trashArticles;
	}

	public ArrayList<String> getEmailList() {
		return emailList;
	}

	public ArrayList<String> getPhoneList() {
		return phoneList;
	}

	public int getNoneOrPhoneOrEmailOrBoth(int i) {
		return NoneOrPhoneOrEmailOrBoth[i];
	}

	public JSONArray getGoLocalJSON() {
		return jArray;
	}

	public ArrayList<String> getContactButtonNames() {
		return contactButtonNames;
	}

	public ArrayList<String> getGoLocalButtonNames() {
		return goLocalButtonNames;
	}

	public ArrayList<String> getGoLocalBusinessNames() {
		return goLocalBusinessNames;
	}

	public ArrayList<String> getGoLocalPhoneNumbers() {
		return goLocalPhoneNumbers;
	}

	public ArrayList<String> getGoLocalAddresses() {
		return goLocalAddresses;
	}

	public ArrayList<String> getGoLocalWebsites() {
		return goLocalWebsites;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getSupportMenuInflater().inflate(R.menu.home, menu);
		return false;
	}

	public void getData(){

	}
	@Override
	/************
	 * Precondition: called when the android system “starts” the app - although this can happen for example when a phone call ends and the app is “restarted”.
	 * Postcondition: sets up all the array lists of information for the different fragments.
	 */
	public void onStart() {
		super.onStart();
		if (!isOnline()){
			TextView loadingText = (TextView) findViewById(R.id.loading_text);
			loadingText.setText("Please Connect to the Internet and restart");
			Toast.makeText(getApplicationContext(), "Network Connection Needed",
					Toast.LENGTH_LONG).show();
			return;
		}




		// CALENDAR
		calendarArticles = new ArrayList<CalendarEventArticle>();
		CalendarEventArticle tempcea;
		for (int i = 0; i < 5; i++) {
			tempcea = new CalendarEventArticle(i, "title" + i,
					res.getString(R.string.lorum) + i, "time" + i, "contact"
							+ i, "location" + i, "date" + i);
			calendarArticles.add(tempcea);
		}
		// TRASH
		trashArticles = new ArrayList<TrashEventArticle>();
		TrashEventArticle temptea;
		for (int i = 0; i < 5; i++) {
			temptea = new TrashEventArticle(i, "title" + i,
					res.getString(R.string.lorum) + i, "time" + i, "contact"
							+ i, "location" + i, "date" + i);
			trashArticles.add(temptea);
		}

		// GOLOCAL
		goLocalBusinessNames = new ArrayList<String>();
		goLocalAddresses = new ArrayList<String>();
		goLocalPhoneNumbers = new ArrayList<String>();
		goLocalWebsites = new ArrayList<String>();
		goLocalButtonNames = new ArrayList<String>(Arrays.asList(res
				.getStringArray(R.array.go_local_button_names)));

		// CONTACT
		NoneOrPhoneOrEmailOrBoth = res.getIntArray(R.array.PhoneOrEmailOrBoth);
		contactButtonNames = new ArrayList<String>(Arrays.asList(res
				.getStringArray(R.array.contact_button_names)));
		phoneList = new ArrayList<String>(Arrays.asList(res
				.getStringArray(R.array.contact_phone_list)));

		//change layout to transition from loading to fragments
		loadingLayout = (LinearLayout) findViewById(R.id.loading_layout);
		container = (FrameLayout) findViewById(R.id.fragment_container);
		drawer = (ListView) findViewById(R.id.left_drawer);

		//elcome.setVisibility(View.INVISIBLE);
		if (refreshNewsArticles()){
			navPage(cFragment);
		}
	}
	/* method to hide the loading screen
	 * input: nothing
	 * output: nothing, sets visibilities of layouts to hide the loading
	 */
	public void hideLoading(){
		loadingLayout.setVisibility(TextView.GONE);
		container.setVisibility(View.VISIBLE);
		drawer.setVisibility(View.VISIBLE);
	}
	/* method to show the loading screen
	 * input: nothing
	 * output: nothing, sets visibilities of layouts to show the loading
	 */
	public void showLoading(){
		loadingLayout.setVisibility(TextView.VISIBLE);
		container.setVisibility(View.GONE);
		drawer.setVisibility(View.GONE);
		//welcome.setVisibility(View.INVISIBLE);
	}
	/* get the most up to date news from the glen rock website
	 * input: void
	 * output: boolean (true if successfully retrieved articles from server, false on fail)
	 */
	public boolean refreshNewsArticles() {
		// NEWS
		newsArticles = new ArrayList<Article>();
		GetJsonTask newsJTask = new GetJsonTask();
		//showLoading();
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
				Log.i("refreshNews", "Could not reach website");
				TextView loadingText = (TextView) findViewById(R.id.loading_text);
				loadingText.setText("Cannot reach server");
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
			return false;
		}
		hideLoading();
		return true;
	}
	/* Opens navigation drawer when clicking home icon
	 * input: menuitem which was selected
	 * output: true if the touch event was handled, false otherwise
	 */
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
	//handles clicking of items in the navigation drawer
	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {

		/* input: int as index of which option selected in navigation drawer
		 *output: nothing, calls navPage on the position to navigate to that fragment
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			navPage(position);
		}
	}

	/********
	 * Precondition: Given the position of which button on the menu the user clicked.
	 * Postcondition: Navigates to that fragment.
	 */
	private void navPage(int position) {
		if (true) {
			navDrawerList.setItemChecked(position, true);
			fragmentManager = getSupportFragmentManager();
			// fragmentManager.addOnBackStackChangedListener(getListener());
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			switch (position) {
			// case 0:
			// //Emergency
			// break;
			case 0:
				// News
				if (nFragment == null) {
					nFragment = new NewsFragment();
				}
				transaction.replace(R.id.fragment_container, nFragment,
						TAG_NEWS_FRAGMENT).addToBackStack("n");
				transaction.commit();
				break;
			case 1:
				// Calendar
				CalendarFragment calfragment = new CalendarFragment();
				transaction.replace(R.id.fragment_container, calfragment,
						TAG_CALENDAR_FRAGMENT).addToBackStack("c");
				transaction.commit();
				break;
			case 2:
				// GoLocal
				GoLocalFragment glfragment = new GoLocalFragment();
				transaction.replace(R.id.fragment_container, glfragment,
						TAG_GO_LOCAL_BUTTONS_FRAGMENT).addToBackStack("gl");
				transaction.commit();
				break;
			case 3:
				// Trash
				TrashFragment trashfragment = new TrashFragment();
				transaction.replace(R.id.fragment_container, trashfragment,
						TAG_TRASH_FRAGMENT).addToBackStack("t");
				transaction.commit();
				break;
			case 4:
				// Contact
				ContactFragment contact_fragment = new ContactFragment();
				transaction.replace(R.id.fragment_container, contact_fragment,
						TAG_CONTACT_BUTTONS_FRAGMENT).addToBackStack("c");
				transaction.commit();
				break;
			}
			this.setTitle(fragTitles.get(position));
			cFragment = position;
		}
		navDrawerLayout.closeDrawer(navDrawerList);
	}
	/* Makes request to populate data for businesses based on category
	input: position of click
	output: void
	 */
	public void goLocalButtonFragmentNavigator(int position) {
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		GoLocalDirFragment go_local_dir_fragment = new GoLocalDirFragment();
		Bundle b = new Bundle();
		b.putInt("position", position);
		go_local_dir_fragment.setArguments(b);
		String phpurl;
		String json_key = "";

		switch (position) {
		case 0:
			json_key = "entertainment";
			break;
		case 1:
			json_key = "education";
			break;
		case 2:
			json_key = "construction";
			break;
		case 3:
			json_key = "medical";
			break;
		case 4:
			json_key = "housing";
			break;
		case 5:
			json_key = "banking";
			break;
		case 6:
			json_key = "retail";
			break;
		case 7:
			json_key = "salons";
			break;
		case 8:
			json_key = "food";
			break;
		case 9:
			json_key = "proServices";
			break;

		}
		// clear all arraylists
		goLocalBusinessNames.clear();
		goLocalAddresses.clear();
		goLocalPhoneNumbers.clear();
		goLocalWebsites.clear();

		// GETTIN DAT JSON
		GetJsonTask jTask = new GetJsonTask();
		try {
			jString = jTask.execute("http://glenrocknj.net/" + json_key + ".php")
					.get();
			jObject = new JSONObject(jString);
			jArray = jObject.getJSONArray(json_key);

			Log.v("MainActivity_jObject", jObject.toString());
			Log.v("MainActivity_jArray", jArray.toString());

			Log.v("MainActivity_jArray", "length = " + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {
				goLocalBusinessNames.add(jArray.getJSONObject(i).getString(
						"Business_Name"));
				goLocalAddresses.add(jArray.getJSONObject(i).getString(
						"Business_Address"));
				goLocalPhoneNumbers.add(jArray.getJSONObject(i).getString(
						"Business_Phone"));
				goLocalWebsites.add(jArray.getJSONObject(i).getString(
						"Business_Website"));
			}
		} catch (Exception e) {
			Log.e("MainActivity_jTask", e.toString());
			if (jArray == null) {
				Log.v("MainActivity_jTask", "jArray is null");
			} else if (jObject == null) {
				Log.v("MainActivity_jTask", "jObject is null");
			} else if (jString == null) {
				Log.v("MainActivity_jTask", "jString is null");
			} else if (goLocalBusinessNames == null || goLocalAddresses == null
					|| goLocalPhoneNumbers == null || goLocalWebsites == null) {
				Log.v("MainActivity_jTask", "arraylist is null");
			}
		}

		transaction.replace(R.id.fragment_container, go_local_dir_fragment,
				TAG_GO_LOCAL_DIRECTORY_FRAGMENT).addToBackStack("goLocalDir");
		transaction.commit();
	}
	/* Displays appropriate contact information when contact button is clicked
	 * input: position of click
	 * output: void
	 */
	public void contactButtonFragmentNavigator(int position) {
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		ContactFormFragment contact_form_fragment = new ContactFormFragment();
		Bundle b = new Bundle();
		b.putInt("position", position);
		contact_form_fragment.setArguments(b);
		switch (position) {
		case 0:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact0_emails)));
			break;
		case 1:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact1_emails)));
			break;
		case 2:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact2_emails)));
			break;
		case 3:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact3_emails)));
			break;
		case 4:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact4_emails)));
			break;
		case 5:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact5_emails)));
			break;
		case 6:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact6_emails)));
			break;
		case 7:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact7_emails)));
			break;
		case 8:
			emailList = new ArrayList<String>(Arrays.asList(res
					.getStringArray(R.array.contact8_emails)));
			break;
		}
		transaction.replace(R.id.fragment_container, contact_form_fragment,
				TAG_CONTACT_FORM_FRAGMENT).addToBackStack("contactform");
		transaction.commit();
	}

	public void calendarClickFragmentNavigator(int position) {

	}
	/*  Saves the instancestate to be redisplayed on restart
	 * input: bundle including the saved instance state
	 * output: nothing
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("cFragment", cFragment);
	}
	/* returns nothing, brings a new article fragment to front
	 * input: article to be displayed
	 * output: nothing, displays article
	 */
	@Override
	public void onArticleSelected(Article item) {
		//Log.i("article title", item.title);
		//Log.i("article text", item.text);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		SherlockFragment fragment = ArticleFragment.newInstance(item);
		ft.replace(R.id.fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}
	/* brings a calendararticle to the front
	 * input: calendareventarticle to be displayed
	 * output: nothing, displays a calendar event
	 */
	@Override
	public void onArticleSelected(CalendarEventArticle item) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		SherlockFragment fragment = CalendarEventArticleFragment
				.newInstance(item);
		ft.replace(R.id.fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}
	/***********
	 * Precondition: receives a TrashEventArticle
	 * Postcondition: Sets up the display page for that article
	 */
	public void onArticleSelected(TrashEventArticle item) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		SherlockFragment fragment = TrashEventArticleFragment.newInstance(item);
		ft.replace(R.id.fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}
	/* returns true if a connection can be made to internet, false otherwise
	 * input: nothing
	 * output: boolean
	 */
	public boolean isOnline(){
		ConnectivityManager CManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo NInfo = CManager.getActiveNetworkInfo();
		if (NInfo != null && NInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	/*
	 * private OnBackStackChangedListener getListener(){
	 * OnBackStackChangedListener result = new OnBackStackChangedListener(){
	 * public void onBackStackChanged(){ FragmentManager manager =
	 * getSupportFragmentManager();
	 * 
	 * if (manager != null){ CalendarFragment currFrag =
	 * (CalendarFragment)manager.findFragmentById(R.id.calendar_frag_rl);
	 * 
	 * currFrag.onResume(); } } }; return result; }
	 */
}


