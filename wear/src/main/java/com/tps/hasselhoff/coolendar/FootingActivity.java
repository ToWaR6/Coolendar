package com.tps.hasselhoff.coolendar;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.Chronometer;

public class FootingActivity extends WearableActivity {
    Chronometer simpleChronometer;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footing);
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer); // initiate a chronometer
        simpleChronometer.start(); // start a chronometer
        setAmbientEnabled();
    }
}
