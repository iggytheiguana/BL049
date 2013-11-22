package com.craftbeer.constants;

import java.util.Arrays;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;

public class Constants {
	
//	public static final String facbookKey="146545745537486";
	public static final String facbookKey="589751437754650";
	
	/** Twitter Api Key **/

//	public static final String CONSUMER_KEY = "jKoOY2bUfIDKw9PzZQ9Iw";
//	public static final String CONSUMER_SECRET = "yRgcVD3Tehbz2myzJJy6uLzTF97PBOevkC6A1beh4E";
//	public static final String CALLBACK_URL = "http://www.google.com";

//	public static final String IEXTRA_AUTH_URL = "https://api.twitter.com/oauth/authorize";
//	public static final String IEXTRA_OAUTH_VERIFIER = "oauth_verifier";
//	public static final String IEXTRA_OAUTH_TOKEN = "oauth_token";

//	public static final String PREF_NAME = "com.example.android-twitter-oauth-demo";
//	public static final String PREF_KEY_ACCESS_TOKEN = "1012685587-zT0gd8IPgFBw7ShEzI13HNFCcsMB4YRi3Xm7xch";
//	public static final String PREF_KEY_ACCESS_TOKEN_SECRET = "PLbBGhVhFyE8ct6FJf8DJl6XFYPenApRvdd7voJDUg";

	public static final String FLURRY_KEY="HJMMTPZ55P2BC5DDBNQQ";

//	public static final String FLURRY_KEY="TR96MZVCJM6K97FHP3YJ";
	
	
	//public static final String FLURRY_KEY="J86DK5BYNNPBJ4BST6TH";
	
	
	
	/*tweeter*/
	
	
	
	/*Consumer key 	jKoOY2bUfIDKw9PzZQ9Iw
	Consumer secret 	yRgcVD3Tehbz2myzJJy6uLzTF97PBOevkC6A1beh4E
	Request token URL 	https://api.twitter.com/oauth/request_token
	Authorize URL 	https://api.twitter.com/oauth/authorize
	Access token URL 	https://api.twitter.com/oauth/access_token
	Callback URL 	http://www.google.com
	*/
	
	// Samit
	
	public static String TWITTER_CONSUMER_KEY = "qD7DepiCzGI97BPgL6VEg"; // place your cosumer key here
	public static String TWITTER_CONSUMER_SECRET = "QX34e4LTbI9wnd8GIDb0dVeII5zMG4e5UVuZX7s6Qk"; // place your consumer secret here

	// Preference Constants
	public static String PREFERENCE_NAME = "twitter_oauth";
	public static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	public static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	public static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	public static final String TWITTER_CALLBACK_URL = "oauth://brewhornapp";

	// Twitter oauth urls
	public static final String URL_TWITTER_AUTH = "auth_url";
	public static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	public static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

	public static final String AUTO_SHARE_FACEBOOK = "AUTO_SHARE_FACEBOOK";
	public static final String AUTO_SHARE_TWITTER = "AUTO_SHARE_TWITTER";
	
	 public static final List<String> permissions=Arrays.asList("publish_actions");
	 
	 public  static final String PERMISSION = "publish_actions";
	
	static ProgressDialog progressDialog;
	
	public static void ShowProgress(Context ctx) {
		progressDialog = new ProgressDialog(ctx);
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	/****
	 * This method is used for Dismiss Progress if Showing
	 */
	public static void DismissProgress() {
		if (progressDialog.isShowing())
			progressDialog.dismiss();
	}

	
}
