package com.craftbeer;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;

public class LoginScreen extends Activity implements HttpListener,
		OnClickListener {

	/**
	 * @author arvind.agarwal {@code class represents login details.}
	 */

	/*
	 * @Objects of View class
	 */
	private EditText _edtUserName, _edtUserPassWord;

	private Button _btnLogin, _btnRegistartPassword, _btnInfo;
	private ImageView _btnForgotPassword;

	private String _response;

	private Toast _toast;
	/*
	 * object of sharedprefrence class
	 */
	private SharedPreferences _sharedPref;
	private SharedPreferences.Editor editor;

	public static Activity logIn;

	private JSONArray _jsonArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		/**
		 * @intialise method@ intialising the views object used within the
		 *            class.
		 */

		intialiseViews();

		logIn = this;

		/**
		 * Setting last login or registerd name, saved in shared preference, at
		 * username.
		 */

		_sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		_edtUserName.setText(_sharedPref.getString("USER_NAME", ""));

	}

	private void intialiseViews() {
		_edtUserName = (EditText) findViewById(R.id.login_user_name_edt);
		_edtUserPassWord = (EditText) findViewById(R.id.login_user_password_edt);
		_btnInfo = (Button) findViewById(R.id.info_btn);
		_btnInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(LoginScreen.this, InformationPage.class);

				startActivity(i);

			}
		});
		_btnRegistartPassword = (Button) findViewById(R.id.login_password_registration_btn);
		_btnLogin = (Button) findViewById(R.id.login_login_btn);
		_btnForgotPassword = (ImageView) findViewById(R.id.login_forgot_btn);
		_btnRegistartPassword.setOnClickListener(this);
		_btnLogin.setOnClickListener(this);
		_btnForgotPassword.setOnClickListener(this);

	}

	/**
	 * @param response
	 *            response comes from hit of server
	 */
	@Override
	public void onResponse(String response) {
		JSONObject login_JsonObject = null;

		if (response.contains("userLogin")) {
			try {

				login_JsonObject = new JSONObject(response);
				String strUserRegistration = login_JsonObject
						.getString("userLogin");

				if (strUserRegistration.equalsIgnoreCase("-1")) {

					/*
					 * Server Error: {"userLogin":"-2"} Successfull :
					 * {"userLogin":"1"} Failure: {"userLogin":"-1"}
					 */

					_response = "Wrong UserName or Password.";

				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
				} else {

					_jsonArray = login_JsonObject.getJSONArray("beerProfile");
					editor = _sharedPref.edit();
					if (_jsonArray.length() != 0) {

						editor.putInt("aromaValue",
								Integer.parseInt(_jsonArray.getString(0)));
						editor.putInt("sweetValue",
								Integer.parseInt(_jsonArray.getString(1)));
						editor.putInt("bitterValue",
								Integer.parseInt(_jsonArray.getString(2)));
						editor.putInt("maltValue",
								Integer.parseInt(_jsonArray.getString(3)));
						editor.putInt("yeastValue",
								Integer.parseInt(_jsonArray.getString(4)));
						editor.putInt("mouthFeelValue",
								Integer.parseInt(_jsonArray.getString(5)));
						editor.putInt("sourValue",
								Integer.parseInt(_jsonArray.getString(6)));
						editor.putInt("additiveValue",
								Integer.parseInt(_jsonArray.getString(7)));
						editor.putInt("boozinessValue",
								Integer.parseInt(_jsonArray.getString(8)));
						editor.putString("sour_status", _jsonArray.getString(9));
						editor.putString("additive_status",
								_jsonArray.getString(10));
						editor.putString("booziness_status",
								_jsonArray.getString(11));
						editor.putString("yeast_status",
								_jsonArray.getString(12));
					}

					editor.putString("USER_NAME", _edtUserName.getText()
							.toString().trim());
					editor.putString("USER_ID", strUserRegistration);
					editor.commit();

					_response = "Login Successfully";
					Intent toHomeScreen = new Intent(LoginScreen.this,
							FindOrAddBeer.class);
					toHomeScreen.putExtra("userId", strUserRegistration);
					startActivity(toHomeScreen);
					finish();
				}

				showDialog(_response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (response.contains("forgotPassword")) {

		}

	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAlreadyExist(String response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.login_password_registration_btn:

			Intent toRegistartionScreen = new Intent(LoginScreen.this,
					RegistrationScreen.class);
			startActivity(toRegistartionScreen);

			break;

		case R.id.login_login_btn:

			if (!CheckInternetConnectivity
					.checkinternetconnection(LoginScreen.this)) {
				Toast.makeText(LoginScreen.this, "Check Internet Connection",
						Toast.LENGTH_SHORT).show();

			} else if (_edtUserName.getText().toString().equalsIgnoreCase("")) {

				Toast.makeText(LoginScreen.this, "Please enter the username.",
						Toast.LENGTH_SHORT).show();

			} else if (_edtUserPassWord.getText().toString()
					.equalsIgnoreCase("")) {

				Toast.makeText(LoginScreen.this, "Please enter the password.",
						Toast.LENGTH_SHORT).show();

			}

			else {
				String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userLogin><username><![CDATA["
						+ _edtUserName.getText().toString().trim()
						+ "]]></username><password><![CDATA["
						+ _edtUserPassWord.getText().toString().trim()
						+ "]]></password></userLogin>";
				HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
						+ Url.LOGIN_URL, LoginScreen.this, REGISTARTION_XML,
						LoginScreen.this);
				hitRegistartion.execute();

			}

			break;

		case R.id.login_forgot_btn:

			Intent toForgotPassword = new Intent(LoginScreen.this,
					ForgotPassword.class);
			startActivity(toForgotPassword);

			break;

		case R.id.login_user_name_edt:

			_edtUserName.setCursorVisible(true);
			break;
		}

	}

	/**
	 * @param _response
	 *            show the response of webservice hitted coresspondingly the
	 *            values send over the server in the toast.
	 * 
	 */
	private void showDialog(String _response) {
		_toast = Toast
				.makeText(LoginScreen.this, _response, Toast.LENGTH_SHORT);
		_toast.setGravity(Gravity.CENTER, 0, 0);
		_toast.show();

	}

}
