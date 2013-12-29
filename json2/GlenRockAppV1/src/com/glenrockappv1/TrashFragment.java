/**********
 * This class is for displaying the grid that the calendar is on in the trash section.
 */

package com.glenrockappv1;

import java.text.DateFormat;
/****
 * This class is for showing the calendar grid for the Trash section, and for setting what happens when a date is clicked.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;

//import android.app.Activity;
import android.content.Context;
//import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TrashFragment extends SherlockFragment {

	public GregorianCalendar month, itemmonth;// calendar instances.
	public Context mContext;
	public TrashAdapter adapter;// adapter instance
	public TrashAdapter tmpAdapter;
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker
	ArrayList<String> event;
	LinearLayout rLayout;
	//ArrayList<String> date;
	//ArrayList<String> desc;
	GridView gridview;
	View vv;
	Utility u;
	String lastgriddate;

	boolean mAlreadyLoaded = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vv = inflater.inflate(R.layout.calendar ,container, false);
		//if(container == null)
			//Log.i("hello", "jajajajajaja");
		
		if (savedInstanceState == null && !mAlreadyLoaded) {
		    mAlreadyLoaded = true;

		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			lastgriddate = df.format(new Date());
		}
		return vv;
	}
	
	@Override
	/***
	 * Precondition: called when app is "resumed" by android system - whenever screen is switched to another app
	 */
	public void onResume() {
		super.onResume();
		Locale.setDefault(Locale.US); // choose what time zone
		mContext = ((MainActivity)getActivity()).getApplicationContext(); 
		rLayout = (LinearLayout) (((MainActivity)getActivity()).findViewById(R.id.text));
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone(); //get a copy of the current month

		items = new ArrayList<String>();
		//RelativeLayout parentrl =(RelativeLayout) ((MainActivity)getActivity()).findViewById(R.id.calendar_frag_rl);
		//LayoutInflater gridInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = gridInflater.inflate(R.layout.calendar, null);
		gridview = (GridView) vv.findViewById(R.id.gridview); //the grid that the calendar is displayed with

		
		tmpAdapter = new TrashAdapter(((MainActivity)getActivity()).getApplicationContext(), month, null); //this is so that you can refresh days once before running everything
		ArrayList<String> days = new ArrayList<String>(tmpAdapter.refreshDays());
		//Log.i("Hello", days.toString());
		adapter = new TrashAdapter(((MainActivity)getActivity()), month, days);
	
		if(gridview == null){
			Log.i("hello", "gridview null");
		}
		if(adapter == null){
			Log.i("hello", "adapter null");
		}


		//adapter.notifyDataSetChanged();
		
		//Log.i("helllllllll", "" + adapter.getItem(4));
		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) ((MainActivity)getActivity()).findViewById(R.id.title); // the view that will display the month name
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month)); // sets the month and year as the title

		RelativeLayout previous = (RelativeLayout) ((MainActivity)getActivity()).findViewById(R.id.previous); //button to go to previous month
		

		previous.setOnClickListener(new OnClickListener() { //action for when previous button is pressed

			@Override
			public void onClick(View v) {
				setPreviousMonth(); //sets a new month and refreshes the grid to display the corresponding dates
				refreshCalendar();
			}
		});
		refreshCalendar();
		RelativeLayout next = (RelativeLayout) ((MainActivity)getActivity()).findViewById(R.id.next); // button to go to next month
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setNextMonth();//sets a new month and refreshes the grid to display the corresponding dates
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// removing the previous view if added
				if (((LinearLayout) rLayout).getChildCount() > 0) {
					((LinearLayout) rLayout).removeAllViews();
				}
				//desc = new ArrayList<String>();
				//date = new ArrayList<String>();
				
				
				
				//LEAVE THIS -------------------------
				((TrashAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = TrashAdapter.dayString.get(position);
				
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*","");
				int gridvalue = Integer.parseInt(gridvalueString);
				// taking last part of date, i.e. 2 from 2012-12-02.
				
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} 
				else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				else{
					
				}
				((TrashAdapter) parent.getAdapter()).setSelected(v);
				
				Log.i("griddate", selectedGridDate);
				lastgriddate = selectedGridDate;
				doJSONStuff(selectedGridDate);
				//desc = null;
			}

		});

		gridview.setAdapter(adapter); //the adapter lets each item be set individually
		doJSONStuff(lastgriddate); //getting trash events from the database
		
		
	}
	/*****
	 * Precondition: given a date in the format YYYY-MM-DD
	 * Postcondition: gets the first 7 database entries after the given date.
	 */
	public void doJSONStuff(String date){
		ArrayList<TrashEventArticle> articles = new ArrayList<TrashEventArticle>();
		JSONArray calJArray;
		JSONObject calJObject;
		//String calJString;
		PostJsonTask jTask = new PostJsonTask(); //allows the app to send arguments to php scripts
		try {
			
			
			String calJString = jTask.execute(date, "http://glenrocknj.net/trash.php").get(); //choosing the ip of the server
			calJObject = new JSONObject(calJString);
			calJArray = calJObject.getJSONArray("trash"); //name of the array in the json object
			
			//Log.v("MainActivity_jObject", newsJObject.toString());
			//Log.v("MainActivity_jArray", newsJArray.toString());
			JSONObject tmp;
			
			//getting all the things in the array and putting them in the list of articles to display
			for (int i = 0; i < calJArray.length(); i++){
				tmp = calJArray.getJSONObject(i); 
				String desc = tmp.getString("Event_Description");
				desc = desc.replaceAll("\\<.*?\\>", ""); //remove html tags from the descriptions
				articles.add(new TrashEventArticle(tmp.getInt("Event_Id"), tmp.getString("Event_Title"), desc, tmp.getString("Event_StartTime"), tmp.getString("Event_Contact"), tmp.getString("Event_Location"), tmp.getString("Event_Start")));
				//get all the needed fields for the database and put them into the list of articles
			}
			showTrashEvents(articles); //displays the events
		} catch (Exception e){
			Log.i("exceptioneeeeeeeeee", e.toString());
		}
	}

	/*******
	 * Precondition: Given a list of trash events
	 * Postcondition: Displays the list of trash events underneath the calendar
	 */
	private void showTrashEvents(ArrayList<TrashEventArticle> ceas){
		ListView lv = (ListView) vv.findViewById(R.id.calendar_short_event_list);
		
		TrashShortEventListAdapter csela = new TrashShortEventListAdapter((MainActivity)getActivity(), ceas);
		lv.setAdapter(csela); //takes the current list of trash events and displays them
	}
	
	/***************
	 * Postcondition: changes the month to the month that comes after the current month
	 */
	protected void setNextMonth() {
		
		//changes the month forward, going forward a year if necessary
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}
	/***************
	 * Postcondition: changes the month to the month that comes before the current month
	 */
	protected void setPreviousMonth() {
		
		//changes the month backward, going back a year if necessary
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	protected void showToast(String string) {
		Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), string, Toast.LENGTH_SHORT).show();

	}

	//to update the current dates
	public void refreshCalendar() {
		TextView title = (TextView) ((MainActivity)getActivity()).findViewById(R.id.title);

		adapter.refreshDays();
		//adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items


		gridview.setAdapter(adapter);

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	//run by the handler to update the dates in the grid to be correct for the current month
	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			//DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			//String itemvalue;
			//event = Utility.readCalendarEvent(mContext);
			//Log.d("=====Event====", event.toString());
			//Log.d("=====Date ARRAY====", Utility.startDates.toString());

			for (int i = 0; i < Utility.startDates.size(); i++) {
				//itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add(Utility.startDates.get(i).toString());
			}
			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
}
