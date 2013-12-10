package com.craftbeer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.flurry.android.FlurryAgent;
import com.model.MODEL_AROMA;

public class EditBeerProfile1 extends Activity implements
		OnSeekBarChangeListener, android.view.View.OnClickListener,
		HttpListener {

	String sharingMessage = "";
	private SharedPreferences preferences;

	public static Activity activity;
	private CheckBox _yeastCheckBox;

	// object of baseAdapter
	private SettingAdapterMalt adapterMalt;
	private SettingAdapterYeast adapterYeast;
	private SettingAdapterMouth adapterMouth;
	private SettingAdapterTexture adapterTexture;
	// String for response of server hit
	String _response;
	private Dialog dialog;
	private String aromaShow = "";

	// Bundle object
	private Bundle bundle;
	private Toast _toast;
	private Button _saveBtn, _cancelBtn, _infoBtn;
	private TextView txtMaltName, txtYeastName, txtMouthFeelName;

	// array for malt
	String[] arrayMalt = { "nutty", "biscuit", "toast", "lighttoast",
			"burnttoast", "caramel", "molasses", "darkfruits", "prune",
			"darkraisin", "smoky", "Chocolaty", "coffee", "burnt",
			"bitterchocolate", "strongcoffee", "licorice", "oaky", "plum",
			"toffee", "oats", "bready", "bandaid", "medicinal" };
	// array for yeast

	String[] arrayYeast = { "spicy", "fruity", "banana", "clove",
			"goldenraisin", "whitefleshfruit", "meaty", "peppery", "bubblegum",
			"horseblanket", "barnyard", "bandaid" };

	// array for yeast
	String[] arrayMouth = {

	"light", "moderate", "heavy", "flat", "crisp", "prickly", "sharp",
			"stinging", "zesty", "spritzy", "bubbly", "soft", "effervescent"

	};

	// array for texture
	String[] arrayTexture = { "lightbody", "medbody", "fullbody", "viscous",
			"syrupy", "creamy", "metallic", "silky", "gritty", "acidic", "dry",
			"chewy", "luscious", "tannic", "watery", "rich", "velvety" };
	private ArrayList<MODEL_AROMA> arrayListMalt, arrayListYeast,
			arrayListMouth, arrayListTexture;
	// object for model class
	private MODEL_AROMA model;
	private int aromaValue = 3, sweetvalue = 3, bitterValue = 3, maltValue = 3,
			yeastvalue = 3, mouthValue = 3;
	private SeekBar _seekBarMalt, _seekBarYeast, _seekBarMouth;
	// object of shared preference
	private SharedPreferences prefrence;
	// object of view class
	private TextView _txt3Malt, _txt4Malt, _txt5Malt, _txt6Malt, _txt7Malt;
	private TextView _txt3Yeast, _txt4Yeast, _txt5Yeast, _txt6Yeast,
			_txt7Yeast;
	private TextView _txt3Mouth, _txt4Mouth, _txt5Mouth, _txt6Mouth,
			_txt7Mouth;
	private TextView _txtMalt, _txtYeast, _txtMouthFeel, _txtTexture;
	// comment for various values of taste params

	private String comment3 = " The beer's taste was much less than you prefer ";
	private String comment4 = "The beer's taste was somewhat less than you prefer";
	private String comment5 = "The beer's taste met your baseline taste preferences";
	private String comment6 = "The beer's taste was somewhat more than you prefer";
	private String comment7 = "The beer's taste was much more than you prefer";

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
		setContentView(R.layout.add_beer_profile_1);
		preferences = PreferenceManager
				.getDefaultSharedPreferences(EditBeerProfile1.this);

		activity = this;

		arrayListMalt = new ArrayList<MODEL_AROMA>();
		arrayListMouth = new ArrayList<MODEL_AROMA>();
		arrayListYeast = new ArrayList<MODEL_AROMA>();
		arrayListTexture = new ArrayList<MODEL_AROMA>();

		// putting values in arraylist
		for (int i = 0; i < arrayMalt.length; i++) {
			model = new MODEL_AROMA();
			model.setName(arrayMalt[i]);
			arrayListMalt.add(model);
		}
		for (int i = 0; i < arrayYeast.length; i++) {
			model = new MODEL_AROMA();
			model.setName(arrayYeast[i]);
			arrayListYeast.add(model);
		}
		for (int i = 0; i < arrayMouth.length; i++) {
			model = new MODEL_AROMA();
			model.setName(arrayMouth[i]);
			arrayListMouth.add(model);
		}
		for (int i = 0; i < arrayTexture.length; i++) {
			model = new MODEL_AROMA();
			model.setName(arrayTexture[i]);
			arrayListTexture.add(model);
		}
		initializeView();
		Log.e("Profile Detail KeyHash:", showHashKey(this));
		initFacebookSession(savedInstanceState);
	}

	/**
	 * initializing view
	 */
	private void initializeView() {

		_yeastCheckBox = (CheckBox) findViewById(R.id.home_yeast_chk_bx);

		_saveBtn = (Button) findViewById(R.id.home_save_btn);
		_cancelBtn = (Button) findViewById(R.id.home_cancel_btn);
		_infoBtn = (Button) findViewById(R.id.info_btn);
		_infoBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toInfoPage = new Intent(EditBeerProfile1.this,
						InformationPage.class);
				startActivity(toInfoPage);
			}
		});
		_saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAlert();
			}
		});
		_cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		prefrence = PreferenceManager
				.getDefaultSharedPreferences(EditBeerProfile1.this);
		bundle = getIntent().getExtras();
		aromaValue = prefrence.getInt("aroma_beer_temp", 3);
		sweetvalue = prefrence.getInt("sweet_beer_temp", 3);
		bitterValue = prefrence.getInt("sweet_beer_temp", 3);
		maltValue = prefrence.getInt("maltValue", 3);
		yeastvalue = prefrence.getInt("yeastValue", 3);
		mouthValue = prefrence.getInt("mouthFeelValue", 3);
		_seekBarMalt = (SeekBar) findViewById(R.id.home_malt_seek_bar);
		_seekBarMouth = (SeekBar) findViewById(R.id.home_mouth_seek_bar);
		_seekBarYeast = (SeekBar) findViewById(R.id.home_yeast_seek_bar);

		if (prefrence.getString("yeast_status", "0").equals("1")) {
			_seekBarYeast.setEnabled(true);
		} else {
			_seekBarYeast.setEnabled(false);
			_yeastCheckBox.setChecked(false);
			yeastvalue = 0;
		}

		/* malt text */

		_txt3Malt = (TextView) findViewById(R.id.malt_text3);
		_txt4Malt = (TextView) findViewById(R.id.malt_text4);
		_txt5Malt = (TextView) findViewById(R.id.malt_text5);
		_txt6Malt = (TextView) findViewById(R.id.malt_text6);
		_txt7Malt = (TextView) findViewById(R.id.malt_text7);

		_txt5Malt.setText(String.valueOf(prefrence.getInt("maltValue", 5)));
		_txt3Malt.setText(String.valueOf(prefrence.getInt("maltValue", 5) - 2));
		_txt4Malt.setText(String.valueOf(prefrence.getInt("maltValue", 5) - 1));
		_txt6Malt.setText(String.valueOf(prefrence.getInt("maltValue", 5) + 1));
		_txt7Malt.setText(String.valueOf(prefrence.getInt("maltValue", 5) + 2));

		_seekBarMalt.setProgress(50);
		/* yeast */

		_txt3Yeast = (TextView) findViewById(R.id.yeast_text3);
		_txt4Yeast = (TextView) findViewById(R.id.yeast_text4);
		_txt5Yeast = (TextView) findViewById(R.id.yeast_text5);
		_txt6Yeast = (TextView) findViewById(R.id.yeast_text6);
		_txt7Yeast = (TextView) findViewById(R.id.yeast_text7);

		_txt5Yeast.setText(String.valueOf(prefrence.getInt("yeastValue", 5)));
		_txt3Yeast
				.setText(String.valueOf(prefrence.getInt("yeastValue", 5) - 2));
		_txt4Yeast
				.setText(String.valueOf(prefrence.getInt("yeastValue", 5) - 1));
		_txt6Yeast
				.setText(String.valueOf(prefrence.getInt("yeastValue", 5) + 1));
		_txt7Yeast
				.setText(String.valueOf(prefrence.getInt("yeastValue", 5) + 2));
		_seekBarYeast.setProgress(50);

		/* mouth feel */

		_txt3Mouth = (TextView) findViewById(R.id.mouth_text3);
		_txt4Mouth = (TextView) findViewById(R.id.mouth_text4);
		_txt5Mouth = (TextView) findViewById(R.id.mouth_text5);
		_txt6Mouth = (TextView) findViewById(R.id.mouth_text6);
		_txt7Mouth = (TextView) findViewById(R.id.mouth_text7);

		_txt5Mouth
				.setText(String.valueOf(prefrence.getInt("mouthFeelValue", 5)));
		_txt3Mouth
				.setText(String.valueOf(prefrence.getInt("mouthFeelValue", 5) - 2));
		_txt4Mouth
				.setText(String.valueOf(prefrence.getInt("mouthFeelValue", 5) - 1));
		_txt6Mouth
				.setText(String.valueOf(prefrence.getInt("mouthFeelValue", 5) + 1));
		_txt7Mouth
				.setText(String.valueOf(prefrence.getInt("mouthFeelValue", 5) + 2));
		_seekBarMouth.setProgress(50);

		_txtMalt = (TextView) findViewById(R.id.home_malt_edt);
		_txtYeast = (TextView) findViewById(R.id.home_yeast_edt);
		_txtMouthFeel = (TextView) findViewById(R.id.home_mouth_edt);

		_txtMalt.setOnClickListener(this);
		_txtYeast.setOnClickListener(this);
		_txtMouthFeel.setOnClickListener(this);
		_txtTexture = (TextView) findViewById(R.id.home_mouth_texture_edt);

		_seekBarMalt.setOnSeekBarChangeListener(this);
		_seekBarMouth.setOnSeekBarChangeListener(this);
		_txtTexture.setOnClickListener(this);
		_seekBarYeast.setOnSeekBarChangeListener(this);

		_yeastCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub

						if (isChecked) {
							_seekBarYeast.setEnabled(true);

							yeastvalue = prefrence.getInt("yeastValue", 3);
							_seekBarYeast.setProgress(50);
							_txtYeast.setEnabled(true);

						} else {

							_seekBarYeast.setEnabled(false);

							yeastvalue = 0;
							_seekBarYeast.setProgress(0);
							_txtYeast.setText("");
							_txtYeast.setEnabled(false);
						}

					}
				});

		txtMaltName = (TextView) findViewById(R.id.home_malt_txt);
		txtMouthFeelName = (TextView) findViewById(R.id.home_mouth_feel_txt);
		txtYeastName = (TextView) findViewById(R.id.home_yeast_txt);

		txtMaltName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("What grain, dark candy or fruit flavor(s) did you pick up and how strong are they?");

			}
		});

		txtMouthFeelName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Is the carbonation crisp, prickly, or stinging? Does the body of the beer feel light, moderate or heavy?");

			}
		});

		txtYeastName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("In a wheat beer, do you taste bubblegum or banana-clove? In a Belgian, do you taste spice or fruit? In a sour or lambic, do you pick up any funk-like taste?");

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

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

		switch (seekBar.getId()) {

		case R.id.home_malt_seek_bar:

			if (_seekBarMalt.getProgress() >= 0
					&& _seekBarMalt.getProgress() < 25) {
				maltValue = prefrence.getInt("maltValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarMalt.setProgress(0);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarMalt.getProgress() >= 50
					&& _seekBarMalt.getProgress() < 75) {
				maltValue = prefrence.getInt("maltValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarMalt.setProgress(50);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarMalt.getProgress() == 100) {
				maltValue = prefrence.getInt("maltValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarMalt.setProgress(100);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (_seekBarMalt.getProgress() >= 25
					&& _seekBarMalt.getProgress() < 50) {
				maltValue = prefrence.getInt("maltValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarMalt.setProgress(25);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarMalt.getProgress() >= 75
					&& _seekBarMalt.getProgress() < 100) {
				maltValue = prefrence.getInt("maltValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarMalt.setProgress(75);
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
		case R.id.home_yeast_seek_bar:

			if (_seekBarYeast.getProgress() >= 0
					&& _seekBarYeast.getProgress() < 25) {

				yeastvalue = prefrence.getInt("yeastValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarYeast.setProgress(0);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarYeast.getProgress() >= 50
					&& _seekBarYeast.getProgress() < 75) {
				yeastvalue = prefrence.getInt("yeastValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarYeast.setProgress(50);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarYeast.getProgress() == 100) {
				yeastvalue = prefrence.getInt("yeastValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarYeast.setProgress(100);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (_seekBarYeast.getProgress() >= 25
					&& _seekBarYeast.getProgress() < 50) {
				yeastvalue = prefrence.getInt("yeastValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarYeast.setProgress(25);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarYeast.getProgress() >= 75
					&& _seekBarYeast.getProgress() < 100) {
				yeastvalue = prefrence.getInt("yeastValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarYeast.setProgress(75);
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
		case R.id.home_mouth_seek_bar:
			if (_seekBarMouth.getProgress() >= 0
					&& _seekBarMouth.getProgress() < 25) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarMouth.setProgress(0);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarMouth.getProgress() >= 50
					&& _seekBarMouth.getProgress() < 75) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarMouth.setProgress(50);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarMouth.getProgress() == 100) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarMouth.setProgress(100);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (_seekBarMouth.getProgress() >= 25
					&& _seekBarMouth.getProgress() < 50) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarMouth.setProgress(25);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (_seekBarMouth.getProgress() >= 75
					&& _seekBarMouth.getProgress() < 100) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarMouth.setProgress(75);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.home_malt_edt:
			showDialogMalt();
			break;

		case R.id.home_yeast_edt:
			showDialogYeast();
			break;
		case R.id.home_mouth_edt:
			showDialogMouth();
			break;

		case R.id.home_mouth_texture_edt:
			showDialogTexture();
			break;

		}

	}

	public String showHashKey(Context context) {
		String hashkey = "";
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					"com.craftbeer", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				hashkey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
			}
		} catch (NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		return hashkey;
	}

	public void showDialogMalt() {
		aromaShow = "";

		dialog = new Dialog(EditBeerProfile1.this);

		dialog.setTitle("Select Descriptor for Maltiness ");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListMalt.size(); i++) {

					if (arrayListMalt.get(i).isChecked() == true) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListMalt.get(i).getName();
						} else {

							aromaShow = arrayListMalt.get(i).getName();
						}
					}
				}

				_txtMalt.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

		adapterMalt = new SettingAdapterMalt(arrayListMalt);
		list.setAdapter(adapterMalt);

		dialog.show();

	}

	/**
	 * this methide shows dialog for yeast
	 */

	public void showDialogYeast() {
		aromaShow = "";

		dialog = new Dialog(EditBeerProfile1.this);

		dialog.setTitle("Select Descriptor for Yeastiness ");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListYeast.size(); i++) {

					if (arrayListYeast.get(i).isChecked() == true) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListYeast.get(i).getName();
						} else {

							aromaShow = arrayListYeast.get(i).getName();
						}
					}
				}

				_txtYeast.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});
		adapterYeast = new SettingAdapterYeast(arrayListYeast);
		list.setAdapter(adapterYeast);

		dialog.show();

	}

	/**
	 * this methide shows dialog for Mouth feel
	 */

	public void showDialogMouth() {
		aromaShow = "";

		dialog = new Dialog(EditBeerProfile1.this);

		dialog.setTitle("Select Descriptor for Carbonation");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListMouth.size(); i++) {

					if (arrayListMouth.get(i).isChecked() == true) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListMouth.get(i).getName();
						} else {

							aromaShow = arrayListMouth.get(i).getName();
						}
					}
				}

				_txtMouthFeel.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

		adapterMouth = (new SettingAdapterMouth(arrayListMouth));
		list.setAdapter(adapterMouth);

		dialog.show();

	}

	public class SettingAdapterMalt extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterMalt(ArrayList<MODEL_AROMA> array2) {
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
					.from(EditBeerProfile1.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (arrayListMalt.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);
			} else {

				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListMalt.get(position).setChecked(
							!arrayListMalt.get(position).isChecked());

					adapterMalt.notifyDataSetChanged();

				}
			});
			return convertView;
		}

	}

	class ViewHolder {
		TextView txt;
		ImageView chkBx;
	}

	public class SettingAdapterYeast extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterYeast(ArrayList<MODEL_AROMA> array2) {
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
					.from(EditBeerProfile1.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (arrayListYeast.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);
			} else {

				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListYeast.get(position).setChecked(
							!arrayListYeast.get(position).isChecked());

					adapterYeast.notifyDataSetChanged();

				}
			});
			return convertView;
		}

	}

	public class SettingAdapterMouth extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterMouth(ArrayList<MODEL_AROMA> array2) {
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
					.from(EditBeerProfile1.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (arrayListMouth.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);
			} else {

				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListMouth.get(position).setChecked(
							!arrayListMouth.get(position).isChecked());

					adapterMouth.notifyDataSetChanged();

				}
			});

			return convertView;
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
				if (strUserRegistration.equalsIgnoreCase("1")) {
					_response = "Beer Profile Successfully created";
					Toast.makeText(EditBeerProfile1.this, _response,
							Toast.LENGTH_LONG).show();
					/* alert(); */

					if (preferences.getBoolean(Constants.AUTO_SHARE_TWITTER,
							false)) {
						Constants.ShowProgress(this);
						if (FindOrAddBeer.twitterHandle.trim().length() > 0) {
							stringMain = "I just profiled  @"
									// + preferences.getString("BREWERY_NAME",
									// "") .replaceAll(" ", "")
									+ FindOrAddBeer.twitterHandle
									+ " #"
									+ preferences.getString(
											"PROFILED_BEER_NAME", "")
											.replaceAll(" ", "")
									+ " with @BrewHornBeerApp. #brewhorn ";
						} else {
							stringMain = "I just profiled  #"
									+ preferences.getString("BREWERY_NAME", "")
											.replaceAll(" ", "")
									+ " #"
									+ preferences.getString(
											"PROFILED_BEER_NAME", "")
											.replaceAll(" ", "")
									+ " with @BrewHornBeerApp. #brewhorn ";
						}
						Log.e("sendTweet", ":" + stringMain);
						sendTweet(stringMain);
					} else if (preferences.getBoolean(
							Constants.AUTO_SHARE_FACEBOOK, false)) {
						Constants.ShowProgress(this);

						loginToFacebook();
					} else {
						finish();
						EditBeerProfile.activity.finish();
						EditUserBeer.activity.finish();
						UserBeerProfile.activity.finish();
					}

				} else if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "Invalid Beer";
					Toast.makeText(EditBeerProfile1.this, _response,
							Toast.LENGTH_LONG).show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(EditBeerProfile1.this, _response,
							Toast.LENGTH_LONG).show();
				} else if (strUserRegistration.equalsIgnoreCase("-3")) {
					_response = "Invalid User";
					Toast.makeText(EditBeerProfile1.this, _response,
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

	public void showAlert() {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				EditBeerProfile1.this);
		builder.setTitle("Would you like to add optional parameters?");

		builder.setPositiveButton("Proceed",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent i = new Intent(EditBeerProfile1.this,
								EditBeerProfile2.class);

						i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						i.putExtra("beerId", bundle.getString("beerId"));
						i.putExtra("userId", bundle.getString("userId"));
						SharedPreferences.Editor editor = prefrence.edit();

						editor.putInt("malt_beer_temp", maltValue);
						editor.putString("malt_cmnt_temp", _txtMalt.getText()
								.toString());
						editor.putInt("yeast_beer_temp", yeastvalue);
						editor.putString("yeast_cmnt_temp", _txtYeast.getText()
								.toString());
						editor.putInt("mouth_feel_beer_temp", mouthValue);
						editor.putString("mouth_feel_cmnt_temp", _txtMouthFeel
								.getText().toString());
						editor.putString("mouth_feel_cmnt_1_temp", _txtTexture
								.getText().toString());
						editor.commit();

						startActivity(i);

					}
				});

		builder.setNegativeButton("Save",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						if (CheckInternetConnectivity
								.checkinternetconnection(EditBeerProfile1.this)) {

							String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><editBeerProfile><beerId><![CDATA["
									+ bundle.getString("beerId")
									+ "]]></beerId><userId><![CDATA["
									+ bundle.getString("userId")
									+ "]]></userId><aroma><![CDATA["
									+ String.valueOf(aromaValue)
									+ "]]></aroma><aroma_cmt><![CDATA["
									+ prefrence
											.getString("aroma_cmnt_temp", "")
									+ "]]></aroma_cmt><sweet><![CDATA["
									+ String.valueOf(sweetvalue)
									+ "]]></sweet><sweet_cmt><![CDATA["
									+ prefrence
											.getString("sweet_cmnt_temp", "")
									+ "]]></sweet_cmt><bitter><![CDATA["
									+ String.valueOf(bitterValue)
									+ "]]></bitter><bitter_cmt><![CDATA["
									+ prefrence.getString("bitter_cmnt_temp",
											"")
									+ "]]></bitter_cmt><malt><![CDATA["
									+ String.valueOf(maltValue)
									+ "]]></malt><malt_cmt><![CDATA["
									+ _txtMalt.getText().toString()
									+ "]]></malt_cmt><yeast><![CDATA["
									+ String.valueOf(yeastvalue)
									+ "]]></yeast><yeast_cmt><![CDATA["
									+ _txtYeast.getText().toString()
									+ "]]></yeast_cmt><mouthFeel><![CDATA["
									+ String.valueOf(mouthValue)
									+ "]]></mouthFeel>"
									+ "<mouthFeel_cmt1><![CDATA["
									+ _txtTexture.getText().toString()
									+ "]]></mouthFeel_cmt1><sour><![CDATA[0]]></sour><sour_cmt><![CDATA[]]></sour_cmt><additive><![CDATA[0]]></additive><additive_cmt><![CDATA[]]></additive_cmt><booziness><![CDATA[0]]></booziness><booziness_cmt><![CDATA[]]></booziness_cmt>"
									+ "<booziness_cmt1><![CDATA[]]></booziness_cmt1></editBeerProfile>";

							HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
									+ Url.EDIT_BEER_PROFILE,
									EditBeerProfile1.this, REGISTARTION_XML,
									EditBeerProfile1.this);
							hitRegistartion.execute();
						} else {

							Toast.makeText(EditBeerProfile1.this,
									"Check Internet Connection",
									Toast.LENGTH_SHORT).show();
						}

					}
				});

		builder.show();

	}

	public void showDialogTexture() {
		aromaShow = "";

		dialog = new Dialog(EditBeerProfile1.this);

		dialog.setTitle("Select Descriptor for Texture ");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListTexture.size(); i++) {

					if (arrayListTexture.get(i).isChecked() == true) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListTexture.get(i).getName();
						} else {

							aromaShow = arrayListTexture.get(i).getName();
						}
					}
				}

				_txtTexture.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

		adapterTexture = (new SettingAdapterTexture(arrayListTexture));
		list.setAdapter(adapterTexture);

		dialog.show();

	}

	public class SettingAdapterTexture extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterTexture(ArrayList<MODEL_AROMA> array2) {
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
					.from(EditBeerProfile1.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (arrayListTexture.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);
			} else {

				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListTexture.get(position).setChecked(
							!arrayListTexture.get(position).isChecked());

					adapterTexture.notifyDataSetChanged();

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

		prefrence = PreferenceManager
				.getDefaultSharedPreferences(EditBeerProfile1.this);
		aromaValue = prefrence.getInt("aroma_beer_temp", 3);
		sweetvalue = prefrence.getInt("sweet_beer_temp", 3);
		bitterValue = prefrence.getInt("sweet_beer_temp", 3);

		maltValue = prefrence.getInt("maltValue", 3);
		yeastvalue = prefrence.getInt("yeastValue", 3);
		mouthValue = prefrence.getInt("mouthFeelValue", 3);

	}

	private void showMeaningOfParameter(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				EditBeerProfile1.this);
		builder.setMessage(message);
		builder.setPositiveButton("Dismiss", null);
		builder.show();
	}

	private void alert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				EditBeerProfile1.this);
		builder.setMessage("Share your BrewHorn moment now!");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startActivity(new Intent(EditBeerProfile1.this,
						SocialNetworkSharing.class).putExtra("via", "Add"));
				finish();
				EditBeerProfile.activity.finish();
				EditUserBeer.activity.finish();
				UserBeerProfile.activity.finish();

			}
		});
		builder.show();
	}

	private void closeActivity() {
		try {
			Constants.DismissProgress();
			finish();
			EditBeerProfile.activity.finish();
			EditUserBeer.activity.finish();
			UserBeerProfile.activity.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				.checkinternetconnection(EditBeerProfile1.this)) {
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
						if (!isFacebookShare()) {
							closeActivity();
						}
					}
				}
			});
			thread.start();
			messageHandler = new Handler() {
				public void handleMessage(Message message) {
					super.handleMessage(message);
					Intent intent = new Intent(EditBeerProfile1.this,
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

	private void initFacebookSession(Bundle savedInstanceState) {
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
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
			if (session.isOpened()) {
				performPublish();
			}

			else if (state.equals(SessionState.CLOSED_LOGIN_FAILED)) {
				Log.e("Clear", ": Session");
				session.closeAndClearTokenInformation();
				Toast.makeText(getApplicationContext(), "Failed to post",	Toast.LENGTH_LONG).show();
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
			if (resultCode == RESULT_OK) {
				Session.getActiveSession().onActivityResult(this, requestCode,
						resultCode, data);
			} else {
				Session session = Session.getActiveSession();
				if (!session.isClosed()) {
					Log.e("Clear", ": Session");
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

		
		if (FindOrAddBeer.facebookHandle.trim().length() > 0) 
		{
			stringMain = "I just profiled  @"
					// + preferences.getString("BREWERY_NAME", "")
					// .replaceAll(" ", "")
					+ FindOrAddBeer.facebookHandle
					+ " #"
					+ preferences.getString("PROFILED_BEER_NAME", "")
							.replaceAll(" ", "")
					+ " with @BrewHornBeerApp. #brewhorn ";

		} 
		else 
		{
			stringMain = "I just profiled  #"
					+ preferences.getString("BREWERY_NAME", "").replaceAll(" ",
							"")
					+ " #"
					+ preferences.getString("PROFILED_BEER_NAME", "")
							.replaceAll(" ", "")
					+ " with @BrewHornBeerApp. #brewhorn ";		
		}
//		Log.e("postStatusUpdate", ":"+stringMain);

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
		Log.e("facebook", ":" + alertMessage);
		Toast.makeText(getApplicationContext(), alertMessage, Toast.LENGTH_LONG)
				.show();
		closeActivity();
	}

}
