package com.craftbeer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;

public class SocialNetworkSharing extends Activity implements
		android.view.View.OnClickListener {

	/**
	 * @author arvind.agarwal
	 */

	// object of facebook call back class
	private com.facebook.Session.StatusCallback statusCallback = new SessionStatusCallback();

	// object of shared preference
	private SharedPreferences prefer;

	// object of view class
	private Button _btnFaceBook, _btnTwitter, _btnCancel;

	// object of Facebook session
	Session session;
	
	Bundle bundle;
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_networking_share);
		bundle=getIntent().getExtras();
	

		/**
		 * initializing the view object in activity
		 */
		initialView();
		if(	bundle.get("via").equals("Add")){
			
			_btnCancel.setBackgroundResource(R.drawable.btn_home);
		Toast.makeText(this, "Skip Sharing and return to the Search Screen", Toast.LENGTH_LONG).show();
		}
		// Getting access token for facebook sharing
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		// getting active session of facebook login
		session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		}

		// updating facebook share button functionality accroding to session
		updateView();

		_btnTwitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialogTwitter();

			}
		});
		
		
		showHashKey(this);

	}

	private void initialView() {

		// TODO Auto-generated method stub

		_btnFaceBook = (Button) findViewById(R.id.facebook_share_btn);

		_btnTwitter = (Button) findViewById(R.id.twitter_share_btn);

		_btnCancel = (Button) findViewById(R.id.cancel_btn);

		_btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		prefer = PreferenceManager
				.getDefaultSharedPreferences(SocialNetworkSharing.this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.facebook_share_btn:

			break;

		}

	}

	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
		
		session = Session.getActiveSession();

	    if (session.isOpened()) {

	        // Check for publish permissions
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	           
	            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
	                    this, PERMISSIONS);
	            session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }
	    }
	    	
	    	
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	/**
	 * updating facebook share button functionality accroding to session This
	 * method updates the functionality of facebook button according to session
	 * if session is valid ,on click of _btnFaceBook a dialog will pop up to
	 * share pre written content over facebook. else it will call for valid
	 * session through facebook login.
	 * 
	 */
	private void updateView() {
		session = Session.getActiveSession();
		if (session.isOpened()) {
			// _btnFaceBook.setText("logout");
			_btnFaceBook.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					
					
					

					/**
					 * Under written code is for posting image on wall
					 * ,commented out because it is out of scope from story
					 * board of project but it can come in use in near future.
					 * 
					 */

					/*
					 * Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					 * R.drawable.ic_launcher);
					 * 
					 * Request request=Request.newUploadPhotoRequest(session,
					 * bmp, new Callback() {
					 * 
					 * @Override public void onCompleted(Response response) { //
					 * TODO Auto-generated method stub
					 * Toast.makeText(SocialNetworkSharing.this, "Shared",
					 * Toast.LENGTH_SHORT).show();
					 * 
					 * } });
					 * 
					 * 
					 * Bundle bundle=request.getParameters();
					 * bundle.putString("message",enter your message over hear);
					 */

					showDialog();

				}
			});
		} else {

			// _btnFaceBook.setText("login");
			_btnFaceBook.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					onClickLogin();
				}
			});
		}
	}

	/**
	 * This method is useful for login in facebook and getting valid session.
	 * After getting a session it calls the SessionStatusCallback to save the
	 * session
	 * 
	 */
	private void onClickLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(SocialNetworkSharing.this)
					.setCallback(statusCallback));
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	/**
	 * This method can be use to logout from FaceBook But it is not used in this
	 * class but kept for future use
	 */

	/*private void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}
*/
	/**
	 * 
	 * @author arvind.agarwal This class is called after login in FaceBook
	 * 
	 * 
	 */
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			
			updateView();
		}
	}

	/**
	 * this method is called when to show alert after posting on FaceBook.
	 */
	private void showAlert() {

		AlertDialog.Builder alert = new AlertDialog.Builder(
				SocialNetworkSharing.this);
		alert.setMessage("You have successfully posted on Facebook");
		alert.setPositiveButton("Dismiss", null);
		alert.show();

	}

	/**
	 * This method shows message going to be shared on facebook containing name
	 * and brewery name of beer user has last created or edited.
	 */
	private void showDialog() {

		final Dialog _dialog = new Dialog(SocialNetworkSharing.this);
		_dialog.setContentView(R.layout.facebook_dialog);
		_dialog.setTitle("Facebook");

		final EditText edt = (EditText) _dialog.findViewById(R.id.edt);

		if (!prefer.getString("BREWERY_NAME", "").equals("")
				&& !prefer.getString("PROFILED_BEER_NAME", "").equals("")) {

			edt.setText("I just profiled #"
					+ prefer.getString("BREWERY_NAME", "") .replaceAll(" ", "")+ " #"
					+ prefer.getString("PROFILED_BEER_NAME", "").replaceAll(" ", "")
					+ " with @brewhornbeerapp.#brewhorn #craftbeer");
		} else {
			edt.setText("@brewhornbeerapp.#brewhorn #craftbeer");
		}
		Button btnShare = (Button) _dialog.findViewById(R.id.btn);

		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!edt.getText().toString().equals("")) {
					
					
				

					Request request = Request.newStatusUpdateRequest(session,
							edt.getText().toString().trim(), new Callback() {

								@Override
								public void onCompleted(Response response) {
									// TODO Auto-generated method stub
									showAlert();
								}
							});
					request.executeAsync();

					_dialog.dismiss();
				}

				else {
					edt.setError("Enter Beer Name");
				}

			}

		});
		_dialog.show();

	}

	/**
	 * This method shows message going to be shared on Twitter containing name
	 * and brewery name of beer user has last created or edited.
	 */

	private void showDialogTwitter() {

		final Dialog _dialog = new Dialog(SocialNetworkSharing.this);
		_dialog.setContentView(R.layout.facebook_dialog);
		_dialog.setTitle("Twitter");

		final EditText edt = (EditText) _dialog.findViewById(R.id.edt);
		Button btnShare = (Button) _dialog.findViewById(R.id.btn);
		String newString = prefer.getString("PROFILED_BEER_NAME", "");
		String newString2 = prefer.getString("BREWERY_NAME", "");

		if (!newString.equals("") && !newString2.equals("")) {

			String stringMain = "I just profiled #"
					+ prefer.getString("BREWERY_NAME", "").replaceAll(" ", "") + " #"
					+ prefer.getString("PROFILED_BEER_NAME", "").replaceAll(" ","")
					+ " with @brewhornbeerapp.#brewhorn #craftbeer";
			edt.setText(stringMain);
		}

		else {
			edt.setText("@brewhornbeerapp.#brewhorn #craftbeer");
		}
		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!edt.getText().toString().equals("")) {

					// This method is used to encode the url containing special
					// chracters like #,$ etc.

					String tweetUrl="";
					try {
						tweetUrl = "https://twitter.com/intent/tweet?text= "
								+ URLEncoder.encode(edt.getText().toString().trim(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

					Uri uri = Uri.parse(tweetUrl);
					startActivity(new Intent(Intent.ACTION_VIEW, uri));
					_dialog.dismiss();
				} else {
					edt.setError("Enter Beer Name");
				}

			}
		});
		_dialog.show();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}
	
	private boolean isSubsetOf(Collection<String> subset,
	        Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static void showHashKey(Context context) {
	    try {
	        PackageInfo info = context.getPackageManager().getPackageInfo(
	                "com.example.craftbeer", PackageManager.GET_SIGNATURES); //Your package name here
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.v("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {
	    } catch (NoSuchAlgorithmException e) {
	    }
	}
}