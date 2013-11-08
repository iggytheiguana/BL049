package com.craftbeer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.craftbeer.httpcall.HttpHit;
import com.craftbeer.httpcall.HttpListener;
import com.craftbeer.utility.CheckInternetConnectivity;
import com.craftbeer.utility.Url;
import com.flurry.android.FlurryAgent;

public class AddBeer extends Activity implements OnSeekBarChangeListener,
		HttpListener {

	/**
	 * @author arvind.agarwal
	 * 
	 */

	private SeekBar _seekBarAroma, _seekBarSweet, _seekBarBitter, _seekBarMalt,
			_seekBarYeast, _seekBarMouth;

	private Button _btnSave;

	private int aromaValue = 0, sweetvalue = 0, bitterValue = 0, maltValue = 0,
			yeastvalue = 0, mouthValue = 0;

	String _response;

	private Bundle bundle;

	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, com.craftbeer.constants.Constants.FLURRY_KEY);
	}

	@Override
	protected void onStop() {
super.onStop();
		FlurryAgent.onEndSession(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_screen);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		initializeView();

		_btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (CheckInternetConnectivity
						.checkinternetconnection(AddBeer.this)) {

					String REGISTARTION_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><addBeerProfile><beerId><![CDATA[77]]></beerId><userId><![CDATA["
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
							+ "]]></mouthFeel><sour><![CDATA[0]]></sour><additive><![CDATA[0]]></additive><booziness><![CDATA[0]]></booziness></addBeerProfile>";

					HttpHit hitRegistartion = new HttpHit(Url.BASE_URL,
							AddBeer.this, REGISTARTION_XML, AddBeer.this);
					hitRegistartion.execute();
				} else {

					Toast.makeText(AddBeer.this, "Check Internet Connection",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	private void initializeView() {
		// TODO Auto-generated method stub

		bundle = getIntent().getExtras();

		_btnSave = (Button) findViewById(R.id.home_save_btn);

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

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

		switch (seekBar.getId()) {

		case R.id.home_aroma_seek_bar:

			aromaValue = (progress / 25) + 3;

			break;

		case R.id.home_sweet_seek_bar:

			sweetvalue = (progress / 25) + 3;
			break;

		case R.id.home_bitter_seek_bar:

			bitterValue = (progress / 25) + 3;

			break;
		case R.id.home_malt_seek_bar:

			maltValue = (progress / 25) + 3;
			break;
		case R.id.home_yeast_seek_bar:

			yeastvalue = (progress / 25) + 3;
			break;
		case R.id.home_mouth_seek_bar:

			mouthValue = (progress / 25) + 3;
			break;

		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		Log.i("", "" + response);

	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAlreadyExist(String response) {
		// TODO Auto-generated method stub

	}

}
