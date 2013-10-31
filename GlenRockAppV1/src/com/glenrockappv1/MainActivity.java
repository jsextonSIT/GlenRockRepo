package com.glenrockappv1;

import java.util.ArrayList;
import java.util.Arrays;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity{
	private final static String TAG_NEWS_FRAGMENT = "NEWS_FRAGMENT";
	private final static String TAG_CONTACT_BUTTONS_FRAGMENT = "CONTACT_BUTTONS_FRAGMENT";
	private final static String TAG_GO_LOCAL_BUTTONS_FRAGMENT = "GO_LOCAL_BUTTONS_FRAGMENT";
	private final static String TAG_GO_LOCAL_DIRECTORY_FRAGMENT = "GO_LOCAL_DIRECTORY_FRAGMENT";
	private ListView navDrawerList;
	private ArrayList<String> contactButtonNames;
	private ArrayList<String> goLocalButtonNames;
	private DrawerLayout navDrawerLayout;
	private ArrayList<String> fragTitles;
	private ActionBarDrawerToggle navDrawerToggle;
	private FragmentManager fragmentManager;
	private int cFragment; //current fragment index, starting at 0, opens to 1(news)
	
	//Delete these later
	private ArrayList<String> stockNewsTitles;
	private ArrayList<String> stockNewsSnipps;

	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragTitles = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.navigation_array)));
		stockNewsTitles = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.stock_news_headers)));
		stockNewsSnipps = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.stock_news_fillers)));
		contactButtonNames = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.contact_button_names)));
		goLocalButtonNames = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.go_local_button_names)));
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
	public ArrayList<String> getContactButtonNames(){
		return contactButtonNames;
	}
	public ArrayList<String> getGoLocalButtonNames(){
		return goLocalButtonNames;
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
		if (cFragment != position){
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
				transaction.replace(R.id.fragment_container, hfragment, TAG_NEWS_FRAGMENT);
				transaction.commit();
				break;
			case 3:
				//GoLocal
				GoLocalFragment glfragment = new GoLocalFragment();
				transaction.replace(R.id.fragment_container, glfragment, TAG_GO_LOCAL_BUTTONS_FRAGMENT);
				transaction.commit();
				break;
			case 5:
				//Contact
				ContactFragment contact_fragment = new ContactFragment();
				transaction.replace(R.id.fragment_container, contact_fragment, TAG_CONTACT_BUTTONS_FRAGMENT);
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
		switch(position){
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		}
		transaction.replace(R.id.fragment_container, go_local_dir_fragment, TAG_GO_LOCAL_DIRECTORY_FRAGMENT).addToBackStack("goLocalDir");
		transaction.commit();
	}
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("cFragment", cFragment);
	}
	

}
