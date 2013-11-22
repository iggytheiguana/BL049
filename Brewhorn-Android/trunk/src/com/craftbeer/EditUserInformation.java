package com.craftbeer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.craftbeer.constants.Constants;
import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;

/**
 * 
 * @author arvind.agarwal this class is useful to edit user profile
 * 
 */
public class EditUserInformation extends Activity implements HttpListener {

	// view class objects
	private EditText _firstNameEdt, _lastNameEdt, _userNameEdt, _zipEdt,
			_emailEdt;

	private Button _changePasswordBtn, _btnSave, _btnCancel;
	private Button _spinner;
	
	private CheckBox chk_box_automated_sharing_facebook;
	private CheckBox chk_box_automated_sharing_twitter;

	// string array to show drinker level

	private String[] array = { "Macro Beer Drinker", "Thinking about  craft",
			"New craft beer drinker", "Casual craft beer drinker",
			"Moderate Beer Geek", "Serious Beer Geek" };

	private Dialog dialog;

	private Bundle bundle;

	
	private SharedPreferences sharedPreference;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edt_user_info);

		initializeView();

		_btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!CheckInternetConnectivity
						.checkinternetconnection(EditUserInformation.this)) {
					Toast.makeText(EditUserInformation.this,
							"Check Internet Connection", Toast.LENGTH_SHORT)
							.show();
				} else if (_firstNameEdt.getText().toString().equals("")) {

					Toast.makeText(EditUserInformation.this,
							"Please enter first name", Toast.LENGTH_SHORT)
							.show();
				}

				else if (_userNameEdt.getText().toString().equals("")) {

					Toast.makeText(EditUserInformation.this,
							"Please enter user name", Toast.LENGTH_SHORT)
							.show();

				} else if (_emailEdt.getText().toString().equals("")) {
					Toast.makeText(EditUserInformation.this,
							"Please enter user name", Toast.LENGTH_SHORT)
							.show();

				}

				/*else if (_spinner.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(EditUserInformation.this,
							"Select Experience Level", Toast.LENGTH_SHORT)
							.show();
				}*/

				else {
					
					//server hit
					
					SharedPreferences.Editor editor = sharedPreference.edit();
					if (chk_box_automated_sharing_facebook.isChecked()) {
						editor.putBoolean(Constants.AUTO_SHARE_FACEBOOK, true);
					} else {
						editor.putBoolean(Constants.AUTO_SHARE_FACEBOOK, false);
					}

					if (chk_box_automated_sharing_twitter.isChecked()) {
						editor.putBoolean(Constants.AUTO_SHARE_TWITTER, true);
					} else {
						editor.putBoolean(Constants.AUTO_SHARE_TWITTER, false);
					}
					editor.commit();

					String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editUserProfile><userId><![CDATA["
							+ bundle.getString("userId")
							+ "]]></userId><firstName><![CDATA["
							+ _firstNameEdt.getText().toString().trim()
							+ "]]></firstName><lastName><![CDATA["
							+ _lastNameEdt.getText().toString().trim()
							+ "]]></lastName><zipcode><![CDATA["
							+ _zipEdt.getText().toString().trim()
							+ "]]></zipcode><drinkerLevel><![CDATA[]]></drinkerLevel><email><![CDATA["
							+ _emailEdt.getText().toString().trim()
							+ "]]></email><username><![CDATA["
							+ _userNameEdt.getText().toString().trim()
							+ "]]></username></editUserProfile>";
					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.EDIT_USER_INFO_URL, EditUserInformation.this,
							REGISTARTION_XML, EditUserInformation.this);
					hitRegistartion.execute();

				}

			}
		});

		_changePasswordBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(EditUserInformation.this,
						ChangePassword.class);
				i.putExtra("userId", bundle.getString("userId"));
				startActivity(i);

			}
		});

	}

	/**
	 * initializing view and objects
	 */

	public void initializeView() {
		bundle = getIntent().getExtras();
		sharedPreference=PreferenceManager.getDefaultSharedPreferences(EditUserInformation.this);
		
		// facebook		
		chk_box_automated_sharing_facebook =(CheckBox)findViewById(R.id.automate_sharing_toggle_facebook);
		chk_box_automated_sharing_twitter =(CheckBox)findViewById(R.id.automate_sharing_toggle_twitter);
		
		if(sharedPreference.getBoolean(Constants.AUTO_SHARE_FACEBOOK,false)){
			chk_box_automated_sharing_facebook.setChecked(true);
		}else{
			chk_box_automated_sharing_facebook.setChecked(false);
		}
		
		if(sharedPreference.getBoolean(Constants.AUTO_SHARE_TWITTER,false)){
			chk_box_automated_sharing_twitter.setChecked(true);
		}else{
			chk_box_automated_sharing_twitter.setChecked(false);
		}
		
		/*
		chk_box_automated_sharing_facebook.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor=sharedPreference.edit();
				if(isChecked){					
					editor.putBoolean(Url.AUTO_SHARE_FACEBOOK,true);
				}else{
					editor.putBoolean(Url.AUTO_SHARE_FACEBOOK,false);
				}
				editor.commit();
			}
		});
		
		// Twitter		
	
			
				chk_box_automated_sharing_twitter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						SharedPreferences.Editor editor=sharedPreference.edit();
						if(isChecked){					
							editor.putBoolean(Url.AUTO_SHARE_TWITTER,true);
						}else{
							editor.putBoolean(Url.AUTO_SHARE_TWITTER,false);
						}
						editor.commit();
					}
				});
				*/

		_firstNameEdt = (EditText) findViewById(R.id.registration_user_first_name_edt_new);
		_lastNameEdt = (EditText) findViewById(R.id.registration_user_last_name_edt);
		_userNameEdt = (EditText) findViewById(R.id.registration_user_name_edt);
		_emailEdt = (EditText) findViewById(R.id.registration_user_email_edt);
		_zipEdt = (EditText) findViewById(R.id.registration_zip_edt);

		_btnSave = (Button) findViewById(R.id.registration_registartion_btn);
		_btnCancel = (Button) findViewById(R.id.registration_cancel_btn);
		_btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		_spinner = (Button) findViewById(R.id.registration_spinner);
		_spinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showDialog();

			}
		});

		_changePasswordBtn = (Button) findViewById(R.id.registration_change_password);
		_firstNameEdt.setText(bundle.getString("first_name"));
		_lastNameEdt.setText(bundle.getString("last_name"));
		_userNameEdt.setText(bundle.getString("user_name"));
		_emailEdt.setText(bundle.getString("email"));
		_zipEdt.setText(bundle.getString("zip"));
		_spinner.setText(bundle.getString("experience_level"));

	}

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		JSONObject jsonObj = null;
		if (response.contains("editUserProfile")) {

			try {
				jsonObj = new JSONObject(response);

				if (jsonObj.getString("editUserProfile").equals("1")) {
					Toast.makeText(EditUserInformation.this,
							"Profile successfully updated", Toast.LENGTH_SHORT)
							.show();

					finish();

				} else if (jsonObj.getString("editUserProfile").equals("-1")) {
					Toast.makeText(EditUserInformation.this,
							"User doesn't exist.", Toast.LENGTH_SHORT).show();

				} else if (jsonObj.getString("editUserProfile").equals("-3")) {
					Toast.makeText(EditUserInformation.this,
							"User name exist.", Toast.LENGTH_SHORT).show();

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block

				// catches json exceptions
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

	/**
	 * Dialog to show drinker level
	 */
	private void showDialog() {
		dialog = new Dialog(EditUserInformation.this);
		dialog.setTitle("Experience  Level");
		dialog.setContentView(R.layout.experience_level);
		ListView list = (ListView) dialog.findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(EditUserInformation.this,
				android.R.layout.simple_list_item_1, array));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				_spinner.setText(array[arg2]);
				dialog.dismiss();

			}
		});

		dialog.show();

	}

	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this,
				com.craftbeer.constants.Constants.FLURRY_KEY);
	}

	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}
}
