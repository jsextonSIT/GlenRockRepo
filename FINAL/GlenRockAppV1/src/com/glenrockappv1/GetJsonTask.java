package com.glenrockappv1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
/*
 * Makes an HTTP request to Glen Rock's server on a new thread
 * Upon success, returns a JSON String containing data from the database which we use all over the app.
 */
public class GetJsonTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... urls){
		
		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpPost httppost = new HttpPost(urls[0]);
		InputStream inputStream = null;
		String result = null;
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 3000;
		int timeoutSocket = 5000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		httpclient.setParams(httpParameters);
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
			Log.i("4", "test");
			result = sb.toString();
			//Log.v("GetJsonTask", result);
		} catch (Exception e) { 
			// Oops
			Log.v("GetJsonTask", "expception thrown!!!!");
			Log.v("GetJsonTask", e.toString());
			e.getStackTrace();
		}
		finally {
			try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}
		return result;
	}

}
