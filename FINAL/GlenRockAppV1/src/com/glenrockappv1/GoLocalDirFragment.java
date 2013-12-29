package com.glenrockappv1;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
/* Fragment for displaying directory of businesses for a certain category
 */
public class GoLocalDirFragment extends SherlockListFragment{
	private ArrayList<String> businessNames;
	private ArrayList<String> businessPhones;
	private ArrayList<String> businessAddresses;
	private ArrayList<String> businessWebsites;
	private String textViewString;
	private int goLocalPosition;
	public GoLocalDirListAdapter adapter;
	/* Constructor - initializes a fragment
	 * input/output: void
	 */
	public GoLocalDirFragment(){
		super();
	}
	/* Sets arguments to bundle if needed
	 * Input: Bundle
	 * Output: void
	 */
	public void setArguments(Bundle b){
		super.setArguments(b);
		goLocalPosition = b.getInt("position");
	}
	/* Generic android func for inflating go_local_dir_fragment layout
	 * Input: LayoutInflater, ViewGroup, and Bundle of previous saved instance (if there is one)
	 * Output: Inflated view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.go_local_dir_fragment, container, false);
		
		return view;
	}
	/* Generic android func: called once when fragment created and again when paused/resumed
	 * input/output: void
	 */
	public void onStart(){
		super.onStart();
		businessNames = ((MainActivity)getActivity()).getGoLocalBusinessNames();
		businessPhones = ((MainActivity)getActivity()).getGoLocalPhoneNumbers();
		businessAddresses = ((MainActivity)getActivity()).getGoLocalAddresses();
		businessWebsites = ((MainActivity)getActivity()).getGoLocalWebsites();

		adapter = new GoLocalDirListAdapter(getActivity(), businessNames, businessPhones, businessAddresses, businessWebsites);
		setListAdapter(adapter);
	}
	/*Inner class derived from ArrayAdapter<String> which populates TextViews inside the fragment*/
	private class GoLocalDirListAdapter extends ArrayAdapter<String>{
		private Context context;
		private ArrayList<String> BusinessNames;
		private ArrayList<String> BusinessPhones;
		private ArrayList<String> BusinessAddresses;
		private ArrayList<String> BusinessWebsites;
		
		/* Constructor that sets ArrayLists and context
		 * Input: Context, 4 ArrayLists representing business names, phone numbers, addresses, and websites (respectively) from the current
		 * 			business category.
		 * Output: void
		 */
		public GoLocalDirListAdapter(Context context, ArrayList<String> bn, ArrayList<String> bp, ArrayList<String> ba, ArrayList<String> bw) {
			super(context, R.layout.go_local_button_fragment_list_item, bn);
			
			BusinessNames = bn;
			BusinessPhones = bp;
			BusinessAddresses = ba;
			BusinessWebsites = bw;
			
			this.context = context;
		}
		/* Sets up TextViews and returns View
		 * Input: Integer position, View, ViewGroup
		 * Output: View
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.go_local_dir_fragment_list_item, parent, false);
			LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.go_local_dir_item_ll);
			
			TextView nametv, sitetv, phonetv, addresstv;
			
			nametv = (TextView) convertView.findViewById(R.id.BusinessNameTextView);
			addresstv = (TextView) convertView.findViewById(R.id.BusinessAddressTextView);
			phonetv = (TextView) convertView.findViewById(R.id.BusinessPhoneTextView);
			sitetv = (TextView) convertView.findViewById(R.id.BusinessWebsiteTextView);
			
			nametv.setText(BusinessNames.get(position));
			addresstv.setText(BusinessAddresses.get(position));
			phonetv.setText(BusinessPhones.get(position));
			sitetv.setText(BusinessWebsites.get(position));
			
			return convertView;
		}
	}
	
}
