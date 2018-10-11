package com.tps.hasselhoff.coolendar;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.CalendarContract;
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
    protected void onHandleIntent(@Nullable Intent intent) {
        int notificationId = intent.getIntExtra(CalendarContract.Events._ID,-1);
        String title = intent.getStringExtra(CalendarContract.Events.TITLE);
        Log.i("Reçu intent service",title);
        NotificationManager mNotificationManager =
                (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notificationId);
    }
}
