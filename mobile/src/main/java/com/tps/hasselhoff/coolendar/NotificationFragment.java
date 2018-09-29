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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NotificationFragment extends Fragment {

    private static int cptNotif = 0;
    NotificationManager manager;
    ArrayList<String> listTypeNotif;
    CheckBox birthday, sport, meeting, health, others;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_notification,container,false);
        this.manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        this.listTypeNotif = new ArrayList<>();

        this.birthday = rootView.findViewById(R.id.birthdayBox);
        this.sport = rootView.findViewById(R.id.sportBox);
        this.meeting = rootView.findViewById(R.id.meetingBox);
        this.health = rootView.findViewById(R.id.healthBox);
        this.others = rootView.findViewById(R.id.othersBox);

        CompoundButton.OnCheckedChangeListener onCheck = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listTypeNotif.add(buttonView.getText().toString());
                } else {
                    listTypeNotif.remove(buttonView.getText().toString());
                }
                createNotification("List:"+listTypeNotif.size(), listTypeNotif.toString());
            }
        };

        birthday.setOnCheckedChangeListener(onCheck);
        sport.setOnCheckedChangeListener(onCheck);
        meeting.setOnCheckedChangeListener(onCheck);
        health.setOnCheckedChangeListener(onCheck);
        others.setOnCheckedChangeListener(onCheck);

        return rootView;
    }

    private final void createNotification(String title, String text){

        Intent intent = new Intent(getActivity(),MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(getActivity());

        builder.setAutoCancel(true);
        builder.setTicker("OMFG ! THERE'S A NOTIF !!!!!!");
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        builder.setSubText("WHAT A BEAUTIFUL NOTIFICATION");
        builder.setNumber(100);
        builder.setLocalOnly(false);
        manager.notify(cptNotif++,  builder.build());
    }


}
