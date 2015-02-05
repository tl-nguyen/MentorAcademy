package com.tlnguyen.classassignment.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.tlnguyen.classassignment.R;
import com.tlnguyen.classassignment.models.Day;

public class DetailActivity extends ActionBarActivity {

    private TextView mTvTemperature;
    private TextView mTvPressure;
    private TextView mTvHumidity;
    private TextView mTvDescription;

    private Day mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDay = getIntent().getExtras().getParcelable("DAY");

        mTvTemperature = (TextView) findViewById(R.id.tvTemperature);
        mTvPressure = (TextView) findViewById(R.id.tvPressure);
        mTvHumidity = (TextView) findViewById(R.id.tvHumidity);
        mTvDescription = (TextView) findViewById(R.id.tvDescription);

        mTvTemperature.setText(mDay.getTemp());
        mTvPressure.setText(mDay.getPressure());
        mTvHumidity.setText(mDay.getHumidity());
        mTvDescription.setText(mDay.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
