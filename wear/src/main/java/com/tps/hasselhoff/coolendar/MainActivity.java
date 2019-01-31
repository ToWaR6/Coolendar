package com.tps.hasselhoff.coolendar;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


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
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(sportChosen.equals(footing)){
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                showActivity(FootingActivity.class);
            else
                Toast.makeText(this,getResources().getString(R.string.access_denied),Toast.LENGTH_SHORT).show();
        }
    }

    private void showActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
