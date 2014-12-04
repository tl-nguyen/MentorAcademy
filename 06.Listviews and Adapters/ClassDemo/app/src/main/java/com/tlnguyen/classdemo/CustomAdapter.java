package com.tlnguyen.classdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TL on 12/4/2014.
 */
public class CustomAdapter extends BaseAdapter{

    private ArrayList<Sport> mSportsList = new ArrayList<Sport>();
    private Context mContext;

    public CustomAdapter(Context context) {
        mContext = context;

        for (int i = 0; i < 1000; i++) {
            Sport newSport = new Sport();
            newSport.setId(i);
            newSport.setName("sport" + i);
            newSport.setParticipants(i);

            mSportsList.add(newSport);
        }
    }

    @Override
    public int getCount() {
        return mSportsList.size();
    }

    @Override
    public Sport getItem(int position) {
        return mSportsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mSportsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView sportName;
        Button button;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sport_row, parent, false);

            sportName = (TextView) convertView.findViewById(R.id.sportName);
            convertView.setTag(R.id.sportName, sportName);

            button = (Button) convertView.findViewById(R.id.button);
            convertView.setTag(R.id.button, button);
        }
        else {
            sportName = (TextView) convertView.getTag(R.id.sportName);
            button = (Button) convertView.getTag(R.id.button);
        }

        final Sport currentSport = getItem(position);

        if (currentSport != null) {
            sportName.setText(currentSport.getName());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ROW_CLICKED", currentSport.getParticipants() + "");
                }
            });
        }

        return convertView;
    }
}
