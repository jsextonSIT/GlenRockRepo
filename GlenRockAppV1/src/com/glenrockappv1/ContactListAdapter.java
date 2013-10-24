
package com.glenrockappv1;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactListAdapter extends ArrayAdapter<String> {
	Context context;
	public ContactListAdapter(Context context) {
		super(context, R.layout.contact_button_fragment_list_item);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.contact_button_fragment_list_item, parent, false);
		return convertView;
	}
}