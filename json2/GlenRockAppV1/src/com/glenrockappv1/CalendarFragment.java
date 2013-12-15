package com.glenrockappv1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

public class CalendarFragment extends SherlockFragment {

	public GregorianCalendar month, itemmonth;// calendar instances.
	public Context mContext;
	public CalendarAdapter adapter;// adapter instance
	public CalendarAdapter tmpAdapter;
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker
	ArrayList<String> event;
	LinearLayout rLayout;
	ArrayList<String> date;
	ArrayList<String> desc;
	GridView gridview;
	View vv;
	String lastgriddate;
	Utility u;
	boolean mAlreadyLoaded = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vv = inflater.inflate(R.layout.calendar ,container, false);
		if(container == null)
			Log.i("hello", "jajajajajaja");
		if (savedInstanceState == null && !mAlreadyLoaded) {
		    mAlreadyLoaded = true;

		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			lastgriddate = df.format(new Date());
		}
		return vv;
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		
	}
	public void onResume(){
		super.onResume();
		Locale.setDefault(Locale.US);
		
		
		Log.i("jasssss", lastgriddate.toString());
		mContext = ((MainActivity)getActivity()).getApplicationContext();
		rLayout = (LinearLayout) (((MainActivity)getActivity()).findViewById(R.id.text));
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();
		//RelativeLayout parentrl =(RelativeLayout) ((MainActivity)getActivity()).findViewById(R.id.calendar_frag_rl);
		//LayoutInflater gridInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = gridInflater.inflate(R.layout.calendar, null);
		gridview = (GridView) vv.findViewById(R.id.gridview);

		
		tmpAdapter = new CalendarAdapter(((MainActivity)getActivity()).getApplicationContext(), month, null);
		ArrayList<String> days = new ArrayList<String>(tmpAdapter.refreshDays());
		Log.i("Hello", days.toString());
		adapter = new CalendarAdapter(((MainActivity)getActivity()), month, days);
	
		if(gridview == null){
			Log.i("hello", "gridview null");
		}
		if(adapter == null){
			Log.i("hello", "adapter null");
		}


		//adapter.notifyDataSetChanged();
		
		Log.i("helllllllll", "" + adapter.getItem(4));
		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) ((MainActivity)getActivity()).findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) ((MainActivity)getActivity()).findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});
		refreshCalendar();
		RelativeLayout next = (RelativeLayout) ((MainActivity)getActivity()).findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// removing the previous view if added
				if (((LinearLayout) rLayout).getChildCount() > 0) {
					((LinearLayout) rLayout).removeAllViews();
				}
				
				
				//LEAVE THIS -------------------------
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString.get(position);
				
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
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				
				Log.i("griddate", selectedGridDate);
				lastgriddate = selectedGridDate;
				doJSONStuff(selectedGridDate);
				desc = null;

			}

		});

		gridview.setAdapter(adapter);
		doJSONStuff(lastgriddate);
	}
	public void doJSONStuff(String date){
		ArrayList<CalendarEventArticle> articles = new ArrayList<CalendarEventArticle>();
		JSONArray calJArray;
		JSONObject calJObject;
		//String calJString;
		PostJsonTask jTask = new PostJsonTask();
		try {
			
			
			String calJString = jTask.execute(date, "http://10.0.2.2/calendar.php").get();
			calJObject = new JSONObject(calJString);
			calJArray = calJObject.getJSONArray("calendar");
			
			//Log.v("MainActivity_jObject", newsJObject.toString());
			//Log.v("MainActivity_jArray", newsJArray.toString());
			JSONObject tmp;
			for (int i = 0; i < calJArray.length(); i++){
				tmp = calJArray.getJSONObject(i);
				String desc = tmp.getString("Event_Description");
				desc = desc.replaceAll("\\<.*?\\>", "");
				articles.add(new CalendarEventArticle(tmp.getInt("Event_ID"), tmp.getString("Event_Title"), desc, tmp.getString("Event_StartTime"), tmp.getString("Event_Contact"), tmp.getString("Event_Location"), tmp.getString("Event_Start")));
				
			}
			showCalendarEvents(articles);
		} catch (Exception e){
			Log.i("exceptioneeeeeeeeee", e.toString());
		}
	}
	
	private void showCalendarEvents(ArrayList<CalendarEventArticle> ceas){
		ListView lv = (ListView) vv.findViewById(R.id.calendar_short_event_list);
		
		CalendarShortEventListAdapter csela = new CalendarShortEventListAdapter((MainActivity)getActivity(), ceas);
		lv.setAdapter(csela);
	}
	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
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

	public void refreshCalendar() {
		TextView title = (TextView) ((MainActivity)getActivity()).findViewById(R.id.title);

		adapter.refreshDays();
		//adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items


		gridview.setAdapter(adapter);

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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
