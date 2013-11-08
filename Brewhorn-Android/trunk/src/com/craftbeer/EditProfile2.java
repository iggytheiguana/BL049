package com.craftbeer;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;

/**
 * 
 * @author arvind.agarwal this class useful to edit remaining 3 params of user
 *         taste profile
 * 
 * 
 */
public class EditProfile2 extends Activity implements OnSeekBarChangeListener,
		HttpListener {

	public static Activity activity;
	// String object for baseline
	private String baseLine = "You prefer an average amount";

	private String _chkSourStatus = "1", _chkBoozinessStatus = "1",
			_chkAdditiveStatus = "1";

	// object of shared preference
	private SharedPreferences mPreference;

	private SharedPreferences.Editor editor;

	// object of view class
	private SeekBar _seekBarSour, _seekBarAdditive, _seekBarBoozines;

	private int sourValue = 3, additiveValue = 3, boozinessvalue = 3;

	private Toast _toast;

	private int aromaValue = 3, sweetvalue = 3, bitterValue = 3, maltValue = 3,
			yeastvalue = 3, mouthValue = 3;

	Button _btnSave, _btnCancel, _btnBack;
	// object of bundle
	Bundle bundle;
	// String object for server response
	String _response;
	
	private TextView txtSourName, txtBoozinessName, txtAdditiveName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen_2);
		// hiding keyboard
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		activity = this;

		initialize();
		
		/*setting progress in seekbars*/
		
		//calculations:
			//say sourvalue comes 5,progress will set to 50 accoding under given formula

		_seekBarSour.setProgress((mPreference.getInt("sourValue", 3) - 3) * 25);

		_seekBarAdditive
				.setProgress((mPreference.getInt("additiveValue", 3) - 3) * 25);
		_seekBarBoozines
				.setProgress((mPreference.getInt("boozinessValue", 3) - 3) * 25);

		_btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (CheckInternetConnectivity
						.checkinternetconnection(EditProfile2.this)) {
					
					FlurryAgent.logEvent("EditUserTasteProfile");

					// server hit

					String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editUserTasteProfile><userId><![CDATA["
							+ bundle.getString("userId")
							+ "]]></userId><aroma><![CDATA["
							+ String.valueOf(aromaValue)
							+ "]]></aroma><sweet><![CDATA["
							+ String.valueOf(sweetvalue)
							+ "]]></sweet><bitter><![CDATA["
							+ String.valueOf(bitterValue)
							+ "]]></bitter><malt><![CDATA["
							+ String.valueOf(maltValue)
							+ "]]></malt><yeast><![CDATA["
							+ String.valueOf(yeastvalue)
							+ "]]></yeast><mouthFeel><![CDATA["
							+ String.valueOf(mouthValue)
							+ "]]></mouthFeel><sour><![CDATA["
							+ String.valueOf(sourValue)
							+ "]]></sour><additive><![CDATA["
							+ String.valueOf(additiveValue)
							+ "]]></additive><booziness><![CDATA["
							+ String.valueOf(boozinessvalue)
							+ "]]></booziness>"
							+ "<sour_status><![CDATA["
							+ _chkSourStatus
							+ "]]></sour_status><additive_status><![CDATA["
							+ _chkAdditiveStatus
							+ "]]></additive_status><booziness_status><![CDATA["
							+ _chkBoozinessStatus
							+ "]]></booziness_status></editUserTasteProfile>";
					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.USER_TASTE_EDIT_URL, EditProfile2.this,
							REGISTARTION_XML, EditProfile2.this);
					hitRegistartion.execute();
				} else {

					Toast.makeText(EditProfile2.this,
							"Check Internet Connection", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

	}

	/**
	 * initializing view
	 */
	private void initialize() {
		// TODO Auto-generated method stub

		TextView title = (TextView) findViewById(R.id.title);
		title.setText("Edit Optional Taste Parameters");

		mPreference = PreferenceManager
				.getDefaultSharedPreferences(EditProfile2.this);

		sourValue = mPreference.getInt("sourValue", 3);

		additiveValue = mPreference.getInt("additiveValue", 3);
		boozinessvalue = mPreference.getInt("boozinessValue", 3);

		bundle = getIntent().getExtras();

		aromaValue = mPreference.getInt("aroma_temp", 3);
		sweetvalue = mPreference.getInt("sweet_temp", 3);
		bitterValue = mPreference.getInt("bitter_temp", 3);
		maltValue = mPreference.getInt("malt_temp", 3);
		yeastvalue = mPreference.getInt("yeast_temp", 3);
		mouthValue = mPreference.getInt("mouth_temp", 3);

		_btnCancel = (Button) findViewById(R.id.home_2_cancel_btn);
		_btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		_seekBarSour = (SeekBar) findViewById(R.id.home_sour_seek_bar);
		_seekBarAdditive = (SeekBar) findViewById(R.id.home_additive_seek_bar);
		_seekBarBoozines = (SeekBar) findViewById(R.id.home_booziness_seek_bar);
		_btnSave = (Button) findViewById(R.id.home_2_save_btn);

		_seekBarSour.setOnSeekBarChangeListener(this);
		_seekBarAdditive.setOnSeekBarChangeListener(this);
		_seekBarBoozines.setOnSeekBarChangeListener(this);
		
		
		
		txtSourName = (TextView) findViewById(R.id.home_sour_txt);
		txtAdditiveName = (TextView) findViewById(R.id.home_additive_txt);
		txtBoozinessName = (TextView) findViewById(R.id.home_booziness_txt);

		txtSourName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Consider how much you like sour or tart flavors?");

			}
		});

		txtAdditiveName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Consider how much you might like fruit, spices or herbs, vegetables, wood aging, or liquor barrel aging in a beer?");

			}
		});

		txtBoozinessName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showMeaningOfParameter("Consider all alcohols you consume. How much do you prefer an alcohol flavor and sensation?");

			}
		});

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * this method is called on stop progress.
	 * this sets the values of 6 parameters according to position of seek bar
	 * 0 to 24 -------3
	 * 25 to 49-------4
	 * 50 to74-------5
	 * 75 to 99------6
	 * 100-----7
	 *  
	 */

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

		switch (seekBar.getId()) {
		case R.id.home_sour_seek_bar:

			int sourProgress = _seekBarSour.getProgress();

			if (sourProgress >= 0 && sourProgress < 25) {
				sourValue = 3;
				_toast = Toast.makeText(this, "You prefer much less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSour.setProgress(0);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (sourProgress >= 50 && sourProgress < 75) {
				sourValue = 5;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSour.setProgress(50);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (sourProgress == 100) {
				sourValue = 7;
				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSour.setProgress(100);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (sourProgress >= 25 && sourProgress < 50) {
				sourValue = 4;
				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSour.setProgress(25);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (sourProgress >= 75 && sourProgress < 100) {
				sourValue = 6;
				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSour.setProgress(75);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			}

			break;

		case R.id.home_additive_seek_bar:

			int additiveProgress = _seekBarAdditive.getProgress();
			if (additiveProgress >= 0 && additiveProgress < 25) {
				additiveValue = 3;
				_toast = Toast.makeText(this, "You prefer much less",
						Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarAdditive.setProgress(0);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (additiveProgress >= 50 && additiveProgress < 75) {
				additiveValue = 5;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarAdditive.setProgress(50);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (additiveProgress == 100) {
				additiveValue = 7;
				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarAdditive.setProgress(100);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (additiveProgress >= 25 && additiveProgress < 50) {
				additiveValue = 4;
				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarAdditive.setProgress(25);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (additiveProgress >= 75 && additiveProgress < 100) {
				additiveValue = 6;

				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarAdditive.setProgress(75);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			}

			break;
		case R.id.home_booziness_seek_bar:

			int boozinessProgress = _seekBarBoozines.getProgress();
			if (boozinessProgress >= 0 && boozinessProgress < 25) {
				boozinessvalue = 3;
				_toast = Toast.makeText(this, "You prefer much less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBoozines.setProgress(0);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (boozinessProgress >= 50 && boozinessProgress < 75) {
				boozinessvalue = 5;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBoozines.setProgress(50);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (boozinessProgress == 100) {
				boozinessvalue = 7;
				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarBoozines.setProgress(100);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (boozinessProgress >= 25 && boozinessProgress < 50) {
				boozinessvalue = 4;
				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBoozines.setProgress(25);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (boozinessProgress >= 75 && boozinessProgress < 100) {
				boozinessvalue = 6;

				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBoozines.setProgress(75);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			}

			break;
		}

	}

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		Log.i("response", "" + response);

		JSONObject registerJsonObj = null;

		if (response.contains("editUserTasteProfile")) {

			try {

				registerJsonObj = new JSONObject(response);
				String strUserRegistration = registerJsonObj
						.getString("editUserTasteProfile");
				if (strUserRegistration.equalsIgnoreCase("1")) {
					_response = "Taste Profile Successfully updated";
					Toast.makeText(EditProfile2.this, _response,
							Toast.LENGTH_LONG).show();

					editor = mPreference.edit();
					editor.putInt("aromaValue", aromaValue);
					editor.putInt("sweetValue", sweetvalue);
					editor.putInt("bitterValue", bitterValue);
					editor.putInt("maltValue", maltValue);
					editor.putInt("yeastValue", yeastvalue);
					editor.putInt("mouthFeelValue", mouthValue);
					editor.putInt("sourValue", sourValue);
					editor.putInt("additiveValue", additiveValue);
					editor.putInt("boozinessValue", boozinessvalue);
					editor.putString("yeast_status",
							bundle.getString("yeast_status"));
					editor.putString("sour_status", _chkSourStatus);
					editor.putString("booziness_status", _chkBoozinessStatus);
					editor.putString("additive_status", _chkAdditiveStatus);

					editor.commit();

					/*
					 * Intent toGraph=new Intent(EditProfile2.this,Graph.class);
					 * toGraph.putExtra("userId", bundle.getString("userId"));
					 * 
					 * startActivity(toGraph);
					 */

					ShowUserTasteProfile.activity.finish();
					EditProfile.activity.finish();
					UserInfo.activity.finish();
					finish();

				} else if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "User has already added  his taste";
					Toast.makeText(EditProfile2.this, _response,
							Toast.LENGTH_LONG).show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(EditProfile2.this, _response,
							Toast.LENGTH_LONG).show();
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	/**updating params coming from EditProfile
	 * 
	 * 
	 */

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		aromaValue = mPreference.getInt("aroma_temp", 3);
		sweetvalue = mPreference.getInt("sweet_temp", 3);
		bitterValue = mPreference.getInt("bitter_temp", 3);
		maltValue = mPreference.getInt("malt_temp", 3);
		yeastvalue = mPreference.getInt("yeast_temp", 3);
		mouthValue = mPreference.getInt("mouth_temp", 3);

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
	
	private void showMeaningOfParameter(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile2.this);
		builder.setMessage(message);
		builder.setPositiveButton("Dismiss", null);

		builder.show();

	}
}
