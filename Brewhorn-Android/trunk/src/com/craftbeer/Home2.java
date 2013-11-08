package com.craftbeer;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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

public class Home2 extends Activity implements OnSeekBarChangeListener,
		HttpListener {

	/**
	 * @author arvind.agarwal this class is useful in filling remaining three
	 *         params sour ,additive and booziness
	 */

	private TextView txtSourName, txtBoozinessName, txtAdditiveName;

	public static Activity activity;

	// string obj comment for baseline
	private String baseLine = "You prefer an average amount";

	private String _chkSourStatus = "1", _chkBoozinessStatus = "1",
			_chkAdditiveStatus = "1";

	// objects of view class
	private SeekBar _seekBarSour, _seekBarAdditive, _seekBarBoozines;

	private int sourValue = 3, additiveValue = 3, boozinessvalue = 3;

	private int aromaValue = 3, sweetvalue = 3, bitterValue = 3, maltValue = 3,
			yeastvalue = 3, mouthValue = 3;

	private Button _btnSave, _btnCancel;

	private TextView _textTitle;

	private String _response;

	private SharedPreferences mPreference;

	private SharedPreferences.Editor editor;

	// object of bundle
	Bundle bundle;

	private Toast _toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_screen_2);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		activity = this;

		initializeView();

		_btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (CheckInternetConnectivity
						.checkinternetconnection(Home2.this)) {
					
					FlurryAgent.logEvent("UserTasteProfile");

					// hit for user taste profile

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
							+ "]]></booziness><sour_status><![CDATA["
							+ _chkSourStatus
							+ "]]></sour_status><additive_status><![CDATA["
							+ _chkAdditiveStatus
							+ "]]></additive_status><booziness_status><![CDATA["
							+ _chkBoozinessStatus
							+ "]]></booziness_status><yeast_status><![CDATA["
							+ bundle.getString("yeast_status")
							+ "]]></yeast_status></editUserTasteProfile>";

					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.USER_TASTE_EDIT_URL, Home2.this,
							REGISTARTION_XML, Home2.this);
					hitRegistartion.execute();
				} else {

					Toast.makeText(Home2.this, "Check Internet Connection",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		_btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});
	}

	/**
	 * initiating variables
	 */
	private void initializeView() {
		// TODO Auto-generated method stub
		mPreference = PreferenceManager.getDefaultSharedPreferences(Home2.this);
		mPreference.getString("userId", "");
		mPreference.getInt("sourValue", 0);
		mPreference.getInt("additiveValue", 0);
		mPreference.getInt("boozinessValue", 0);

		bundle = getIntent().getExtras();

		/* by default values are set 3 */
		aromaValue = mPreference.getInt("aroma_temp", 3);
		sweetvalue = mPreference.getInt("sweet_temp", 3);
		bitterValue = mPreference.getInt("bitter_temp", 3);
		maltValue = mPreference.getInt("malt_temp", 3);
		yeastvalue = mPreference.getInt("yeast_temp", 3);
		mouthValue = mPreference.getInt("mouth_temp", 3);

		_seekBarSour = (SeekBar) findViewById(R.id.home_sour_seek_bar);
		_seekBarAdditive = (SeekBar) findViewById(R.id.home_additive_seek_bar);
		_seekBarBoozines = (SeekBar) findViewById(R.id.home_booziness_seek_bar);
		_btnSave = (Button) findViewById(R.id.home_2_save_btn);
		_btnCancel = (Button) findViewById(R.id.home_2_cancel_btn);

		// textName
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
		_seekBarSour.setOnSeekBarChangeListener(this);
		_seekBarAdditive.setOnSeekBarChangeListener(this);
		_seekBarBoozines.setOnSeekBarChangeListener(this);

		_textTitle = (TextView) findViewById(R.id.title);
		/* _textTitle.setText(bundle.getString("title")); */

		_textTitle.setText("Optional Taste Parameters");

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

		// As progress lies in b/w 0 and 100 and we need a value in b/w 3 and 7
		// below given formula gives value in 3 and 7
		// eg. say progress 50 the sourValue will come 5
		switch (seekBar.getId()) {
		case R.id.home_sour_seek_bar:

			sourValue = (progress / 25) + 3;

			break;

		case R.id.home_additive_seek_bar:
			additiveValue = (progress / 25) + 3;

			break;
		case R.id.home_booziness_seek_bar:
			boozinessvalue = (progress / 25) + 3;

			break;
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	/**
	 * this method is called on stop progress. this sets the values of 6
	 * parameters according to position of seek bar 0 to 24 -------3 25 to
	 * 49-------4 50 to74-------5 75 to 99------6 100-----7
	 * 
	 */

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

		switch (seekBar.getId()) {
		case R.id.home_sour_seek_bar:
			if (sourValue == 3) {
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
			} else if (sourValue == 5) {
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

			} else if (sourValue == 7) {
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

			} else if (sourValue == 4) {
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
			} else if (sourValue == 6) {

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
			if (additiveValue == 3) {
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
			} else if (additiveValue == 5) {
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
			} else if (additiveValue == 7) {
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

			} else if (additiveValue == 4) {
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
			} else if (additiveValue == 6) {

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
			if (boozinessvalue == 3) {
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
			} else if (boozinessvalue == 5) {
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
			} else if (boozinessvalue == 7) {
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

			} else if (boozinessvalue == 4) {
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
			} else if (boozinessvalue == 6) {

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

	/**
	 * Getting response of webserver hit for remaining 3 params
	 * 
	 */

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
					_response = "Taste Profile Successfully created";
					Toast.makeText(Home2.this, _response, Toast.LENGTH_LONG)
							.show();

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
					editor.putString("userId", bundle.getString("userId"));

					editor.commit();

					Intent toGraph = new Intent(Home2.this, FindOrAddBeer.class);
					toGraph.putExtra("userId", bundle.getString("userId"));

					startActivity(toGraph);

					HomeScreen.home1.finish();
					finish();

				} else if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "User has already added  his taste";
					Toast.makeText(Home2.this, _response, Toast.LENGTH_LONG)
							.show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(Home2.this, _response, Toast.LENGTH_LONG)
							.show();
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

	// updating values 6 params coming HomeScreen
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

	private void showMeaningOfParameter(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(Home2.this);
		builder.setMessage(message);
		builder.setPositiveButton("Dismiss", null);

		builder.show();

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
