package com.craftbeer;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.model.MODEL_AROMA;

/**
 * 
 * @author arvind.agarwal
 * 
 */

public class AddBeerProfile extends Activity implements
		OnSeekBarChangeListener, HttpListener, OnClickListener {

	private SettingAdapterb adapterB;
	private SettingAdapterb1 adapterB1;
	private SettingAdapterb2 adapterB2;

	private Dialog dialog;

	private String aromaShow = "";

	public ArrayList<MODEL_AROMA> arrayListNew = null;

	public ArrayList<MODEL_AROMA> arrayListSweet = null;

	public ArrayList<MODEL_AROMA> arrayListBitter = null;
	MODEL_AROMA model;

	String[] arrayAroma = { "floral", "citrus", "tropical", "fruity", "earthy",
			"banana", "clove", "barnyard", "horseblanket", "rosy", "vegetal",
			"herbal", "leathery", "metallic", "piney", "resiny", "bubblegum",
			"nutty", "Bready", "Toast", "Malty", " Noble Hop" };

	String[] arraySweet = { "light", "moderate", "heavy", "cloying",
			"candisugar", "residual", "aftertaste" };

	String[] arrayBitter = {

	"citrus", "tropical", "floral", "perfumy", "grapefruit", "residual",
			"aftertaste", "catty", "hoppy", "herbal", "lemony", "piney",
			"resiny", "spicy", "dank", "lingering", "bright", "pineapple",
			"mango"

	};

	private String comment3 = "The beer's taste was much less than you prefer ";
	private String comment4 = "The beer's taste was somewhat less than you prefer";
	private String comment5 = "The beer's taste met your baseline taste preferences";
	private String comment6 = "The beer's taste was somewhat more than you prefer";
	private String comment7 = "The beer's taste was much more than you prefer";

	private TextView _textTitle;

	private SeekBar _seekBarAroma, _seekBarSweet, _seekBarBitter, _seekBarMalt,
			_seekBarYeast, _seekBarMouth;

	private int aromaValue = 3, sweetvalue = 3, bitterValue = 3, maltValue = 3,
			yeastvalue = 3, mouthValue = 3, sourValue = 3, additiveValue = 3,
			boozinessvalue = 3;

	private Bundle bundle;
	private String _response;

	private AlertDialog.Builder builder;

	public static Activity addBeerProfile;

	private Toast _toast;

	private TextView _txt3Aroma, _txt4Aroma, _txt5Aroma, _txt6Aroma,
			_txt7Aroma;
	private TextView _txt3Sweet, _txt4Sweet, _txt5Sweet, _txt6Sweet,
			_txt7Sweet;
	private TextView _txt3Bitter, _txt4Bitter, _txt5Bitter, _txt6Bitter,
			_txt7Bitter;

	private TextView _txt3Malt, _txt4Malt, _txt5Malt, _txt6Malt, _txt7Malt;

	private TextView _txt3Yeast, _txt4Yeast, _txt5Yeast, _txt6Yeast,
			_txt7Yeast;

	private TextView _txt3Mouth, _txt4Mouth, _txt5Mouth, _txt6Mouth,
			_txt7Mouth;

	private SharedPreferences prefrence;

	private SharedPreferences.Editor editor;

	private TextView _txtAroma, _txtSweet, _txtBitter;

	private Button _nextBtn, _cancelBtn, _infoBtn;

	private TextView txtAromaName, txtBitterName, txtSweetName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_beer_profile);

		initializeView();

		arrayListNew = new ArrayList<MODEL_AROMA>();
		arrayListSweet = new ArrayList<MODEL_AROMA>();
		arrayListBitter = new ArrayList<MODEL_AROMA>();

		for (int i = 0; i < arrayAroma.length; i++) {

			model = new MODEL_AROMA();

			model.setName(arrayAroma[i]);

			arrayListNew.add(model);

		}

		for (int i = 0; i < arraySweet.length; i++) {
			model = new MODEL_AROMA();

			model.setName(arraySweet[i]);

			arrayListSweet.add(model);
		}

		for (int i = 0; i < arrayBitter.length; i++) {
			model = new MODEL_AROMA();

			model.setName(arrayBitter[i]);

			arrayListBitter.add(model);
		}

		addBeerProfile = this;

		_nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(AddBeerProfile.this,
						AddBeerProfile1.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				i.putExtra("beerId", bundle.getString("beerId"));
				i.putExtra("userId", bundle.getString("userId"));

				editor = prefrence.edit();
				editor.putInt("aroma_beer_temp", aromaValue);
				editor.putInt("sweet_beer_temp", sweetvalue);
				editor.putInt("bitter_beer_temp", bitterValue);
				editor.putString("aroma_cmnt_temp", _txtAroma.getText()
						.toString());
				editor.putString("sweet_cmnt_temp", _txtSweet.getText()
						.toString());
				editor.putString("bitter_cmnt_temp", _txtBitter.getText()
						.toString());
				editor.commit();

				/*
				 * i.putExtra("aroma", aromaValue); i.putExtra("aroma_cmnt",
				 * _txtAroma.getText().toString()); i.putExtra("sweet",
				 * sweetvalue); i.putExtra("sweet_cmnt",
				 * _txtSweet.getText().toString()); i.putExtra("bitter",
				 * bitterValue); i.putExtra("bitter_cmnt",
				 * _txtBitter.getText().toString());
				 */
				startActivity(i);

			}
		});
		
		
		Toast.makeText(
				this,
				"Tap the parameter for a description.",
				Toast.LENGTH_LONG).show();

	}

	private void initializeView() {
		// TODO Auto-generated method stub

		prefrence = PreferenceManager
				.getDefaultSharedPreferences(AddBeerProfile.this);

		aromaValue = prefrence.getInt("aromaValue", 3);
		sweetvalue = prefrence.getInt("sweetValue", 3);
		bitterValue = prefrence.getInt("bitterValue", 3);

		bundle = getIntent().getExtras();

		_textTitle = (TextView) findViewById(R.id.title);
		_textTitle.setText(bundle.getString("title"));

		_txtAroma = (TextView) findViewById(R.id.home_aroma_edt);
		_txtSweet = (TextView) findViewById(R.id.home_sweet_edt);
		_txtBitter = (TextView) findViewById(R.id.home_bitter_edt);
		_cancelBtn = (Button) findViewById(R.id.home_cancel_btn);
		_infoBtn = (Button) findViewById(R.id.info_btn);
		_infoBtn.setOnClickListener(this);
		_cancelBtn.setOnClickListener(this);
		_txtAroma.setOnClickListener(this);
		_txtBitter.setOnClickListener(this);
		_txtSweet.setOnClickListener(this);

		_nextBtn = (Button) findViewById(R.id.home_next_btn);

		_seekBarAroma = (SeekBar) findViewById(R.id.home_aroma_seek_bar);
		_seekBarBitter = (SeekBar) findViewById(R.id.home_bitter_seek_bar);

		_seekBarMalt = (SeekBar) findViewById(R.id.home_malt_seek_bar);
		_seekBarMouth = (SeekBar) findViewById(R.id.home_mouth_seek_bar);
		_seekBarSweet = (SeekBar) findViewById(R.id.home_sweet_seek_bar);
		_seekBarYeast = (SeekBar) findViewById(R.id.home_yeast_seek_bar);

		_seekBarAroma.setOnSeekBarChangeListener(this);
		_seekBarBitter.setOnSeekBarChangeListener(this);

		_seekBarMalt.setOnSeekBarChangeListener(this);
		_seekBarMouth.setOnSeekBarChangeListener(this);
		_seekBarSweet.setOnSeekBarChangeListener(this);
		_seekBarYeast.setOnSeekBarChangeListener(this);

		// /////////////////////////////////////////////////////Texts///////////////////////////////////////////////////////////////////

		txtAromaName = (TextView) findViewById(R.id.home_aroma_txt);
		txtBitterName = (TextView) findViewById(R.id.home_bitter_txt);
		txtSweetName = (TextView) findViewById(R.id.home_sweet_txt);

		txtAromaName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showMeaningOfParameter("Through a normal drinking approach, consider whether or not you smell anything familiar. What’s the strength of the aroma?");

			}
		});
		
		txtBitterName.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("How much bitterness do you experience in the beer? *Do not think about roast bitterness, as in chocolate or coffee bitterness here.");
	
				
			}
			
		});
		txtSweetName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMeaningOfParameter("How sweet is the beer from a general sugary or sweetener perspective?");

			}
		});

		/* aroma text */

		_txt3Aroma = (TextView) findViewById(R.id.aroma_text3);
		_txt4Aroma = (TextView) findViewById(R.id.aroma_text4);
		_txt5Aroma = (TextView) findViewById(R.id.aroma_text5);
		_txt6Aroma = (TextView) findViewById(R.id.aroma_text6);
		_txt7Aroma = (TextView) findViewById(R.id.aroma_text7);

		_txt5Aroma.setText(String.valueOf(prefrence.getInt("aromaValue", 5)));
		_txt3Aroma
				.setText(String.valueOf(prefrence.getInt("aromaValue", 5) - 2));
		_txt4Aroma
				.setText(String.valueOf(prefrence.getInt("aromaValue", 5) - 1));
		_txt6Aroma
				.setText(String.valueOf(prefrence.getInt("aromaValue", 5) + 1));
		_txt7Aroma
				.setText(String.valueOf(prefrence.getInt("aromaValue", 5) + 2));
		_seekBarAroma.setProgress(50);

		/* sweet Text */

		_txt3Sweet = (TextView) findViewById(R.id.sweet_text3);
		_txt4Sweet = (TextView) findViewById(R.id.sweet_text4);
		_txt5Sweet = (TextView) findViewById(R.id.sweet_text5);
		_txt6Sweet = (TextView) findViewById(R.id.sweet_text6);
		_txt7Sweet = (TextView) findViewById(R.id.sweet_text7);

		_txt5Sweet.setText(String.valueOf(prefrence.getInt("sweetValue", 5)));
		_txt3Sweet
				.setText(String.valueOf(prefrence.getInt("sweetValue", 5) - 2));
		_txt4Sweet
				.setText(String.valueOf(prefrence.getInt("sweetValue", 5) - 1));
		_txt6Sweet
				.setText(String.valueOf(prefrence.getInt("sweetValue", 5) + 1));
		_txt7Sweet
				.setText(String.valueOf(prefrence.getInt("sweetValue", 5) + 2));

		_seekBarSweet.setProgress(50);

		/* bitter text */

		_txt3Bitter = (TextView) findViewById(R.id.bitter_text3);
		_txt4Bitter = (TextView) findViewById(R.id.bitter_text4);
		_txt5Bitter = (TextView) findViewById(R.id.bitter_text5);
		_txt6Bitter = (TextView) findViewById(R.id.bitter_text6);
		_txt7Bitter = (TextView) findViewById(R.id.bitter_text7);

		_txt5Bitter.setText(String.valueOf(prefrence.getInt("bitterValue", 5)));
		_txt3Bitter
				.setText(String.valueOf(prefrence.getInt("bitterValue", 5) - 2));
		_txt4Bitter
				.setText(String.valueOf(prefrence.getInt("bitterValue", 5) - 1));
		_txt6Bitter
				.setText(String.valueOf(prefrence.getInt("bitterValue", 5) + 1));
		_txt7Bitter
				.setText(String.valueOf(prefrence.getInt("bitterValue", 5) + 2));

		_seekBarBitter.setProgress(50);

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

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) {
		case R.id.home_aroma_seek_bar:
			if (_seekBarAroma.getProgress() >= 0
					&& _seekBarAroma.getProgress() < 25) {
				aromaValue = prefrence.getInt("aromaValue", 5) - 2;

				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
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

			} else if (_seekBarAroma.getProgress() >= 50
					&& _seekBarAroma.getProgress() < 75) {
				aromaValue = prefrence.getInt("aromaValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
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
			} else if (_seekBarAroma.getProgress() == 100) {
				aromaValue = prefrence.getInt("aromaValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
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

			} else if (_seekBarAroma.getProgress() >= 25
					&& _seekBarAroma.getProgress() < 50) {
				aromaValue = prefrence.getInt("aromaValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
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
			} else if (_seekBarAroma.getProgress() >= 75
					&& _seekBarAroma.getProgress() < 100) {
				aromaValue = prefrence.getInt("aromaValue", 5) + 1;

				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
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

			if (_seekBarSweet.getProgress() >= 0
					&& _seekBarSweet.getProgress() < 25) {
				sweetvalue = prefrence.getInt("sweetValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
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
			} else if (_seekBarSweet.getProgress() >= 50
					&& _seekBarSweet.getProgress() < 75) {
				sweetvalue = prefrence.getInt("sweetValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
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
			} else if (_seekBarSweet.getProgress() == 100) {
				sweetvalue = prefrence.getInt("sweetValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
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

			} else if (_seekBarSweet.getProgress() >= 25
					&& _seekBarSweet.getProgress() < 50) {
				sweetvalue = prefrence.getInt("sweetValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
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
			} else if (_seekBarSweet.getProgress() >= 75
					&& _seekBarSweet.getProgress() < 100) {
				sweetvalue = prefrence.getInt("sweetValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
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

			if (_seekBarBitter.getProgress() >= 0
					&& _seekBarBitter.getProgress() < 25) {
				bitterValue = prefrence.getInt("bitterValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
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
			} else if (_seekBarBitter.getProgress() >= 50
					&& _seekBarBitter.getProgress() < 75) {
				bitterValue = prefrence.getInt("bitterValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
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
			} else if (_seekBarBitter.getProgress() == 100) {
				bitterValue = prefrence.getInt("bitterValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
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

			} else if (_seekBarBitter.getProgress() >= 25
					&& _seekBarBitter.getProgress() < 50) {
				bitterValue = prefrence.getInt("bitterValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
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
			} else if (_seekBarBitter.getProgress() >= 75
					&& _seekBarBitter.getProgress() < 100) {
				bitterValue = prefrence.getInt("bitterValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
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

			if (maltValue == 3) {
				maltValue = prefrence.getInt("maltValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (maltValue == 5) {
				maltValue = prefrence.getInt("maltValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (maltValue == 7) {
				maltValue = prefrence.getInt("maltValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (maltValue == 4) {
				maltValue = prefrence.getInt("maltValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (maltValue == 6) {
				maltValue = prefrence.getInt("maltValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
				_toast.show();
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

			if (yeastvalue == 3) {
				yeastvalue = prefrence.getInt("yeastValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (yeastvalue == 5) {
				yeastvalue = prefrence.getInt("yeastValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (yeastvalue == 7) {
				yeastvalue = prefrence.getInt("yeastValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (yeastvalue == 4) {
				yeastvalue = prefrence.getInt("yeastValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (yeastvalue == 6) {
				yeastvalue = prefrence.getInt("yeastValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
				_toast.show();
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
			if (mouthValue == 3) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5) - 2;
				_toast = Toast.makeText(this, comment3, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (mouthValue == 5) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5);
				_toast = Toast.makeText(this, comment5, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (mouthValue == 7) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5) + 2;
				_toast = Toast.makeText(this, comment7, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);

			} else if (mouthValue == 4) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5) - 1;
				_toast = Toast.makeText(this, comment4, Toast.LENGTH_SHORT);
				_toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						_toast.cancel();

					}
				}, 1000);
			} else if (mouthValue == 6) {
				mouthValue = prefrence.getInt("mouthFeelValue", 5) + 1;
				_toast = Toast.makeText(this, comment6, Toast.LENGTH_SHORT);
				_toast.show();
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

		/*
		 * switch (seekBar.getId()) {
		 * 
		 * case R.id.home_aroma_seek_bar:
		 * 
		 * aromaValue = (progress / 25) + 3;
		 * 
		 * break;
		 * 
		 * case R.id.home_sweet_seek_bar:
		 * 
		 * sweetvalue = (progress / 25) + 3; break;
		 * 
		 * case R.id.home_bitter_seek_bar:
		 * 
		 * bitterValue = (progress / 25) + 3;
		 * 
		 * break; case R.id.home_malt_seek_bar:
		 * 
		 * maltValue = (progress / 25) + 3; break; case
		 * R.id.home_yeast_seek_bar:
		 * 
		 * yeastvalue = (progress / 25) + 3; break; case
		 * R.id.home_mouth_seek_bar:
		 * 
		 * mouthValue = (progress / 25) + 3; break;
		 * 
		 * }
		 */

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
					Toast.makeText(AddBeerProfile.this, _response,
							Toast.LENGTH_LONG).show();
					finish();

				} else if (strUserRegistration.equalsIgnoreCase("-1")) {
					_response = "Invalid Beer";
					Toast.makeText(AddBeerProfile.this, _response,
							Toast.LENGTH_LONG).show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(AddBeerProfile.this, _response,
							Toast.LENGTH_LONG).show();
				} else if (strUserRegistration.equalsIgnoreCase("-3")) {
					_response = "Invalid User";
					Toast.makeText(AddBeerProfile.this, _response,
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

		builder = new AlertDialog.Builder(AddBeerProfile.this);
		builder.setTitle("Would you like to add optional parameters?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent toAddBeerProfile2 = new Intent(AddBeerProfile.this,
						AddBeerProfile2.class);
				toAddBeerProfile2.putExtra("beerId", bundle.getString("beerId"));
				toAddBeerProfile2.putExtra("userId", bundle.getString("userId"));
				toAddBeerProfile2.putExtra("aroma", aromaValue);
				toAddBeerProfile2.putExtra("sweet", sweetvalue);
				toAddBeerProfile2.putExtra("bitter", bitterValue);
				toAddBeerProfile2.putExtra("malt", maltValue);
				toAddBeerProfile2.putExtra("yeast", yeastvalue);
				toAddBeerProfile2.putExtra("mouthFeel", mouthValue);
				startActivity(toAddBeerProfile2);

			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				if (CheckInternetConnectivity
						.checkinternetconnection(AddBeerProfile.this)) {

					String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><addBeerProfile><beerId><![CDATA["
							+ bundle.getString("beerId")
							+ "]]></beerId><userId><![CDATA["
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
							+ "]]></booziness></addBeerProfile>";

					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.ADD_BEER_PROFILE, AddBeerProfile.this,
							REGISTARTION_XML, AddBeerProfile.this);
					hitRegistartion.execute();
				} else {

					Toast.makeText(AddBeerProfile.this,
							"Check Internet Connection", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		builder.show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.home_aroma_edt:

			showDialog();

			break;

		case R.id.home_sweet_edt:

			showDialog1();
			break;

		case R.id.home_bitter_edt:

			showDialog2();
			break;

		case R.id.home_cancel_btn:
			finish();
			break;

		case R.id.info_btn:
			startActivity(new Intent(AddBeerProfile.this,Instruction.class).putExtra("via", "PRO_BEER"));


	
			break;

		}

	}

	public void showDialog() {
		aromaShow = "";
		/*
		 * for(int i=0;i<arrayListNew.size();i++){
		 * 
		 * arrayListNew.get(i).setChecked(false);
		 * 
		 * 
		 * }
		 */
		dialog = new Dialog(AddBeerProfile.this);

		dialog.setTitle("Select Descriptor for Aroma ");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListNew.size(); i++) {

					if (arrayListNew.get(i).isChecked() == true) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListNew.get(i).getName();
						} else {

							aromaShow = arrayListNew.get(i).getName();
						}
					}
				}

				_txtAroma.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

		adapterB = new SettingAdapterb(arrayListNew);
		list.setAdapter(adapterB);

		dialog.show();

	}

	public void showDialog1() {

		aromaShow = "";

		/*
		 * for(int i=0;i<arrayListSweet.size();i++){
		 * arrayListSweet.get(i).setChecked(false);
		 * 
		 * }
		 */

		dialog = new Dialog(AddBeerProfile.this);

		dialog.setTitle("Select Descriptor for Sweet ");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListSweet.size(); i++) {

					if (arrayListSweet.get(i).isChecked()) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListSweet.get(i).getName();
						} else {

							aromaShow = arrayListSweet.get(i).getName();
						}
					}
				}

				_txtSweet.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

		adapterB1 = new SettingAdapterb1(arrayListSweet);

		list.setAdapter(adapterB1);

		dialog.show();

	}

	public void showDialog2() {

		aromaShow = "";

		/*
		 * for(int i=0;i<arrayListBitter.size();i++){
		 * arrayListBitter.get(i).setChecked(false);
		 * 
		 * }
		 */
		dialog = new Dialog(AddBeerProfile.this);

		dialog.setTitle("Select Descriptor for Bitter ");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListBitter.size(); i++) {

					if (arrayListBitter.get(i).isChecked()) {

						if (!aromaShow.equals("")) {

							aromaShow = aromaShow + ","
									+ arrayListBitter.get(i).getName();
						} else {

							aromaShow = arrayListBitter.get(i).getName();
						}
					}
				}

				_txtBitter.setText(aromaShow);

				dialog.dismiss();
				aromaShow = "";

			}
		});

		adapterB2 = new SettingAdapterb2(arrayListBitter);

		list.setAdapter(adapterB2);

		dialog.show();

	}

	public class SettingAdapterb extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterb(ArrayList<MODEL_AROMA> array2) {
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

			LayoutInflater inflater = LayoutInflater.from(AddBeerProfile.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				ViewHolder holder = new ViewHolder();
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();

			holder.txt.setText(array1.get(position).getName());

			if (arrayListNew.get(position).isChecked()) {

				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);

			} else {

				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListNew.get(position).setChecked(
							!arrayListNew.get(position).isChecked());

					adapterB.notifyDataSetChanged();

				}
			});

			return convertView;
		}

	}

	class ViewHolder {
		TextView txt;
		ImageView chkBx;
	}

	public class SettingAdapterb2 extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterb2(ArrayList<MODEL_AROMA> array2) {
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
			LayoutInflater inflater = LayoutInflater.from(AddBeerProfile.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());

			if (arrayListBitter.get(position).isChecked()) {
				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);

			} else {

				holder.chkBx.setBackgroundResource(R.drawable.unchecked);

			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListBitter.get(position).setChecked(
							!arrayListBitter.get(position).isChecked());
					adapterB2.notifyDataSetChanged();

				}
			});

			/*
			 * holder.chkBx.setChecked(array1.get(position).isChecked());
			 * 
			 * holder.chkBx .setOnCheckedChangeListener(new
			 * OnCheckedChangeListener() {
			 * 
			 * @Override public void onCheckedChanged(CompoundButton buttonView,
			 * boolean isChecked) { // TODO Auto-generated method stub
			 * 
			 * arrayListBitter.get(position).setChecked(isChecked);
			 * 
			 * } });
			 */

			return convertView;
		}

	}

	public class SettingAdapterb1 extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterb1(ArrayList<MODEL_AROMA> array2) {
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
			LayoutInflater inflater = LayoutInflater.from(AddBeerProfile.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());
			if (arrayListSweet.get(position).isChecked()) {
				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);

			} else {

				holder.chkBx.setBackgroundResource(R.drawable.unchecked);

			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					arrayListSweet.get(position).setChecked(
							!arrayListSweet.get(position).isChecked());

					adapterB1.notifyDataSetChanged();
				}
			});

			/*
			 * holder.chkBx.setChecked(array1.get(position).isChecked());
			 * 
			 * holder.chkBx .setOnCheckedChangeListener(new
			 * OnCheckedChangeListener() {
			 * 
			 * @Override public void onCheckedChanged(CompoundButton buttonView,
			 * boolean isChecked) { // TODO Auto-generated method stub
			 * 
			 * arrayListSweet.get(position).setChecked(isChecked);
			 * 
			 * } });
			 */

			return convertView;
		}

	}

	private void showMeaningOfParameter(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				AddBeerProfile.this);
		builder.setMessage(message);
		builder.setPositiveButton("Dismiss", null);

		builder.show();

	}

}
