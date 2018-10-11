package com.tps.hasselhoff.coolendar;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WearDataLayerListenerService extends WearableListenerService {


    private static final String START_ACTIVITY_PATH = "/start-activity";
    public void onMessageReceived(MessageEvent messageEvent){
        Log.i("TEST WEARABLE","ALLEZ ! ");
        super.onMessageReceived(messageEvent);
        if(messageEvent.getPath().equals(START_ACTIVITY_PATH)){
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
