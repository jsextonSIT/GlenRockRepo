package com.glenrockappv1;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

}
