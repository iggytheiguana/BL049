package com.craftbeer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

public class InformationPage extends Activity {

	
	/**
	 * @author arvind.agarwal
	 * this class shows the information regarding the app
	 * 
	 */

	//object of view class
	private Button prevBtn, nextBtn;
	private Button _btnCancel;
	
	//object of a customized imageView class designed to zoom in and out
	private com.craftbeer.utility.TouchImageView text;

	
	// int object to set info page 1 initially
	private int pageNumber = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information_page);

		_btnCancel = (Button) findViewById(R.id.cancel_btn);

		prevBtn = (Button) findViewById(R.id.previous_btn);
		
		
	/*	setting visibility invisible*/
		prevBtn.setVisibility(View.INVISIBLE);
		nextBtn = (Button) findViewById(R.id.next_btn);
		text = (com.craftbeer.utility.TouchImageView) findViewById(R.id.text);
		text.setImageResource(R.drawable.brew1);
		_btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		prevBtn.setOnClickListener(new OnClickListener() {
			//sets prev page of info
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				
				if (pageNumber <= 1) {

					Toast.makeText(InformationPage.this,
							"Please press next button", Toast.LENGTH_SHORT)
							.show();
				} else {
					
					
					if(pageNumber==2){
						prevBtn.setVisibility(View.INVISIBLE);
					}
					nextBtn.setVisibility(View.VISIBLE);
					pageNumber = pageNumber - 1;
					setPageNumber(pageNumber);
				}

			}
		});

		nextBtn.setOnClickListener(new OnClickListener() {
		// sets next page of information
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (pageNumber >= 4) {

					Toast.makeText(InformationPage.this,
							"Please press previous button", Toast.LENGTH_SHORT)
							.show();
				} else {
					
					if(pageNumber==3){
						nextBtn.setVisibility(View.INVISIBLE);
					}
					prevBtn.setVisibility(View.VISIBLE);
					pageNumber = pageNumber + 1;
					setPageNumber(pageNumber);
				}

			}
		});
	}
	
	
	/**
	 * 
	 * @param pageNo
	 * this method sets the info page
	 */

	private void setPageNumber(int pageNo) {

		if (pageNo == 1) {
			text.setImageResource(R.drawable.brew1);
	} else if (pageNo == 2) {
			text.setImageResource(R.drawable.brew2);
		
		} else if (pageNo == 3) {
			text.setImageResource(R.drawable.brew3);
			
		} else if (pageNo == 4) {
			text.setImageResource(R.drawable.brew4);
		
		}
		//setting the max level of zoom in
		text.setMaxZoom(4f);

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
