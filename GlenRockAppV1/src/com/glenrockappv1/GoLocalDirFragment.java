package com.glenrockappv1;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GoLocalDirFragment extends SherlockFragment{
	private ArrayList<String> businessNames;
	private ArrayList<String> businessPhones;
	private ArrayList<String> businessAddresses;
	private String textViewString;
	private int goLocalPosition;
	
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
		textViewString = "";
		for(int i=0;i<businessNames.size();i++){
			textViewString += businessNames.get(i);
			textViewString += "\n";
			textViewString += businessPhones.get(i);
			textViewString += "\n";
			textViewString += businessAddresses.get(i);
			textViewString += "\n";
			textViewString += "\n";
		}
		
		
		TextView textView = (TextView) ((MainActivity)getActivity()).findViewById(R.id.glTextView);
		textView.setMovementMethod(new ScrollingMovementMethod());
		textView.setText(textViewString);
	}
	
}
