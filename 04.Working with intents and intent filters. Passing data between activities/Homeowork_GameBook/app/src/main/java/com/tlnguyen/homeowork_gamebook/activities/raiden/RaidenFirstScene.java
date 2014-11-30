package com.tlnguyen.homeowork_gamebook.activities.raiden;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tlnguyen.homeowork_gamebook.R;
import com.tlnguyen.homeowork_gamebook.common.Constants;
import com.tlnguyen.homeowork_gamebook.models.Hero;

public class RaidenFirstScene extends Activity implements View.OnClickListener {

    private Hero mainCharacter;

    private TextView tvStatus;
    private Button btnPunch;
    private Button btnKick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiden_first_scene);

        Bundle bundle = getIntent().getExtras();
        mainCharacter = bundle.getParcelable(Constants.MAIN_CHARACTER);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnPunch = (Button) findViewById(R.id.btnPunch);
        btnKick = (Button) findViewById(R.id.btnKick);

        tvStatus.setText(String.format(getString(R.string.statusText), mainCharacter.getBlood(), mainCharacter.getEnergy()));
        btnPunch.setOnClickListener(this);
        btnKick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnPunch:
                intent = new Intent(this, RaidenPunch.class);
                break;
            case R.id.btnKick:
                intent = new Intent(this, RaidenKick.class);
                break;
        }

        if (intent != null) {
            intent.putExtra(Constants.MAIN_CHARACTER, mainCharacter);
        }
        startActivity(intent);
    }
}
