package com.craftbeer;

import java.util.ArrayList;

import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.craftbeer.constants.Constants;
import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.facebook.FacebookRequestError;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import com.flurry.android.FlurryAgent;
import com.model.MODEL_AROMA;

public class EditBeerProfile2 extends Activity implements
		OnSeekBarChangeListener, HttpListener {


	String sharingMessage = "";
	private SettingAdapterAdditive adapterAdditive;

	private SettingAdapterWarmth adapterWarmth;

	private SettingAdapterBooziness adapterBooziness;

	private SettingAdapterSour adapterSour;

	private String aromaShow = "";

	private Dialog dialog;

	private String[] _a0 = { "Minimal", "Moderate", "Heavy", "Raspberry",
			"Blueberry", "Elderberry", "Blackberry", "Apple", "Cucumber",
			"Banana", "Strawberry", "Peppers", "Apricot", "Kiwi", "Melon",
			"Mulberry", "Atemoiaberry", "Seabuckthornberry", "Raisins",
			"Goldenraisins", "Cranberry" };

	private String[] _a1 = { "Minimal", "Moderate", "Heavy", "Vanilla", "Sage",
			"Peppercorn", "Salt", "Smoke", "Rosemary", "Thyme", "Coriander",
			"Cumin", "Cinnamon", "Mint", "Rosehips" };

	private String[] _a2 = { "Minimal", "Moderate", "Heavy", "Oak", "Pecan",
			"Mesquite", "Aburana" };

	private String[] _a3 = { "Minimal", "Moderate", "Heavy", "Oak", "Bourbon",
			"Whisky", "Rum", "Tequila", "Chardonnay", "Whitewine", "Cabernet",
			"Redwine", "Port", "Palosanto" };

	private String[] _a4 = { "Minimal", "Moderate", "Heavy", "Pecans",
			"Macaroot", "Chocolate", "Coffee", "Oysters", "Clams",
			"Brettanomyces" };

	final String[] arrayAdditive = new String[] { "Fruit/ vegetable",
			"Spices/ Herbs", "Wood", "Barrel Aging", "Miscellaneous" };

	// array for booziness
	private String[] arrayBooziness = { "light", "moderate", "heavy",
			"residual", "rubbingalcohol", "spicy"

	};
	// array for Warmth
	private String[] arrayWarmth = { "slight", "moderate", "hot", "residual",
			"burning", "rubbingalcohol", "warm" };
	// array for Sour
	private String[] arraySour = { "tart", "vinegar", "puckering", "acidic",
			"horseblanket", "barnyard" };

	private ArrayList<MODEL_AROMA> arrayListBooziness, arrayListSour,
			arrayListWarmth, arrayListAdditive;

	private ArrayList<MODEL_AROMA> a0, a1, a2, a3, a4;
	// comments
	private String comment3 = " The beer's taste was much less than you prefer ";
	private String comment4 = "The beer's taste was somewhat less than you prefer";
	private String comment5 = "The beer's taste met your baseline taste preferences";
	private String comment6 = "The beer's taste was somewhat more than you prefer";
	private String comment7 = "The beer's taste was much more than you prefer";

	private SeekBar _seekBarSour, _seekBarAdditive, _seekBarBoozines;

	private int sourValue = 3, additiveValue = 3, boozinessvalue = 3;

	private int aromaValue = 3, sweetvalue = 3, bitterValue = 3, maltValue = 3,
			yeastvalue = 3, mouthValue = 3;

	Button _btnSave, _btnCancel, _btnInfo;

	private String _response;
	// object for bundle
	private Bundle bundle;

	private Toast _toast;
	// object of view class
	private TextView _txt3Sour, _txt4Sour, _txt5Sour, _txt6Sour, _txt7Sour;
	private TextView _txt3Additive, _txt4Additive, _txt5Additive,
			_txt6Additive, _txt7Additive;

	private TextView _txt3Booziness, _txt4Booziness, _txt5Booziness,
			_txt6Booziness, _txt7Booziness;

	private SharedPreferences preferences;

	private TextView _txtSour, _txtBooziness;
	private TextView _txtAdditive;

	// object of model class
	private MODEL_AROMA model;

	private TextView _txtWarmth;

	private CheckBox _sourChkBx, _additiveChkBx, _boozinessChkBx;

	public static Activity activity;

	private TextView txtSourName, txtBoozinessName, txtAdditiveName;
	
	// Twitter
	private static Twitter twitter;
	private static RequestToken requestToken;
	private AccessToken accessToken;
	private Handler messageHandler = null;
	static String stringMain = null;
	public static final int REQUEST_TWITTER_LOGIN = 111;
	
	// Facebook
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private Session.StatusCallback statusCallbackLogin = new statusCallbackLogin();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_beer_profile_2);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		activity = this;

		// putting values in arrays
		arrayListBooziness = new ArrayList<MODEL_AROMA>();
		arrayListSour = new ArrayList<MODEL_AROMA>();
		arrayListAdditive = new ArrayList<MODEL_AROMA>();
		arrayListWarmth = new ArrayList<MODEL_AROMA>();
		a0=new ArrayList<MODEL_AROMA>();
		a1=new ArrayList<MODEL_AROMA>();
		a2=new ArrayList<MODEL_AROMA>();
		a3=new ArrayList<MODEL_AROMA>();
		a4=new ArrayList<MODEL_AROMA>();
		for (int i = 0; i <_a0.length; i++) {

			model = new MODEL_AROMA();

			model.setName(_a0[i]);

			a0.add(model);

		}
		
		for (int i = 0; i <_a1.length; i++) {

			model = new MODEL_AROMA();

			model.setName(_a1[i]);

			a1.add(model);

		}
		
		for (int i = 0; i <_a2.length; i++) {

			model = new MODEL_AROMA();

			model.setName(_a2[i]);

			a2.add(model);

		}
		
		for (int i = 0; i <_a3.length; i++) {

			model = new MODEL_AROMA();

			model.setName(_a3[i]);

			a3.add(model);

		}
		
		for (int i = 0; i <_a4.length; i++) {

			model = new MODEL_AROMA();

			model.setName(_a4[i]);

			a4.add(model);

		}
		
		

		for (int i = 0; i < arrayBooziness.length; i++) {

			model = new MODEL_AROMA();

			model.setName(arrayBooziness[i]);

			arrayListBooziness.add(model);

		}

		for (int i = 0; i < arraySour.length; i++) {

			model = new MODEL_AROMA();

			model.setName(arraySour[i]);

			arrayListSour.add(model);

		}

		for (int i = 0; i < arrayWarmth.length; i++) {

			model = new MODEL_AROMA();
			model.setName(arrayWarmth[i]);

			arrayListWarmth.add(model);
		}

		for (int i = 0; i < arrayAdditive.length; i++) {
			model = new MODEL_AROMA();
			model.setName(arrayAdditive[i]);

			arrayListAdditive.add(model);
		}

		initializeView();

		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		// getting active session of facebook login
		

		_btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (CheckInternetConnectivity
						.checkinternetconnection(EditBeerProfile2.this)) {

					/* hitting webservice */
					String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editBeerProfile><beerId><![CDATA["
							+ bundle.getString("beerId")
							+ "]]></beerId><userId><![CDATA["
							+ bundle.getString("userId")
							+ "]]></userId><aroma><![CDATA["
							+ String.valueOf(aromaValue)
							+ "]]></aroma><aroma_cmt><![CDATA["
							+ preferences.getString("aroma_cmnt_temp", "")
							+ "]]></aroma_cmt><sweet><![CDATA["
							+ String.valueOf(sweetvalue)
							+ "]]></sweet><sweet_cmt><![CDATA["
							+ preferences.getString("sweet_cmnt_temp", "")
							+ "]]></sweet_cmt><bitter><![CDATA["
							+ String.valueOf(bitterValue)
							+ "]]></bitter><bitter_cmt><![CDATA["
							+ preferences.getString("bitter_cmnt_temp", "")
							+ "]]></bitter_cmt><malt><![CDATA["
							+ String.valueOf(maltValue)
							+ "]]></malt><malt_cmt><![CDATA["
							+ preferences.getString("malt_cmnt_temp", "")
							+ "]]></malt_cmt><yeast><![CDATA["
							+ String.valueOf(yeastvalue)
							+ "]]></yeast><yeast_cmt><![CDATA["
							+ preferences.getString("yeast_cmnt_temp", "")
							+ "]]></yeast_cmt><mouthFeel><![CDATA["
							+ String.valueOf(mouthValue)
							+ "]]></mouthFeel>"
							+ "<mouthFeel_cmt1><![CDATA["
							+ preferences.getString("mouth_feel_cmnt_1_temp",
									"")
							+ "]]></mouthFeel_cmt1><sour><![CDATA["
							+ String.valueOf(sourValue)
							+ "]]></sour><sour_cmt><![CDATA["
							+ _txtSour.getText().toString()
							+ "]]></sour_cmt><additive><![CDATA["
							+ String.valueOf(additiveValue)
							+ "]]></additive><additive_cmt><![CDATA["
							+ _txtAdditive.getText().toString()
							+ "]]></additive_cmt><booziness><![CDATA["
							+ String.valueOf(boozinessvalue)
							+ "]]></booziness><booziness_cmt><![CDATA["
							+ _txtBooziness.getText().toString()
							+ "]]></booziness_cmt>"
							+ "<booziness_cmt1><![CDATA["
							+ _txtWarmth.getText().toString()
							+ "]]></booziness_cmt1></editBeerProfile>";

					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.EDIT_BEER_PROFILE, EditBeerProfile2.this,
							REGISTARTION_XML, EditBeerProfile2.this);
					hitRegistartion.execute();
				} else {

					Toast.makeText(EditBeerProfile2.this,
							"Check Internet Connection", Toast.LENGTH_SHORT)
							.show();
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
		
		initFacebookSession(savedInstanceState);
	}

	/**
	 * Initializing view
	 */

	private void initializeView() {
		// TODO Auto-generated method stub

		preferences = PreferenceManager
				.getDefaultSharedPreferences(EditBeerProfile2.this);

		bundle = getIntent().getExtras();
		aromaValue = preferences.getInt("aroma_beer_temp", 3);
		sweetvalue = preferences.getInt("sweet_beer_temp", 3);
		bitterValue = preferences.getInt("bitter_beer_temp", 3);
		maltValue = preferences.getInt("malt_beer_temp", 3);
		yeastvalue = preferences.getInt("yeast_beer_temp", 3);
		mouthValue = preferences.getInt("mouth_feel_beer_temp", 3);
		sourValue = preferences.getInt("sourValue", 3);
		additiveValue = preferences.getInt("additiveValue", 3);
		boozinessvalue = preferences.getInt("boozinessValue", 3);

		_txtWarmth = (TextView) findViewById(R.id.home_booziness_warmth_edt);

		_btnInfo = (Button) findViewById(R.id.info_btn);
		_btnInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toInfoPage = new Intent(EditBeerProfile2.this,
						InformationPage.class);
				startActivity(toInfoPage);

			}
		});

		_seekBarSour = (SeekBar) findViewById(R.id.home_2_sour_seek_bar);
		_seekBarAdditive = (SeekBar) findViewById(R.id.home_2_additive_seek_bar);
		_seekBarBoozines = (SeekBar) findViewById(R.id.home_2_booziness_seek_bar);
		_btnSave = (Button) findViewById(R.id.home_2_save_btn);
		_btnCancel = (Button) findViewById(R.id.home_2_cancel_btn);

		_seekBarSour.setOnSeekBarChangeListener(this);
		_seekBarAdditive.setOnSeekBarChangeListener(this);
		_seekBarBoozines.setOnSeekBarChangeListener(this);

		// //////////////////////////////////////////////////texts////////////////////////////////////

		/* sour text */

		_txt3Sour = (TextView) findViewById(R.id.sour_text3);
		_txt4Sour = (TextView) findViewById(R.id.sour_text4);
		_txt5Sour = (TextView) findViewById(R.id.sour_text5);
		_txt6Sour = (TextView) findViewById(R.id.sour_text6);
		_txt7Sour = (TextView) findViewById(R.id.sour_text7);

		_txt5Sour.setText(String.valueOf(preferences.getInt("sourValue", 5)));
		_txt3Sour
				.setText(String.valueOf(preferences.getInt("sourValue", 5) - 2));
		_txt4Sour
				.setText(String.valueOf(preferences.getInt("sourValue", 5) - 1));
		_txt6Sour
				.setText(String.valueOf(preferences.getInt("sourValue", 5) + 1));
		_txt7Sour
				.setText(String.valueOf(preferences.getInt("sourValue", 5) + 2));

		_seekBarSour.setProgress(50);

		/* additive text */
		_txt3Additive = (TextView) findViewById(R.id.additive_text3);
		_txt4Additive = (TextView) findViewById(R.id.additive_text4);
		_txt5Additive = (TextView) findViewById(R.id.additive_text5);
		_txt6Additive = (TextView) findViewById(R.id.additive_text6);
		_txt7Additive = (TextView) findViewById(R.id.additive_text7);

		_txt5Additive.setText(String.valueOf(preferences.getInt(
				"additiveValue", 5)));
		_txt3Additive.setText(String.valueOf(preferences.getInt(
				"additiveValue", 5) - 2));
		_txt4Additive.setText(String.valueOf(preferences.getInt(
				"additiveValue", 5) - 1));
		_txt6Additive.setText(String.valueOf(preferences.getInt(
				"additiveValue", 5) + 1));
		_txt7Additive.setText(String.valueOf(preferences.getInt(
				"additiveValue", 5) + 2));

		_seekBarAdditive.setProgress(50);

		/* booziness text */
		_txt3Booziness = (TextView) findViewById(R.id.booziness_text3);
		_txt4Booziness = (TextView) findViewById(R.id.booziness_text4);
		_txt5Booziness = (TextView) findViewById(R.id.booziness_text5);
		_txt6Booziness = (TextView) findViewById(R.id.booziness_text6);
		_txt7Booziness = (TextView) findViewById(R.id.booziness_text7);

		_txt5Booziness.setText(String.valueOf(preferences.getInt(
				"boozinessValue", 5)));
		_txt3Booziness.setText(String.valueOf(preferences.getInt(
				"boozinessValue", 5) - 2));
		_txt4Booziness.setText(String.valueOf(preferences.getInt(
				"boozinessValue", 5) - 1));
		_txt6Booziness.setText(String.valueOf(preferences.getInt(
				"boozinessValue", 5) + 1));
		_txt7Booziness.setText(String.valueOf(preferences.getInt(
				"boozinessValue", 5) + 2));
		_seekBarBoozines.setProgress(50);

		_txtBooziness = (TextView) findViewById(R.id.home_booziness_edt);
		_txtSour = (TextView) findViewById(R.id.home_sour_edt);
		_txtAdditive = (TextView) findViewById(R.id.home_additive_edt);

		_txtAdditive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showDialogAdditive();

			}
		});

		_txtBooziness.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showDialogBooziness();

			}
		});

		_txtSour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialogSour();
			}
		});

		_txtWarmth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialogWarmth();

			}
		});

		_sourChkBx = (CheckBox) findViewById(R.id.home_toggle_sour);
		_additiveChkBx = (CheckBox) findViewById(R.id.home_toggle_additive);
		_boozinessChkBx = (CheckBox) findViewById(R.id.home_toggle_booziness);

		_sourChkBx.setChecked(true);
		_additiveChkBx.setChecked(true);
		_boozinessChkBx.setChecked(true);

		_sourChkBx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (isChecked) {

					_seekBarSour.setEnabled(true);

					sourValue = preferences.getInt("sourValue", 3);
					_seekBarSour.setProgress(50);
					_txtSour.setEnabled(true);
				} else {

					_seekBarSour.setEnabled(false);

					sourValue = 0;
					_seekBarSour.setProgress(0);
					_txtSour.setText("");
					_txtSour.setEnabled(false);

				}

			}
		});

		_additiveChkBx
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub

						if (isChecked) {

							_seekBarAdditive.setEnabled(true);

							additiveValue = preferences
									.getInt("addiveValue", 3);
							_seekBarAdditive.setProgress(50);
							_txtAdditive.setEnabled(true);
						} else {

							_seekBarAdditive.setEnabled(false);

							additiveValue = 0;
							_seekBarAdditive.setProgress(0);
							_txtAdditive.setText("");
							_txtAdditive.setEnabled(false);

						}

					}
				});

		_boozinessChkBx
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub

						if (isChecked) {

							_seekBarBoozines.setEnabled(true);

							boozinessvalue = preferences.getInt(
									"boozinessValue", 3);
							_seekBarBoozines.setProgress(50);
							_txtBooziness.setEnabled(true);
							_txtWarmth.setEnabled(true);
						} else {

							_seekBarBoozines.setEnabled(false);

							boozinessvalue = 0;
							_seekBarBoozines.setProgress(0);
							_txtBooziness.setText("");
							_txtWarmth.setText("");
							_txtBooziness.setEnabled(false);
							_txtWarmth.setEnabled(false);

						}

					}
				});

		txtSourName = (TextView) findViewById(R.id.home_sour_txt);
		txtAdditiveName = (TextView) findViewById(R.id.home_additive_txt);
		txtBoozinessName = (TextView) findViewById(R.id.home_booziness_txt);

		txtSourName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showMeaningOfParameter("How puckering is the beer? Is there a specific flavor associated with the sour / tart experience?");

			}
		});

		txtAdditiveName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("How well did the additive come through? Did it overpower the base beer? Was the additive even detectable?");

			}
		});

		txtBoozinessName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Does the beer have an alcohol flavor of hard liquor? Does it have a warm, spicy, or burning sensation?");

			}
		});

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		/*
		 * switch (seekBar.getId()) { case R.id.home_2_sour_seek_bar:
		 * 
		 * sourValue= (progress / 25)+3;
		 * 
		 * break;
		 * 
		 * case R.id.home_2_additive_seek_bar: additiveValue= (progress / 25)+3;
		 * break; case R.id.home_2_booziness_seek_bar: boozinessvalue= (progress
		 * / 25)+3;
		 * 
		 * break; }
		 */

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

		switch (seekBar.getId()) {
		case R.id.home_2_sour_seek_bar:
			if (_seekBarSour.getProgress() >= 0
					&& _seekBarSour.getProgress() < 25) {
				sourValue = preferences.getInt("sourValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
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
			} else if (_seekBarSour.getProgress() >= 50
					&& _seekBarSour.getProgress() < 75) {
				sourValue = preferences.getInt("sourValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
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
			} else if (_seekBarSour.getProgress() == 100) {
				sourValue = preferences.getInt("sourValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
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

			} else if (_seekBarSour.getProgress() >= 25
					&& _seekBarSour.getProgress() < 50) {
				sourValue = preferences.getInt("sourValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
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
			} else if (_seekBarSour.getProgress() >= 75
					&& _seekBarSour.getProgress() < 100) {
				sourValue = preferences.getInt("sourValue", 5) + 1;

				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
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

		case R.id.home_2_additive_seek_bar:
			if (_seekBarAdditive.getProgress() >= 0
					&& _seekBarAdditive.getProgress() < 25) {
				additiveValue = preferences.getInt("additiveValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
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
			} else if (_seekBarAdditive.getProgress() >= 50
					&& _seekBarAdditive.getProgress() < 75) {
				additiveValue = preferences.getInt("additiveValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
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
			} else if (_seekBarAdditive.getProgress() == 100) {
				additiveValue = preferences.getInt("additiveValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
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

			} else if (_seekBarAdditive.getProgress() >= 25
					&& _seekBarAdditive.getProgress() < 50) {
				additiveValue = preferences.getInt("additiveValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
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
			} else if (_seekBarAdditive.getProgress() >= 75
					&& _seekBarAdditive.getProgress() < 100) {
				additiveValue = preferences.getInt("additiveValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
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
		case R.id.home_2_booziness_seek_bar:
			if (_seekBarBoozines.getProgress() >= 0
					&& _seekBarBoozines.getProgress() < 25) {
				boozinessvalue = preferences.getInt("boozinessValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
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
			} else if (_seekBarBoozines.getProgress() >= 50
					&& _seekBarBoozines.getProgress() < 75) {
				boozinessvalue = preferences.getInt("boozinessValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
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
			} else if (_seekBarBoozines.getProgress() == 100) {
				boozinessvalue = preferences.getInt("boozinessValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
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

			} else if (_seekBarBoozines.getProgress() >= 25
					&& _seekBarBoozines.getProgress() < 50) {
				boozinessvalue = preferences.getInt("boozinessValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
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
			} else if (_seekBarBoozines.getProgress() >= 75
					&& _seekBarBoozines.getProgress() < 100) {
				boozinessvalue = preferences.getInt("boozinessValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
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

		if (response.contains("editBeerProfile")) {
			try {
				registerJsonObj = new JSONObject(response);
				String strUserRegistration = registerJsonObj
						.getString("editBeerProfile");
				if (strUserRegistration.equalsIgnoreCase("1")) 
				{
					_response = "Beer Profile Successfully created";
					Toast.makeText(EditBeerProfile2.this, _response,
							Toast.LENGTH_LONG).show();

					if (preferences.getBoolean(Constants.AUTO_SHARE_TWITTER,	false)) {
						Constants.ShowProgress(this);
						if(FindOrAddBeer.twitterHandle.trim().length() > 0)
						{
							stringMain = "I just profiled  @"
								//	+ preferences.getString("BREWERY_NAME", "")	.replaceAll(" ", "")
									+ FindOrAddBeer.twitterHandle
									+ " #"
									+ preferences.getString("PROFILED_BEER_NAME",
											"").replaceAll(" ", "")
									+ " with @BrewHornBeerApp. #brewhorn ";
						}
						else
						{
						stringMain = "I just profiled  #"
								+ preferences.getString("BREWERY_NAME", "")
										.replaceAll(" ", "")
								+ " #"
								+ preferences.getString("PROFILED_BEER_NAME",
										"").replaceAll(" ", "")
								+ " with @BrewHornBeerApp. #brewhorn ";
						}
						Log.e("sendTweet", ":" + stringMain);
						sendTweet(stringMain);
					} else if (preferences.getBoolean(
							Constants.AUTO_SHARE_FACEBOOK, false)) {
						Constants.ShowProgress(this);
						
						loginToFacebook();
					}
					else
					{
						finish();
						EditBeerProfile.activity.finish();
						EditBeerProfile1.activity.finish();
						EditUserBeer.activity.finish();
						UserBeerProfile.activity.finish();
					}
			
				} else if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "Invalid Beer";
					Toast.makeText(EditBeerProfile2.this, _response,
							Toast.LENGTH_LONG).show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(EditBeerProfile2.this, _response,
							Toast.LENGTH_LONG).show();
				} else if (strUserRegistration.equalsIgnoreCase("-3")) {
					_response = "Invalid User";
					Toast.makeText(EditBeerProfile2.this, _response,
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

	public void showDialogBooziness() {
		aromaShow = "";

		dialog = new Dialog(EditBeerProfile2.this);

		dialog.setTitle("Select Descriptor for Flavour");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListBooziness.size(); i++) {
					if (arrayListBooziness.get(i).isChecked() == true) {
						if (!aromaShow.equals("")) {
							aromaShow = aromaShow + ","
									+ arrayListBooziness.get(i).getName();
						} else {
							aromaShow = arrayListBooziness.get(i).getName();
						}
					}
				}
				_txtBooziness.setText(aromaShow);
				dialog.dismiss();
				aromaShow = "";
			}
		});
		adapterBooziness = new SettingAdapterBooziness(arrayListBooziness);
		list.setAdapter(adapterBooziness);
		dialog.show();
	}

	public class SettingAdapterBooziness extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterBooziness(ArrayList<MODEL_AROMA> array2) {
			// TODO Auto-generated constructor stub
			this.array1 = array2;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return array1.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater
					.from(EditBeerProfile2.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (arrayListBooziness.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);

			} else {
				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListBooziness.get(position).setChecked(
							!arrayListBooziness.get(position).isChecked());
					adapterBooziness.notifyDataSetChanged();

				}
			});

			return convertView;
		}

	}

	class ViewHolder {
		TextView txt;
		ImageView chkBx;
	}

	public void showDialogSour() {
		aromaShow = "";

		dialog = new Dialog(EditBeerProfile2.this);

		dialog.setTitle("Select Descriptor for Sourness");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListSour.size(); i++) {

					if (arrayListSour.get(i).isChecked() == true) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListSour.get(i).getName();
						} else {

							aromaShow = arrayListSour.get(i).getName();
						}
					}
				}

				_txtSour.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

		adapterSour = new SettingAdapterSour(arrayListSour);

		list.setAdapter(adapterSour);

		dialog.show();

	}

	public void showDialogWarmth() {
		aromaShow = "";

		dialog = new Dialog(EditBeerProfile2.this);

		dialog.setTitle("Select Descriptor for Warmth");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListWarmth.size(); i++) {

					if (arrayListWarmth.get(i).isChecked() == true) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListWarmth.get(i).getName();
						} else {

							aromaShow = arrayListWarmth.get(i).getName();
						}
					}
				}

				_txtWarmth.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

		adapterWarmth = new SettingAdapterWarmth(arrayListWarmth);
		list.setAdapter(adapterWarmth);

		dialog.show();

	}

	public class SettingAdapterWarmth extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterWarmth(ArrayList<MODEL_AROMA> array2) {
			// TODO Auto-generated constructor stub
			this.array1 = array2;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return array1.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater
					.from(EditBeerProfile2.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (arrayListWarmth.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);

			} else {
				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListWarmth.get(position).setChecked(
							!arrayListWarmth.get(position).isChecked());
					adapterWarmth.notifyDataSetChanged();

				}
			});

			return convertView;
		}

	}

	public class SettingAdapterSour extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterSour(ArrayList<MODEL_AROMA> array2) {
			// TODO Auto-generated constructor stub
			this.array1 = array2;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return array1.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater
					.from(EditBeerProfile2.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (arrayListSour.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);

			} else {
				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListSour.get(position).setChecked(
							!arrayListSour.get(position).isChecked());
					adapterSour.notifyDataSetChanged();

				}
			});

			return convertView;
		}

	}

	/**
	 * updating values of taste params
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		preferences = PreferenceManager
				.getDefaultSharedPreferences(EditBeerProfile2.this);

		aromaValue = preferences.getInt("aroma_beer_temp", 3);
		sweetvalue = preferences.getInt("sweet_beer_temp", 3);
		bitterValue = preferences.getInt("bitter_beer_temp", 3);
		maltValue = preferences.getInt("malt_beer_temp", 3);
		yeastvalue = preferences.getInt("yeast_beer_temp", 3);
		mouthValue = preferences.getInt("mouth_feel_beer_temp", 3);
		sourValue = preferences.getInt("sourValue", 3);
		additiveValue = preferences.getInt("additiveValue", 3);
		boozinessvalue = preferences.getInt("boozinessValue", 3);
	}

	/**
	 * method for dialog of additive
	 */

	private void alert() {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				EditBeerProfile2.this);
		builder.setMessage("Share your BrewHorn moment now!");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startActivity(new Intent(EditBeerProfile2.this,
						SocialNetworkSharing.class).putExtra("via", "Add"));

				finish();

				EditBeerProfile.activity.finish();
				EditBeerProfile1.activity.finish();
				EditUserBeer.activity.finish();
				UserBeerProfile.activity.finish();

			}
		});
		builder.show();
	}

	public class SettingAdapterAdditive extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterAdditive(ArrayList<MODEL_AROMA> array2) {
			// TODO Auto-generated constructor stub
			this.array1 = array2;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return array1.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater
					.from(EditBeerProfile2.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (array1.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);

			} else {
				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					array1.get(position).setChecked(
							!array1.get(position).isChecked());
					adapterAdditive.notifyDataSetChanged();

				}
			});

			return convertView;
		}

	}

	public void showDialogAdditive() {
		aromaShow = "";

		dialog = new Dialog(EditBeerProfile2.this);

		dialog.setTitle("Select Descriptor for Additiveness");

		dialog.setContentView(R.layout.dialog_list);

		final ListView list = (ListView) dialog.findViewById(R.id.list);
		list.setVisibility(View.GONE);
		final ListView listNew = (ListView) dialog.findViewById(R.id.list_new);

		listNew.setAdapter(new ArrayAdapter<String>(EditBeerProfile2.this,
				android.R.layout.simple_list_item_1, arrayAdditive));
		listNew.setVisibility(View.VISIBLE);
		listNew.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				arrayListAdditive=new ArrayList<MODEL_AROMA>();
				if(arg2==0){
					for (int i = 0; i < _a0.length; i++) {

						model = new MODEL_AROMA();

						model.setName(_a0[i]);

						arrayListAdditive.add(model);

					}
					}else if(arg2==1){
						for (int i = 0; i < _a1.length; i++) {

							model = new MODEL_AROMA();

							model.setName(_a1[i]);

							arrayListAdditive.add(model);
						}
						}else if(arg2==2){
							for (int i = 0; i < _a2.length; i++) {

								model = new MODEL_AROMA();

								model.setName(_a2[i]);

								arrayListAdditive.add(model);
							}
							}else if(arg2==3){
								for (int i = 0; i < _a3.length; i++) {

									model = new MODEL_AROMA();

									model.setName(_a3[i]);

									arrayListAdditive.add(model);
								}
							}else if(arg2==4){
								for (int i = 0; i < _a4.length; i++) {

									model = new MODEL_AROMA();

									model.setName(_a4[i]);

									arrayListAdditive.add(model);
								}
							}
				
				/*if(arg2==0){
				arrayListAdditive=a0;
				}else if(arg2==1){
					arrayListAdditive=a1;
					}else if(arg2==2){
						arrayListAdditive=a2;
						}else if(arg2==3){
							arrayListAdditive=a3;
						}else if(arg2==4){
							arrayListAdditive=a4;
						}
				*/
				
				
				
				adapterAdditive = new SettingAdapterAdditive(arrayListAdditive);

				list.setAdapter(adapterAdditive);
				list.setVisibility(View.VISIBLE);
				listNew.setVisibility(View.GONE);

			}
		});
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListAdditive.size(); i++) {

					if (arrayListAdditive.get(i).isChecked() == true) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListAdditive.get(i).getName();
						} else {

							aromaShow = arrayListAdditive.get(i).getName();
						}
					}
				}

				_txtAdditive.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

	

		dialog.show();

	}

	

	private void showMeaningOfParameter(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				EditBeerProfile2.this);
		builder.setMessage(message);
		builder.setPositiveButton("Dismiss", null);

		builder.show();

	}

	
	// Facebook and Twitter Integration
	
	private void closeActivity() 
	{
		Constants.DismissProgress();
		finish();
		EditBeerProfile.activity.finish();
		EditBeerProfile1.activity.finish();
		EditUserBeer.activity.finish();
		UserBeerProfile.activity.finish();
	}

	
	// Twitter Integration
	
	private void checTwitterLogin(Uri uri) {
		if (!isTwitterLoggedInAlready()) {
			if (uri != null) {
			} else {
				uri = getIntent().getData();
			}
			if (uri != null
					&& uri.toString()
							.startsWith(Constants.TWITTER_CALLBACK_URL)) {
				// oAuth verifier
				final String verifier = uri
						.getQueryParameter(Constants.URL_TWITTER_OAUTH_VERIFIER);
				try {
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								// Get the access token
								accessToken = twitter.getOAuthAccessToken(
										requestToken, verifier);
								// Shared Preferences
								Editor e = preferences.edit();
								e.putString(Constants.PREF_KEY_OAUTH_TOKEN,
										accessToken.getToken());
								e.putString(Constants.PREF_KEY_OAUTH_SECRET,
										accessToken.getTokenSecret());
								// Store login status - true
								e.putBoolean(Constants.PREF_KEY_TWITTER_LOGIN,
										true);
								e.commit(); // save changes
								Log.e("Twitter OAuth Token",
										"> " + accessToken.getToken());
								messageHandler.sendEmptyMessage(0);
							} catch (Exception e) {
								e.printStackTrace();
								if (!isFacebookShare()) {
									Constants.DismissProgress();
								}
							}
						}
					});
					thread.start();
					messageHandler = new Handler() {
						public void handleMessage(Message message) {
							super.handleMessage(message);
							new updateTwitterStatus().execute(stringMain);
						}
					};
				} catch (Exception e) {
					Log.e("Twitter Login Error", "> " + e.getMessage());
					e.printStackTrace();
					if (!isFacebookShare()) {
						Constants.DismissProgress();
					}
				}
			}
		}
	}

	private void sendTweet(String msg) {
		// Check if Internet present
		if (!CheckInternetConnectivity
				.checkinternetconnection(EditBeerProfile2.this)) {
			Log.d("sendTweet  ",
					"Please connect to working Internet connection");
			closeActivity();
			return;
		}

		if (!isTwitterLoggedInAlready()) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ConfigurationBuilder builder = new ConfigurationBuilder();
						builder.setOAuthConsumerKey(Constants.TWITTER_CONSUMER_KEY);
						builder.setOAuthConsumerSecret(Constants.TWITTER_CONSUMER_SECRET);
						twitter4j.conf.Configuration configuration = builder
								.build();
						TwitterFactory factory = new TwitterFactory(
								configuration);
						twitter = factory.getInstance();
						requestToken = twitter
								.getOAuthRequestToken(Constants.TWITTER_CALLBACK_URL);

						messageHandler.sendEmptyMessage(0);
					} catch (Exception e) {
						e.printStackTrace();
						  if(!isFacebookShare())
						    {
						    	   closeActivity();
						    }
					}
				}
			});
			thread.start();
			messageHandler = new Handler() {
				public void handleMessage(Message message) {
					super.handleMessage(message);
					Intent intent = new Intent(EditBeerProfile2.this,
							TwitterLoginWebviewActivity.class);
					intent.putExtra("URL",
							(String) requestToken.getAuthenticationURL());
					startActivityForResult(intent, REQUEST_TWITTER_LOGIN);
				}
			};
		} else {
			Log.d("Tweet login ", "Already Logged In");
			new updateTwitterStatus().execute(msg);
		}
	}

	/**
	 * Function to update status
	 * */
	class updateTwitterStatus extends AsyncTask<String, String, String> {
		boolean statusOk = false;
		String message = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... args) {
			Log.d("Tweet Text", "> " + args[0]);
			String status = args[0];
			try {
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(Constants.TWITTER_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(Constants.TWITTER_CONSUMER_SECRET);
				// Access Token
				String access_token = preferences.getString(
						Constants.PREF_KEY_OAUTH_TOKEN, "");
				// Access Token Secret
				String access_token_secret = preferences.getString(
						Constants.PREF_KEY_OAUTH_SECRET, "");
				AccessToken accessToken = new AccessToken(access_token,
						access_token_secret);
				Twitter twitter = new TwitterFactory(builder.build())
						.getInstance(accessToken);
				// Only Single Tweet
				twitter4j.Status response = twitter.updateStatus(status);
				Log.d("Status", "> " + response.getText());
				statusOk = true;
			} catch (TwitterException e) {
				// Error in updating status
				Log.e("Twitter Update Error", e.getMessage());
				message = e.getMessage();
				statusOk = false;
				closeActivity();
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog and show
		 * the data in UI Always use runOnUiThread(new Runnable()) to update UI
		 * from background thread, otherwise you will get error
		 * **/
		protected void onPostExecute(String file_url) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (statusOk) {
						Toast.makeText(getApplicationContext(),
								"Message tweeted successfully !",
								Toast.LENGTH_LONG).show();
						Log.e("", "Message tweeted successfully !");
						if (!isFacebookShare()) {
							closeActivity();
						}
					} else {
						if (!isFacebookShare()) {
							if (message != null) {
								Log.e("Twiiter", ":" + message);
							}
							closeActivity();
						}
					}
				}
			});
		}
	}

	/**
	 * Check user already logged in your application using twitter Login flag is
	 * fetched from Shared Preferences
	 * */
	private boolean isTwitterLoggedInAlready() {
		return preferences.getBoolean(Constants.PREF_KEY_TWITTER_LOGIN, false);
	}
	// facebook

	private boolean isFacebookShare() {
		if (preferences.getBoolean(Constants.AUTO_SHARE_FACEBOOK, false)) {
			Log.e("Post loginToFacebook ", ":Facebook");
			loginToFacebook();		
			return true;
		} else {
			return false;
		}
	}

	private void loginToFacebook() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this)
					.setCallback(statusCallbackLogin));
		} else {
			Session.openActiveSession(this, true, statusCallbackLogin);
		}
	}

	private void initFacebookSession(Bundle savedInstanceState) 
	{
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) 
		{
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) 
			{
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {			
		}
	}

	private class statusCallbackLogin implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			Log.e("statusCallbackLogin session", ":" + session);
			if (session.isOpened()) 
			{
				performPublish();
			}
			else  if (state.equals(SessionState.CLOSED_LOGIN_FAILED))
			{
			        	Log.e("Clear", ": Session"  );
			            session.closeAndClearTokenInformation();
			            Toast.makeText(getApplicationContext(), "Failed to post",Toast.LENGTH_LONG).show();
			            closeActivity();
			}
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
		FlurryAgent.onStartSession(this,
				com.craftbeer.constants.Constants.FLURRY_KEY);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
		FlurryAgent.onEndSession(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("requestCode", ":" + requestCode);
		Log.e("resultCode", ":" + resultCode);
		if (requestCode == REQUEST_TWITTER_LOGIN) {
			if (resultCode == RESULT_OK) {
				checTwitterLogin(Uri.parse(data.getStringExtra("URL")));
			}
		} else {
			if(resultCode == RESULT_OK)
			{
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);
			}
			else
			{
				Session session = Session.getActiveSession();
		        if (!session.isClosed()) {
		        	Log.e("Clear", ": Session"  );
		            session.closeAndClearTokenInformation();
		        }
				closeActivity();
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null
				&& session.getPermissions().contains("publish_actions");
	}

	private void performPublish() {

		Session session = Session.getActiveSession();
		if (session != null) {
			if (hasPublishPermission()) {
				Log.e("performPublish ", " :  postStatusUpdate");
				postStatusUpdate();
				return;
			} else if (session.isOpened()) {
				session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
						this, Constants.PERMISSION));
				return;
			}
		}
	}

	private void postStatusUpdate() 
	{
		
//		Log.e("FindOrAddBeer.facebookHandle", ":"+FindOrAddBeer.facebookHandle);
//		Log.e("FindOrAddBeer.facebookUrl", ":"+FindOrAddBeer.facebookUrl);
//		Log.e("FindOrAddBeer.twitterHandle", ":"+FindOrAddBeer.twitterHandle);
		
		if(FindOrAddBeer.facebookHandle.trim().length() > 0)
		{
			stringMain = "I just profiled  @"
				//	+ preferences.getString("BREWERY_NAME", "")	.replaceAll(" ", "")
					+ FindOrAddBeer.facebookHandle
					+ " #"
					+ preferences.getString("PROFILED_BEER_NAME",
							"").replaceAll(" ", "")
					+ " with @BrewHornBeerApp. #brewhorn ";
		}
		else
		{
		stringMain = "I just profiled  #"
				+ preferences.getString("BREWERY_NAME", "")
						.replaceAll(" ", "")
				+ " #"
				+ preferences.getString("PROFILED_BEER_NAME",
						"").replaceAll(" ", "")
				+ " with @BrewHornBeerApp. #brewhorn ";
		}
		Log.e("postStatusUpdate", ":"+stringMain);
		if (hasPublishPermission()) {
			Request request = Request.newStatusUpdateRequest(
					Session.getActiveSession(), stringMain,
					new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							showPublishResult(stringMain,
									response.getGraphObject(),
									response.getError());
						}
					});
			Bundle bExtra = new Bundle();
			if(FindOrAddBeer.facebookUrl.trim().length() > 0)
				bExtra.putString("link",	FindOrAddBeer.facebookUrl);
			bExtra.putString("message", stringMain);
			request.setParameters(bExtra);
			request.executeAsync();
		}
	}

	private interface GraphObjectWithId extends GraphObject {
		String getId();
	}

	private void showPublishResult(String message, GraphObject result,
			FacebookRequestError error) {
		String alertMessage = null;
		if (error == null) {
			String id = result.cast(GraphObjectWithId.class).getId();
			// alertMessage = getString(R.string.successfully_posted_post,
			// message, id);
			alertMessage = "Message posted to your facebook wall successfully !";
		} else {
			alertMessage = error.getErrorMessage();
		}
		Toast.makeText(getApplicationContext(), alertMessage, Toast.LENGTH_LONG)
				.show();
		closeActivity();
	}


}
