package com.tlnguyen.classasignment2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    private LinearLayout wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wrapper = (LinearLayout) findViewById(R.id.wrapper);
        wrapper.setBackgroundColor(Color.BLACK);

        createPotraitView();
    }

    private void createPotraitView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.weight = 1f;
        params.setMargins(1, 1, 1, 1);

        for (int i = 0; i < 3; i++) {
            LinearLayout level1Layout = new LinearLayout(this);
            level1Layout.setOrientation(LinearLayout.HORIZONTAL);
            level1Layout.setLayoutParams(params);
            level1Layout.setWeightSum(2f);

            if (i % 2 == 0) {
                for (int j = 0; j < 2; j++) {
                    View innerView = new View(this);
                    innerView.setLayoutParams(params);
                    innerView.setBackgroundColor(j%2==0 ? Color.BLUE : Color.CYAN);
                    level1Layout.addView(innerView);
                }
            }
            else {
                for (int j = 0; j < 2; j++) {
                    View innerView = new View(this);
                    innerView.setLayoutParams(params);
                    innerView.setBackgroundColor(j%2==0 ? Color.RED : Color.GRAY);
                    level1Layout.addView(innerView);
                }
            }

            wrapper.addView(level1Layout);
        }
    }

    private void makeInnerView() {

    }

}
