package com.glenrockappv1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity implements NewsListAdapter.ArticleSelectedListener, CalendarShortEventListAdapter.CalendarArticleSelectedListener, TrashShortEventListAdapter.TrashArticleSelectedListener{
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
	private int cFragment; //current fragment index, starting at 0, opens to 1(news)
	
	//Delete these later
	private ArrayList<String> stockNewsTitles;
	private ArrayList<String> stockNewsSnipps;
	private ArrayList<Article> stockNewsArticles;
	
	//JSON
	private String jString;
	private JSONObject jObject;
	private JSONArray jArray;
	
	//News Json
	private String newsJString;
	private JSONObject newsJObject;
	private JSONArray newsJArray;
	
	//Calendar:
	private ArrayList<CalendarEventArticle> calendarArticles;
	
	//Trash:
	private ArrayList<TrashEventArticle> trashArticles;
	
	//Go Local
	private ArrayList<String> goLocalBusinessNames;
	private ArrayList<String> goLocalPhoneNumbers;
	private ArrayList<String> goLocalAddresses;

	//Contact page
	private ArrayList<String> contactButtonNames;
	private ArrayList<String> emailList;
	private ArrayList<String> phoneList;
	private int[] NoneOrPhoneOrEmailOrBoth;
	
	private SharedPreferences.Editor spe;
	private SharedPreferences sp;
	private String SP_NAME;
	private Resources res;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);
		//RESOURCES AND SHARED PREFERENCES
		res = getResources();
		SP_NAME = res.getString(R.string.SP_NAME);
		sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		spe = sp.edit();
		
		goLocalBusinessNames = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.go_local_leisure_business_names)));
		//this is hacky ^ we need to fix goLocalDirFragment because it crashes if this is null. 
		
		//NEWS
		//Get news JSON
//		GetJsonTask newsJTask = new GetJsonTask();
//		try {
//			newsJString = newsJTask.execute("http://10.0.2.2/news.php").get();
//			newsJObject = new JSONObject(jString);
//			newsJArray = newsJObject.getJSONArray("news");
//			
//			Log.v("MainActivity_newsJObject", newsJObject.toString());
//			Log.v("MainActivity_newsJArray",  newsJArray.toString());
//		} catch (Exception e) {
//			Log.e("MainActivity_GetJsonTask", e.toString());
//		}
		
		
		stockNewsTitles = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.stock_news_headers)));
		stockNewsSnipps = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.stock_news_fillers)));
		stockNewsArticles = new ArrayList<Article>();
		Article temp;
		for (int i = 0; i < 5; i++) {
			temp = new Article(i, stockNewsTitles.get(i), stockNewsSnipps.get(i));
			stockNewsArticles.add(temp);
		}
//		//GETTIN DAT JSON
//		GetJsonTask jTask = new GetJsonTask();
//		try {
//			jString = jTask.execute("http://10.0.2.2/banking.php").get();
//			jObject = new JSONObject(jString);
//			//jArray = jObject.getJSONArray("banking");
//
//			Log.v("MainActivity_jObject", jObject.toString());
//			Log.v("MainActivity_jArray", jArray.toString());
//		} catch (Exception e){
//			Log.e("MainActivity_jTask", e.toString());
//		}
		
//		//CREATING JSON OBJECT
//		try {
//			JSONObject jObject = new JSONObject(result);
//			Log.v("GetJsonTask_jObject", jObject.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Log.v("GetJsonTask", "exception making json object: " + e.toString());
//		}
		
		//CALENDAR
		calendarArticles = new ArrayList<CalendarEventArticle>();
		CalendarEventArticle tempcea;
		for (int i = 0; i < 5; i++){
			tempcea = new CalendarEventArticle(i, "title" + i, res.getString(R.string.lorum) + i, "time" + i, "contact" + i, "location" + i, "date" + i);
			calendarArticles.add(tempcea);
		}
		//TRASH
		trashArticles = new ArrayList<TrashEventArticle>();
		TrashEventArticle temptea;
		for (int i = 0; i < 5; i++){
			temptea = new TrashEventArticle(i, "title" + i, res.getString(R.string.lorum) + i, "time" + i, "contact" + i, "location" + i, "date" + i);
			trashArticles.add(temptea);
		}
		
		//GOLOCAL
		goLocalButtonNames = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.go_local_button_names)));
		
		//CONTACT
		NoneOrPhoneOrEmailOrBoth = res.getIntArray(R.array.PhoneOrEmailOrBoth);
		contactButtonNames = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact_button_names)));
		phoneList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact_phone_list)));
		
		//SHERLOCK
		fragTitles = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.navigation_array)));
		navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navDrawerList = (ListView) findViewById(R.id.left_drawer);
		navDrawerList.setAdapter(new NavListAdapter(this, fragTitles));
		navDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		getSupportActionBar().setHomeButtonEnabled(true);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null){
			cFragment = 1; //set fragment as news
		} else {
			//get fragment number from savedinstancestate to display saved page
			cFragment = savedInstanceState.getInt("cFragment");
		}
		navDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				navDrawerLayout,         /* DrawerLayout object */
				R.drawable.menuicon,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				);
		navDrawerLayout.setDrawerListener(navDrawerToggle);
		
		
	}
	public ArrayList<String> getStockNewsSnipps(){
		return stockNewsSnipps;
	}
	public ArrayList<String> getStockNewsTitles(){
		return stockNewsTitles;
	}
	public ArrayList<Article> getStockNewsArticles() {
		return stockNewsArticles;
	}
	public ArrayList<CalendarEventArticle> getCalendarEventArticles() {
		return calendarArticles;
	}
	public ArrayList<TrashEventArticle> getTrashEventArticles() {
		return trashArticles;
	}
	public ArrayList<String> getEmailList(){
		return emailList;
	}
	public ArrayList<String> getPhoneList(){
		return phoneList;
	}
	public int getNoneOrPhoneOrEmailOrBoth(int i){
		return NoneOrPhoneOrEmailOrBoth[i];
	}
	
	public JSONArray getGoLocalJSON(){
		return jArray;
	}
	
	public ArrayList<String> getContactButtonNames(){
		return contactButtonNames;
	}
	public ArrayList<String> getGoLocalButtonNames(){
		return goLocalButtonNames;
	}
	public ArrayList<String> getGoLocalBusinessNames(){
		return goLocalBusinessNames;
	}
	public ArrayList<String> getGoLocalPhoneNumbers(){
		return goLocalPhoneNumbers;
	}
	public ArrayList<String> getGoLocalAddresses(){
		return goLocalAddresses;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getSupportMenuInflater().inflate(R.menu.home, menu);
		return false;
	}
	@Override
	public void onStart(){
		super.onStart();
		navPage(cFragment);
	}
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
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			navPage(position);
		}
	}
	private void navPage(int position) {
		if (true){
			navDrawerList.setItemChecked(position, true);
			fragmentManager = getSupportFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			switch(position) { 
			case 0:
				//Emergency
				break;
			case 1:
				//News
				NewsFragment hfragment = new NewsFragment();
				transaction.replace(R.id.fragment_container, hfragment, TAG_NEWS_FRAGMENT).addToBackStack("n");
				transaction.commit();
				break;
			case 2:
				//Calendar
				CalendarFragment calfragment = new CalendarFragment();
				transaction.replace(R.id.fragment_container, calfragment, TAG_CALENDAR_FRAGMENT).addToBackStack("c");
				transaction.commit();
				break;
			case 3:
				//GoLocal
				GoLocalFragment glfragment = new GoLocalFragment();
				transaction.replace(R.id.fragment_container, glfragment, TAG_GO_LOCAL_BUTTONS_FRAGMENT).addToBackStack("gl");
				transaction.commit();
				break;
			case 4:
				//Trash
				TrashFragment trashfragment = new TrashFragment();
				transaction.replace(R.id.fragment_container,  trashfragment, TAG_TRASH_FRAGMENT).addToBackStack("t");
				transaction.commit();
				break;
			case 5:
				//Contact
				ContactFragment contact_fragment = new ContactFragment();
				transaction.replace(R.id.fragment_container, contact_fragment, TAG_CONTACT_BUTTONS_FRAGMENT).addToBackStack("c");
				transaction.commit();
				break;
			}
			this.setTitle(fragTitles.get(position));
			cFragment = position;
		}
		navDrawerLayout.closeDrawer(navDrawerList);
	}
	
	public void goLocalButtonFragmentNavigator(int position) {
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		GoLocalDirFragment go_local_dir_fragment = new GoLocalDirFragment();
		Bundle b = new Bundle();
		b.putInt("position", position);
		go_local_dir_fragment.setArguments(b);
		String phpurl;
		String json_key = "";
		
		switch(position){
		case 0:
			goLocalBusinessNames = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.go_local_leisure_business_names)));
			goLocalPhoneNumbers = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.go_local_leisure_business_phone_numbers)));
			goLocalAddresses = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.go_local_leisure_business_addresses)));
//			//break;
			json_key = "entertainment";
			break;
		case 1:
			json_key = "education";
			break;
		case 2:
		case 3:
			json_key = "construction";
			break;
		case 4:
			json_key = "medical";
			break;
		case 5:
			json_key = "housing";
			break;
		case 6:
			json_key = "banking";
			break;
		case 7:
			json_key = "retail";
			break;
		case 8:
			goLocalBusinessNames = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.go_local_leisure_business_names)));
			goLocalPhoneNumbers = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.go_local_leisure_business_phone_numbers)));
			goLocalAddresses = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.go_local_leisure_business_addresses)));
		
			json_key = "salons";
			break;
		case 9:
			json_key = "food";
			break;
		case 10:
			json_key = "proServices";
			break;
		
		}
		//GETTIN DAT JSON
		GetJsonTask jTask = new GetJsonTask();
		try {
			jString = jTask.execute("http://10.0.2.2/" + json_key + ".php").get();
			jObject = new JSONObject(jString);
			jArray = jObject.getJSONArray(json_key);

			Log.v("MainActivity_jObject", jObject.toString());
			Log.v("MainActivity_jArray", jArray.toString());
		} catch (Exception e){
			Log.e("MainActivity_jTask", e.toString());
		}
		
		transaction.replace(R.id.fragment_container, go_local_dir_fragment, TAG_GO_LOCAL_DIRECTORY_FRAGMENT).addToBackStack("goLocalDir");
		transaction.commit();
	}
	
	public void contactButtonFragmentNavigator(int position) {
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		ContactFormFragment contact_form_fragment = new ContactFormFragment();
		Bundle b = new Bundle();
		b.putInt("position", position);
		contact_form_fragment.setArguments(b);
		switch(position){
		case 0:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact0_emails)));
			break;
		case 1:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact1_emails)));
			break;
		case 2:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact2_emails)));
			break;
		case 3:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact3_emails)));
			break;
		case 4:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact4_emails)));
			break;
		case 5:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact5_emails)));
			break;
		case 6:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact6_emails)));
			break;
		case 7:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact7_emails)));
			break;
		case 8:
			emailList = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.contact8_emails)));
			break;
		}
		transaction.replace(R.id.fragment_container, contact_form_fragment, TAG_CONTACT_FORM_FRAGMENT).addToBackStack("contactform");
		transaction.commit();
	}
	
	public void calendarClickFragmentNavigator(int position){
		
	}
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("cFragment", cFragment);
	}
	
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
	@Override
	public void onArticleSelected(CalendarEventArticle item) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		SherlockFragment fragment = CalendarEventArticleFragment.newInstance(item);
		ft.replace(R.id.fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();
		
	}
	public void onArticleSelected(TrashEventArticle item) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		SherlockFragment fragment = TrashEventArticleFragment.newInstance(item);
		ft.replace(R.id.fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();
		
	}
}
