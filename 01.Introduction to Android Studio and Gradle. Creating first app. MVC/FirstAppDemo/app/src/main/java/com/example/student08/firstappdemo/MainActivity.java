package com.example.student08.firstappdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText txt;
    private EditText txt2;
    private EditText txt3;
    private Button btn;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txt = (EditText) findViewById(R.id.editText);
        this.txt2 = (EditText) findViewById(R.id.editText2);
        this.txt3 = (EditText) findViewById(R.id.editText3);

        this.btn = (Button) findViewById(R.id.btnShow);
        this.btn2 = (Button) findViewById(R.id.btnShow2);

        this.btn.setOnClickListener(this);
        this.btn2.setOnClickListener(this);
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
        if (v.getId() == R.id.btnShow) {
            Toast toast = Toast.makeText(this, String.format("Hello %s !", this.txt.getText()), Toast.LENGTH_SHORT);
            toast.show();
        } else if (v.getId() == R.id.btnShow2) {
            try {
                int current = Calendar.getInstance().get(Calendar.YEAR);
                int birth = Integer.parseInt(this.txt3.getText().toString());

                Toast toast = Toast.makeText(this, String.format("Hello %s ! Your age %d", this.txt2.getText(), current - birth), Toast.LENGTH_SHORT);
                toast.show();
            } catch (Exception e) {
                //.......
            }
        }
    }
}
