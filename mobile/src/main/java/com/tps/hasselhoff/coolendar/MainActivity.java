package com.tps.hasselhoff.coolendar;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private BottomNavigationView navigation;

    public final static int REQUEST_CALENDAR = 1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new SearchFragment());
                    return true;
                case R.id.navigation_add_event:
                    showFragment(new AddEventFragment());
                    return true;
                case R.id.navigation_notifications:
                    showFragment(new NotificationFragment());
                    return true;

            }
            return false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_CALENDAR},
                    REQUEST_CALENDAR);
        }
        showFragment(new SearchFragment());
    }

    private Fragment showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commitAllowingStateLoss();
        return fragment;
    }




    //voice recognitio

    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    public void displaySpeechRecognizer(MenuItem item) {
        
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // Start the activity, the intent will be populated with the speech text
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch(ActivityNotFoundException e) {
            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            if (spokenText.toLowerCase().contains(this.getResources().getString(R.string.add))) {
                ((BottomNavigationView) findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_add_event);
                showFragment(new AddEventFragment());
            } else if (spokenText.toLowerCase().contains(this.getResources().getString(R.string.next))) {
               ((BottomNavigationView) findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_home);
               showFragment(new SearchFragment().withSearch());

            }else if(spokenText.toLowerCase().contains(getResources().getString(R.string.weather))){

                Toast.makeText(this, this.getResources().getString(R.string.weather_app), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
