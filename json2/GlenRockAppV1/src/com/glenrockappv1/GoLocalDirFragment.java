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

public class GoLocalDirFragment extends SherlockListFragment{
	private ArrayList<String> businessNames;
	private ArrayList<String> businessPhones;
	private ArrayList<String> businessAddresses;
	private ArrayList<String> businessWebsites;
	private String textViewString;
	private int goLocalPosition;
	public GoLocalDirListAdapter adapter;
	public GoLocalDirFragment(){
		super();
	}
	public void setArguments(Bundle b){
		super.setArguments(b);
		goLocalPosition = b.getInt("position");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.go_local_dir_fragment, container, false);
		
		return view;
	}
	
	public void onStart(){
		super.onStart();
		businessNames = ((MainActivity)getActivity()).getGoLocalBusinessNames();
		businessPhones = ((MainActivity)getActivity()).getGoLocalPhoneNumbers();
		businessAddresses = ((MainActivity)getActivity()).getGoLocalAddresses();
		businessWebsites = ((MainActivity)getActivity()).getGoLocalWebsites();

		adapter = new GoLocalDirListAdapter(getActivity(), businessNames, businessPhones, businessAddresses, businessWebsites);
		setListAdapter(adapter);
	}
	private class GoLocalDirListAdapter extends ArrayAdapter<String>{
		private Context context;
		private ArrayList<String> BusinessNames;
		private ArrayList<String> BusinessPhones;
		private ArrayList<String> BusinessAddresses;
		private ArrayList<String> BusinessWebsites;
		
		public GoLocalDirListAdapter(Context context, ArrayList<String> bn, ArrayList<String> bp, ArrayList<String> ba, ArrayList<String> bw) {
			super(context, R.layout.go_local_button_fragment_list_item, bn);
			
			
			BusinessNames = bn;
			BusinessPhones = bp;
			BusinessAddresses = ba;
			BusinessWebsites = bw;
			
			this.context = context;
		}

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
