package com.craftbeer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.crittercism.app.Crittercism;
import com.flurry.android.FlurryAgent;

public class Splash extends Activity {

	/**
	 * @author arvind.agarwal
	 */

	/**
	 * This class is splash screen which comes for 5 seconds before the login
	 * screen comes
	 * 
	 */

	// time of splash screen is set 5 sec.
	int mSplashTime = 5000;

	// critisicm key
	String criticismKey = "51b55ea28b2e332fd1000004";
	SharedPreferences prefer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// intialising the flurry agent ....

		// Initiating criticism

		Crittercism.init(getApplicationContext(), criticismKey);

		prefer = PreferenceManager.getDefaultSharedPreferences(Splash.this);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (prefer.getString("USER_NAME", "").equals("")
						&& prefer.getString("USER_ID", "").equals("")) {

					Intent toLoginScreen = new Intent(Splash.this,
							LoginScreen.class);

					startActivity(toLoginScreen);
					finish();
				}

				else {
					Intent toLoginScreen = new Intent(Splash.this,
							FindOrAddBeer.class);
					toLoginScreen.putExtra("userId",
							prefer.getString("USER_ID", ""));
					startActivity(toLoginScreen);
					finish();
				}

			}
		}, mSplashTime);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// starting the flurry app key is ******TR96MZVCJM6K97FHP3YJ******
	@Override
	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, com.craftbeer.constants.Constants.FLURRY_KEY);
	}

	@Override
	protected void onStop() {
super.onStop();
		FlurryAgent.onEndSession(this);
	}
}
