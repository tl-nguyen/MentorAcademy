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

public class RaidenPunch extends Activity implements View.OnClickListener {

    private Hero mainCharacter;

    private TextView tvStatus;
    private Button btnBlock;
    private Button btnTeleport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiden_punch);

        Bundle bundle = getIntent().getExtras();
        mainCharacter = bundle.getParcelable(Constants.MAIN_CHARACTER);

        double energyLost = mainCharacter.getEnergy() - mainCharacter.getEnergy() * 0.1;
        double bloodLost = mainCharacter.getBlood() * 0.1;
        mainCharacter.setEnergy(mainCharacter.getEnergy() - (int)(energyLost));
        mainCharacter.setBlood(mainCharacter.getBlood() - (int)(bloodLost));

        Log.i(Constants.TAG, String.format("You have lost %d Blood, %d Energy", bloodLost, energyLost));


        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnBlock = (Button) findViewById(R.id.btnBlock);
        btnTeleport = (Button) findViewById(R.id.btnTeleport);

        tvStatus.setText(String.format(getString(R.string.statusText), mainCharacter.getBlood(), mainCharacter.getEnergy()));
        btnBlock.setOnClickListener(this);
        btnTeleport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnTeleport:
                intent = new Intent(this, RaidenTeleport.class);
                break;
            case R.id.btnBlock:
                intent = new Intent(this, RaidenBlock.class);
                break;
        }

        if (intent != null) {
            intent.putExtra(Constants.MAIN_CHARACTER, mainCharacter);
        }

        startActivity(intent);
    }
}
