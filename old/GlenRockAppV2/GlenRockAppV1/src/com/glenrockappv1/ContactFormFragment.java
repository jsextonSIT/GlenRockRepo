package com.glenrockappv1;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockFragment;

public class ContactFormFragment extends SherlockFragment{
	private ArrayList<String> emailList;
	private ArrayList<String> nameList;
	private boolean emails;
	private Context context;
	private int contactPosition;
	private SharedPreferences sp;
	private SharedPreferences.Editor spe;
	private String SP_NAME;
	private Resources res;
	private LayoutInflater infl;
	private View ov;
	public ContactFormFragment(){
		super();
	}
	
	public void setArguments(Bundle b){
		super.setArguments(b);
		contactPosition = b.getInt("position");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ov = inflater.inflate(R.layout.contact_form_fragment, container, false);
		infl = inflater;
		return ov;
	}
	
	public void onStart(){
		super.onStart();
		context = ((MainActivity)getActivity()).getApplicationContext();
		emailList = ((MainActivity)getActivity()).getEmailList();
		nameList = ((MainActivity)getActivity()).getContactButtonNames();
		res = ((MainActivity)getActivity()).getResources();
		SP_NAME = res.getString(R.string.SP_NAME);
		sp = ((MainActivity)getActivity()).getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		spe = sp.edit();
		String prevName = sp.getString("name", "");
		Log.i("Aaaaaaaaaaa", prevName + "a");
		String prevEmail = sp.getString("email", "");
		String prevPhone = sp.getString("phone", "");
		if(emailList != null && !emailList.isEmpty()){
			emails = true;
		}
		
		TextView sendTo =(TextView) ((MainActivity)getActivity()).findViewById(R.id.sending_to_textview);
		sendTo.setText(nameList.get(contactPosition));
		
		EditText returnEmailET = (EditText)((MainActivity)getActivity()).findViewById(R.id.ReturnEmailEditText);
		EditText returnPhoneET = (EditText)((MainActivity)getActivity()).findViewById(R.id.ReturnPhoneEditText);
		EditText nameET = (EditText)((MainActivity)getActivity()).findViewById(R.id.NameEditText);
		
		returnEmailET.setText(prevEmail);
		returnPhoneET.setText(prevPhone);
		nameET.setText(prevName);
		
		Button sendButton = (Button) ((MainActivity)getActivity()).findViewById(R.id.button1);
		sendButton.setOnClickListener(new SendButtonListener());
	}
	private class SendButtonListener implements View.OnClickListener{
		public void onClick(View view) {
			String[] emailStringArray = (String[]) emailList
					.toArray(new String[emailList.size()]);
			Toast toast;
			String email_subject = "";
			String email_body = "";
			EditText bodyET = (EditText) ((MainActivity)getActivity()).findViewById(R.id.BodyEditText);
			EditText returnEmailET = (EditText) ((MainActivity)getActivity()).findViewById(R.id.ReturnEmailEditText);
			EditText phoneNumberET = (EditText) ((MainActivity)getActivity()).findViewById(R.id.ReturnPhoneEditText);
			EditText nameET = (EditText) ((MainActivity)getActivity()).findViewById(R.id.NameEditText);

			email_body += "Message from:  ";
			email_body += nameET.getText();
			if (nameET.getText() == null || nameET.getText().equals("")) {
				toast = Toast.makeText(context, "No name entered",
						Toast.LENGTH_LONG);
				toast.show();
				return;
			}
			spe.putString("name", (String) nameET.getText().toString());
			Log.i("aaaa", (String) nameET.getText().toString());
			email_body += "\n";
			email_body += "Phone Number:  ";
			email_body += phoneNumberET.getText();
			if (phoneNumberET.getText() == null
					|| phoneNumberET.getText().equals("")) {
				toast = Toast.makeText(context, "No phone entered",
						Toast.LENGTH_LONG);
				toast.show();
				return;
			}

			spe.putString("phone", (String) phoneNumberET.getText().toString());
			email_body += "\n";
			email_body += "Return email:  ";
			email_body += returnEmailET.getText();
			if (returnEmailET.getText() == null
					|| returnEmailET.getText().equals("")) {
				toast = Toast.makeText(context, "No email entered",
						Toast.LENGTH_LONG);
				toast.show();
				return;
			}
			spe.putString("email", (String) returnEmailET.getText().toString());
			email_body += "\n\n";
			email_body += "Message: \n\n";
			email_body += bodyET.getText();
			if (bodyET.getText() == null || bodyET.getText().equals("")) {
				toast = Toast.makeText(context, "No message entered",
						Toast.LENGTH_LONG);
				toast.show();
				return;
			}
			spe.commit();
			Log.i("email testing", email_body);

			// TODO: "Do you want to add your email to our mailing list?"

			if (sp.getBoolean("show_again", true)) {
				if(context == null){
					Log.i("contextnull", "nullllll");
				}
				View checkBoxView = infl.inflate(R.layout.email_newsletter_dialog, null);
				CheckBox checkBox = new CheckBox((MainActivity)getActivity());
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						if(isChecked){
							spe.putBoolean("show_again", false);
						}
						else{
							spe.putBoolean("show_again", true);
						}
						spe.commit();
						Log.i("spe boolean", "" +  sp.getBoolean("show_again", true));
					}
				});
				checkBox.setText("Never show again");

				MyDialog dialogClickListener = new MyDialog(emailStringArray, email_body, email_subject);
				
				

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
				builder.setMessage(
						"Would you like to add your email to our mailing list?")
						.setView(checkBox)
						.setPositiveButton("Yes", dialogClickListener)
						.setNegativeButton("No", dialogClickListener);
				builder.show();
			}

			
		}
		
	}
	public void sendEmail(String[] emailStringArray, String email_body, String email_subject){
		if (emailStringArray != null) {
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL, emailStringArray);
			i.putExtra(Intent.EXTRA_SUBJECT, email_subject);
			i.putExtra(Intent.EXTRA_TEXT, email_body);
			startActivity(Intent.createChooser(i, "Send Email"));
		}
	}
	private class MyDialog implements DialogInterface.OnClickListener{
		String[] esa;
		String eb, es;
		public MyDialog(String[] emailStringArray, String email_body, String email_subject){
			esa = emailStringArray;
			eb = email_body;
			es = email_subject;
		}
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				//Yes clicked
				sendEmail(esa, eb, es);
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// No button clicked
				sendEmail(esa, eb, es);
				break;
			}
		}
	}
}
