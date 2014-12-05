package com.tlnguyen.classassignment2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.tlnguyen.classassignment2.adapters.CountryAdapter;
import com.tlnguyen.classassignment2.models.Country;

import java.util.ArrayList;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{

    private GridView mGvCountries;

    private ArrayList<Country> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        countries = new ArrayList<Country>();

        Country bulgaria = new Country("BG", 11111, R.drawable.bulgaria);
        Country france = new Country("FR", 11111, R.drawable.france);
        countries.add(bulgaria);
        countries.add(france);

        mGvCountries = (GridView) findViewById(R.id.gvCountry);
        mGvCountries.setAdapter(new CountryAdapter(this, countries));

        mGvCountries.setOnItemClickListener(this);
        mGvCountries.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, countries.get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, countries.get(position).getYear() + "", Toast.LENGTH_SHORT).show();
        return false;
    }
}
