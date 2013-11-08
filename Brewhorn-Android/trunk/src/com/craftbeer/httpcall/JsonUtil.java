package com.craftbeer.httpcall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


public class JsonUtil extends AsyncTask<Void, Void, Void> {

	String url;
	String strresponse;
	ResponseListener mListener;
	Context mContext;
	static InputStream is = null;
	static JSONObject jObj = null;
	ProgressDialog mDialog;

	public JsonUtil(String url, ResponseListener mListener, Context mContext) {
		// TODO Auto-generated constructor stub
		this.mListener = mListener;
	
		this.url = url;
		this.mContext = mContext;
		mDialog = ProgressDialog.show(mContext, "", "Processing");
		
		
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		if (!strresponse.equalsIgnoreCase("")) {
			mListener.response(strresponse);
			mDialog.dismiss();
		} else {
			Log.e("error", ":::error");
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		// defaultHttpClient

		// mDialog.show();
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		HttpGet httpget = new HttpGet(url);
		try {
			response = httpClient.execute(httpget);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					strresponse = convertStreamToString(instream);

					instream.close();

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String convertStreamToString(InputStream instream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				instream));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();

	}

}
