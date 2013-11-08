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

public class HttpCall extends AsyncTask<Void, Void, Void> {

	HttpListener httpListener;
	String strUrl;
	String strEntity;
	ProgressDialog mDialog;
	Context context;
	String strresponse;

	public HttpCall(String strUrl, HttpListener requestListener,
			String strEntity, Context context) {

		this.strUrl = strUrl;
		this.httpListener = requestListener;
		this.strEntity = strEntity;
		this.context = context;
		mDialog = ProgressDialog.show(context, "Processing", "Processing");
		
		
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

			strresponse = Response;

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
		if (strresponse != null) {

			httpListener.onResponse(strresponse);

			mDialog.dismiss();

		}

		else {

			httpListener.onError();
		}

		super.onPostExecute(result);
	}

}
