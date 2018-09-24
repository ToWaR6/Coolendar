package com.tps.hasselhoff.coolendar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AddEventFragment extends Fragment{

    private Calendar date;
    private EditText titleEditText;
    private EditText descriptionEditText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        final View rootView = inflater.inflate(R.layout.fragment_add_event,container,false);
        titleEditText = rootView.findViewById(R.id.titleEvent);
        descriptionEditText = rootView.findViewById(R.id.descriptionEvent);
        //Get heure courante
        date  = Calendar.getInstance();
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        final SimpleDateFormat simpleTimeFormat  = new SimpleDateFormat("HH:mm");

        final TextView myTime = rootView.findViewById(R.id.time);
        myTime.setText( simpleTimeFormat.format(date.getTime()));
        final TimePickerDialog  timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                date.set(Calendar.HOUR,selectedHour);
                date.set(Calendar.MINUTE,selectedMinute);
                myTime.setText(simpleTimeFormat.format(date.getTime()));
            }
        }, hour, minute, true);

        myTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                timePicker.show();
            }
        });

        final TextView myDate = rootView.findViewById(R.id.date);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");

        myDate.setText(simpleDateFormat.format(date.getTime()));
        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar date  = Calendar.getInstance();
                date.set(year,month,dayOfMonth);
                myDate.setText(simpleDateFormat.format(date.getTime()));

            }
        },date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH));

        myDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                datePicker.show();
            }
        });
        rootView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               closeFragment();
            }
        });

        rootView.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });
        return rootView;
    }

    public void saveEvent(){
        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID,1234);
        values.put(CalendarContract.Events.DTSTART,date.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND,date.getTimeInMillis());
        values.put(CalendarContract.Events.TITLE,titleEditText.getText().toString());

        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
        values.put(CalendarContract.Events.DESCRIPTION,descriptionEditText.getText().toString());

        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values); // ZERO CHECK CAR LOL

        long eventID = Long.parseLong(uri.getLastPathSegment());
        Log.i("test",uri.toString());
        closeFragment();
    }

    public void closeFragment(){
        getFragmentManager().beginTransaction().remove(AddEventFragment.this).commit();
        getActivity().findViewById(R.id.navigation_home).performClick();//Hack api <25.0.3
    }
}