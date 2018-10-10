package com.tps.hasselhoff.coolendar;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessageSenderIntentService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MessageSenderIntentService() {
        super("MessageSenderService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("CREATE","OK");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("TAGE","OK");
    }
}
