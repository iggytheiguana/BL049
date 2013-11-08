package com.craftbeer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.craftbeer.EditBeerProfile2.SettingAdapterAdditive;
import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.flurry.android.FlurryAgent;
import com.model.MODEL_AROMA;

public class AddBeerProfile2 extends Activity implements
		OnSeekBarChangeListener, HttpListener {

	private SettingAdapterWarmth adapterWarmth;

	private SettingAdapterBooziness adapterBooziness;

	private SettingAdapterSour adapterSour;

	private SettingAdapterAdditive adapterAdditive;

	private String aromaShow = "";

	private Dialog dialog;

	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");

	private TextView txtSourName, txtBoozinessName, txtAdditiveName;
	
	
	
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

	private String[] arrayBooziness = { "light", "moderate", "heavy",
			"residual", "rubbingalcohol", "spicy"

	};

	private String[] arrayWarmth = { "slight", "moderate", "hot", "residual",
			"burning", "rubbingalcohol", "warm" };

	private String[] arraySour = { "tart", "vinegar", "puckering", "acidic",
			"horseblanket", "barnyard" };
	final String[] arrayAdditive = new String[] { "Fruit/ vegetable",
			"Spices/ Herbs", "Wood", "Barrel Aging", "Miscellaneous" };

	private ArrayList<MODEL_AROMA> arrayListBooziness, arrayListSour,
			arrayListWarmth, arrayListAdditive;

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

	private Bundle bundle;

	private Toast _toast;

	private TextView _txt3Sour, _txt4Sour, _txt5Sour, _txt6Sour, _txt7Sour;
	private TextView _txt3Additive, _txt4Additive, _txt5Additive,
			_txt6Additive, _txt7Additive;

	private TextView _txt3Booziness, _txt4Booziness, _txt5Booziness,
			_txt6Booziness, _txt7Booziness;

	private SharedPreferences preferences;

	private TextView _txtSour, _txtBooziness;

	private TextView _txtAdditive;
	private MODEL_AROMA model;

	private TextView _txtWarmth;

	public static Activity activity;

	private CheckBox _sourChkBx, _additiveChkBx, _boozinessChkBx;

	Session session;

	private com.facebook.Session.StatusCallback statusCallback = new SessionStatusCallback();

	String sharingMessage = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_beer_profile_2);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		activity = this;
		arrayListBooziness = new ArrayList<MODEL_AROMA>();
		arrayListSour = new ArrayList<MODEL_AROMA>();

		arrayListWarmth = new ArrayList<MODEL_AROMA>();
		arrayListAdditive = new ArrayList<MODEL_AROMA>();

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

		_btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (CheckInternetConnectivity
						.checkinternetconnection(AddBeerProfile2.this)) {

					String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><addBeerProfile><beerId><![CDATA["
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
							+ "]]></mouthFeel><mouthFeel_cmt><![CDATA["
							+ preferences.getString("mouth_feel_cmnt_temp", "")
							+ "]]></mouthFeel_cmt>"
							+ ""
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
							+ "]]></booziness>"
							+ "<booziness_cmt1><![CDATA["
							+ _txtWarmth.getText().toString()
							+ "]]></booziness_cmt1></addBeerProfile>";

					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.ADD_BEER_PROFILE, AddBeerProfile2.this,
							REGISTARTION_XML, AddBeerProfile2.this);
					hitRegistartion.execute();
				} else {

					Toast.makeText(AddBeerProfile2.this,
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
	}

	private void initializeView() {
		// TODO Auto-generated method stub

		preferences = PreferenceManager
				.getDefaultSharedPreferences(AddBeerProfile2.this);

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

		_txtWarmth = (TextView) findViewById(R.id.home_booziness_warmth_edt);
		_btnInfo = (Button) findViewById(R.id.info_btn);
		_btnInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(AddBeerProfile2.this,
						InformationPage.class);
				startActivity(i);

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

		if (response.contains("addBeerProfile")) {

			try {

				registerJsonObj = new JSONObject(response);
				String strUserRegistration = registerJsonObj
						.getString("addBeerProfile");
				if (strUserRegistration.equalsIgnoreCase("1")) {
					_response = "Beer Profile Successfully created";
					Toast.makeText(AddBeerProfile2.this, _response,
							Toast.LENGTH_LONG).show();

					if (preferences.getBoolean("AUTO_SHARE", false)) {
						
						
						String stringMain = "I just profiled #"
								+ preferences.getString("BREWERY_NAME", "").replaceAll(" ", "") + " #"
								+ preferences.getString("PROFILED_BEER_NAME", "").replaceAll(" ","")
								+ " with @brewhornbeerapp.#brewhorn #craftbeer";
						
					String tweetUrl=""	;
						try {
							tweetUrl = "https://twitter.com/intent/tweet?text= "
									+ URLEncoder.encode(stringMain, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						

						Uri uri = Uri.parse(tweetUrl);
						startActivity(new Intent(Intent.ACTION_VIEW, uri));
						
						AddBeerProfile2.this.finish();

						AddBeerProfile1.activity.finish();
						AddBeerProfile.addBeerProfile.finish();
				/*		if (session != null && session.isOpened()) {
							sharingMessage = "I just profiled #"
									+ preferences.getString("BREWERY_NAME", "")
											.replaceAll(" ", "")
									+ " #"
									+ preferences.getString(
											"PROFILED_BEER_NAME", "")
											.replaceAll(" ", "")
									+ " with @brewhornbeerapp.#brewhorn #craftbeer";

							Request request = Request.newStatusUpdateRequest(
									session, sharingMessage, new Callback() {

										@Override
										public void onCompleted(
												Response response) {
											// TODO Auto-generated method stub
											showAlert();
										}
									});
							request.executeAsync();

						} else {
							onClickLogin();
						}*/
					} else {

						AddBeerProfile.addBeerProfile.finish();
						AddBeerProfile1.activity.finish();
						AddBeerProfile2.this.finish();
					}

				} else if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "Invalid Beer";
					Toast.makeText(AddBeerProfile2.this, _response,
							Toast.LENGTH_LONG).show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(AddBeerProfile2.this, _response,
							Toast.LENGTH_LONG).show();
				} else if (strUserRegistration.equalsIgnoreCase("-3")) {
					_response = "Invalid User";
					Toast.makeText(AddBeerProfile2.this, _response,
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

		dialog = new Dialog(AddBeerProfile2.this);

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
			LayoutInflater inflater = LayoutInflater.from(AddBeerProfile2.this);
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

		dialog = new Dialog(AddBeerProfile2.this);

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

	public void showDialogAdditive() {
		aromaShow = "";

		dialog = new Dialog(AddBeerProfile2.this);

		dialog.setTitle("Select Descriptor for Additiveness");

		dialog.setContentView(R.layout.dialog_list);

		final ListView list = (ListView) dialog.findViewById(R.id.list);
		
		list.setVisibility(View.GONE);
		final ListView listNew = (ListView) dialog.findViewById(R.id.list_new);

		listNew.setAdapter(new ArrayAdapter<String>(AddBeerProfile2.this,
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

	public void showDialogWarmth() {
		aromaShow = "";

		dialog = new Dialog(AddBeerProfile2.this);

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
			LayoutInflater inflater = LayoutInflater.from(AddBeerProfile2.this);
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
			LayoutInflater inflater = LayoutInflater.from(AddBeerProfile2.this);
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
			LayoutInflater inflater = LayoutInflater.from(AddBeerProfile2.this);
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		preferences = PreferenceManager
				.getDefaultSharedPreferences(AddBeerProfile2.this);

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

	private void alert() {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				AddBeerProfile2.this);
		builder.setMessage("Share your BrewHorn moment now!");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				/*
				 * startActivity(new Intent(AddBeerProfile2.this,
				 * SocialNetworkSharing.class).putExtra("via", "Add"));
				 */

				/* finish(); */
				AddBeerProfile.addBeerProfile.finish();
				AddBeerProfile1.activity.finish();

			}
		});
		builder.show();
	}

	private void showMeaningOfParameter(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				AddBeerProfile2.this);
		builder.setMessage(message);
		builder.setPositiveButton("Dismiss", null);

		builder.show();

	}

	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this,
				com.craftbeer.constants.Constants.FLURRY_KEY);
		Session.getActiveSession().addCallback(statusCallback);

	}

	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
		Session.getActiveSession().removeCallback(statusCallback);
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		AddBeerProfile2.this.finish();

		AddBeerProfile1.activity.finish();
		AddBeerProfile.addBeerProfile.finish();
		/*Session.getActiveSession().onActivityResult(this, requestCode,
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
		}else if(session.isOpened()){
			AddBeerProfile2.this.finish();

			AddBeerProfile1.activity.finish();
			AddBeerProfile.addBeerProfile.finish();
		}
*/
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

	private void onClickLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(AddBeerProfile2.this)
					.setCallback(new StatusCallback() {

						@Override
						public void call(Session session, SessionState state,
								Exception exception) {
							// TODO Auto-generated method stub

							if (session.isOpened()) {

								sharingMessage = "I just profiled #"
										+ preferences.getString("BREWERY_NAME",
												"").replaceAll(" ", "")
										+ " #"
										+ preferences.getString(
												"PROFILED_BEER_NAME", "")
												.replaceAll(" ", "")
										+ " with @brewhornbeerapp.#brewhorn #craftbeer";

								Request request = Request
										.newStatusUpdateRequest(session,
												sharingMessage, new Callback() {

													@Override
													public void onCompleted(
															Response response) {
														// TODO Auto-generated
														// method stub
														showAlert();
													}
												});
								request.executeAsync();
							}

						}
					}));
		} else {
			Session.openActiveSession(this, true, new StatusCallback() {

				@Override
				public void call(Session session, SessionState state,
						Exception exception) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	private void showAlert() {

		AlertDialog.Builder alert = new AlertDialog.Builder(
				AddBeerProfile2.this);
		alert.setMessage("You have successfully posted on Facebook");
		alert.setPositiveButton("Dismiss",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						AddBeerProfile2.this.finish();

						AddBeerProfile1.activity.finish();
						AddBeerProfile.addBeerProfile.finish();
					}
				});
		alert.show();

	}
}
