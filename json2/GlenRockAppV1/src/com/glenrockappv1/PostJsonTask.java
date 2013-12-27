/**********
 * This class is for calling the php script on the server WITH arguments. The "GetJsonTask" does not allow arguments, 
 * but arguments are necessary in the Calendar and Trash section, to get only events for a specific date.
 * Runs asynchronously from the code it is called in so that the app does not get shut down from inactivity by the Android system.
 */
package com.glenrockappv1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class PostJsonTask extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		ArrayList<NameValuePair> nvPairs = new ArrayList<NameValuePair>();
		nvPairs.add(new BasicNameValuePair("date", params[0]));
		InputStream inputStream = null;
		String result = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            System.out.println(nvPairs.toString());
            HttpPost httppost = new HttpPost(params[1]);
            httppost.setEntity(new UrlEncodedFormEntity(nvPairs));
            inputStream = null;
    		result = null;
         // Execute HTTP Post Request
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

            Log.i("postData", result);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally{}
        
        return result;
	}
	
}
