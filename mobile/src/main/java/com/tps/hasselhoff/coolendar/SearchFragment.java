package com.tps.hasselhoff.coolendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
    private Calendar date;
    private final Calendar today = Calendar.getInstance();
    private Spinner spinner;
    private String[] arraySpinner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        rootView = inflater.inflate(R.layout.home_fragment,container,false);
        rootView.findViewById(R.id.deleteDate).setOnClickListener(this);
        rootView.findViewById(R.id.deleteSpinner).setOnClickListener(this);
        rootView.findViewById(R.id.searchButton).setOnClickListener(this);
        final TextView myDate = rootView.findViewById(R.id.date);
        date = Calendar.getInstance();

        ((TextView)rootView.findViewById(R.id.date)).setText(simpleDateFormat.format(date.getTime()));
        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.set(year, month, dayOfMonth);
                myDate.setText(simpleDateFormat.format(date.getTime()));

            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        myDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                datePicker.show();
            }
        });
        spinner = rootView.findViewById(R.id.spinner);
        arraySpinner = getActivity().getResources().getStringArray(R.array.type_event_array);
        spinner.setSelection(arraySpinner.length-1);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.deleteDate:
                ((TextView)rootView.findViewById(R.id.date)).setText(simpleDateFormat.format(today.getTime()));
                break;
            case R.id.deleteSpinner:
                spinner.setSelection(arraySpinner.length-1);
                break;
            case R.id.searchButton:
                String nothing = arraySpinner[arraySpinner.length-1];
                String typeEvent = "";
                if(!spinner.getSelectedItem().equals(nothing))
                    typeEvent = "[" + spinner.getSelectedItem() + "]";
                searchEvents(typeEvent, date);
                break;
        }
    }

    public void searchEvents(String typeEvent, Calendar date) {
        QueryCalendar task = new QueryCalendar(getActivity(),date,typeEvent);
        task.execute();

    }


}
