package com.tps.hasselhoff.coolendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View rootView = inflater.inflate(R.layout.fragment_add_event,container,false);
        //Get heure courante
        final Calendar date  = Calendar.getInstance();
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        SimpleDateFormat simpleTimeFormat  = new SimpleDateFormat("HH:mm");

        final TextView myTime = rootView.findViewById(R.id.time);
        myTime.setText( simpleTimeFormat.format(date.getTime()));
        final TimePickerDialog  timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                date.set(Calendar.HOUR,selectedHour);
                date.set(Calendar.MINUTE,selectedMinute);
                myTime.setText( selectedHour + ":" + selectedMinute);
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
        return rootView;
    }
}
