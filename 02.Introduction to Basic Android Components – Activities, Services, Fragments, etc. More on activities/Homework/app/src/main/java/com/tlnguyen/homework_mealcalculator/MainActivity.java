package com.tlnguyen.homework_mealcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private static final Double DEFAULT_DISH_PRICE = 5.0;
    private static final Double DEFAULT_DESERT_PRICE = 2.0;
    private static final Double DEFAULT_DRINK_PRICE = 1.0;
    private static final Double DEFAULT_DELIVERY = 10.0;

    private static final Double DEFAULT_USD_COEF = 1.2;
    private static final Double DEFAULT_BGN_COEF = 1.9;

    private Double dishPrice;
    private Double desertPrice;
    private Double drinkPrice;
    private Double deliveryPrice;

    private Currency currency;

    private RadioGroup rgCurrencyGroup;

    private TextView tvDishPrice;
    private TextView tvDishQuantity;
    private TextView tvDesertPrice;
    private TextView tvDesertQuantity;
    private TextView tvDrinkPrice;
    private TextView tvTotal;

    private SeekBar sbDrinkQuantity;

    private Button btnDishQuantityPlus;
    private Button btnDishQuantityMinus;
    private Button btnDesertQuantityPlus;
    private Button btnDesertQuantityMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dishPrice = 0.0;
        desertPrice = 0.0;
        drinkPrice = 0.0;
        deliveryPrice = 0.0;
        currency = Currency.EUR;

        rgCurrencyGroup = (RadioGroup) findViewById(R.id.rgCurrencyGroup);

        tvDishPrice = (TextView) findViewById(R.id.tvDishPrice);
        tvDishQuantity = (TextView) findViewById(R.id.tvDishQuantity);
        tvDesertPrice = (TextView) findViewById(R.id.tvDesertPrice);
        tvDesertQuantity = (TextView) findViewById(R.id.tvDesertQuantity);
        tvDrinkPrice = (TextView) findViewById(R.id.tvDrinkPrice);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        sbDrinkQuantity = (SeekBar) findViewById(R.id.sbDrinkQuantity);

        btnDishQuantityPlus = (Button) findViewById(R.id.btnDishQuantityPlus);
        btnDishQuantityMinus = (Button) findViewById(R.id.btnDishQuantityMinus);
        btnDesertQuantityPlus = (Button) findViewById(R.id.btnDesertQuantityPlus);
        btnDesertQuantityMinus = (Button) findViewById(R.id.btnDesertQuantityMinus);

        rgCurrencyGroup.setOnCheckedChangeListener(this);

        btnDishQuantityPlus.setOnClickListener(this);
        btnDishQuantityMinus.setOnClickListener(this);
        btnDesertQuantityPlus.setOnClickListener(this);
        btnDesertQuantityMinus.setOnClickListener(this);

        sbDrinkQuantity.setOnSeekBarChangeListener(this);
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbEur:
                currency = Currency.EUR;
                break;
            case R.id.rbBgn:
                currency = Currency.BGN;
                break;
            case R.id.rbUsd:
                currency = Currency.USD;
                break;
        }

        resetPrices();
    }

    private void resetPrices() {
        setPrice(this.dishPrice, this.tvDishPrice);
        setPrice(this.desertPrice, this.tvDesertPrice);
        setPrice(this.drinkPrice, this.tvDrinkPrice);
        setTotal();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDishQuantityPlus:
                if (incrementQuantity(this.tvDishQuantity)) {
                    int quantity = Integer.parseInt(this.tvDishQuantity.getText().toString());
                    this.dishPrice = DEFAULT_DISH_PRICE * quantity;
                    setPrice(this.dishPrice, this.tvDishPrice);
                }
                break;
            case R.id.btnDishQuantityMinus:
                if (reduceQuantity(this.tvDishQuantity)) {
                    int quantity = Integer.parseInt(this.tvDishQuantity.getText().toString());
                    this.dishPrice = DEFAULT_DISH_PRICE * quantity;
                    setPrice(this.dishPrice, this.tvDishPrice);
                }
                break;
            case R.id.btnDesertQuantityPlus:
                if (incrementQuantity(this.tvDesertQuantity)) {
                    int quantity = Integer.parseInt(this.tvDesertQuantity.getText().toString());
                    this.desertPrice = DEFAULT_DESERT_PRICE * quantity;
                    setPrice(this.desertPrice, this.tvDesertPrice);
                }
                break;
            case R.id.btnDesertQuantityMinus:
                if (reduceQuantity(this.tvDesertQuantity)) {
                    int quantity = Integer.parseInt(this.tvDesertQuantity.getText().toString());
                    this.desertPrice = DEFAULT_DESERT_PRICE * quantity;
                    setPrice(this.desertPrice, this.tvDesertPrice);
                }
                break;
        }

        setTotal();
    }

    private void setTotal() {
        Double total = this.dishPrice + this.desertPrice + this.drinkPrice;
        setPrice(total, this.tvTotal);
    }

    private void setPrice(Double price, TextView textView) {
        double itemPrice;
        NumberFormat formatter = new DecimalFormat("#0.00");

        switch (this.currency) {
            case EUR:
                itemPrice = price;
                textView.setText(formatter.format(itemPrice) + " EUR");
                break;
            case BGN:
                itemPrice = price * DEFAULT_BGN_COEF;
                textView.setText(formatter.format(itemPrice) + " BGN");
                break;
            case USD:
                itemPrice = price * DEFAULT_USD_COEF;
                textView.setText(formatter.format(itemPrice) + " USD");
                break;
        }
    }

    private boolean incrementQuantity(TextView textView) {
        int value = Integer.parseInt(textView.getText().toString());

        if (value >= 10) {
            return false;
        }

        value++;
        textView.setText(Integer.toString(value));

        return true;
    }

    private boolean reduceQuantity(TextView textView) {
        int value = Integer.parseInt(textView.getText().toString());

        if (value <= 0) {
            return false;
        }

        value--;
        textView.setText(Integer.toString(value));

        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.sbDrinkQuantity) {
            int quantity = this.sbDrinkQuantity.getProgress();
            this.drinkPrice = DEFAULT_DRINK_PRICE * quantity;
            setPrice(drinkPrice, this.tvDrinkPrice);
        }

        setTotal();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
