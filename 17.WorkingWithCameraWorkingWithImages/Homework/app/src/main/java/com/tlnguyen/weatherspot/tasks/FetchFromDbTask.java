package com.tlnguyen.weatherspot.tasks;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.tlnguyen.weatherspot.data.Constants;
import com.tlnguyen.weatherspot.models.Spot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FetchFromDbTask extends AsyncTask<Void, Void, Void>{

    private Activity mHostActivity;
    private List<Spot> mSpots;
    private ArrayAdapter<Spot> mAdapter;

    public FetchFromDbTask(Activity activity, List<Spot> spots, ArrayAdapter<Spot> adapter) {
        this.mHostActivity = activity;
        this.mSpots = spots;
        this.mAdapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        mSpots.clear();

        Cursor cursor = mHostActivity.getContentResolver().query(Constants.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Constants.COL_ID));
                double latitude = cursor.getDouble(cursor.getColumnIndex(Constants.COL_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(Constants.COL_LONGITUDE));
                String weather = cursor.getString(cursor.getColumnIndex(Constants.COL_WEATHER));
                String photoPath = cursor.getString(cursor.getColumnIndex(Constants.COL_PHOTO_PATH));
                String createdAt = cursor.getString(cursor.getColumnIndex(Constants.COL_CREATED_AT));

                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");
                Date createdDate = null;

                try {
                    createdDate = sdf.parse(createdAt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Spot currentSpot = new Spot(id,
                        new LatLng(latitude, longitude),
                        weather,
                        photoPath,
                        createdDate);

                mSpots.add(currentSpot);
            }
            while (cursor.moveToNext());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mAdapter.notifyDataSetChanged();
    }
}
