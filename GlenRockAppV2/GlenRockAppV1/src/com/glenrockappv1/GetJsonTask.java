package com.glenrockappv1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class GetJsonTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... urls){
		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpPost httppost = new HttpPost(urls[0]);
		InputStream inputStream = null;
		String result = null;
		try {
		    HttpResponse response = httpclient.execute(httppost);           
		    HttpEntity entity = response.getEntity();

		    inputStream = entity.getContent();
		    // json is UTF-8 by default
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    while ((line = reader.readLine()) != null)
		    {
		        sb.append(line + "\n");
		    }
		    result = sb.toString();
		    Log.v("GetJsonTask", result);
		} catch (Exception e) { 
		    // Oops
			Log.v("GetJsonTask", "expceiton thrown1!!!!");
			Log.v("GetJsonTask", e.toString());
			e.getStackTrace();
		}
		finally {
		    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}
//		//CREATING JSON OBJECT
//		try {
//			JSONObject jObject = new JSONObject(result);
//			Log.v("GetJsonTask_jObject", jObject.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Log.v("GetJsonTask", "exception making json object: " + e.toString());
//		}
		return result;
	}


	
}
