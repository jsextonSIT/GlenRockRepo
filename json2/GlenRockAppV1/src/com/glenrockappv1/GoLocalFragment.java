package com.glenrockappv1;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockListFragment;
/*
 * Fragment that displays list of business categories for Local
 */
public class GoLocalFragment extends SherlockListFragment{
	public ArrayList<String> goLocalButtonNames;
	public GoLocalListAdapter adapter;
	
	/*
	 * Generic android func to inflate go_local_button_fragment view
	 * input: LayoutInflater, ViewGroup, Bundle of possible saved instance
	 * output: an inflated view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.go_local_button_fragment,container, false);

		return view;
	}
	/* Generic android func: called once when fragment created and again when paused/resumed
	 * input/output: void
	 */
	@Override
	public void onStart(){
		super.onStart();

		goLocalButtonNames = ((MainActivity)getActivity()).getGoLocalButtonNames();
		adapter = new GoLocalListAdapter(getActivity(), goLocalButtonNames);
		setListAdapter(adapter);
		
	}

	/* Generic android func, called when app is closed
	 * input/output: void
	 */
	@Override
	public void onStop() {
		super.onStop();
	}
	
	/* Inner adapter class responsible for the names/views of each individual button */
	private class GoLocalListAdapter extends ArrayAdapter<String>{
		private Context context;
		private ArrayList<String> buttonNames;
		/* Constructor for class
		 * input: Context, ArrayList of button names
		 * output: void (just sets up instance variables)
		 */
		public GoLocalListAdapter(Context context, ArrayList<String> goLocalButtonNames) {
			super(context, R.layout.go_local_button_fragment_list_item, goLocalButtonNames);
			this.context = context;
			buttonNames = goLocalButtonNames;
		}
		/*	Returns the view that it set up
		 * 	input: integer index of button,  View, and ViewGroup
		 * 	output: returns view
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.go_local_button_fragment_list_item, parent, false);
			Button b = (Button) convertView.findViewById(R.id.go_local_button1);
			LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.go_local_item_ll);
			b.setText(buttonNames.get(position));
			//
			b.setOnClickListener(new goLocalClickListener(position));
			return convertView;
		}
		//Inner class that handles event when user clicks on a category
		private class goLocalClickListener implements Button.OnClickListener{
			private int currentPosition;
			/* Constructor
			 * input: position of click
			 * output: void (justs sets instance variable currentPosition)
			 */
			public goLocalClickListener(int p){
				currentPosition = p;
			}
			/* Handles what to do when clicked
			 * input: View
			 * output: void
			 */
			public void onClick(View view){
				Log.i("goLocal", "" + currentPosition);
				((MainActivity) GoLocalFragment.this.getActivity()).goLocalButtonFragmentNavigator(currentPosition);
					
			}
		}
	}
}
