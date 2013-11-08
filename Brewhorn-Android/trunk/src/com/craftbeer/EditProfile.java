package com.craftbeer;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class EditProfile extends Activity implements OnSeekBarChangeListener,
		HttpListener {

	/**
	 * @author arvind.agarwal this class is useful in editing user profile first 6 parameters
	 */
	// String object for baseline or midpoint
	private String baseLine = "You prefer an average amount";

	// objects of view class
	private SeekBar _seekBarAroma, _seekBarSweet, _seekBarBitter, _seekBarMalt,
			_seekBarYeast, _seekBarMouth;

	private TextView _textTitle;

	private Button _btnSave, _btnNext, _btnBack;
	
	
	
	private TextView txtAromaName, txtBitterName, txtSweetName, txtMaltName,
	txtYeastName, txtMouthFeelName;
	//objects of intiger

	private int aromaValue = 3, sweetvalue = 3, bitterValue = 3, maltValue = 3,
			yeastvalue = 3, mouthValue = 3, sourValue = 3, additiveValue = 3,
			boozinessvalue = 3;

	String _response;
	// object of bundle
	Bundle bundle;

	private Toast _toast;
	// object of shared preference
	private SharedPreferences mPreference;
	private SharedPreferences.Editor editor;

	public static Activity activity;

	private String yeastStatus = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);

		initializeView();


		activity = this;

		
		Toast.makeText(
				this,
				"Adjust each slider button according to how much you think you prefer that taste parameter.Tap the parameter for a description.",
				Toast.LENGTH_LONG).show();
		_btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub\

				showAlert();

			}
		});

		_btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});
	}

	/**
	 * initializing view
	 */
	private void initializeView() {
		// TODO Auto-generated method stub

		mPreference = PreferenceManager
				.getDefaultSharedPreferences(EditProfile.this);
		mPreference.getString("userId", "");
		aromaValue = mPreference.getInt("aromaValue", 3);
		sweetvalue = mPreference.getInt("sweetValue", 3);
		bitterValue = mPreference.getInt("bitterValue", 3);
		maltValue = mPreference.getInt("maltValue", 3);
		yeastvalue = mPreference.getInt("yeastValue", 3);
		mouthValue = mPreference.getInt("mouthFeelValue", 3);
		sourValue = mPreference.getInt("sourValue", 3);
		additiveValue = mPreference.getInt("additiveValue", 3);
		boozinessvalue = mPreference.getInt("boozinessValue", 3);

		bundle = getIntent().getExtras();

		_textTitle = (TextView) findViewById(R.id.title);
		_textTitle.setText("Edit Your Taste Profile");

		_btnSave = (Button) findViewById(R.id.home_save_btn);
		_btnNext = (Button) findViewById(R.id.home_cancel_btn);
		_seekBarAroma = (SeekBar) findViewById(R.id.home_aroma_seek_bar);
		_seekBarBitter = (SeekBar) findViewById(R.id.home_bitter_seek_bar);

		_seekBarMalt = (SeekBar) findViewById(R.id.home_malt_seek_bar);
		_seekBarMouth = (SeekBar) findViewById(R.id.home_mouth_seek_bar);
		_seekBarSweet = (SeekBar) findViewById(R.id.home_sweet_seek_bar);
		_seekBarYeast = (SeekBar) findViewById(R.id.home_yeast_seek_bar);

		_seekBarAroma
				.setProgress((mPreference.getInt("aromaValue", 3) - 3) * 25);

		_seekBarBitter
				.setProgress((mPreference.getInt("bitterValue", 3) - 3) * 25);

		_seekBarSweet
				.setProgress((mPreference.getInt("sweetValue", 3) - 3) * 25);
		_seekBarMalt.setProgress((mPreference.getInt("maltValue", 3) - 3) * 25);
		_seekBarMouth
				.setProgress((mPreference.getInt("mouthFeelValue", 3) - 3) * 25);
		_seekBarYeast
				.setProgress((mPreference.getInt("yeastValue", 3) - 3) * 25);

		_seekBarAroma.setOnSeekBarChangeListener(this);

		_seekBarBitter.setOnSeekBarChangeListener(this);

		_seekBarMalt.setOnSeekBarChangeListener(this);
		_seekBarMouth.setOnSeekBarChangeListener(this);
		_seekBarSweet.setOnSeekBarChangeListener(this);
		_seekBarYeast.setOnSeekBarChangeListener(this);

		_btnBack = (Button) findViewById(R.id.back_btn);
		_btnBack.setVisibility(View.INVISIBLE);
		_btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		txtAromaName = (TextView) findViewById(R.id.home_aroma_txt);
		txtBitterName = (TextView) findViewById(R.id.home_bitter_txt);
		txtSweetName = (TextView) findViewById(R.id.home_sweet_txt);
		txtMaltName = (TextView) findViewById(R.id.home_malt_txt);
		txtMouthFeelName = (TextView) findViewById(R.id.home_mouth_feel_txt);
		txtYeastName = (TextView) findViewById(R.id.home_yeast_txt);
		txtAromaName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showMeaningOfParameter("How strong of an aroma do you prefer from food and drink?");

			}
		});

		txtBitterName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Consider your preference for bitter flavors in food and drink.");

			}
		});
		txtSweetName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Consider your preference for general sweetness in food and drink.");

			}
		});

		txtYeastName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Consider how much you like;\nbubblegum or banana-clove flavors\na general spiciness (peppery flavor) \ngeneral fruitiness\nwhite fruits such as grapes, apples, pears\nFarm animal aromas (barnyard)");


			}
		});
		txtMaltName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Consider how much you like;\n Biscuits, bread\nOatmeal, toast\ncandies like chocolate- caramel or toffee\ndark fruits like dates or prunes");

			}
		});
		txtMouthFeelName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("Do you prefer thin or thick food or drink? How much carbonation do you like?");
				
			}
		});

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
		case R.id.home_aroma_seek_bar:
			int aromaProgress = _seekBarAroma.getProgress();

			if (aromaProgress >= 0 && aromaProgress < 25) {

				aromaValue = 3;

				_toast = Toast.makeText(this, "You prefer much less",
						Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarAroma.setProgress(0);

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (aromaProgress >= 50 && aromaProgress < 75) {

				aromaValue = 5;

				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
				_toast.show();

				_seekBarAroma.setProgress(50);

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (aromaProgress == 100) {

				aromaValue = 7;
				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarAroma.setProgress(100);

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (aromaProgress >= 25 && aromaProgress < 50) {

				aromaValue = 4;

				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarAroma.setProgress(25);

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (aromaProgress >= 75 && aromaProgress < 100) {
				aromaValue = 6;

				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarAroma.setProgress(75);

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

		case R.id.home_sweet_seek_bar:

			int sweetProgress = _seekBarSweet.getProgress();

			if (sweetProgress >= 0 && sweetProgress < 25) {

				sweetvalue = 3;
				_toast = Toast.makeText(this, "You prefer much less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSweet.setProgress(0);

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (sweetProgress >= 50 && sweetProgress < 75) {

				sweetvalue = 5;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSweet.setProgress(50);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (sweetProgress == 100) {

				sweetvalue = 7;
				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSweet.setProgress(100);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (sweetProgress >= 25 && sweetProgress < 50) {
				sweetvalue = 4;
				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSweet.setProgress(25);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (sweetProgress >= 75 && sweetProgress < 100) {

				sweetvalue = 6;

				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarSweet.setProgress(75);
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

		case R.id.home_bitter_seek_bar:

			int bitterProgress = _seekBarBitter.getProgress();

			if (bitterProgress >= 0 && bitterProgress < 25) {

				bitterValue = 3;
				_toast = Toast.makeText(this, "You prefer much less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBitter.setProgress(0);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (bitterProgress >= 50 && bitterProgress < 75) {
				bitterValue = 5;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBitter.setProgress(50);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (bitterProgress == 100) {
				bitterValue = 7;

				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBitter.setProgress(100);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (bitterProgress >= 25 && bitterProgress < 50) {
				bitterValue = 4;
				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBitter.setProgress(25);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (bitterProgress >= 75 && bitterProgress < 100) {
				bitterValue = 6;
				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
				_toast.show();
				_seekBarBitter.setProgress(75);
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
		case R.id.home_malt_seek_bar:
			int maltProgess = _seekBarMalt.getProgress();

			if (maltProgess >= 0 && maltProgess < 25) {

				maltValue = 3;
				_toast = Toast.makeText(this, "You prefer much less",
						Toast.LENGTH_SHORT);
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

			} else if (maltProgess >= 50 && maltProgess < 75) {
				maltValue = 5;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
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

			} else if (maltProgess == 100) {
				maltValue = 7;
				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
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

			} else if (maltProgess >= 25 && maltProgess < 50) {
				maltValue = 4;
				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
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

			} else if (maltProgess >= 75 && maltProgess < 100) {

				maltValue = 6;
				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
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

			int yeastProgress = _seekBarYeast.getProgress();

			if (yeastProgress >= 0 && yeastProgress < 25) {

				yeastvalue = 3;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
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
			} else if (yeastProgress >= 50 && yeastProgress < 75) {

				yeastvalue = 5;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
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
			} else if (yeastProgress == 100) {
				yeastvalue = 7;
				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
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

			} else if (yeastProgress >= 25 && yeastProgress < 50) {

				yeastvalue = 4;
				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
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
			} else if (yeastProgress >= 75 && yeastProgress < 100) {

				yeastvalue = 6;

				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
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

			int mouthProgress = _seekBarMouth.getProgress();
			if (mouthProgress >= 0 && mouthProgress < 25) {
				mouthValue = 3;
				_toast = Toast.makeText(this, "You prefer much less",
						Toast.LENGTH_SHORT);
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
			} else if (mouthProgress >= 50 && mouthProgress < 75) {
				mouthValue = 5;
				_toast = Toast.makeText(this, baseLine, Toast.LENGTH_SHORT);
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
			} else if (mouthProgress == 100) {
				mouthValue = 7;
				_toast = Toast.makeText(this, "You prefer much more",
						Toast.LENGTH_SHORT);
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

			} else if (mouthProgress >= 25 && mouthProgress < 50) {
				mouthValue = 4;
				_toast = Toast.makeText(this, "You prefer somewhat less",
						Toast.LENGTH_SHORT);
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
			} else if ((mouthProgress >= 75 && mouthProgress < 100)) {
				mouthValue = 6;

				_toast = Toast.makeText(this, "You prefer somewhat more",
						Toast.LENGTH_SHORT);
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
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
		

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
					Toast.makeText(EditProfile.this, _response,
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

					editor.putString("yeast_status", yeastStatus);

					editor.commit();


					finish();
					EditBeerProfile2.activity.finish();
					UserInfo.activity.finish();

					ShowUserTasteProfile.activity.finish();

				} else if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "User  has no taste Profile";
					Toast.makeText(EditProfile.this, _response,
							Toast.LENGTH_LONG).show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(EditProfile.this, _response,
							Toast.LENGTH_LONG).show();
				}
				if (strUserRegistration.equalsIgnoreCase("-3")) {
					_response = "In-Valid User" + "";
					Toast.makeText(EditProfile.this, _response,
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

	
	/**
	 * this method shows alert for optional parameters
	 */
	private void showAlert() {

		AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);

		builder.setMessage("You must proceed to edit the optional taste parameters.");

		builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				Intent i = new Intent(EditProfile.this, EditProfile2.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

				editor = mPreference.edit();

				editor.putInt("aroma_temp", aromaValue);
				editor.putInt("sweet_temp", sweetvalue);
				editor.putInt("bitter_temp", bitterValue);
				editor.putInt("malt_temp", maltValue);
				editor.putInt("yeast_temp", yeastvalue);
				editor.putInt("mouth_temp", mouthValue);

				editor.commit();
				
				i.putExtra("userId", bundle.getString("userId"));

				startActivity(i);

			}
		});

		builder.setNegativeButton("Save",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						if (CheckInternetConnectivity
								.checkinternetconnection(EditProfile.this)) {

							
							
							//server hit
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
									+ "]]></booziness><sour_status><![CDATA[0]]></sour_status><additive_status><![CDATA[0]]></additive_status><booziness_status><![CDATA[0]]></booziness_status><yeast_status><![CDATA["
									+ yeastStatus
									+ "]]></yeast_status></editUserTasteProfile>";
							HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
									+ Url.USER_TASTE_EDIT_URL,
									EditProfile.this, REGISTARTION_XML,
									EditProfile.this);
							hitRegistartion.execute();
						} else {

							Toast.makeText(EditProfile.this,
									"Check Internet Connection",
									Toast.LENGTH_SHORT).show();
						}

					}
				});

		builder.show();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

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
		AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
		builder.setMessage(message);
		builder.setPositiveButton("Dismiss", null);

		builder.show();

	}
}
