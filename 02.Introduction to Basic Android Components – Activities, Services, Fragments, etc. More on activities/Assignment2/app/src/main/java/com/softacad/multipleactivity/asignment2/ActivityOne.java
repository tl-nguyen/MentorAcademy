package com.softacad.multipleactivity.asignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityOne extends Activity implements View.OnClickListener {

    private Button toTwo;
    private Button toThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_one);

        toTwo = (Button) findViewById(R.id.btnOneToTwo);
        toThree = (Button) findViewById(R.id.btnOneToThree);

        toTwo.setOnClickListener(this);
        toThree.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_one, menu);
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ActivityTwo.class);

        switch (v.getId()) {
            case R.id.btnOneToTwo :
                break;

            case R.id.btnOneToThree :
                intent = new Intent(this, ActivityThree.class);
                break;
        }

        this.startActivity(intent);
    }
}
