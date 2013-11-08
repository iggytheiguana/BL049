package com.craftbeer;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;



/**
 * 
 * @author arvind.agarwal
 * this class is required to add new beer
 *
 */
public class AddNewBeer extends Activity implements HttpListener {
	
	
	//Array for mood
	private String[] moodArray = { "Physically Tired", "Long Day",
			"Happy", "Frustrated", "Mentally Tired", "BlowOffSteam","Excited","FeelingMeh" ,"Exhausted","FeelingGood","FeelingGreat","FeelingJustOK","RoughDay","Celebratory","NeedADrink"};
	//array for venue
	private String[] venueArray = {  "Home", "Patio", "Porch",
			"Friend's House", "Favourite Bar", "Tasting Room","Ballgame" ,"NewBar","Restaurant","Airport","Plane","Train","Cruise","VacationSpot","Beach"};
	//array for event
	private String[] eventArray = { "None", "Small Gathering", "Dinner",
			"BBQ", "Festival", "BeerFoodPairing", "Party", "Happy Hour" ,"BottleShare","BeerGeekMeetup","TastingEvent","Vacation","WorkTravel"};
	//array for hype
	private String[] hypeArray = {"None","Recent", "Longstanding","TopXList"
			,"HighScore","GottaHave","NeverHeardOf","Recommendation","JustHeardOf","Grapevine"};
	
//objects for view class
	private EditText _edtBrewery, _edtBeerName, _edtAbv, _edtIbu;

	private Button _spinnerVenue,
			_spinnerEvent, _spinnerHypeExpectation;
	
	
	private Button _spinnerMood;

	private Button _btnSave, _btnCancel;
	
	private Button  _spinnerBeerStyle;

	private Bundle bundle;
	private String _response;
	
	
	private Dialog _dialog;
	
	
	
	private String title,line;
	
	private  int position ;
	//shared preference object
	private SharedPreferences prefer;
	
	private Button instructinBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_new_beer);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		initializeView();

		_btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				FlurryAgent.logEvent("Add_Beer");

				if (!CheckInternetConnectivity

				.checkinternetconnection(AddNewBeer.this)) {

					Toast.makeText(AddNewBeer.this,
							"Check Internet Connection", Toast.LENGTH_SHORT)
							.show();

				} else if (_edtBrewery.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(AddNewBeer.this, "Enter brewery name.",
							Toast.LENGTH_SHORT).show();
				} else if (_edtBeerName.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(AddNewBeer.this, "Enter beer name.",
							Toast.LENGTH_SHORT).show();
				} else {

					Log.i("",""+_edtBeerName.getText().toString());
					
					
					String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><addUserBeer><userId><![CDATA["
							+ bundle.getString("userId")
							+ "]]></userId><brewery><![CDATA["
							+ _edtBrewery.getText().toString().trim()
							+ "]]></brewery><beerName><![CDATA["
							+ _edtBeerName.getText().toString().trim()
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
							+ "]]></hype></addUserBeer>";

					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
							+Url.ADD_USER_BEER_URL, AddNewBeer.this,
							REGISTARTION_XML, AddNewBeer.this);
					hitRegistartion.execute();

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
		
		
		prefer=PreferenceManager.getDefaultSharedPreferences(AddNewBeer.this);

		bundle = getIntent().getExtras();
		
		instructinBtn=(Button)findViewById(R.id.btn_instruction);
		instructinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AddNewBeer.this,Instruction.class).putExtra("via", "ADD_BEER"));

			}
		});
		

		_edtBrewery = (EditText) findViewById(R.id.add_beer_brewery_edt_new);
		_edtBeerName = (EditText) findViewById(R.id.add_beer_name_edt_new);
		_edtAbv = (EditText) findViewById(R.id.add_beer_abv_edt);
		_edtIbu = (EditText) findViewById(R.id.add_beer_ibu_edt);

		_spinnerBeerStyle = (Button) findViewById(R.id.add_beer_style_spinner);
		
		_spinnerBeerStyle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				createPicker();
			}
		});

		/*_spinnerBeerStyle.setAdapter(new ArrayAdapter<String>(AddNewBeer.this,
				android.R.layout.simple_spinner_item, beerStyleArray));*/

		_spinnerMood = (Button) findViewById(R.id.add_beer_mood_spinner);
		_spinnerMood.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				showListMood();
				
				
			}
		});

		_spinnerVenue = (Button) findViewById(R.id.add_beer_venue_spinner);

		_spinnerVenue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showListVenue();
				
			}
		});
		_spinnerEvent = (Button) findViewById(R.id.add_beer_event_spinner);
		_spinnerEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showListEvent();
				
				
			}
		});
		_spinnerHypeExpectation = (Button) findViewById(R.id.add_beer_hype_expectation_spinner);
		_spinnerHypeExpectation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showListHype();
				
			}
		});

		_btnSave = (Button) findViewById(R.id.add_beer_save_btn);
		_btnCancel = (Button) findViewById(R.id.add_beer_cancel_btn);

	}

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		Log.i("response", "" + response);

		JSONObject registerJsonObj = null;

		if (response.contains("addUserBeer")) {

			try {

				registerJsonObj = new JSONObject(response);
				final String strUserRegistration = registerJsonObj
						.getString("addUserBeer");
				if (strUserRegistration.equalsIgnoreCase("-3")) {
					_response = "Invalid User";
					Toast.makeText(AddNewBeer.this, _response,
							Toast.LENGTH_LONG).show();
				}

				else if (strUserRegistration.equalsIgnoreCase("-2")) {
					_response = "Server Error";
					Toast.makeText(AddNewBeer.this, _response,
							Toast.LENGTH_LONG).show();
				} else if (strUserRegistration.equalsIgnoreCase("-4")) {
					_response = "Beer name already exists";
					Toast.makeText(AddNewBeer.this, _response,
							Toast.LENGTH_LONG).show();
				} else {
					_response = "Beer Successfully added";
					
					
					Toast.makeText(AddNewBeer.this, _response,
							Toast.LENGTH_SHORT).show();
						
					SharedPreferences.Editor editor=prefer.edit();
					editor.putString("PROFILED_BEER_NAME", _edtBeerName.getText().toString());
					editor.putString("BREWERY_NAME",_edtBrewery.getText().toString());
					editor.commit();


					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							Intent toAddBeerProfile = new Intent(
									AddNewBeer.this, AddBeerProfile.class);
							toAddBeerProfile.putExtra("beerId",
									strUserRegistration);
							toAddBeerProfile.putExtra("title", " Profile The Beer");
							toAddBeerProfile.putExtra("userId",
									bundle.getString("userId"));
							startActivity(toAddBeerProfile);

							finish();

						}
					}, 1200);
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

	
	
	
	private void createPicker(){
		
		
		_dialog=new Dialog(AddNewBeer.this);
		
		_dialog.setContentView(R.layout.picker_dialog);
		

	final 	ListView l1=(ListView)_dialog.findViewById(R.id.list1);
		final ListView l2=(ListView)_dialog.findViewById(R.id.list2);
		
		Button  backBtn=(Button)_dialog.findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				_dialog.dismiss();
				
				
			}
		});
		
		
		
		final String[] array1=new String[]{"American Ales","Belgian/French Ales","English Ales","Finnish Ales","German Ales","Irish Ales","Russian Ales","Scottish Ales","American Lagers","Czech Lagers","European Lagers","German Lagers","Japnese Lagers","Fruit Beer"};
	final 	String[] a0=new String[]{"American Amber/Red Ale","American Barleywine","American Black Ale","American Blonde Ale","American Brown Ale","American Dark Wheat Ale","American Double/Imperial IPA","American Double/Imperial Stout","American IPA","American Pale Ale(APA)","American Pale Wheat Ale","American Porter","American Stout","American Strong Ale","American Wild Ale ","Black And Tan","Chile Beer","Cream Ale","Pumpkin Ale","Rye Beer","Wheatwine"};
	final 	String[] a1=new String[]{"Belgian Dark Ale","Belgian IPA","Belgian Pale Ale","Belgian Strong Dark Ale",
		"Belgian Strong Pale Ale",
	"Bière de Champagne / Bière Brut",
	"Bière de Garde",
	"Dubbel",
	"Faro",
	"Flanders Oud Bruin",
	"Flanders Red Ale",
	"Gueuze",
	"Lambic - Fruit",
	"Lambic - Unblended",
	"Quadrupel (Quad)",
	"Saison / Farmhouse Ale",
	"Tripel",
	"Witbier"
	};
	
	final String[] a2=new String[] {
	
	"Baltic Porter",
	"Braggot",
	"English Barleywine",
	"English Bitter",
	"English Brown Ale",
	"English Dark Mild Ale",
	"English India Pale Ale (IPA)",
	"English Pale Ale",
	"English Pale Mild Ale",
	"English Porter",
	"English Stout",
	"English Strong Ale",
	"Extra Special / Strong Bitter (ESB)",
	"Foreign / Export Stout",
	"Milk / Sweet Stout",
	"Oatmeal Stout",
	"Old Ale",
	"Russian Imperial Stout",
	"Winter Warmer"

	};
	
	
	final String[] a3=new String[] {"Sahti"};
	
	final String[] a4=new String[]{
	
	
	"Altbier",
	"Berliner Weissbier",
	"Dunkelweizen",
	"Gose",
	"Hefeweizen",
	"Kölsch",
	"Kristalweizen",
	"Roggenbier",
	"Weizenbock"};

	final String[] a5=new String[]{
	"Irish Dry Stout",
			"Irish Red Ale"
	};
	
	
	final String[] a6=new String[]{
		"Kvass"
	};
	
	
	final String[] a7=new String[]{
		
		"Scotch Ale / Wee Heavy",
			"Scottish Ale",
			"Scottish Gruit / Ancient Herbed Ale"
	};
	
	
	
	final String[] a8=new String[]{
			
			
			"American Adjunct Lager",
			"American Amber / Red Lager",
			"American Double / Imperial Pilsner",
			"American Malt Liquor",
			"American Pale Lager",
			"California Common / Steam Beer",
			"Light Lager",
			"Low Alcohol Beer"
		};
	
	
	
	
	final String[] a9=new String[]{
		
			"Czech Pilsener"
			
	};
	
	
	
	final String[] a10=new String[]{
			
			"Euro Dark Lager",
			"Euro Pale Lager",
			"Euro Strong Lager"
	
	};
	
	
	final String[] a11=new String[]{
			
			"Bock",
			"Doppelbock",
			"Dortmunder / Export Lager",
			"Eisbock",
			"German Pilsener",
			"Keller Bier / Zwickel Bier",
			"Maibock / Helles Bock",
			"Märzen / Oktoberfest",
			"Munich Dunkel Lager",
			"Munich Helles Lager",
			"Rauchbier",
			"Schwarzbier",
			"Vienna Lager"
			
	};
	final String[] a12=new String[]{
			
			"Happoshu",
			"Japanese Rice Lager"
	};
	
	final String[] a13=new String[]{
			"Fruit Beer"
	};
	
	
	final ArrayList<String[]> arrayList=new ArrayList<String[]>();
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
	
		l1.setAdapter(new ArrayAdapter<String>(AddNewBeer.this,android.R.layout.simple_list_item_1,array1));
		
		l1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			
			title=array1[arg2];
			
			position=arg2;
					
					l2.setAdapter(new ArrayAdapter<String>(AddNewBeer.this,android.R.layout.simple_list_item_1,arrayList.get(arg2)));
				
				
			}
		});
		
		
		l2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				
				line=arrayList.get(position)[arg2];
				
				_spinnerBeerStyle.setText(title+" "+line);
				_dialog.dismiss();
				
				
			}
		});
		_dialog.show();
		
		
	}
	
	/**
	 * shows list of mood
	 */
	
	
private void showListMood()
{
	_dialog=new Dialog(AddNewBeer.this);
	_dialog.setTitle("Mood");
	
	_dialog.setContentView(R.layout.dialg_listing);
	
	
	
	ListView list=(ListView)_dialog.findViewById(R.id.list);
	list.setAdapter(new ArrayAdapter<String>(AddNewBeer.this, android.R.layout.simple_list_item_1,moodArray));
	
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
 * shows list of venues
 */
private void showListVenue()
{
	_dialog=new Dialog(AddNewBeer.this);
	_dialog.setTitle("Venue");
	
	_dialog.setContentView(R.layout.dialg_listing);
	
	
	
	ListView list=(ListView)_dialog.findViewById(R.id.list);
	list.setAdapter(new ArrayAdapter<String>(AddNewBeer.this, android.R.layout.simple_list_item_1,venueArray));
	
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
 * this method shows event list
 */
private void showListEvent()
{
	_dialog=new Dialog(AddNewBeer.this);
	_dialog.setTitle("Event");
	
	_dialog.setContentView(R.layout.dialg_listing);
	
	
	
	ListView list=(ListView)_dialog.findViewById(R.id.list);
	list.setAdapter(new ArrayAdapter<String>(AddNewBeer.this, android.R.layout.simple_list_item_1,eventArray));
	
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
 * this methode shows hype list
 */
private void showListHype()
{
	_dialog=new Dialog(AddNewBeer.this);
	_dialog.setTitle("Hype/Expectation");
	
	_dialog.setContentView(R.layout.dialg_listing);
	
	
	
	ListView list=(ListView)_dialog.findViewById(R.id.list);
	list.setAdapter(new ArrayAdapter<String>(AddNewBeer.this, android.R.layout.simple_list_item_1,hypeArray));
	
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
