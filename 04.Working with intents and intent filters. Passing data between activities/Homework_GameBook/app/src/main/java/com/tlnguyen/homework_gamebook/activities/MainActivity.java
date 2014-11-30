package com.tlnguyen.homework_gamebook.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tlnguyen.homework_gamebook.R;
import com.tlnguyen.homework_gamebook.activities.raiden.RaidenFirstScene;
import com.tlnguyen.homework_gamebook.activities.shaokahn.ShaokahnFirstScene;
import com.tlnguyen.homework_gamebook.common.Constants;
import com.tlnguyen.homework_gamebook.common.Dice;
import com.tlnguyen.homework_gamebook.common.Name;
import com.tlnguyen.homework_gamebook.models.Hero;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnChooseRaiden;
    private Button btnChooseShaoKahn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChooseRaiden = (Button) findViewById(R.id.btnChooseRaiden);
        btnChooseShaoKahn = (Button) findViewById(R.id.btnChooseShaoKahn);

        btnChooseRaiden.setOnClickListener(this);
        btnChooseShaoKahn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Hero mainCharacter = null;
        Intent firstScene = null;
        switch (v.getId()) {
            case R.id.btnChooseRaiden:
                mainCharacter = new Hero(Name.Raiden, Dice.roll() * 10, Dice.roll() * 15);
                firstScene = new Intent(this, RaidenFirstScene.class);
                break;
            case R.id.btnChooseShaoKahn:
                mainCharacter = new Hero(Name.Shaokahn, Dice.roll() * 15, Dice.roll() * 10);
                firstScene = new Intent(this, ShaokahnFirstScene.class);
                break;
        }

        if (firstScene != null) {
            firstScene.putExtra(Constants.MAIN_CHARACTER, mainCharacter);
        }
        startActivity(firstScene);
    }
}
