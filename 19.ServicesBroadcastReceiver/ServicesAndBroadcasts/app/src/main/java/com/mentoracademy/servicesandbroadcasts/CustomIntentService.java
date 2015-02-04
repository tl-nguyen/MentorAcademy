package com.mentoracademy.servicesandbroadcasts;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Stefan on 31.1.2015 г..
 */
public class CustomIntentService extends IntentService {

    public CustomIntentService() {
        super("CustomIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("CUSTOM_INTENT_SERVICE", "Updating the data");
        SystemClock.sleep(2500); // 2.5 seconds
        //TODO Implement the real update of the data
        Log.d("CUSTOM_INTENT_SERVICE", "Data successfully updated!");
    }
}
