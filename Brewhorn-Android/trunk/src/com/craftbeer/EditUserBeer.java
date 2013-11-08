package com.craftbeer;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;

public class EditUserBeer extends Activity implements HttpListener,
		OnClickListener {

	/**
	 * @author arvind.agarwal this class is useful edit beer
	 * 
	 */

	// string object
	private String title, line;

	private int position;

	private Dialog _dialog;

	// array String for mood
	private String[] moodArray = { "Physically Tired", "Long Day", "Happy",
			"Frustrated", "Mentally Tired", "BlowOffSteam", "Excited",
			"FeelingMeh", "Exhausted", "FeelingGood", "FeelingGreat",
			"FeelingJustOK", "RoughDay", "Celebratory", "NeedADrink" };
	// array String for venue
	private String[] venueArray = { "Home", "Patio", "Porch", "Friend's House",
			"Favourite Bar", "Tasting Room", "Ballgame", "NewBar",
			"Restaurant", "Airport", "Plane", "Train", "Cruise",
			"VacationSpot", "Beach" };
	// array String for event
	private String[] eventArray = { "None", "Small Gathering", "Dinner", "BBQ",
			"Festival", "BeerFoodPairing", "Party", "Happy Hour",
			"BottleShare", "BeerGeekMeetup", "TastingEvent", "Vacation",
			"WorkTravel" };
	// array String for hype
	private String[] hypeArray = { "None", "Recent", "Longstanding",
			"TopXList", "HighScore", "GottaHave", "NeverHeardOf",
			"Recommendation", "JustHeardOf", "Grapevine" };
	// object of view class
	private EditText _edtBrewery, _edtBeerName, _edtAbv, _edtIbu;
	private Button _spinnerMood, _spinnerVenue, _spinnerEvent,
			_spinnerHypeExpectation;

	private Button _btnSave, _btnCancel, _spinnerBeerStyle;
	// object of bundle
	private Bundle bundle;
	// string object to show response of server hit
	private String _response;

	private TextView _titleText;

	public static Activity activity;
	// object of shared preference
	private SharedPreferences prefer;

	private Button instructionBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_new_beer);

		// to hide ey board first time comes in activity
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		activity = this;

		// initializing view and objects
		initializeView();

		_edtBrewery.setText(bundle.getString("BREWERY"));
		_edtBeerName.setText(bundle.getString("BEER_NAME"));
		_spinnerBeerStyle.setText(bundle.getString("BEER_STYLE"));
		_edtAbv.setText(bundle.getString("ABV"));
		_edtIbu.setText(bundle.getString("IBU"));

		if (!_edtBrewery.getText().toString().equals("")) {
			_edtBrewery.setEnabled(false);
		}

		if (!_edtBeerName.getText().toString().equals("")) {
			_edtBeerName.setEnabled(false);
		}

		if (!_spinnerBeerStyle.getText().toString().equals("")) {

			_spinnerBeerStyle.setBackgroundResource(R.drawable.text_field);
			_spinnerBeerStyle.setEnabled(false);

		}
		if (!_edtAbv.getText().toString().equals("")) {
			_edtAbv.setEnabled(false);
		}

		if (!_edtIbu.getText().toString().equals("")) {
			_edtIbu.setEnabled(false);
		}

		_btnSave.setOnClickListener(new OnClickListener() {
			
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				FlurryAgent.logEvent("Edit_Beer");

				if (CheckInternetConnectivity
						.checkinternetconnection(EditUserBeer.this)) {

					// hit for edit beer
					String EDIT_USER_BEER_XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><editUserBeer><beerId><![CDATA["
							+ bundle.getString("beerId")
							+ "]]></beerId><userId><![CDATA["
							+ bundle.getString("userId")
							+ "]]></userId><brewery><![CDATA["
							+ _edtBrewery.getText().toString().trim()
							+ "]]></brewery><beerName><![CDATA["
							+ _edtBeerName.getText().toString()
							+ "]]></beerName><beerStyle><![CDATA["
							+ _spinnerBeerStyle.getText().toString()
							+ "]]></beerStyle><abv><![CDATA["
							+ _edtAbv.getText().toString().trim()
							+ "]]></abv><ibu><![CDATA["
							+ _edtIbu.getText().toString().trim()
							+ "]]></ibu><mood><![CDATA["
							+ _spinnerMood.getText().toString()
							+ "]]></mood><venue><![CDATA["
							+ _spinnerVenue.getText().toString()
							+ "]]></venue><event><![CDATA["
							+ _spinnerEvent.getText().toString()
							+ "]]></event><hype><![CDATA["
							+ _spinnerHypeExpectation.getText().toString()
							+ "]]></hype></editUserBeer>";

					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+ Url.EDIT_USER_BEER_URL, EditUserBeer.this,
							EDIT_USER_BEER_XML, EditUserBeer.this);
					hitRegistartion.execute();
				} else {

					Toast.makeText(EditUserBeer.this,
							"Check Internet Connection", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

	}

	/**
	 * Initializing view and objects
	 * 
	 */

	private void initializeView() {
		// TODO Auto-generated method stub

		bundle = getIntent().getExtras();
		prefer = PreferenceManager
				.getDefaultSharedPreferences(EditUserBeer.this);

		_edtBrewery = (EditText) findViewById(R.id.add_beer_brewery_edt_new);
		_edtBeerName = (EditText) findViewById(R.id.add_beer_name_edt_new);
		_edtAbv = (EditText) findViewById(R.id.add_beer_abv_edt);
		_edtIbu = (EditText) findViewById(R.id.add_beer_ibu_edt);

		instructionBtn = (Button) findViewById(R.id.btn_instruction);
		instructionBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(EditUserBeer.this, Instruction.class)
						.putExtra("via", "EDIT_BEER"));

			}
		});

		_spinnerBeerStyle = (Button) findViewById(R.id.add_beer_style_spinner);
		_spinnerMood = (Button) findViewById(R.id.add_beer_mood_spinner);

		_spinnerVenue = (Button) findViewById(R.id.add_beer_venue_spinner);
		_spinnerEvent = (Button) findViewById(R.id.add_beer_event_spinner);
		_spinnerHypeExpectation = (Button) findViewById(R.id.add_beer_hype_expectation_spinner);

		_spinnerBeerStyle.setOnClickListener(this);
		_spinnerMood.setOnClickListener(this);

		_spinnerVenue.setOnClickListener(this);
		_spinnerEvent.setOnClickListener(this);
		_spinnerHypeExpectation.setOnClickListener(this);

		_btnSave = (Button) findViewById(R.id.add_beer_save_btn);

		_btnCancel = (Button) findViewById(R.id.add_beer_cancel_btn);

		_btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		_titleText = (TextView) findViewById(R.id.title);
		if (bundle.getString("BEERBOTTLE").equals("0")) {
			_titleText.setText("Edit Beer and Taste Influencers");
		} else {

			_titleText.setText("Edit Taste Influencers");
		}

	}

	/**
	 * getting response of server hit
	 */
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		Log.i("", "" + response);

		JSONObject jsonForResponse = null;

		if (response.contains("editUserBeer")) {
			try {

				jsonForResponse = new JSONObject(response);

				String editBeerResponse = jsonForResponse
						.getString("editUserBeer");

				if (editBeerResponse.equalsIgnoreCase("-2")) {
					_response = "Server Error";

					Toast.makeText(EditUserBeer.this, _response,
							Toast.LENGTH_SHORT).show();

				} else if (editBeerResponse.equalsIgnoreCase("1")) {
					_response = "Beer successfully edited";
					Toast.makeText(EditUserBeer.this, _response,
							Toast.LENGTH_SHORT).show();

					SharedPreferences.Editor editor = prefer.edit();
					editor.putString("PROFILED_BEER_NAME", _edtBeerName
							.getText().toString());
					editor.putString("BREWERY_NAME", _edtBrewery.getText()
							.toString());
					editor.commit();

					Intent i = new Intent(EditUserBeer.this,
							EditBeerProfile.class);

					i.putExtra("beerId", bundle.getString("beerId"));
					i.putExtra("userId", bundle.getString("userId"));

					startActivity(i);

					finish();

				} else if (editBeerResponse.equalsIgnoreCase("-3")) {

					_response = "Invalid User";
					Toast.makeText(EditUserBeer.this, _response,
							Toast.LENGTH_SHORT).show();

				} else if (editBeerResponse.equalsIgnoreCase("-4")) {
					_response = "Beer name already exists";
					Toast.makeText(EditUserBeer.this, _response,
							Toast.LENGTH_SHORT).show();

				}

			} catch (JSONException e) {

				/* catches json exceptions */

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
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.add_beer_style_spinner:

			createPicker();
			break;

		case R.id.add_beer_mood_spinner:
			showListMood();
			break;

		case R.id.add_beer_venue_spinner:

			showListVenue();
			break;
		case R.id.add_beer_event_spinner:
			showListEvent();
			break;

		case R.id.add_beer_hype_expectation_spinner:
			showListHype();
			break;

		}

	}

	/**
	 * @param this method shows a list of moods
	 */

	private void showListMood() {
		_dialog = new Dialog(EditUserBeer.this);
		_dialog.setTitle("Mood");

		_dialog.setContentView(R.layout.dialg_listing);

		ListView list = (ListView) _dialog.findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(EditUserBeer.this,
				android.R.layout.simple_list_item_1, moodArray));

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				_spinnerMood.setText(moodArray[arg2]);

				_dialog.dismiss();

			}
		});

		_dialog.show();

	}

	/**
	 * @param this method shows a list of venues
	 */
	private void showListVenue() {
		_dialog = new Dialog(EditUserBeer.this);
		_dialog.setTitle("Venue");

		_dialog.setContentView(R.layout.dialg_listing);

		ListView list = (ListView) _dialog.findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(EditUserBeer.this,
				android.R.layout.simple_list_item_1, venueArray));

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				_spinnerVenue.setText(venueArray[arg2]);

				_dialog.dismiss();

			}
		});

		_dialog.show();

	}

	/**
	 * @param this method shows a list of event
	 */
	private void showListEvent() {
		_dialog = new Dialog(EditUserBeer.this);
		_dialog.setTitle("Event");

		_dialog.setContentView(R.layout.dialg_listing);

		ListView list = (ListView) _dialog.findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(EditUserBeer.this,
				android.R.layout.simple_list_item_1, eventArray));

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				_spinnerEvent.setText(eventArray[arg2]);

				_dialog.dismiss();

			}
		});

		_dialog.show();

	}

	/**
	 * @param this method shows a list of hype
	 */
	private void showListHype() {
		_dialog = new Dialog(EditUserBeer.this);
		_dialog.setTitle("Hype/Expectation");

		_dialog.setContentView(R.layout.dialg_listing);

		ListView list = (ListView) _dialog.findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(EditUserBeer.this,
				android.R.layout.simple_list_item_1, hypeArray));

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				_spinnerHypeExpectation.setText(hypeArray[arg2]);

				_dialog.dismiss();

			}
		});

		_dialog.show();

	}

	/**
	 * @param this method shows a list of styles
	 */

	private void createPicker() {

		_dialog = new Dialog(EditUserBeer.this);

		_dialog.setContentView(R.layout.picker_dialog);

		final ListView l1 = (ListView) _dialog.findViewById(R.id.list1);
		final ListView l2 = (ListView) _dialog.findViewById(R.id.list2);

		Button backBtn = (Button) _dialog.findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				_dialog.dismiss();

			}
		});

		final String[] array1 = new String[] { "American Ales",
				"Belgian/French Ales", "English Ales", "Finnish Ales",
				"German Ales", "Irish Ales", "Russian Ales", "Scottish Ales",
				"American Lagers", "Czech Lagers", "European Lagers",
				"German Lagers", "Japnese Lagers", "Fruit Beer" };
		final String[] a0 = new String[] { "American Amber/Red Ale",
				"American Barleywine", "American Black Ale",
				"American Blonde Ale", "American Brown Ale",
				"American Dark Wheat Ale", "American Double/Imperial IPA",
				"American Double/Imperial Stout", "American IPA",
				"American Pale Ale(APA)", "American Pale Wheat Ale",
				"American Porter", "American Stout", "American Strong Ale",
				"American Wild Ale ", "Black And Tan", "Chile Beer",
				"Cream Ale", "Pumpkin Ale", "Rye Beer", "Wheatwine" };
		final String[] a1 = new String[] { "Belgian Dark Ale", "Belgian IPA",
				"Belgian Pale Ale", "Belgian Strong Dark Ale",
				"Belgian Strong Pale Ale", "Bière de Champagne / Bière Brut",
				"Bière de Garde", "Dubbel", "Faro", "Flanders Oud Bruin",
				"Flanders Red Ale", "Gueuze", "Lambic - Fruit",
				"Lambic - Unblended", "Quadrupel (Quad)",
				"Saison / Farmhouse Ale", "Tripel", "Witbier" };

		final String[] a2 = new String[] {

		"Baltic Porter", "Braggot", "English Barleywine", "English Bitter",
				"English Brown Ale", "English Dark Mild Ale",
				"English India Pale Ale (IPA)", "English Pale Ale",
				"English Pale Mild Ale", "English Porter", "English Stout",
				"English Strong Ale", "Extra Special / Strong Bitter (ESB)",
				"Foreign / Export Stout", "Milk / Sweet Stout",
				"Oatmeal Stout", "Old Ale", "Russian Imperial Stout",
				"Winter Warmer"

		};

		final String[] a3 = new String[] { "Sahti" };

		final String[] a4 = new String[] {

		"Altbier", "Berliner Weissbier", "Dunkelweizen", "Gose", "Hefeweizen",
				"Kölsch", "Kristalweizen", "Roggenbier", "Weizenbock" };

		final String[] a5 = new String[] { "Irish Dry Stout", "Irish Red Ale" };

		final String[] a6 = new String[] { "Kvass" };

		final String[] a7 = new String[] {

		"Scotch Ale / Wee Heavy", "Scottish Ale",
				"Scottish Gruit / Ancient Herbed Ale" };

		final String[] a8 = new String[] {

		"American Adjunct Lager", "American Amber / Red Lager",
				"American Double / Imperial Pilsner", "American Malt Liquor",
				"American Pale Lager", "California Common / Steam Beer",
				"Light Lager", "Low Alcohol Beer" };

		final String[] a9 = new String[] {

		"Czech Pilsener"

		};

		final String[] a10 = new String[] {

		"Euro Dark Lager", "Euro Pale Lager", "Euro Strong Lager"

		};

		final String[] a11 = new String[] {

		"Bock", "Doppelbock", "Dortmunder / Export Lager", "Eisbock",
				"German Pilsener", "Keller Bier / Zwickel Bier",
				"Maibock / Helles Bock", "Märzen / Oktoberfest",
				"Munich Dunkel Lager", "Munich Helles Lager", "Rauchbier",
				"Schwarzbier", "Vienna Lager"

		};
		final String[] a12 = new String[] {

		"Happoshu", "Japanese Rice Lager" };

		final String[] a13 = new String[] {

		"Fruit Beer" };

		final ArrayList<String[]> arrayList = new ArrayList<String[]>();
		arrayList.add(a0);
		arrayList.add(a1);
		arrayList.add(a2);
		arrayList.add(a3);
		arrayList.add(a4);
		arrayList.add(a5);
		arrayList.add(a6);
		arrayList.add(a7);
		arrayList.add(a8);
		arrayList.add(a9);
		arrayList.add(a10);
		arrayList.add(a11);
		arrayList.add(a12);
		arrayList.add(a13);

		l1.setAdapter(new ArrayAdapter<String>(EditUserBeer.this,
				android.R.layout.simple_list_item_1, array1));

		l1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				title = array1[arg2];

				position = arg2;

				l2.setAdapter(new ArrayAdapter<String>(EditUserBeer.this,
						android.R.layout.simple_list_item_1, arrayList
								.get(arg2)));

			}
		});

		l2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				line = arrayList.get(position)[arg2];

				_spinnerBeerStyle.setText(title + " " + line);
				_dialog.dismiss();

			}
		});
		_dialog.show();

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
