package com.tps.hasselhoff.coolendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NotificationFragment extends Fragment {


    NotificationManager manager;
    CheckBox birthday, sport, meeting, health, others;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = getContext().getSharedPreferences(Preferences.PREFS, Context.MODE_PRIVATE);

        final View rootView = inflater.inflate(R.layout.fragment_notification,container,false);
        this.manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);



        this.birthday = rootView.findViewById(R.id.birthdayBox);
        this.birthday.setHint(Preferences.PREF_BIRTHDAY);
        this.sport = rootView.findViewById(R.id.sportBox);
        this.sport.setHint(Preferences.PREF_SPORT);
        this.meeting = rootView.findViewById(R.id.meetingBox);
        this.meeting.setHint(Preferences.PREF_MEETING);
        this.health = rootView.findViewById(R.id.healthBox);
        this.health.setHint(Preferences.PREF_HEALTH);
        this.others = rootView.findViewById(R.id.othersBox);
        this.others.setHint(Preferences.PREF_OTHERS);

        CompoundButton.OnCheckedChangeListener onCheck = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPreferences.edit().putBoolean(buttonView.getHint().toString(),true).apply();
                } else {

                    sharedPreferences.edit().putBoolean(buttonView.getHint().toString(),false).apply();
                }
                createNotification("", "");
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
        boolean b = true;
        Log.i("TEST",""+sharedPreferences.getBoolean(Preferences.PREF_BIRTHDAY,b));
    }


}
