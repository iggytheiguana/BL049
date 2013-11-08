package com.craftbeer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserBeerProfile extends Activity {

	/**
	 * @author arvind.agarwal 
	 * this class shows how much the profile of beer
	 *         matches with your profile
	 */
	// object of view class

	private ImageView imag;

	private Button _backBtn, _edtBeerProfileBtn;

	public static Activity activity;

	private TextView title;

	private Toast mToast;

	// object of bundle

	private Bundle bundle;

	
	private Button instructionBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.beer_user_match);

		activity = this;

		bundle = getIntent().getExtras();

		mToast = Toast.makeText(UserBeerProfile.this,
				"Be First  To Profile The Beer ", Toast.LENGTH_LONG);

		imag = (ImageView) findViewById(R.id.image);
		title = (TextView) findViewById(R.id.title);

		_backBtn = (Button) findViewById(R.id.back_btn_new);
		
		instructionBtn=(Button)findViewById(R.id.info_btn);
		
		instructionBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(UserBeerProfile.this, Instruction.class).putExtra("via", "MATCH"));
				
			}
		});

		_edtBeerProfileBtn = (Button) findViewById(R.id.edt_beer_btn_new);

		_edtBeerProfileBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mToast.cancel();

				Toast.makeText(UserBeerProfile.this,
						"Mood,Venue,Event,Hype will be reset.",
						Toast.LENGTH_LONG).show();

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						Intent i = new Intent(UserBeerProfile.this,
								EditUserBeer.class);
						i.putExtra("userId", bundle.getString("userId"));
						i.putExtra("beerId", bundle.getString("beerId"));

						i.putExtra("BREWERY", bundle.getString("BREWERY"));
						i.putExtra("BEER_NAME", bundle.getString("BEER_NAME"));
						i.putExtra("BEER_STYLE", bundle.getString("BEER_STYLE"));
						i.putExtra("ABV", bundle.getString("ABV"));
						i.putExtra("IBU", bundle.getString("IBU"));
						i.putExtra("MOOD", bundle.getString("MOOD"));
						i.putExtra("VENUE", bundle.getString("VENUE"));
						i.putExtra("EVENT", bundle.getString("EVENT"));
						i.putExtra("HYPE", bundle.getString("HYPE"));
						i.putExtra("BEERBOTTLE", bundle.getString("TASTE_PERCENTAGE"));
						startActivity(i);
						finish();

					}
				}, 1000);

			}
		});

		_backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mToast.cancel();
				finish();

			}
		});

		title.setText("Beer Profile Matches "
				+ bundle.getString("TASTE_PERCENTAGE")
				+ " % With Your Taste Profile.");

		int i = Integer.parseInt(bundle.getString("TASTE_PERCENTAGE"));

		if (i == 0) {
			
			imag.setImageResource(R.drawable.zero);
		} else if (i == 10) {

			imag.setImageResource(R.drawable.one);

		} else if (i == 20) {

			imag.setImageResource(R.drawable.two);

		} else if (i == 30) {

			imag.setImageResource(R.drawable.three);

		} else if (i == 40) {

			imag.setImageResource(R.drawable.four);

		} else if (i == 50) {

			imag.setImageResource(R.drawable.five);

		} else if (i == 60) {

			imag.setImageResource(R.drawable.six);

		} else if (i == 70) {

			imag.setImageResource(R.drawable.seven);

		} else if (i == 80) {

			imag.setImageResource(R.drawable.eight);

		} else if (i == 90) {

			imag.setImageResource(R.drawable.ninty);

		} else if (i == 100) {

			imag.setImageResource(R.drawable.ten);

		}

		if (bundle.getString("AROMA_VALUE").equals("0")
				&& bundle.getString("SWEET_VALUE").equals("0")
				&& bundle.getString("BITTER_VALUE").equals("0")
				&& bundle.getString("MALT_VALUE").equals("0")
				
				&& bundle.getString("MOUTH_VALUE").equals("0")
				) {

			/* for(int j=0;j<5;j++){ */

			// showing message when all parameters come 0

		//	mToast.show();
			title.setText("You can be the first to profile this beer");

			/* } */
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		
	}

}
