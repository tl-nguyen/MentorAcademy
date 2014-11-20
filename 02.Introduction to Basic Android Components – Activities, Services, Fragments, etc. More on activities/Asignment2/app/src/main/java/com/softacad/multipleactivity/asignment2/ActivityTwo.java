package com.softacad.multipleactivity.asignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityTwo extends Activity implements View.OnClickListener {

    private Button toOne;
    private Button toThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_two);

        toOne = (Button) findViewById(R.id.btnTwoToOne);
        toThree = (Button) findViewById(R.id.btnTwoToThree);

        toOne.setOnClickListener(this);
        toThree.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_two, menu);
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
        Intent intent = new Intent(this, ActivityOne.class);

        switch (v.getId()) {
            case R.id.btnTwoToOne :
                break;

            case R.id.btnTwoToThree :
                intent = new Intent(this, ActivityThree.class);
                break;
        }

        this.startActivity(intent);
    }
}
