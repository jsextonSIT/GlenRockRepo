
package com.glenrockappv1;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactListAdapter extends ArrayAdapter<String> {
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
		LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.contact_item_ll);
		b.setText(buttonNames.get(position));
		Log.i("Button", "position");
		b.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View view){
				
			}
		});
		return convertView;
	}
}