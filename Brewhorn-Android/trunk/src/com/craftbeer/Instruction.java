package com.craftbeer;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.flurry.android.FlurryAgent;

public class Instruction  extends Activity{
	
	Bundle bundle;
	

	//object of view class
	private Button prevBtn, nextBtn;
	private Button _btnCancel;
	
	//object of a customized imageView class designed to zoom in and out
	private com.craftbeer.utility.TouchImageView text;

	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information_page);
		
		bundle=getIntent().getExtras();
		
		_btnCancel = (Button) findViewById(R.id.cancel_btn);
		
		
		_btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		});

		prevBtn = (Button) findViewById(R.id.previous_btn);
		
		
	/*	setting visibility invisible*/
		prevBtn.setVisibility(View.INVISIBLE);
		nextBtn = (Button) findViewById(R.id.next_btn);
		nextBtn.setVisibility(View.INVISIBLE);
		
		text = (com.craftbeer.utility.TouchImageView) findViewById(R.id.text);
		
		text.setImageResource(R.drawable.brewhorn_instructions);
		
		
		
				
				
				
				if(bundle.getString("via").equals("REG")){
					text.setImageResource(R.drawable.register);
					}else if(bundle.getString("via").equals("ADD_BEER")){
						text.setImageResource(R.drawable.add_beer);
						}else if(bundle.getString("via").equals("PRO_BEER")){
							text.setImageResource(R.drawable.profile_beer);
							}else if(bundle.getString("via").equals("MATCH")){
								text.setImageResource(R.drawable.match_beer);
							}else if(bundle.getString("via").equals("ADD_BEER")){
								text.setImageResource(R.drawable.add_beer);
							}else if(bundle.getString("via").equals("FIND")){
								text.setImageResource(R.drawable.find_add);
							}else if(bundle.getString("via").equals("EDIT_BEER")){
								text.setImageResource(R.drawable.edit_beer);
							}else if(bundle.getString("via").equals("TASTE_PRO")){
								text.setImageResource(R.drawable.taste_profile);
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
