package com.craftbeer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

public class ShowUserTasteProfile extends Activity {

	/**
	 * @author arvind.agarwal This class shows the taste profile of user through
	 *         the seek bars
	 */

	// objects of view class
	private SeekBar _seekBarAroma, _seekBarSweet, _seekBarBitter, _seekBarMalt,
			_seekBarYeast, _seekBarMouth;
	private Button _btnEdit;
	private Button _btnBack;

	// object of activity class
	public static Activity activity;

	// object of shared preference
	private SharedPreferences mPreference;
	// object of bundle
	private Bundle bundle;

	private Button instructionBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_user_taste_profile);

		initialView();

	}

	/**
	 * Initializing views
	 */

	private void initialView() {

		activity = this;

		bundle = getIntent().getExtras();

		_btnEdit = (Button) findViewById(R.id.edit_btn);

		_btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(ShowUserTasteProfile.this,
						EditProfile.class);
				i.putExtra("userId", bundle.getString("userId"));
				startActivity(i);

			}
		});

		mPreference = PreferenceManager
				.getDefaultSharedPreferences(ShowUserTasteProfile.this);
		mPreference.getString("userId", "");
		mPreference.getInt("aromaValue", 3);
		mPreference.getInt("sweetValue", 3);
		mPreference.getInt("bitterValue", 3);
		mPreference.getInt("maltValue", 3);
		mPreference.getInt("yeastValue", 3);
		mPreference.getInt("mouthFeelValue", 3);
		mPreference.getInt("sourValue", 3);
		mPreference.getInt("additiveValue", 3);
		mPreference.getInt("boozinessValue", 3);

		_seekBarAroma = (SeekBar) findViewById(R.id.home_aroma_seek_bar);
		_seekBarBitter = (SeekBar) findViewById(R.id.home_bitter_seek_bar);
		_seekBarMalt = (SeekBar) findViewById(R.id.home_malt_seek_bar);
		_seekBarMouth = (SeekBar) findViewById(R.id.home_mouth_seek_bar);
		_seekBarSweet = (SeekBar) findViewById(R.id.home_sweet_seek_bar);
		_seekBarYeast = (SeekBar) findViewById(R.id.home_yeast_seek_bar);

		// taste profile of user is set in seek bars

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

		// disabled seek bars
		_seekBarAroma.setEnabled(false);
		_seekBarSweet.setEnabled(false);
		_seekBarBitter.setEnabled(false);
		_seekBarMalt.setEnabled(false);
		_seekBarMouth.setEnabled(false);
		_seekBarYeast.setEnabled(false);

		instructionBtn = (Button) findViewById(R.id.info_btn);
		instructionBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ShowUserTasteProfile.this,
						Instruction.class).putExtra("via", "TASTE_PRO"));

			}
		});

		_btnBack = (Button) findViewById(R.id.back_btn);

		_btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
}
