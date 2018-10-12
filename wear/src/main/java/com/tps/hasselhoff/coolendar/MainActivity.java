package com.tps.hasselhoff.coolendar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends WearableActivity {
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enables Always-on
        String title = getIntent().getStringExtra(CalendarContract.Events.TITLE);
        spinner = findViewById(R.id.spinner);
        if(title != null)
            ((EditText)findViewById(R.id.title)).setText(title.replace("[Sport]",""));
        setAmbientEnabled();

    }

    public void startSport(View button){
        String footing = getResources().getString(R.string.footing);
        String sportChosen = spinner.getSelectedItem().toString();
        if(sportChosen.equals(footing)){
            showActivity(FootingActivity.class);
        }
    }

    private void showActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
