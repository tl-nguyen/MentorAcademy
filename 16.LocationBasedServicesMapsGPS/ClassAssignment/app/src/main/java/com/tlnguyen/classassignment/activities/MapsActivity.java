package com.tlnguyen.classassignment.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tlnguyen.classassignment.R;
import com.tlnguyen.classassignment.data.Constants;
import com.tlnguyen.classassignment.helpers.JSONArrayInterable;
import com.tlnguyen.classassignment.models.Office;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private JSONArrayInterable mOffices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fetchDataFromJson();

        importDataToDatabase();

        setUpMapIfNeeded();
    }

    private void fetchDataFromJson() {
        InputStream jsonInputStream;
        String jsonString = null;
        JSONObject jsonObject = null;

        try {
            jsonInputStream = getAssets().open("offices");

            int size = jsonInputStream.available();

            byte[] byteArray = new byte[size];
            jsonInputStream.read(byteArray);
            jsonString = new String(byteArray, "UTF-8");

            jsonObject = new JSONObject(jsonString);
            JSONArray offices = jsonObject.getJSONArray("offices");

            // Make interable offices
            mOffices = new JSONArrayInterable(offices);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void importDataToDatabase() {
        Cursor c = MapsActivity.this.getContentResolver().query(Constants.CONTENT_URI, null, null, null, null);
        String result = "Results:";
        if (!c.moveToFirst()) {
            for (JSONObject office: mOffices) {
                try {
                    Office newOffice = new Office();
                    newOffice.setName(office.getString("name"));
                    newOffice.setLatitude(office.getDouble("lad"));
                    newOffice.setLongtitude(office.getDouble("long"));

                    saveToSQLite(newOffice);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fetchFromDb() {
        Cursor c = MapsActivity.this.getContentResolver().query(Constants.CONTENT_URI, null, null, null, "name");
        String result = "Results:";
        if (!c.moveToFirst()) {
            Toast.makeText(MapsActivity.this, result + " no content yet!", Toast.LENGTH_LONG).show();
        } else {
            do{
                LatLng coord = new LatLng(c.getDouble(c.getColumnIndex(Constants.COL_LAT)),
                        c.getDouble(c.getColumnIndex(Constants.COL_LONG)));
                String name = c.getString(c.getColumnIndex(Constants.COL_NAME));

                mMap.addMarker(new MarkerOptions()
                        .position(coord)
                        .title(name));
            } while (c.moveToNext());
        }
    }

    private void saveToSQLite(Office office) {
        ContentValues values = new ContentValues();
        values.put(Constants.COL_NAME, office.getName());
        values.put(Constants.COL_LAT, office.getLatitude());
        values.put(Constants.COL_LONG, office.getLongtitude());

        MapsActivity.this.getContentResolver().insert(Constants.CONTENT_URI, values);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                fetchFromDb();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
}
