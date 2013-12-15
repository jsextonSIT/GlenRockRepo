package com.glenrockappv1;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

public class ContactFragment extends SherlockListFragment{
	public ArrayList<String> contactButtonNames;
	public ContactListAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.contact_button_fragment,container, false);

		return view;
	}

	@Override
	public void onStart(){
		super.onStart();

		contactButtonNames = ((MainActivity)getActivity()).getContactButtonNames();
		adapter = new ContactListAdapter(getActivity(), contactButtonNames);
		Log.i("buttons", contactButtonNames.get(0));
		setListAdapter(adapter);
		
	}


	@Override
	public void onStop() {
		super.onStop();
	}
	
	private class ContactListAdapter extends ArrayAdapter<String> {
		private Context context;
		private ArrayList<String> buttonNames;
		public ContactListAdapter(Context context, ArrayList<String> contactButtonNames) {
			super(context, R.layout.contact_button_fragment_list_item, contactButtonNames);
			this.context = context;
			buttonNames = contactButtonNames;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.contact_button_fragment_list_item, parent, false);
			Button b = (Button) convertView.findViewById(R.id.contact_button1);
			b.setText(buttonNames.get(position));
			Log.i("contact", "" + position);
			Log.i("Button", "position");
			b.setOnClickListener(new contactClickListener(position));
			return convertView;
		}
		private class contactClickListener implements Button.OnClickListener{
			private int currentPosition;
			private int displayOptions;
			private ArrayList<String> phoneNumbers;
			private Context context;
			private final CharSequence[] options = {"Email", "Call"};
			public contactClickListener(int p){
				currentPosition = p;
				phoneNumbers = ((MainActivity)getActivity()).getPhoneList();
				context = ((MainActivity)getActivity()).context;
				displayOptions = ((MainActivity)ContactFragment.this.getActivity()).getNoneOrPhoneOrEmailOrBoth(p);
			}
			public void onClick(View view){

				switch(displayOptions){
				case 0:
					break;
				case 1:
					Log.i("contact", "" + currentPosition);
		        	String s = "Please call the following phone number:\n";
		        	SpannableStringBuilder phonesb = new SpannableStringBuilder("Please call the following phone number:\n" + phoneNumbers.get(currentPosition));
		        	ForegroundColorSpan fcsBLUE = new ForegroundColorSpan(Color.rgb(0, 102, 255));
		        	ForegroundColorSpan fcsBLACK = new ForegroundColorSpan(Color.rgb(0, 0, 0));
		        	phonesb.setSpan(fcsBLACK, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		        	phonesb.setSpan(fcsBLUE, s.length(), s.length() + phoneNumbers.get(currentPosition).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		        	AlertDialog.Builder phoneDisplay = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		        	TextView phonetv = new TextView(context);
		        	phonetv.setText(phonesb);
		        	phonetv.setPadding(20, 10, 20, 10);
		        	phonetv.setTextSize(25);
		        	phoneDisplay.setView(phonetv);
		        	phoneDisplay.show();
					break;
				case 2:
					((MainActivity) ContactFragment.this.getActivity()).contactButtonFragmentNavigator(currentPosition);
					break;
				case 3:
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setItems(options, new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        if(which == 0){
								((MainActivity) ContactFragment.this.getActivity()).contactButtonFragmentNavigator(currentPosition);
					        }
					        else if (which == 1){
								Log.i("contact", "" + currentPosition);
					        	String s = "Please call the following phone number:\n";
					        	SpannableStringBuilder phonesb = new SpannableStringBuilder("Please call the following phone number:\n" + phoneNumbers.get(currentPosition));
					        	ForegroundColorSpan fcsBLUE = new ForegroundColorSpan(Color.rgb(0, 102, 255));
					        	ForegroundColorSpan fcsBLACK = new ForegroundColorSpan(Color.rgb(0, 0, 0));
					        	phonesb.setSpan(fcsBLACK, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
					        	phonesb.setSpan(fcsBLUE, s.length(), s.length() + phoneNumbers.get(currentPosition).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
					        	AlertDialog.Builder phoneDisplay = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
					        	TextView phonetv = new TextView(context);
					        	phonetv.setText(phonesb);
					        	phonetv.setPadding(20, 10, 20, 10);
					        	phonetv.setTextSize(25);
					        	phoneDisplay.setView(phonetv);
					        	phoneDisplay.show();
					        }
					    }
					});
					builder.show();
					break;
				}
					
			}
		}
	}

}
