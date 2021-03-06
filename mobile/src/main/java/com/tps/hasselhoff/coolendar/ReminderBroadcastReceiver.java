package com.tps.hasselhoff.coolendar;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ReminderBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse("content://com.android.calendar/events");
        final String selection = CalendarContract.Events.CALENDAR_ID + "<"
                + 3 + " AND " + "("
                + CalendarContract.Events.DIRTY + "=" + 1 + " OR "
                + CalendarContract.Events.DELETED + "=" + 1 + ")" + " AND "
                + CalendarContract.Events.DTEND + " > "
                + Calendar.getInstance().getTimeInMillis();
       Cursor cursor = cr.query(uri,null,selection,null,null);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.PREFS,Context.MODE_PRIVATE);
       while(cursor.moveToNext()) {
           String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
           final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy, HH:mm");
           Calendar cal = Calendar.getInstance();
           cal.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART)));
           String text = simpleDateFormat.format(cal.getTime());
           int event_id = cursor.getInt(cursor.getColumnIndex(CalendarContract.Events._ID));
           for(String pref : Preferences.getStringArrayPref(context,Preferences.PREFS_LIST)){
               if(title.contains(pref)) {
                   showNotification(context, event_id, title, text);
               }
           }
       }
    }

    public void showNotification(Context context,int event_id,String title, String text) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.WearableExtender wearNotification =  new NotificationCompat.WearableExtender();
        if(title.contains(context.getResources().getString(R.string.sport))) {

            Intent messageIntent = new Intent(context,MessageSenderIntentService.class);
            messageIntent.putExtra(CalendarContract.Events._ID,event_id)
                    .putExtra(CalendarContract.Events.TITLE,title);
            messageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent messagePendingIntent =
                    PendingIntent.getService(context, 0, messageIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_directions_run_white_24dp, context.getResources().getString(R.string.go), messagePendingIntent);
            NotificationCompat.Action
                    .WearableExtender actionExtender =
                    new NotificationCompat.Action.WearableExtender()
                            .setHintLaunchesActivity(true)
                            .setHintDisplayActionInline(true);
            wearNotification.addAction(new NotificationCompat.Action.Builder(action).extend(actionExtender).build());
        }
        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(context,"default")
                .setSmallIcon(R.drawable.ic_event_available_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                .setContentTitle(title) // title for notification
                .setContentText(text)
                .setSound(soundUri)// message for notification
                .extend(wearNotification)//Display notification on wearable and Phone
                .setAutoCancel(true) // clear notification after click
                .setLights(Color.rgb(61,0,41), 100000, 0)
                .setVibrate(new long[] {200,30,200,30,200,30,500,30,500,30,500,30,200,30,200,30,200});

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(event_id, mBuilder.build());
    }
}
