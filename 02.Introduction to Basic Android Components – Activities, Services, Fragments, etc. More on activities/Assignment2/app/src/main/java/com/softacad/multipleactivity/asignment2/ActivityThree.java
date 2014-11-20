package com.softacad.multipleactivity.asignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityThree extends Activity implements View.OnClickListener {

    private Button toOne;
    private Button toTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_three);

        toOne = (Button) findViewById(R.id.btnThreeToOne);
        toTwo = (Button) findViewById(R.id.btnThreeToTwo);

        toOne.setOnClickListener(this);
        toTwo.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_three, menu);
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
            case R.id.btnThreeToTwo :
                break;

            case R.id.btnThreeToOne :
                intent = new Intent(this, ActivityOne.class);
                break;
        }

        this.startActivity(intent);
    }
}
