package com.craftbeer.httpcall;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class HttpHit extends AsyncTask<Void, Void, Void> {

	HttpListener httpListener;
	String strUrl;
	String strEntity;

	String strresponse;

	ProgressDialog mDialog;
Context context;
	public HttpHit(String strUrl, HttpListener requestListener, String strEntity,Context mContext) {
		this.strUrl = strUrl;
		this.httpListener = requestListener;
		this.strEntity = strEntity;
		this.context=mContext;
		mDialog = ProgressDialog.show(context, "", "Processing");
	}
	@Override
	protected Void doInBackground(Void... params) {
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(strUrl);
			StringEntity stringEntity = new StringEntity(strEntity);

			stringEntity.setContentType("application/atom+xml");
			httpPost.setEntity(stringEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			String Response = EntityUtils.toString(httpEntity);

			Log.i("Response", Response);
			if (Response != null) {

				strresponse = Response;

			}

			else {

				strresponse = "";
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	@Override
	protected void onPostExecute(Void result) {
		if (strresponse != null && !strresponse.equalsIgnoreCase("")) {
			httpListener.onResponse(strresponse);
			mDialog.dismiss();
		} else {
			Log.e("error", ":::error");
			mDialog.dismiss();
		}
		super.onPostExecute(result);
	}
}
