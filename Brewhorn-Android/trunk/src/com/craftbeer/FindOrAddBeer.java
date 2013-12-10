package com.craftbeer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.craftbeer.adapter.ShowBeerAdapter;
import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.modal.SearchBeerModel;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;

/**
 * 
 * @author arvind.agarwal
 * 
 *         this class is useful for finding or adding beer
 * 
 */


@SuppressLint("DefaultLocale")
public class FindOrAddBeer extends Activity implements HttpListener {

	// object of view class
	private Button _btnFindBeer, _btnEnterNewBeer, _btnEditBeer, _btnShare,
			_btnUserProfile;
	private EditText _edtFindBeer;

	// arraylist of beer params getter setter class
	private ArrayList<SearchBeerModel> SEARCHBEER_AL;

	private ListView _listViewBeer;

	private AlertDialog.Builder alert;
	// object of bundle
	private Bundle bundle;

	// object of baseAdapter class

	private ShowBeerAdapter adapter;
	
	private Button instructionBtn;
	
	TextView textHint;
	
	public static String facebookHandle = "";
	public static String twitterHandle = "";
	public static String facebookUrl = "";
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.find_add_beer);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		initializeView();

		SEARCHBEER_AL = new ArrayList<SearchBeerModel>();
		adapter = new ShowBeerAdapter(FindOrAddBeer.this, SEARCHBEER_AL);

		_listViewBeer.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		_listViewBeer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				// Seding all parameters of beer along with the percentage match
				// with your taste profile
				facebookHandle = "";
				twitterHandle = "";
				facebookUrl = "";
				Intent toUserBeerProfile = new Intent(FindOrAddBeer.this,
						UserBeerProfile.class);
				toUserBeerProfile.putExtra("TASTE_PERCENTAGE", SEARCHBEER_AL
						.get(arg2).getTastePercentage());
				toUserBeerProfile.putExtra("userId", bundle.getString("userId"));
				toUserBeerProfile.putExtra("beerId", SEARCHBEER_AL.get(arg2)
						.getBeerId());
				toUserBeerProfile.putExtra("BREWERY", SEARCHBEER_AL.get(arg2)
						.getBrewery());
				toUserBeerProfile.putExtra("BEER_NAME", SEARCHBEER_AL.get(arg2)
						.getBeerName());
				toUserBeerProfile.putExtra("BEER_STYLE", SEARCHBEER_AL
						.get(arg2).getBeerStyle());
				toUserBeerProfile.putExtra("ABV", SEARCHBEER_AL.get(arg2)
						.getAbv());
				toUserBeerProfile.putExtra("IBU", SEARCHBEER_AL.get(arg2)
						.getIbu());
				toUserBeerProfile.putExtra("MOOD", SEARCHBEER_AL.get(arg2)
						.getMood());
				toUserBeerProfile.putExtra("VENUE", SEARCHBEER_AL.get(arg2)
						.getVenue());
				toUserBeerProfile.putExtra("EVENT", SEARCHBEER_AL.get(arg2)
						.getEvent());
				toUserBeerProfile.putExtra("HYPE", SEARCHBEER_AL.get(arg2)
						.getHype());
				toUserBeerProfile.putExtra("AROMA_VALUE",
						SEARCHBEER_AL.get(arg2).getAroma());
				toUserBeerProfile.putExtra("SWEET_VALUE",
						SEARCHBEER_AL.get(arg2).getSweet());
				toUserBeerProfile.putExtra("BITTER_VALUE",
						SEARCHBEER_AL.get(arg2).getBitter());
				toUserBeerProfile.putExtra("MALT_VALUE", SEARCHBEER_AL
						.get(arg2).getMalt());
				toUserBeerProfile.putExtra("YEAST_VALUE",
						SEARCHBEER_AL.get(arg2).getYeast());
				toUserBeerProfile.putExtra("MOUTH_VALUE",
						SEARCHBEER_AL.get(arg2).getMouthfeel());
				toUserBeerProfile.putExtra("SOUR_VALUE", SEARCHBEER_AL
						.get(arg2).getSour());
				toUserBeerProfile.putExtra("ADDITIVE_VALUE",
						SEARCHBEER_AL.get(arg2).getAdditive());
				toUserBeerProfile.putExtra("BOOZINESS_VALUE", SEARCHBEER_AL
						.get(arg2).getBooziness());
				facebookHandle = SEARCHBEER_AL.get(arg2).getFacebookHandle();
				twitterHandle = SEARCHBEER_AL.get(arg2).getTwitterHandle();
				facebookUrl = SEARCHBEER_AL.get(arg2).getFacebookurl();
				
//				Log.e("FindOrAddBeer.facebookHandle", ":"+FindOrAddBeer.facebookHandle);
//				Log.e("FindOrAddBeer.facebookUrl", ":"+facebookUrl);
//				Log.e("FindOrAddBeer.twitterHandle", ":"+FindOrAddBeer.twitterHandle);
				
				startActivity(toUserBeerProfile);

				_edtFindBeer.setText("");
				SEARCHBEER_AL.clear();
				adapter.notifyDataSetChanged();
				textHint.setVisibility(View.VISIBLE);
			}
		});

	}

	private void initializeView() {
		// TODO Auto-generated method stub

		bundle = getIntent().getExtras();
		_btnFindBeer = (Button) findViewById(R.id.find_add_search_beer_btn_new);
		_btnEditBeer = (Button) findViewById(R.id.find_add_edit_user_beer_btn);
		_btnShare = (Button) findViewById(R.id.find_add_share_btn);
		_edtFindBeer = (EditText) findViewById(R.id.find_add_beer_name_edit);
		_btnEnterNewBeer = (Button) findViewById(R.id.find_add_enter_new_btn);

		_btnUserProfile = (Button) findViewById(R.id.find_add_my_profile);
		
		instructionBtn=(Button)findViewById(R.id.btn_instruction);
		
		
		textHint=(TextView)findViewById(R.id.textHint);
		textHint.setText(" Search Hints:\n 1. Search unique beer names by their specific beer name.\n 2. Search generic named beers like 'Pale Ale' by brewery name. ");
		
		instructionBtn.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FindOrAddBeer.this,Instruction.class).putExtra("via", "FIND"));
				
			}
		});

		_listViewBeer = (ListView) findViewById(R.id.find_add_list);
		SEARCHBEER_AL = new ArrayList<SearchBeerModel>();
		_btnFindBeer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				findBeer();

			}
		});

		_edtFindBeer.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub

				if (actionId == EditorInfo.IME_ACTION_SEARCH) {

					findBeer();
					return true;
				}

				return false;
			}
		});
		
	

		_btnEnterNewBeer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showAddDialog();
				facebookHandle = "";
				twitterHandle = "";
				facebookUrl = "";
				_edtFindBeer.setText("");

				SEARCHBEER_AL.clear();
				adapter.notifyDataSetChanged();

			}
		});

		_btnEditBeer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*
				 * Toast.makeText(FindOrAddBeer.this, "Work in progress",
				 * Toast.LENGTH_LONG).show();
				 */

			}
		});

		_btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toSharing = new Intent(FindOrAddBeer.this,
						SocialNetworkSharing.class);
				toSharing.putExtra("via", "Find");
				startActivity(toSharing);

			}
		});

		_btnUserProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent toEdiProfile = new Intent(FindOrAddBeer.this,
						UserInfo.class);

				toEdiProfile.putExtra("userId", bundle.getString("userId"));

				startActivity(toEdiProfile);
				_edtFindBeer.setText("");

			}
		});
		

	}

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		Log.i("response", "" + response);

		JSONObject jsonObj = null;

		if (response.contains("searchBeer")) {

			try {
				jsonObj = new JSONObject(response);

				JSONArray jsonArray = jsonObj.getJSONArray("searchBeer");

				if (jsonArray.length() == 0) {

					showAddDialog();
					/*
					 * Toast.makeText(FindOrAddBeer.this, "No result found",
					 * Toast.LENGTH_SHORT).show();
					 */
					/*
					 * SEARCHBEER_AL = new ArrayList<SEARCHBEER>(); adapter =
					 * new ShowBeerAdapter(FindOrAddBeer.this, SEARCHBEER_AL);
					 */

					SEARCHBEER_AL.clear();
					adapter.notifyDataSetChanged();
					textHint.setVisibility(View.VISIBLE);

				} else {

					SEARCHBEER_AL.clear();
					
					textHint.setVisibility(View.GONE);

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObjectChild = jsonArray.getJSONObject(i);
						/**
						 * /**
						 * {"searchBeer":[{"searchBeer":{"aroma":"","sweet":""
						 * ,"bitter":"","malt":""
						 * ,"yeast":"","mouthFeel":"","sour"
						 * :"","additive":"","booziness":"", "brewery"
						 * :"55","beerName"
						 * :"1","beerStyle":"1","abv":"1","ibu":"1","mood":"1"
						 * ,"venue":"1","event":"1","hype":"1"}}]}
						 */

						SearchBeerModel searchbeer = new SearchBeerModel();

						if (jsonObjectChild.getString("aroma") == null) {
							searchbeer.setAroma("");
						} else {
							searchbeer.setAroma(jsonObjectChild
									.getString("aroma"));
						}
						searchbeer.setBeerId(jsonObjectChild
								.getString("beerId"));
						searchbeer.setSweet(jsonObjectChild.getString("sweet"));
						searchbeer.setBitter(jsonObjectChild
								.getString("bitter"));
						searchbeer.setMalt(jsonObjectChild.getString("malt"));
						searchbeer.setYeast(jsonObjectChild.getString("yeast"));
						searchbeer.setMouthfeel(jsonObjectChild
								.getString("mouthFeel"));
						searchbeer.setSour(jsonObjectChild.getString("sour"));
						searchbeer.setAdditive(jsonObjectChild
								.getString("additive"));
						searchbeer.setBooziness(jsonObjectChild
								.getString("booziness"));
						searchbeer.setBeerName(jsonObjectChild
								.getString("beerName"));
						searchbeer.setBrewery(jsonObjectChild
								.getString("brewery"));
						searchbeer.setBeerStyle(jsonObjectChild
								.getString("beerStyle"));
						searchbeer.setAbv(jsonObjectChild.getString("abv"));
						searchbeer.setIbu(jsonObjectChild.getString("ibu"));
						searchbeer.setMood(jsonObjectChild.getString("mood"));
						searchbeer.setEvent(jsonObjectChild.getString("event"));
						searchbeer.setVenue(jsonObjectChild.getString("venue"));
						searchbeer.setHype(jsonObjectChild.getString("hype"));
						searchbeer.setTastePercentage(jsonObjectChild
								.getString("beerBottle"));
						// New Change By Samit
//						searchbeer.setFacebookHandle("TimelessPintsBrewingCompany");
//						searchbeer.setTwitterHandle("timelesspints");
//						searchbeer.setFacebookurl("https://www.facebook.com/TimelessPintsBrewingCompany");
						searchbeer.setFacebookHandle(jsonObjectChild.optString("facebook"));
						searchbeer.setTwitterHandle(jsonObjectChild.optString("twitter"));
						searchbeer.setFacebookurl(jsonObjectChild.optString("facebookurl"));
						SEARCHBEER_AL.add(searchbeer);
					}
					adapter.notifyDataSetChanged();
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(_edtFindBeer.getWindowToken(), 0);

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
	 * 
	 * show dialog to add beer
	 */

	private void showAddDialog() {

		alert = new AlertDialog.Builder(FindOrAddBeer.this);
		alert.setMessage("Would you like to add a/the beer ?");
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				Intent toAddBeer = new Intent(FindOrAddBeer.this,
						AddNewBeer.class);
				toAddBeer.putExtra("userId", bundle.getString("userId"));

				startActivity(toAddBeer);

			}
		});
		alert.setNegativeButton("No", null);
		alert.show();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		/* disabled back action */
		// super.onBackPressed();

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage("Do You Want To Exit From App?");
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				FindOrAddBeer.this.finish();

			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		alert.show();

	}

	/**
	 * method to find new beer
	 */
	public void findBeer() {
		// check internet connection
		if (!CheckInternetConnectivity
				.checkinternetconnection(FindOrAddBeer.this)) {

			Toast.makeText(FindOrAddBeer.this, "Check Internet Connection",
					Toast.LENGTH_SHORT).show();

		} else if (_edtFindBeer.getText().toString().equalsIgnoreCase("")) {

			Toast.makeText(FindOrAddBeer.this, "Enter a beer.",
					Toast.LENGTH_SHORT).show();

		}

		else {
			
			//server hit
			
			FlurryAgent.logEvent("Search_Beer");

			String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><searchBeer><beerName><![CDATA["
					+ _edtFindBeer.getText().toString().toLowerCase().trim()
					+ "]]></beerName><userId><![CDATA["
					+ bundle.getString("userId") + "]]></userId></searchBeer>";

			HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
					+ Url.BEER_SEARCH, FindOrAddBeer.this, REGISTARTION_XML,
					FindOrAddBeer.this);
			hitRegistartion.execute();

		}
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
