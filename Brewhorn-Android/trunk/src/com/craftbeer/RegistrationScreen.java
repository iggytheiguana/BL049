package com.craftbeer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;

/**
 * 
 * @author arvind.agarwal This class is useful to register a new user needs
 *         user's first name ,last name,username,password (at least 8 chracters)
 *         ,zipcode and email id
 */

public class RegistrationScreen extends Activity implements HttpListener {

	// object of view class
	private EditText _editUserFirstName, _editUserLastName, _editUserName,
			_editZip, _editPassword, _editEmail, _editConfirmPassword;

	private Button _spinner;

	private Button _btnRegister;
	
	private CheckBox chkBoxTermsConditions;
	
	private ImageView imgTermsCondition;

	// obj of String to show response of webservice hit to register new user
	private String _response;
	// String array to suggest drinker's level
	private String[] _drinkerLevel = new String[] { "Macro Beer Drinker",
			"Thinking about  craft", "New craft beer drinker",
			"Casual craft beer drinker", "Moderate Beer Geek",
			"Serious Beer Geek" };

	// object of dialog
	private Dialog dialog;
	// object of shared preference
	private SharedPreferences _sharedPref;
	private SharedPreferences.Editor editor;

	private Button instructionBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registration_screen);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		initializeView();

		/* initiating variable of shared preference */
		_sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		_sharedPref.getString("USER_NAME", "");

	}

	/**
	 * initializing variables
	 */
	private void initializeView() {
		// TODO Auto-generated method stub

		instructionBtn = (Button) findViewById(R.id.btn_instruction);
		instructionBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(new Intent(RegistrationScreen.this,
						Instruction.class).putExtra("via", "REG"));

			}
		});
		
		imgTermsCondition=(ImageView)findViewById(R.id.img_terms_conditions);
		imgTermsCondition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://www.netsmartz.net/portfolio"));
				startActivity(browserIntent);

				
			}
		});
		chkBoxTermsConditions=(CheckBox)findViewById(R.id.chk_terms_conditions);

		_editUserFirstName = (EditText) findViewById(R.id.registration_user_first_name_edt_new);
		_editUserLastName = (EditText) findViewById(R.id.registration_user_last_name_edt);
		_editUserName = (EditText) findViewById(R.id.registration_user_name_edt);
		_editZip = (EditText) findViewById(R.id.registration_zip_edt);
		_spinner = (Button) findViewById(R.id.registration_spinner);
		_btnRegister = (Button) findViewById(R.id.registration_registartion_btn);
		_editPassword = (EditText) findViewById(R.id.registration_password_edt);
		_editEmail = (EditText) findViewById(R.id.registration_user_email_edt);
		_editConfirmPassword = (EditText) findViewById(R.id.registration_confirm_password_edt);

		_spinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showDialog();

			}
		});

		_btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// checking internet connectivity first then make a server hit
				if (!CheckInternetConnectivity
						.checkinternetconnection(RegistrationScreen.this)) {

					Toast.makeText(RegistrationScreen.this,
							"Check internet connection.", Toast.LENGTH_SHORT)
							.show();
				} else if (_editUserFirstName.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(RegistrationScreen.this,
							"Enter your first name", Toast.LENGTH_SHORT).show();

				} else if (_editUserLastName.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(RegistrationScreen.this,
							"Enter your last name", Toast.LENGTH_SHORT).show();
				} else if (_editUserName.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(RegistrationScreen.this,
							"Enter your user name", Toast.LENGTH_SHORT).show();
				} else if (_editPassword.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(RegistrationScreen.this,
							"Enter your user password", Toast.LENGTH_SHORT)
							.show();
				} else if (_editPassword.getText().toString().length() < 8) {
					Toast.makeText(RegistrationScreen.this,
							"Atleast 8 chracters in password.",
							Toast.LENGTH_SHORT).show();
				} else if (_editConfirmPassword.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(RegistrationScreen.this,
							"Enter password for confirmation",
							Toast.LENGTH_SHORT).show();
				} else if (!_editPassword.getText().toString()
						.equals(_editConfirmPassword.getText().toString())) {
					Toast.makeText(RegistrationScreen.this,
							"Passwords do not match.", Toast.LENGTH_SHORT)
							.show();
				}

				else if (_editEmail.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(RegistrationScreen.this,
							"Enter your user email", Toast.LENGTH_SHORT).show();
				} else if (!isEmailValid(_editEmail.getText().toString().trim())) {

					Toast.makeText(RegistrationScreen.this, "Invalid Email",
							Toast.LENGTH_SHORT).show();

				} else if (_editZip.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(RegistrationScreen.this,
							"Enter Your Zip Code", Toast.LENGTH_SHORT).show();
				} else if(!chkBoxTermsConditions.isChecked()){
					Toast.makeText(RegistrationScreen.this,
							"Please accept terms and conditions", Toast.LENGTH_SHORT).show();
				}
				else {
					// hit for new user registration
					String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userRegistration><firstName><![CDATA["
							+ _editUserFirstName.getText().toString().trim()
							+ "]]></firstName><lastName><![CDATA["
							+ _editUserLastName.getText().toString().trim()
							+ "]]></lastName><username><![CDATA["
							+ _editUserName.getText().toString().trim()
							+ "]]></username><email><![CDATA["
							+ _editEmail.getText().toString().trim()
							+ "]]></email><password><![CDATA["
							+ _editPassword.getText().toString().trim()
							+ "]]></password><zipcode><![CDATA["
							+ _editZip.getText().toString().trim()
							+ "]]></zipcode><drinkerLevel><![CDATA[]]></drinkerLevel></userRegistration>";

					Log.i("Email", "" + _editEmail.getText().toString());
					Log.i("Password", "" + _editPassword.getText().toString());
					Log.i("FirstName", ""
							+ _editUserFirstName.getText().toString());
					Log.i("LastName", ""
							+ _editUserLastName.getText().toString());
					Log.i("Username", "" + _editUserName.getText().toString());
					Log.i("Zip", "" + _editZip.getText().toString());

					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.REGISTARTION_URL, RegistrationScreen.this,
							REGISTARTION_XML, RegistrationScreen.this);
					hitRegistartion.execute();
				}

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.craftbeer.httpcall.HttpListener#onResponse(java.lang.String)
	 * 
	 * this method gets response of server hit
	 */
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		Log.e("", "" + response);

		JSONObject registerJsonObj = null;

		if (response.contains("userRegistration")) {

			try {

				registerJsonObj = new JSONObject(response);
				String strUserRegistration = registerJsonObj
						.getString("userRegistration");
				if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "Email Already Exists.";
					Toast.makeText(RegistrationScreen.this, _response,
							Toast.LENGTH_LONG).show();

				} else if (strUserRegistration.equalsIgnoreCase("-3")) {
					_response = "Username Already Exists.";
					Toast.makeText(RegistrationScreen.this, _response,
							Toast.LENGTH_LONG).show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(RegistrationScreen.this, _response,
							Toast.LENGTH_LONG).show();
				} else {

					_response = "Registered Successfully.";
					Toast.makeText(RegistrationScreen.this, _response,
							Toast.LENGTH_SHORT).show();

					// saving user name and id in shared preference
					// initiating object of shared preference editor
					editor = _sharedPref.edit();
					editor.putString("USER_NAME", _editUserName.getText()
							.toString());
					editor.putString("USER_ID", strUserRegistration);
					editor.commit();

					Intent toHome = new Intent(RegistrationScreen.this,
							HomeScreen.class);

					toHome.putExtra("userId", strUserRegistration);
					toHome.putExtra("title", "Create Your Taste Profile");

					startActivity(toHome);

					LoginScreen.logIn.finish();
					finish();

				}

			} catch (Exception e) {

				// catches JSON exceptions
				e.printStackTrace();
			}

			// showing response of sever hit to register new user
			Toast.makeText(RegistrationScreen.this, _response,
					Toast.LENGTH_LONG).show();

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

	/**
	 * 
	 * @param email
	 * @return
	 * 
	 *         this method returns whether the email is valid or not
	 */
	private boolean isEmailValid(String email) {

		boolean isValid;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		} else {

			Log.i(".....Email", "Not valid");

			isValid = false;

		}
		return isValid;
	}

	/**
	 * @param this method shows a pop up to select user experience level
	 */
	private void showDialog() {
		dialog = new Dialog(RegistrationScreen.this);
		dialog.setTitle("Experience  Level");
		dialog.setContentView(R.layout.experience_level);
		ListView list = (ListView) dialog.findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(RegistrationScreen.this,
				android.R.layout.simple_list_item_1, _drinkerLevel));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				_spinner.setText(_drinkerLevel[arg2]);
				dialog.dismiss();

			}
		});

		dialog.show();

	}
}
