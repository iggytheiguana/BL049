package com.craftbeer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;


/**
 * 
 * @author arvind.agarwal
 * this class is helpful for changing password 
 * user just need to put its older password and new desired password
 *
 */
public class ChangePassword extends Activity implements HttpListener {

	
	
	//objects of view class
	private EditText _oldPassEdt, _newPassEdt, _confirmPassEdt;

	private Button _saveButton,_cancelBtn;

	//String object to show response of webservice
	private String _response="";
	
	//object of bundle
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		initView();
	}

	
	//initializing view
	private void initView(){
		
		bundle=getIntent().getExtras();
		
		_oldPassEdt=(EditText)findViewById(R.id.old_password_edt);
		_newPassEdt=(EditText)findViewById(R.id.new_password_edt );
		_confirmPassEdt=(EditText)findViewById(R.id.confirm_password_edt);
		_cancelBtn=(Button)findViewById(R.id.cancel_btn);
		
		
		_cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		});
		
		_saveButton=(Button)findViewById(R.id.done_btn);
		
		
		_saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!CheckInternetConnectivity.checkinternetconnection(ChangePassword.this)){
					
					Toast.makeText(ChangePassword.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

				}
				if(_oldPassEdt.getText().toString().equals("")){
					
					Toast.makeText(ChangePassword.this, "Enter Old Password", Toast.LENGTH_SHORT).show();
					
					
				}if(_oldPassEdt.getText().toString().length()<8){
					
					Toast.makeText(ChangePassword.this, "Minimum 8 Chracters", Toast.LENGTH_SHORT).show();
					
					
				}
				else if (_newPassEdt.getText().toString().equals("")){
					Toast.makeText(ChangePassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();

					
				}else if(!_newPassEdt.getText().toString().equals(_confirmPassEdt.getText().toString())){
					Toast.makeText(ChangePassword.this, "Passwords Donot Match", Toast.LENGTH_SHORT).show();

				}else {
					
					String REGISTARTION_XML ="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><changePassword><userId><![CDATA["+bundle.getString("userId")+"]]></userId><password><![CDATA["+_oldPassEdt.getText().toString()+"]]></password><newPassword><![CDATA["+_newPassEdt.getText().toString()+"]]></newPassword></changePassword>";
					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.CHANGE_PASSWORD, ChangePassword.this, REGISTARTION_XML,
							ChangePassword.this);
					hitRegistartion.execute();
					
					
					
					
				}
				
				
				
			}
		});
		
		
	}
	
	/**
	 * response of webservice is recieved here
	 */

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
		
		JSONObject jsonObject=null;
		
		if(response.contains("changePassword")){
			
			try {
				jsonObject=new JSONObject(response);
				
				if(jsonObject.getString("changePassword").equalsIgnoreCase("2")){
					
					_response="Server Error";
				
					
				}else if(jsonObject.getString("changePassword").equalsIgnoreCase("1")){
					_response="You Have SuccessFully Changed Your Password";
					finish();
				}else if(jsonObject.getString("changePassword").equalsIgnoreCase("-1")){
					_response="USer Doesnot Exist";
					
					
				}else if(jsonObject.getString("changePassword").equalsIgnoreCase("-3")){
					_response="Old Password Doesnot Match with Your Profile ";
					
					
				}else if(jsonObject.getString("changePassword").equalsIgnoreCase("-4")){
					_response="Old And New Password Has Same Value";
					
					
				}
				
				Toast.makeText(ChangePassword.this, _response, Toast.LENGTH_SHORT).show();
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
