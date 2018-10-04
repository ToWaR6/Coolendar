package com.tps.hasselhoff.coolendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NotificationFragment extends Fragment {


    NotificationManager manager;
    CheckBox birthday, sport, meeting, health, others;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sharedPreferences = getContext().getSharedPreferences(Preferences.PREFS, Context.MODE_PRIVATE);
        final View rootView = inflater.inflate(R.layout.fragment_notification,container,false);
        this.manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        initCheckBoxes(rootView);
        CompoundButton.OnCheckedChangeListener onCheck = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<String> res = Preferences.getStringArrayPref(getContext(),Preferences.PREFS_LIST);
                if (isChecked) {
                    res.add(buttonView.getText().toString());

                } else {
                    res.remove(buttonView.getText().toString());
                }
                Preferences.setStringArrayPref(getContext(),Preferences.PREFS_LIST,res);
            }
        };

        birthday.setOnCheckedChangeListener(onCheck);
        sport.setOnCheckedChangeListener(onCheck);
        meeting.setOnCheckedChangeListener(onCheck);
        health.setOnCheckedChangeListener(onCheck);
        others.setOnCheckedChangeListener(onCheck);

        return rootView;
    }

    private void initCheckBoxes(View rootView) {
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        this.birthday = rootView.findViewById(R.id.birthdayBox);
        checkBoxes.add(this.birthday);
        this.sport = rootView.findViewById(R.id.sportBox);
        checkBoxes.add(this.sport);
        this.meeting = rootView.findViewById(R.id.meetingBox);
        checkBoxes.add(this.meeting);
        this.health = rootView.findViewById(R.id.healthBox);
        checkBoxes.add(this.health);
        this.others = rootView.findViewById(R.id.othersBox);
        checkBoxes.add(this.others);
        ArrayList<String> prefs = Preferences.getStringArrayPref(getContext(),Preferences.PREFS_LIST);
        for(CheckBox c : checkBoxes) {
            if(prefs.contains(c.getText().toString()))
                c.setChecked(true);
        }
    }



}
