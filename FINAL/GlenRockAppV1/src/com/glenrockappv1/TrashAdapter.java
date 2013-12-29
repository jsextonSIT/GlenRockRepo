/****************
 * This class is for showing each date in the grid. It has code for showing a single date in the grid, and
 * the code is applied to every date in the grid.
 */

package com.glenrockappv1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrashAdapter extends ArrayAdapter<String> {
	private Context mContext;

	private java.util.Calendar month;
	public GregorianCalendar pmonth; // calendar instance for previous month
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;
	String itemvalue, curentDateString;
	DateFormat df;

	private ArrayList<String> items;
	public static List<String> dayString;
	private View previousView;
// constructor
	public TrashAdapter(Context c, GregorianCalendar monthCalendar, ArrayList<String> days) {
		super(c, R.layout.calendar_item, days);
		Log.i("here here", "here");
		dayString = days;
		Locale.setDefault(Locale.US);
		month = monthCalendar;
		if (c == null || monthCalendar == null){
			Log.i("hello null", "something null");
		}
		
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		mContext = c;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		this.items = new ArrayList<String>();
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		curentDateString = df.format(selectedDate.getTime());
		if(days == null){
			dayString = new ArrayList<String>();
		}
		
		refreshDays();
	}
	
	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}
	
	/***
	 * The following two methods are necessary for the Android system to know that there are items to be displayed
	 */
	@Override
	public int getCount() {
		Log.i("hello", ""+dayString.size());
		return dayString.size();
	}
	@Override
	public String getItem(int position) {
		Log.i("hello", dayString.get(position));
		return dayString.get(position);
	}


	// create a new view for each item referenced by the Adapter
	@Override
	/***************
	 * Precondition: Given a position in the grid
	 * Postcondition: Displays the correct date at that position, with the appropriate color if it is in the current 
	 * month, or in an adjacent month
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView dayView;
		//LinearLayout ll = (LinearLayout) ((MainActivity)getActivity()).findViewById(R.id.calendar_ll)
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_item, parent, false);
			Log.i("convertviewwwwwwwwwwww"," ----------");
		} // this is for efficiency
		dayView = (TextView) v.findViewById(R.id.date);
				
		String[] separatedTime = dayString.get(position).split("-"); 	// separates daystring into parts.
		
		String gridvalue = separatedTime[2].replaceFirst("^0*", "");// taking last part of date. ie; 2 from 2012-12-02
		
		// checking whether the day is in current month or not.
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			// setting offdays to white color.
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else {
			// setting curent month's days in blue color.
			dayView.setTextColor(Color.BLUE);
		}

		if (dayString.get(position).equals(curentDateString)) {
			setSelected(v);
			previousView = v;
		} else {
			v.setBackgroundResource(R.drawable.list_item_background);
		}
		dayView.setText(gridvalue);

		// create date string for comparison
		String date = dayString.get(position);

		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
		ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		Log.i("itemss!", items.toString());
		if (date.length() > 0 && items != null && items.contains(date)) {
			Log.i("here?", "here?????????");
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.VISIBLE);
		}
		return v;

	}
/**
 * Postconditon: sets the correct layouts for the given item
 */
	public View setSelected(View view) {
		if (previousView != null) {
			previousView.setBackgroundResource(R.drawable.list_item_background);
		}
		Log.i("hello", "set selected called");
		previousView = view;
		view.setBackgroundResource(R.drawable.calendar_cel_selectl);
		return view;
	}
/****
 * Postcondition: refreshes the days of the month to be the correct day matching each date
 * 
 * */
	public List<String> refreshDays() {
		// clear items
		items.clear();
		dayString.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			dayString.add(itemvalue);

		}
		
		return dayString;
	}

	/****
	 * Postcondition: returns the last date of the current month (so for Feb, returns 28)
	 */
	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
		return maxP;
	}

	/****
	 * Necessary method for android to get id of item at a current position
	 */
	@Override
	public long getItemId(int position) {
		//Log.i("hrrrrrrrrrrrrrr", "" + position);
		return position;
	}

}