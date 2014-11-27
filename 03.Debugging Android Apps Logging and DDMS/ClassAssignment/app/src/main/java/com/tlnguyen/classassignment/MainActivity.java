package com.tlnguyen.classassignment;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements View.OnClickListener{

    private String currentOp;
    private Double result;

    private Double currentNumber;

    private boolean mIsBuildingNumber;

    private EditText mEtResult;

    private Button mbtn1;
    private Button mbtn2;
    private Button mbtn3;
    private Button mbtn4;
    private Button mbtn5;
    private Button mbtn6;
    private Button mbtn7;
    private Button mbtn8;
    private Button mbtn9;
    private Button mbtn0;

    private Button mbtnPlus;
    private Button mbtnMinus;
    private Button mbtnDivide;
    private Button mbtnMultiply;
    private Button mbtnPow;
    private Button mbtnSqrt;

    private Button mbtnCancel;
    private Button mbtnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        clearAll();

        Typeface digital7 = Typeface.createFromAsset(getAssets(), "digital-7.ttf");

        mEtResult = (EditText) findViewById(R.id.etResult);

        mbtn0 = (Button) findViewById(R.id.button0);
        mbtn1 = (Button) findViewById(R.id.button1);
        mbtn2 = (Button) findViewById(R.id.button2);
        mbtn3 = (Button) findViewById(R.id.button3);
        mbtn4 = (Button) findViewById(R.id.button4);
        mbtn5 = (Button) findViewById(R.id.button5);
        mbtn6 = (Button) findViewById(R.id.button6);
        mbtn7 = (Button) findViewById(R.id.button7);
        mbtn8 = (Button) findViewById(R.id.button8);
        mbtn9 = (Button) findViewById(R.id.button9);

        mbtnPlus = (Button) findViewById(R.id.buttonPlus);
        mbtnMinus = (Button) findViewById(R.id.buttonMinus);
        mbtnDivide = (Button) findViewById(R.id.buttonDivide);
        mbtnMultiply = (Button) findViewById(R.id.buttonMultiply);
        mbtnSqrt = (Button) findViewById(R.id.buttonSqrt);
        mbtnPow = (Button) findViewById(R.id.buttonPower);

        mbtnCancel = (Button) findViewById(R.id.buttonCancel);
        mbtnCalculate = (Button) findViewById(R.id.buttonEqual);

        mEtResult.setTypeface(digital7);

        mbtn0.setOnClickListener(this);
        mbtn1.setOnClickListener(this);
        mbtn2.setOnClickListener(this);
        mbtn3.setOnClickListener(this);
        mbtn4.setOnClickListener(this);
        mbtn5.setOnClickListener(this);
        mbtn6.setOnClickListener(this);
        mbtn7.setOnClickListener(this);
        mbtn8.setOnClickListener(this);
        mbtn9.setOnClickListener(this);
        mbtnPlus.setOnClickListener(this);
        mbtnMinus.setOnClickListener(this);
        mbtnDivide.setOnClickListener(this);
        mbtnMultiply.setOnClickListener(this);
        mbtnSqrt.setOnClickListener(this);
        mbtnPow.setOnClickListener(this);
        mbtnCancel.setOnClickListener(this);
        mbtnCalculate.setOnClickListener(this);
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
            case R.id.button0:
                buildCurrentNumber(0.0);

                break;
            case R.id.button1:
                buildCurrentNumber(1.0);

                break;
            case R.id.button2:
                buildCurrentNumber(2.0);

                break;
            case R.id.button3:
                buildCurrentNumber(3.0);

                break;
            case R.id.button4:
                buildCurrentNumber(4.0);

                break;
            case R.id.button5:
                buildCurrentNumber(5.0);

                break;
            case R.id.button6:
                buildCurrentNumber(6.0);

                break;
            case R.id.button7:
                buildCurrentNumber(7.0);

                break;
            case R.id.button8:
                buildCurrentNumber(8.0);

                break;
            case R.id.button9:
                buildCurrentNumber(9.0);

                break;
            case R.id.buttonPlus:
                calculateResult("+");
                break;
            case R.id.buttonMinus:
                calculateResult("-");
                break;
            case R.id.buttonDivide:
                calculateResult("/");
                break;
            case R.id.buttonMultiply:
                calculateResult("*");
                break;
            case R.id.buttonSqrt:
                calculateResult("sqrt");
                break;
            case R.id.buttonPower:
                calculateResult("pow");
                break;
            case R.id.buttonCancel:
                clearAll();
                setResultTextField(0.0);
                break;
            case R.id.buttonEqual:
                calculateResult("=");
                break;
        }
    }

    private void setResultTextField(Double number) {
        this.mEtResult.setText(number.toString());
    }

    private void calculateResult(String operation) {
        mIsBuildingNumber = false;

        if (currentOp.equals("")) {
            result += this.currentNumber;
        }
        else {
            if (operation.equals("=")) {
                calculateResultTillNow();
                setResultTextField(this.result);
                clearAll();
            }
            else {
                calculateResultTillNow();
            }
        }

        this.currentOp = operation;
    }

    private void calculateResultTillNow() {
        if (this.currentOp.equals("+")) {
            result += this.currentNumber;
        }
        else if (this.currentOp.equals("-")) {
            result -= this.currentNumber;
        }
        else if (this.currentOp.equals("/")) {
            if (this.currentNumber == 0.0) {
                return;
            }
            result /= this.currentNumber;
        }
        else if (this.currentOp.equals("*")) {
            result *= this.currentNumber;
        }
        else if (this.currentOp.equals("sqrt")) {
            result = Math.sqrt(result);
        }
        else if (this.currentOp.equals("pow")) {
            result *= result;
        }
    }

    private void buildCurrentNumber(double currentDigit) {
        if (!this.mIsBuildingNumber) {
            this.currentNumber = 0.0;
            setResultTextField(0.0);
            this.mIsBuildingNumber = true;
        }

        this.currentNumber = this.currentNumber * 10 + currentDigit;

        mEtResult.setText(this.currentNumber.toString());
    }

    private void clearAll() {
        this.result = 0.0;
        this.currentOp = "";
        this.mIsBuildingNumber = true;
        this.currentNumber = 0.0;
    }
}
