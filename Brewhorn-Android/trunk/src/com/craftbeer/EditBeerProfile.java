package com.craftbeer;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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

import com.flurry.android.FlurryAgent;
import com.model.MODEL_AROMA;

public class EditBeerProfile extends Activity implements
		OnSeekBarChangeListener, OnClickListener {

	/**
	 * @author arvind.agarwal
	 * 
	 *         This activity is useful for changing taste parametres of Beer
	 *         Aroma ,Bitter,Sweet
	 */

	/**
	 * creating Variables
	 */
	
	public static Activity activity;
	
	private Bundle bundle ;

	private SettingAdapterAroma adapterAroma;
	private SettingAdapterSweet adapterSweet;
	private SettingAdapterBitter adapterBitter;

	private Dialog dialog;
	private String aromaShow = "";
	
	
	
	//arrayList of model aroma class

	public ArrayList<MODEL_AROMA> arrayListNew = null;

	public ArrayList<MODEL_AROMA> arrayListSweet = null;

	public ArrayList<MODEL_AROMA> arrayListBitter = null;
	MODEL_AROMA model;
	
	//array for Aroma

	String[] arrayAroma = { "floral", "citrus", "tropical", "fruity", "earthy",
			"banana", "clove", "barnyard", "horseblanket", "rosy", "vegetal",
			"herbal", "leathery", "metallic", "piney", "resiny", "bubblegum",
			"nutty" ,"Bready","Toast", "Malty"," Noble Hop" };
	
	//array for Sweet

	String[] arraySweet = { "light", "moderate", "heavy", "cloying",
			"candisugar", "residual", "aftertaste" };

	
	//array for bitter
	String[] arrayBitter = {

	"citrus", "tropical", "floral", "perfumy", "grapefruit", "residual",
			"aftertaste", "catty", "hoppy", "herbal", "lemony", "piney",
			"resiny", "spicy", "dank", "lingering", "bright", "pineapple",
			"mango"

	};

	private Toast _toast;
	private int aromaValue = 3, sweetvalue = 3, bitterValue = 3;
	//comments for various values of user taste profile
	private String comment3 = " The beer's taste was much less than you prefer ";
	private String comment4 = "The beer's taste was somewhat less than you prefer";
	private String comment5 = "The beer's taste met your baseline taste preferences";
	private String comment6 = "The beer's taste was somewhat more than you prefer";
	private String comment7 = "The beer's taste was much more than you prefer";

	//object of view class
	private SeekBar _seekBarAroma, _seekBarSweet, _seekBarBitter;

	private SharedPreferences prefrence;

	private TextView _txt3Aroma, _txt4Aroma, _txt5Aroma, _txt6Aroma,
			_txt7Aroma;
	private TextView _txt3Sweet, _txt4Sweet, _txt5Sweet, _txt6Sweet,
			_txt7Sweet;
	private TextView _txt3Bitter, _txt4Bitter, _txt5Bitter, _txt6Bitter,
			_txt7Bitter;

	private TextView _txtAroma, _txtSweet, _txtBitter;
	
	private Button _nextBtn, _cancelBtn,_infoBtn;
	
	private TextView txtAromaName, txtBitterName, txtSweetName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_beer_profile);
		activity=this;

		arrayListNew = new ArrayList<MODEL_AROMA>();
		arrayListSweet = new ArrayList<MODEL_AROMA>();
		arrayListBitter = new ArrayList<MODEL_AROMA>();

		
		//setting values in arraylists
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

		initializeView();
		
		Toast.makeText(
				this,
				"Tap the parameter for a description.",
				Toast.LENGTH_LONG).show();

	}

	/**
	 * Initializing View
	 * 
	 */

	/**
	 * 
	 */
	private void initializeView() {

		prefrence = PreferenceManager
				.getDefaultSharedPreferences(EditBeerProfile.this);
		bundle=getIntent().getExtras();
		
		
		aromaValue=prefrence.getInt("aromaValue", 5);
		
		sweetvalue=prefrence.getInt("sweetValue", 5);
		
		bitterValue=prefrence.getInt("bitterValue", 5);

		_seekBarAroma = (SeekBar) findViewById(R.id.home_aroma_seek_bar);
		_seekBarSweet = (SeekBar) findViewById(R.id.home_sweet_seek_bar);
		_seekBarBitter = (SeekBar) findViewById(R.id.home_bitter_seek_bar);
		
		_seekBarAroma.setOnSeekBarChangeListener(this);
		_seekBarSweet.setOnSeekBarChangeListener(this);
		_seekBarBitter.setOnSeekBarChangeListener(this);

		_txtAroma = (TextView) findViewById(R.id.home_aroma_edt);
		_txtSweet = (TextView) findViewById(R.id.home_sweet_edt);
		_txtBitter = (TextView) findViewById(R.id.home_bitter_edt);
		_cancelBtn=(Button)findViewById(R.id.home_cancel_btn);
		_cancelBtn.setOnClickListener(this);

		_txtAroma.setOnClickListener(this);
		_txtBitter.setOnClickListener(this);
		_txtSweet.setOnClickListener(this);
		
		_nextBtn=(Button)findViewById(R.id.home_next_btn);
		_nextBtn.setOnClickListener(this);
		
		_infoBtn=(Button)findViewById(R.id.info_btn);
		_infoBtn.setOnClickListener(this);

		// aroma texts
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


	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

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

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.home_aroma_edt:
			
			showDialogAroma();
			break;

		case R.id.home_sweet_edt:
			
			showDialogSweet();
			break;

		case R.id.home_bitter_edt:
			
			showDialogBitter();
			break;
			
			
		case R.id.home_next_btn:
			
			
			Intent i=new Intent(EditBeerProfile.this,EditBeerProfile1.class);
			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			i.putExtra("userId", bundle.getString("userId"));
			i.putExtra("beerId", bundle.getString("beerId"));
			
			SharedPreferences.Editor editor=prefrence.edit();
			
			editor.putInt("aroma_beer_temp", aromaValue);
			editor.putInt("sweet_beer_temp", sweetvalue);
			editor.putInt("bitter_beer_temp", bitterValue);
			editor.putString("aroma_cmnt_temp", _txtAroma.getText().toString());
			editor.putString("sweet_cmnt_temp", _txtSweet.getText().toString());
			editor.putString("bitter_cmnt_temp", _txtBitter.getText().toString());
			editor.commit();
			
			
			
			startActivity(i);
			
			
			
			break;
			
		case R.id.home_cancel_btn:
			finish();
			break;
		case R.id.info_btn:
			
			Intent toInfoPage=new Intent(EditBeerProfile.this,InformationPage.class);
			startActivity(toInfoPage);
			break;

		}

	}

	public void showDialogAroma() {

		aromaShow = "";

		/*
		 * for(int i=0;i<arrayListSweet.size();i++){
		 * arrayListSweet.get(i).setChecked(false);
		 * 
		 * }
		 */

		dialog = new Dialog(EditBeerProfile.this);

		dialog.setTitle("Select Descriptor for Aroma");

		dialog.setContentView(R.layout.dialog_list);

		ListView list = (ListView) dialog.findViewById(R.id.list);
		Button btn = (Button) dialog.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < arrayListNew.size(); i++) {

					if (arrayListNew.get(i).isChecked()) {

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

		adapterAroma = new SettingAdapterAroma(arrayListNew);

		list.setAdapter(adapterAroma);

		dialog.show();

	}
	
	
	public void showDialogSweet() {

		aromaShow = "";
		
		
	/*	for(int i=0;i<arrayListSweet.size();i++){
			arrayListSweet.get(i).setChecked(false);
			
		}*/

		dialog = new Dialog(EditBeerProfile.this);

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
		
		adapterSweet=new SettingAdapterSweet(arrayListSweet);
		
		list.setAdapter(adapterSweet);

		dialog.show();

	}

	

	
	
	public void showDialogBitter() {

		aromaShow = "";
		
		/*
		for(int i=0;i<arrayListBitter.size();i++){
			arrayListBitter.get(i).setChecked(false);
			
		}
*/
		dialog = new Dialog(EditBeerProfile.this);

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
		
		adapterBitter=new SettingAdapterBitter(arrayListBitter);
		
		list.setAdapter(adapterBitter);

		dialog.show();

	}

	

	public class SettingAdapterAroma extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterAroma(ArrayList<MODEL_AROMA> array2) {
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

			LayoutInflater inflater = LayoutInflater.from(EditBeerProfile.this);
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

					adapterAroma.notifyDataSetChanged();

				}
			});

			return convertView;
		}

	}

	class ViewHolder {
		TextView txt;
		ImageView chkBx;
	}

	
	public class SettingAdapterBitter extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterBitter(ArrayList<MODEL_AROMA> array2) {
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
			LayoutInflater inflater = LayoutInflater.from(EditBeerProfile.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());
			
			
			if(arrayListBitter.get(position).isChecked()){
				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);
				
			}else {
				
				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
				
			}
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					arrayListBitter.get(position).setChecked(!arrayListBitter.get(position).isChecked());	
					adapterBitter.notifyDataSetChanged();
					
				}
			});
			
			
			
			
			
		/*	holder.chkBx.setChecked(array1.get(position).isChecked());

			holder.chkBx
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub

							arrayListBitter.get(position).setChecked(isChecked);

						}
					});*/

			return convertView;
		}

	}
	
	
	public class SettingAdapterSweet extends BaseAdapter {
		// int a;
		// ArrayList<String> arrayList2;
		//
		ArrayList<MODEL_AROMA> array1;

		//
		public SettingAdapterSweet(ArrayList<MODEL_AROMA> array2) {
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
			LayoutInflater inflater = LayoutInflater.from(EditBeerProfile.this);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.dialog_aroma, null);
				holder.txt = (TextView) convertView.findViewById(R.id.text);
				holder.chkBx = (ImageView) convertView.findViewById(R.id.chk);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.txt.setText(array1.get(position).getName());
			if(arrayListSweet.get(position).isChecked()){
				holder.chkBx.setBackgroundResource(R.drawable.trans_check_mark);
				
			}else {
				
				holder.chkBx.setBackgroundResource(R.drawable.unchecked);
				
			}
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					arrayListSweet.get(position).setChecked(!arrayListSweet.get(position).isChecked());
					
					adapterSweet.notifyDataSetChanged();
				}
			});
			
			
			

			return convertView;
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

	private void showMeaningOfParameter(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				EditBeerProfile.this);
		builder.setMessage(message);
		builder.setPositiveButton("Dismiss", null);

		builder.show();

	}
}
