package com.glenrockappv1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private JSONArray jArray;
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
		jArray = ((MainActivity)getActivity()).getGoLocalJSON();

		adapter = new GoLocalDirListAdapter(getActivity(), jArray, businessNames, businessPhones, businessAddresses, businessWebsites);
		setListAdapter(adapter);
	}
	private class GoLocalDirListAdapter extends ArrayAdapter<String>{
		private Context context;
		private ArrayList<String> BusinessNames;
		private ArrayList<String> BusinessPhones;
		private ArrayList<String> BusinessAddresses;
		private ArrayList<String> BusinessWebsites;
		private JSONArray JArray;
		
		public GoLocalDirListAdapter(Context context, JSONArray ja, ArrayList<String> bn, ArrayList<String> bp, ArrayList<String> ba, ArrayList<String> bw) {
			super(context, R.layout.go_local_button_fragment_list_item, bn);
			
			JArray = ja;
			
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
			
//			try {
//				JSONObject jObj = jArray.getJSONObject(position);
//				nametv.setText(jObj.getString("Business_Name"));
//				addresstv.setText(jObj.getString("Business_Address"));
//				phonetv.setText(jObj.getString("Business_Phone"));
//				sitetv.setText(jObj.getString("Business_Website"));
//			} catch (JSONException e) {
//				Log.e("GoLocalDirFragment", "error retreiving json" + e.toString());
//				e.printStackTrace();
//			}
//			
			nametv.setText(BusinessNames.get(position));
			addresstv.setText(BusinessAddresses.get(position));
			phonetv.setText(BusinessPhones.get(position));
			sitetv.setText(BusinessWebsites.get(position));
			return convertView;
		}
	}
	
}
