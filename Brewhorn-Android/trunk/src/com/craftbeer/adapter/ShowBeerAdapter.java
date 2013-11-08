package com.craftbeer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.craftbeer.R;
import com.craftbeer.modal.SearchBeerModel;

public class ShowBeerAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<SearchBeerModel> arrayList;

	public ShowBeerAdapter(Context _context, ArrayList<SearchBeerModel> _arrayList) {
		this.context = _context;
		this.arrayList = _arrayList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater
					.inflate(R.layout.beer_adapter_inflater, null);
			holder.textToShowBeer = (TextView) convertView
					.findViewById(R.id.text_inflater);

			holder.textToShowBrewery = (TextView) convertView
					.findViewById(R.id.text_inflater_1);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textToShowBeer.setText(arrayList.get(position).getBeerName());
		holder.textToShowBrewery.setText(arrayList.get(position).getBrewery());
		return convertView;
	}

	class ViewHolder {

		TextView textToShowBeer, textToShowBrewery;
	}

}
