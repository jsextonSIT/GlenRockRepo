package com.glenrockappv1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

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
	Utility u;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vv = inflater.inflate(R.layout.calendar ,container, false);
		if(container == null)
			Log.i("hello", "jajajajajaja");
		return vv;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Locale.setDefault(Locale.US);
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
				desc = new ArrayList<String>();
				date = new ArrayList<String>();
				
				
				
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
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				// ------------------------ LEAVE THIS
				/******
				for (int i = 0; i < u.startDates.size(); i++) {
					if (u.startDates.get(i).equals(selectedGridDate)) {
						desc.add(u.nameOfEvent.get(i));
					}
				}

				if (desc.size() > 0) {
					for (int i = 0; i < desc.size(); i++) {
						showCalendarEvents(new ArrayList<CalendarEventArticle>());
					}

				}
				*****/
				
				ArrayList<CalendarEventArticle> articles = new ArrayList<CalendarEventArticle>();
				
				//TODO: Create ArrayList of CalendarEventArticles, one for each event taking place on this date.
//				CalendarEventArticle article;
//				int artId;
//				String title, desc, location, contact, time, date;
//				while(there are still articles){
//					artId = ;
//					title = ;
//					desc = ;
//					location = ;
//					contact = ;
//					time = ;
//					date = ;
//					article = new CalendarEventArticle();
//				}
				

				desc = null;

			}

		});

		gridview.setAdapter(adapter);
		ArrayList<CalendarEventArticle> ceas = new ArrayList<CalendarEventArticle>();
		ceas = ((MainActivity)getActivity()).getCalendarEventArticles();
		showCalendarEvents(ceas);
		
		
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
			String itemvalue;
			event = Utility.readCalendarEvent(mContext);
			Log.d("=====Event====", event.toString());
			Log.d("=====Date ARRAY====", Utility.startDates.toString());

			for (int i = 0; i < Utility.startDates.size(); i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add(Utility.startDates.get(i).toString());
			}
			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
}
