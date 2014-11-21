package com.tlnguyen.homework_mealcalculator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tlnguyen.homework_mealcalculator.R;
import com.tlnguyen.homework_mealcalculator.common.Currency;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener,
        SeekBar.OnSeekBarChangeListener,
        CompoundButton.OnCheckedChangeListener{

    private static final Double DEFAULT_DISH_PRICE = 5.0;
    private static final Double DEFAULT_DESERT_PRICE = 2.0;
    private static final Double DEFAULT_DRINK_PRICE = 1.0;
    private static final Double DEFAULT_DELIVERY_PRICE = 10.0;

    private static final Double DEFAULT_USD_COEF = 1.2;
    private static final Double DEFAULT_BGN_COEF = 1.9;

    private Double dishPrice;
    private Double desertPrice;
    private Double drinkPrice;
    private Double deliveryPrice;

    private int dishQuantity;
    private int desertQuantity;
    private int drinkQuantity;

    private Currency currency;

    private RadioGroup rgCurrencyGroup;
    private RadioButton rbEur;
    private RadioButton rbBgn;
    private RadioButton rbUsd;

    private TextView tvDishPrice;
    private TextView tvDishQuantity;
    private TextView tvDesertPrice;
    private TextView tvDesertQuantity;
    private TextView tvDrinkPrice;
    private TextView tvTotal;

    private SeekBar sbDrinkQuantity;

    private CheckBox cbHomeDelivery;

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
        dishQuantity = 0;
        desertQuantity = 0;
        drinkQuantity = 0;
        currency = Currency.EUR;

        rgCurrencyGroup = (RadioGroup) findViewById(R.id.rgCurrencyGroup);
        rbEur = (RadioButton) findViewById(R.id.rbEur);
        rbBgn = (RadioButton) findViewById(R.id.rbBgn);
        rbUsd = (RadioButton) findViewById(R.id.rbUsd);

        tvDishPrice = (TextView) findViewById(R.id.tvDishPrice);
        tvDishQuantity = (TextView) findViewById(R.id.tvDishQuantity);
        tvDesertPrice = (TextView) findViewById(R.id.tvDesertPrice);
        tvDesertQuantity = (TextView) findViewById(R.id.tvDesertQuantity);
        tvDrinkPrice = (TextView) findViewById(R.id.tvDrinkPrice);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        sbDrinkQuantity = (SeekBar) findViewById(R.id.sbDrinkQuantity);

        cbHomeDelivery = (CheckBox) findViewById(R.id.cbHomeDelivery);

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

        cbHomeDelivery.setOnCheckedChangeListener(this);

        resetPrices();
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
        if (id == R.id.aboutUs) {
            Intent aboutUsScreen = new Intent(this, AboutUs.class);
            this.startActivity(aboutUsScreen);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDishQuantityPlus:
                if (incrementQuantity(this.tvDishQuantity)) {
                    setDishPrice();
                }
                break;
            case R.id.btnDishQuantityMinus:
                if (reduceQuantity(this.tvDishQuantity)) {
                    setDishPrice();
                }
                break;
            case R.id.btnDesertQuantityPlus:
                if (incrementQuantity(this.tvDesertQuantity)) {
                    setDesertPrice();
                }
                break;
            case R.id.btnDesertQuantityMinus:
                if (reduceQuantity(this.tvDesertQuantity)) {
                    setDesertPrice();
                }
                break;
        }

        setTotal();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.sbDrinkQuantity) {
            setDrinkPrice();
        }

        setTotal();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //TODO
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //TODO
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            this.deliveryPrice = DEFAULT_DELIVERY_PRICE;
        } else {
            this.deliveryPrice = 0.0;
        }

        setTotal();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putDouble("DISH_PRICE", this.dishPrice);
        outState.putDouble("DESERT_PRICE", this.desertPrice);
        outState.putDouble("DRINK_PRICE", this.drinkPrice);
        outState.putDouble("DELIVERY_PRICE", this.deliveryPrice);

        outState.putInt("DISH_QUANTITY", this.dishQuantity);
        outState.putInt("DESERT_QUANTITY", this.desertQuantity);
        outState.putInt("DRINK_QUANTITY", this.drinkQuantity);

        outState.putString("CURRENCY", this.currency.toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        this.dishPrice = savedInstanceState.getDouble("DISH_PRICE");
        this.desertPrice = savedInstanceState.getDouble("DESERT_PRICE");
        this.drinkPrice = savedInstanceState.getDouble("DRINK_PRICE");
        this.deliveryPrice = savedInstanceState.getDouble("DELIVERY_PRICE");

        this.dishQuantity = savedInstanceState.getInt("DISH_QUANTITY");
        this.desertQuantity = savedInstanceState.getInt("DESERT_QUANTITY");
        this.drinkQuantity = savedInstanceState.getInt("DRINK_QUANTITY");

        String storedCurrency = savedInstanceState.getString("CURRENCY");

        resetPrices();
        restoreHomeDelivery();
        restoreQuantities();
        restoreCurrency(storedCurrency);
    }

    private void setDesertPrice() {
        this.desertQuantity = Integer.parseInt(this.tvDesertQuantity.getText().toString());
        this.desertPrice = DEFAULT_DESERT_PRICE * this.desertQuantity;
        setPrice(this.desertPrice, this.tvDesertPrice);
    }

    private void setDishPrice() {
        this.dishQuantity = Integer.parseInt(this.tvDishQuantity.getText().toString());
        this.dishPrice = DEFAULT_DISH_PRICE * this.dishQuantity;
        setPrice(this.dishPrice, this.tvDishPrice);
    }

    private void setDrinkPrice() {
        this.drinkQuantity = this.sbDrinkQuantity.getProgress();
        this.drinkPrice = DEFAULT_DRINK_PRICE * this.drinkQuantity;
        setPrice(drinkPrice, this.tvDrinkPrice);
    }

    private void restoreHomeDelivery() {
        if(this.deliveryPrice > 0) {
            this.cbHomeDelivery.setChecked(true);
        }
    }

    private void restoreQuantities() {
        this.tvDishQuantity.setText(Integer.toString(this.dishQuantity));
        this.tvDesertQuantity.setText(Integer.toString(this.desertQuantity));
        this.sbDrinkQuantity.setProgress(this.drinkQuantity);
    }

    private void restoreCurrency(String storedCurrency) {
        if (storedCurrency.equals("EUR")) {
            this.currency = Currency.EUR;
            this.rbEur.setChecked(true);
        } else if (storedCurrency.equals("BGN")) {
            this.currency = Currency.BGN;
            this.rbBgn.setChecked(true);
        } else {
            this.currency = Currency.USD;
            this.rbUsd.setChecked(true);
        }
    }

    private void resetPrices() {
        setPrice(this.dishPrice, this.tvDishPrice);
        setPrice(this.desertPrice, this.tvDesertPrice);
        setPrice(this.drinkPrice, this.tvDrinkPrice);
        setTotal();
    }

    private void setTotal() {
        Double total = this.dishPrice + this.desertPrice + this.drinkPrice + this.deliveryPrice;
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
}
