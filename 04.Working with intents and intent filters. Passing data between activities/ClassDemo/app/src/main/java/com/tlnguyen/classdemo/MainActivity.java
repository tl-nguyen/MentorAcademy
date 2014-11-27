package com.tlnguyen.classdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button mBtnImplicit;
    private Button mBtnExplicit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent obtainedIntent = getIntent();

        this.mBtnImplicit = (Button) findViewById(R.id.btnImplicit);
        this.mBtnExplicit = (Button) findViewById(R.id.btnExplicit);

        this.mBtnImplicit.setOnClickListener(this);
        this.mBtnExplicit.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        switch (v.getId()) {
            case R.id.btnImplicit:
                sendMessage();
                break;
            case R.id.btnExplicit:
                goToSecondScreen();
                break;
        }
    }

    private void goToSecondScreen() {
        Intent toSecondScreenIntent = new Intent(this, SecondActivity.class);
        toSecondScreenIntent.putExtra("TEST", 2);
        startActivity(toSecondScreenIntent);
    }

    private void sendMessage() {
        Intent messageIntent = new Intent(Intent.ACTION_SEND);
        messageIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        messageIntent.putExtra("sms_body", "Hello, how r u");

        Intent intentChooser = Intent.createChooser(messageIntent, getString(R.string.choose_title));

        if (messageIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(intentChooser);
        }
        else {
            Toast.makeText(this, getString(R.string.no_email_feedback), Toast.LENGTH_SHORT).show();
        }
    }
}
