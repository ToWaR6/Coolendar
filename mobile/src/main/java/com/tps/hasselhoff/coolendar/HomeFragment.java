package com.tps.hasselhoff.coolendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View rootView;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        rootView = inflater.inflate(R.layout.home_fragment,container,false);
        rootView.findViewById(R.id.deleteDate).setOnClickListener(this);
        rootView.findViewById(R.id.deleteSpinner).setOnClickListener(this);
        rootView.findViewById(R.id.searchButton).setOnClickListener(this);
        final TextView myDate = rootView.findViewById(R.id.date);

        Calendar date = Calendar.getInstance();
        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
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
        spinner.setSelection(4);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.deleteDate:
                ((TextView)rootView.findViewById(R.id.date)).setText(getContext().getResources().getString(R.string.default_date));
                break;
            case R.id.deleteSpinner:
                spinner.setSelection(4,true);
                break;
            case R.id.searchButton:
                searchEvents();
                break;
        }
    }

    private void searchEvents() {
        String textDate = ((TextView) rootView.findViewById(R.id.date)).getText().toString();
        Date date;
        try {
            date = simpleDateFormat.parse(textDate);
        } catch (ParseException e) {
            date = null;
        }
        if(date!=null){
            Log.i("OK",simpleDateFormat.format(date));
        }
    }

}
