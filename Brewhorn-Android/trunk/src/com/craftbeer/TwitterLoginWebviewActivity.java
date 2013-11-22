package com.craftbeer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.craftbeer.constants.Constants;

public class TwitterLoginWebviewActivity extends Activity {
	private WebView webView;
	private String url;
	private WebviewClient webviewClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_webview);
		webView = (WebView) findViewById(R.id.webView1);
		webviewClient = new WebviewClient(new CallBack() {
			@Override
			public void onLoad(String url) {
				// TODO Auto-generated method stub
				Log.i("shouldOverrideUrlLoading", "shouldOverrideUrlLoading@"
						+ url);
				Intent intent = new Intent();
				intent.putExtra("URL", url);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			url = bundle.getString("URL");
			Log.i("URL", "@" + url);
			webView.setWebViewClient(webviewClient);
			webView.loadUrl(url);
		} else {
			setResult(RESULT_CANCELED);
			finish();
		}

	}

	class WebviewClient extends WebViewClient {

		private CallBack callBack;

		public WebviewClient(CallBack callBack) {
			// TODO Auto-generated constructor stub
			this.callBack = callBack;
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			Log.i("shouldOverrideUrlLoading", "shouldOverrideUrlLoading@" + url);
			if (url != null
					&& url.toString()
							.startsWith(Constants.TWITTER_CALLBACK_URL)) {
				if (callBack != null) {
					callBack.onLoad(url);
				}
				return false;
			} else {
				return super.shouldOverrideUrlLoading(view, url);
			}
		}
	}
}
