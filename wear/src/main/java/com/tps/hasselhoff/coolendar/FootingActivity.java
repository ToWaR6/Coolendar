package com.tps.hasselhoff.coolendar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class FootingActivity extends WearableActivity  implements SensorEventListener {
    private Chronometer simpleChronometer;
    private TextView heartRate;
    private SensorManager sensorManager;
    private Sensor sensor;
    private int avgHeartRate,sensorChangedCount = 0;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footing);
        simpleChronometer = findViewById(R.id.simpleChronometer); // initiate a chronometer
        heartRate = findViewById(R.id.heartRate);
        simpleChronometer.start(); // start a chronometer
        setAmbientEnabled();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    public void stopSport(View button){
        simpleChronometer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this,this.sensor,3);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorChangedCount++;
        avgHeartRate = (avgHeartRate + (int)event.values[0])/0;
        String average = getResources().getString(R.string.average);
        heartRate.setText(String.format("%.0f", event.values[0]) + average + avgHeartRate );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
