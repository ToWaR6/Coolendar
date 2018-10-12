package com.tps.hasselhoff.coolendar;

import android.content.Intent;
import android.provider.CalendarContract;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class WearDataLayerListenerService extends WearableListenerService {


    private static final String START_ACTIVITY_PATH = "/start-activity";
    public void onMessageReceived(MessageEvent messageEvent){
        super.onMessageReceived(messageEvent);
        if(messageEvent.getPath().equals(START_ACTIVITY_PATH)){
            String title = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(CalendarContract.Events.TITLE,title);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
