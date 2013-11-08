package com.craftbeer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.craftbeer.adapter.ShowBeerAdapter;
import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.modal.SearchBeerModel;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;

public class History extends Activity  implements HttpListener{
	
	private Button _btnCancel;
	private ListView _listHistory;
	
	ShowBeerAdapter adapter;
	
	private ArrayList<SearchBeerModel> SEARCHBEER_AL;
	
	private SharedPreferences sharedPreference;
	
	private SearchBeerModel  searchBeer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.history);
		sharedPreference=PreferenceManager.getDefaultSharedPreferences(this);
		_btnCancel=(Button)findViewById(R.id.cancel_btn);
		_listHistory=(ListView)findViewById(R.id.list_history);
		
		SEARCHBEER_AL=new ArrayList<SearchBeerModel>();
		
		_btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		} );
		
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
	if (!CheckInternetConnectivity
			.checkinternetconnection(History.this)) {

		Toast.makeText(History.this, "Check Internet Connection",
				Toast.LENGTH_SHORT).show();

	} else{
		
		SEARCHBEER_AL.clear();
		
		String HISTORY_XML="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><beerHistory><userId><![CDATA["+sharedPreference.getString("USER_ID", "")+"]]></userId></beerHistory>";
		HttpHit hitRegistartion = new HttpHit(Url.BASE_URL
				+ Url.HISTORY, History.this, HISTORY_XML,
				History.this);
		hitRegistartion.execute();
		
	//	"+sharedPreference.getString("USER_ID", "")+"
	}
	
	
}
@Override
public void onResponse(String response) {
	// TODO Auto-generated method stub
	if(response!=null && response.contains("beerHistory")){
		JSONObject jObj;
		try {
			jObj = new JSONObject(response);
	
		
		JSONArray jArray=jObj.getJSONArray("beerHistory");
		if(jArray.length()==0){
			showAddDialog();
			
		}else{
		for(int i=0;i<jArray.length();i++){
			
			searchBeer=new SearchBeerModel();
			
			searchBeer.setBeerId(jArray.getJSONObject(i).getString("beerId"));
			searchBeer.setBeerName(jArray.getJSONObject(i).getString("beerName"));
			searchBeer.setBrewery(jArray.getJSONObject(i).getString("breweryName"));
			
			SEARCHBEER_AL.add(searchBeer);
			
		}
		}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	adapter=new ShowBeerAdapter(History.this, SEARCHBEER_AL);
	_listHistory.setAdapter(adapter);
	
	/*{"beerHistory":[{"beerId":"25308","beerName":"BeerTest","breweryName":"testNew"},{"beerId":"3763","beerName":"Citra Hop Test #1","breweryName":"Pizza Boy Brewing"}]}*/
}
@Override
public void onError() {
	// TODO Auto-generated method stub
	
}
@Override
public void onAlreadyExist(String response) {
	// TODO Auto-generated method stub
	
}

private void showAddDialog() {

	AlertDialog.Builder alert = new AlertDialog.Builder(History.this);
	alert.setMessage("You haven't profiled any beer.");
	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
			finish();
			
		}
	});

	alert.show();

}

}
