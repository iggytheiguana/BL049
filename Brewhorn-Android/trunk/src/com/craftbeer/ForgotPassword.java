package com.craftbeer;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;

public class ForgotPassword extends Activity implements OnClickListener,
		HttpListener {
	
	
	
	/**
	 * @author arvind.agarwal
	 * this class is useful to get password  if forget
	 * server will return the password to corresponding email id to username
	 */

	
	
	//view class object
	private Toast _toast;

	private Button _btnSubmit, _btnCancel;

	private EditText _edtUserName;

	private String _response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//to remove title from activity
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password);

		initializeView();

	}

	
	/**
	 * initializing view and objects
	 */
	private void initializeView() {
		// TODO Auto-generated method stub

		_btnSubmit = (Button) findViewById(R.id.forgot_password_btn);
		_edtUserName = (EditText) findViewById(R.id.forgot_user_name_edt);
		_btnSubmit.setOnClickListener(this);

		_btnCancel = (Button) findViewById(R.id.forgot_password_cancel_btn);
		_btnCancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.forgot_password_btn:

			
			//check internet connectivity
			if (CheckInternetConnectivity
					.checkinternetconnection(ForgotPassword.this)) {
				
				//server hit to retrieve password

				String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><forgotPassword><username><![CDATA["
						+ _edtUserName.getText().toString().trim()
						+ "]]></username></forgotPassword>";

				Log.i("", "" + _edtUserName.getText().toString().trim());

				HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
						+ Url.FORGOT_PASSWORD, ForgotPassword.this,
						REGISTARTION_XML, ForgotPassword.this);
				hitRegistartion.execute();
			}

			break;

		case R.id.forgot_password_cancel_btn:

			finish();
			break;

		}

	}

	
	/**
	 * getting response of server hit
	 */
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		Log.i("response", "" + response);
		JSONObject forgot_resonse = null;

		if (response.contains("forgotPassword")) {

			try {

				forgot_resonse = new JSONObject(response);
				String strUserRegistration = forgot_resonse
						.getString("forgotPassword");
				if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "Invalid Email /Username";

					showDialog(_response);

				} else if (strUserRegistration.equalsIgnoreCase("1")) {
					_response = "Check Your Email ";
					showDialog(_response);
					finish();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					showDialog(_response);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

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

	private void showDialog(String _response) {
		_toast = Toast.makeText(ForgotPassword.this, _response,
				Toast.LENGTH_SHORT);
		_toast.setGravity(Gravity.CENTER, 0, 0);
		_toast.show();

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		finish();
	}
	
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
