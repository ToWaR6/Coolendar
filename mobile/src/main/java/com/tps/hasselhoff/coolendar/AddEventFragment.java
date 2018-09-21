package com.tps.hasselhoff.coolendar;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddEventFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_event,container,false);
        //Get heure courante
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        final TextView my_Time = rootView.findViewById(R.id.time_picker);
        my_Time.setText( hour + ":" + minute);
        final TimePickerDialog  timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                my_Time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);

        rootView.findViewById(R.id.time_picker).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                timePicker.show();
            }
        });


        
        return rootView;
    }
}
