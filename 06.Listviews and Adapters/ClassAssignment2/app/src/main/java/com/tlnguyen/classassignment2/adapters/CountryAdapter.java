package com.tlnguyen.classassignment2.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlnguyen.classassignment2.R;
import com.tlnguyen.classassignment2.models.Country;

import java.util.ArrayList;

/**
 * Created by TL on 12/4/2014.
 */
public class CountryAdapter extends BaseAdapter {

    private ArrayList<Country> mCountryList;
    private Context mContext;

    public CountryAdapter(Context context, ArrayList<Country> dataSource) {
        this.mContext = context;
        this.mCountryList = dataSource;
    }

    @Override
    public int getCount() {
        return this.mCountryList.size();
    }

    @Override
    public Country getItem(int position) {
        return this.mCountryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CountryHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) this.mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.country_row, parent, false);

            holder = new CountryHolder();
            holder.name = (TextView) row.findViewById(R.id.tvName);
            holder.year = (TextView) row.findViewById(R.id.tvYear);
            holder.image = (ImageView) row.findViewById(R.id.imageView);

            row.setTag(holder);
        }
        else {
            holder = (CountryHolder) row.getTag();
        }

        Country country = this.mCountryList.get(position);

        if (country != null) {
            holder.name.setText(country.getName());
//            holder.year.setText(country.getYear() + "");
            holder.image.setImageResource(country.getImage());
        }

        return row;
    }

    static class CountryHolder {
        private TextView name;
        private TextView year;
        private ImageView image;
    }
}
