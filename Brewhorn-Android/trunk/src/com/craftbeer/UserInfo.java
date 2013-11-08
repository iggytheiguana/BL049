package com.craftbeer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;

public class UserInfo extends Activity implements HttpListener ,OnClickListener{

	/**
	 * @author arvind.agarwal
	 */
	
	//object of view class
	private TextView _firstNameTxt, _lastNameTxt, _userNameTxt, _zipTxt,
			_emailTxt, _expLevelTxt;
	
	private Button _edtInfoBtn,_showTasteBtn,_btnInfo,_btnCancel,_btnShowHistory;
	SharedPreferences sharedPreference;
	
	
	
	//object of bundle
	Bundle bundle;

	private CheckBox checkBoxAutoShare;
	
	public static Activity activity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_user_info);

		initializeView();
		
		activity=this;
		
		_edtInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(UserInfo.this,EditUserInformation.class);
				i.putExtra("first_name",_firstNameTxt.getText().toString());
				i.putExtra("last_name", _lastNameTxt.getText().toString());
				i.putExtra("user_name", _userNameTxt.getText().toString());
				i.putExtra("email", _emailTxt.getText().toString());
				i.putExtra("zip", _zipTxt.getText().toString());
				i.putExtra("experience_level", _expLevelTxt.getText().toString());
				i.putExtra("userId",bundle.getString("userId"));
				
				startActivity(i);
				
				
				
				
			}
		});

	}

	private void initializeView() {
		
		sharedPreference=PreferenceManager.getDefaultSharedPreferences(UserInfo.this);
		
		
		bundle=getIntent().getExtras();
		
		checkBoxAutoShare=(CheckBox)findViewById(R.id.automate_toggle);

		_firstNameTxt = (TextView) findViewById(R.id.user_info_first_name_txt_1);
		_lastNameTxt = (TextView) findViewById(R.id.user_info_last_name_txt_1);
		_userNameTxt = (TextView) findViewById(R.id.user_info_user_name_txt_1);
		_emailTxt = (TextView) findViewById(R.id.user_info_email_txt_1);
		_zipTxt = (TextView) findViewById(R.id.user_info_zip_txt_1);
		_expLevelTxt = (TextView) findViewById(R.id.user_info_experience_txt_1);
		_edtInfoBtn=(Button)findViewById(R.id.user_info_edt_btn);
		_showTasteBtn=(Button)findViewById(R.id.user_info_show_taste_btn);
		_btnInfo=(Button)findViewById(R.id.info_btn);
		_btnCancel=(Button)findViewById(R.id.cancel_btn);
		_btnShowHistory=(Button)findViewById(R.id.view_history);
		_btnShowHistory.setOnClickListener(this);
		_btnCancel.setOnClickListener(this);
		_btnInfo.setOnClickListener(this);
		_showTasteBtn.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(sharedPreference.getBoolean("AUTO_SHARE",false)){
			checkBoxAutoShare.setChecked(true);
			checkBoxAutoShare.setClickable(false);
		}else{
			checkBoxAutoShare.setChecked(false);
			checkBoxAutoShare.setClickable(false);
		}
		
		
		if (!CheckInternetConnectivity
				.checkinternetconnection(UserInfo.this)) {
			Toast.makeText(UserInfo.this, "Check Internet Connection",
					Toast.LENGTH_SHORT).show();
			}else
			{
				//server hit to get user details
				String REGISTARTION_XML ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><userProfile><userId><![CDATA["+bundle.getString("userId")+"]]></userId><type><![CDATA[2]]></type></userProfile>";
				HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
						+ Url.USER_INFO_URL, UserInfo.this, REGISTARTION_XML,
						UserInfo.this);
				hitRegistartion.execute();
				
				
			}

		
		
		
		
	}
	
	
	
	/**
	 * @param response
	 * this method get the response of server hit for user profile
	 * 
	 */

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
		
		
		JSONObject jsonObject=null;
		
		
		if(response.contains("userProfile")){
		try {
			jsonObject=new JSONObject(response);
			
			JSONObject newJsonObj=jsonObject.getJSONObject("userProfile");
if (newJsonObj.has("firstName")) {
	

	String strFirstName=newJsonObj.getString("firstName");
	
	_firstNameTxt.setText(newJsonObj.getString("firstName"));
	_lastNameTxt.setText(newJsonObj.getString("lastName"));
	_userNameTxt.setText(newJsonObj.getString("username"));
	_zipTxt.setText(newJsonObj.getString("zipcode"));
	_emailTxt.setText(newJsonObj.getString("email"));
	_expLevelTxt.setText(newJsonObj.getString("drinkerLevel"));
	
	
	Log.i("",""+strFirstName);
}			
			//String name= newJsonObj.getString("firstName");
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
//		{"userProfile":{"firstName":"Arvind","lastName":"Aggarwal","zipcode":"140301","drinkerLevel":"Macro Beer Drinker","email":"arvindaggarwal77@gmail.com","username":"arvind"}}
		

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
		
		switch(v.getId()){
		
		case R.id.user_info_show_taste_btn:
			Intent i=new Intent(UserInfo.this, ShowUserTasteProfile.class);
			i.putExtra("userId", bundle.getString("userId"));
			startActivity(i);
			
			
			break;
			
		case R.id.info_btn:
			
			Intent f=new Intent(UserInfo.this, InformationPage.class);
		
			startActivity(f);
			
			break;
			
		case R.id.cancel_btn:
			
			finish();
			break;
			
		case R.id.view_history:
			
			startActivity(new Intent(UserInfo.this, History.class));
			
			break;
		
		
		}
		
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
