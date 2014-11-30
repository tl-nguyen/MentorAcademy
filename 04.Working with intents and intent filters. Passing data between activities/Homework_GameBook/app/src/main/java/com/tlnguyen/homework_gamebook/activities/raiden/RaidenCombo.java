package com.tlnguyen.homework_gamebook.activities.raiden;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tlnguyen.homework_gamebook.R;
import com.tlnguyen.homework_gamebook.activities.MainActivity;
import com.tlnguyen.homework_gamebook.common.Constants;
import com.tlnguyen.homework_gamebook.models.Hero;

public class RaidenCombo extends Activity implements View.OnClickListener {

    private Hero mainCharacter;

    private TextView tvStatus;
    private Button btnNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiden_combo);

        Bundle bundle = getIntent().getExtras();
        mainCharacter = bundle.getParcelable(Constants.MAIN_CHARACTER);

        double energyLost = mainCharacter.getEnergy() * 0.5;
        mainCharacter.setEnergy(mainCharacter.getEnergy() - (int)(energyLost));

        Log.i(Constants.TAG, String.format("You have lost %d Blood, %d Energy", mainCharacter.getBlood(), energyLost));
        mainCharacter.setBlood(0);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);

        tvStatus.setText(String.format(getString(R.string.statusText), mainCharacter.getBlood(), mainCharacter.getEnergy()));
        btnNewGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewGame:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }
}
