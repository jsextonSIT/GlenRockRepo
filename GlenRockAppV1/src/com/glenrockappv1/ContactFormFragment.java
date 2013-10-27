package com.glenrockappv1;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;

public class ContactFormFragment extends SherlockFragment{
	private ArrayList<String> emailList;
	private ArrayList<String> nameList;
	private boolean emails;
	private Context context;
	private int contactPosition;
	
	public ContactFormFragment(){
		super();
	}
	
	public void setArguments(Bundle b){
		super.setArguments(b);
		contactPosition = b.getInt("position");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.contact_form_fragment, container, false);
		
		return view;
	}
	
	public void onStart(){
		super.onStart();
		emails = false;
		emailList = ((MainActivity)getActivity()).getEmailList();
		nameList = ((MainActivity)getActivity()).getContactButtonNames();
		if(emailList != null && !emailList.isEmpty()){
			emails = true;
		}
		
		EditText sendTo =(EditText) ((MainActivity)getActivity()).findViewById(R.id.sending_to_edittext);
		sendTo.setText(nameList.get(contactPosition));
	}
	
	public void sendEmail(View view){
		String email_subject = "";
		String email_body = "";
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL,  emailList);
		i.putExtra(Intent.EXTRA_SUBJECT, email_subject);
		i.putExtra(Intent.EXTRA_TEXT, email_body);
	}
}
