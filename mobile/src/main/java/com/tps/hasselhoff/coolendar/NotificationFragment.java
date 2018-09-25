package com.tps.hasselhoff.coolendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NotificationFragment extends Fragment {

    private static int cptNotif = 0;
    private Button buttonNotification;
    NotificationManager manager;
    private Notification myNotication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_notification,container,false);

        manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        this.buttonNotification = rootView.findViewById(R.id.buttonNotification);
        this.buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification("Yo tout le monde c'est...", "SQUEEEEEEEZZZZZZIIIIIIIIEEEEEE");
            }
        });
        return rootView;
    }

    private final void createNotification(String title, String text){

        Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");

        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(getContext());

        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("This is subtext...");   //API level 16
        builder.setNumber(100);
        builder.build();

        myNotication = builder.build();
        manager.notify(11, myNotication);
    }
}
