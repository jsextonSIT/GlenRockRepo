package com.glenrockappv1;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockListFragment;

public class GoLocalFragment extends SherlockListFragment{
	public ArrayList<String> goLocalButtonNames;
	public GoLocalListAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.go_local_button_fragment,container, false);

		return view;
	}

	@Override
	public void onStart(){
		super.onStart();

		goLocalButtonNames = ((MainActivity)getActivity()).getGoLocalButtonNames();
		adapter = new GoLocalListAdapter(getActivity(), goLocalButtonNames);
		Log.i("buttons", goLocalButtonNames.get(0));
		setListAdapter(adapter);
		
	}


	@Override
	public void onStop() {
		super.onStop();
	}
	private class GoLocalListAdapter extends ArrayAdapter<String>{
		private Context context;
		private ArrayList<String> buttonNames;
		public GoLocalListAdapter(Context context, ArrayList<String> goLocalButtonNames) {
			super(context, R.layout.go_local_button_fragment_list_item, goLocalButtonNames);
			this.context = context;
			buttonNames = goLocalButtonNames;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.go_local_button_fragment_list_item, parent, false);
			Button b = (Button) convertView.findViewById(R.id.go_local_button1);
			LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.go_local_item_ll);
			b.setText(buttonNames.get(position));
			Log.i("Button", "position");
			b.setOnClickListener(new Button.OnClickListener(){
				public void onClick(View view){
					
				}
			});
			return convertView;
		}
		private class goLocalClickListener implements Button.OnClickListener{
			private int currentPosition;
			public goLocalClickListener(int p){
				currentPosition = p;
			}
			public void onClick(View view){
				Log.i("goLocal", "" + currentPosition);
				((MainActivity) GoLocalFragment.this.getActivity()).goLocalButtonFragmentNavigator(currentPosition);
					
			}
		}
	}
}
