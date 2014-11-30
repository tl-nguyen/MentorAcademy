package com.tlnguyen.homework_gamebook.activities.raiden;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tlnguyen.homework_gamebook.R;
import com.tlnguyen.homework_gamebook.common.Constants;
import com.tlnguyen.homework_gamebook.models.Hero;

public class RaidenKick extends Activity implements View.OnClickListener {

    private Hero mainCharacter;

    private TextView tvStatus;
    private Button btnCombo;
    private Button btnThunder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiden_kick);

        Bundle bundle = getIntent().getExtras();
        mainCharacter = bundle.getParcelable(Constants.MAIN_CHARACTER);
        double energyLost = mainCharacter.getEnergy() * 0.1;
        mainCharacter.setEnergy(mainCharacter.getEnergy() - (int)(energyLost));

        Log.i(Constants.TAG, String.format("You have lost %d Blood, %d Energy", 0, energyLost));

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnCombo = (Button) findViewById(R.id.btnCombo);
        btnThunder = (Button) findViewById(R.id.btnThunder);

        tvStatus.setText(String.format(getString(R.string.statusText), mainCharacter.getBlood(), mainCharacter.getEnergy()));
        btnCombo.setOnClickListener(this);
        btnThunder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnCombo:
                intent = new Intent(this, RaidenCombo.class);
                break;
            case R.id.btnThunder:
                intent = new Intent(this, RaidenThunder.class);
                break;
        }

        if (intent != null) {
            intent.putExtra(Constants.MAIN_CHARACTER, mainCharacter);
        }
        startActivity(intent);
    }
}
