package com.tlnguyen.servicesdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(WorkerService.KEY_MESSAGE)) {
                txtView.append(intent.getStringExtra(WorkerService.KEY_MESSAGE));
            }
        }
    };

    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.txt_view);

//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
//                new IntentFilter(WorkerService.BROADCAST_RESULT));

        registerReceiver(receiver,
                new IntentFilter(WorkerService.BROADCAST_RESULT));

        Intent intent = new Intent(this, WorkerService.class);
        intent.setAction(WorkerService.ACTION_ASYNC);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        unregisterReceiver(receiver);
    }
}
