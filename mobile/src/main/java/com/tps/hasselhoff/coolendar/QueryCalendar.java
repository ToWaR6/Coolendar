package com.tps.hasselhoff.coolendar;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QueryCalendar extends AsyncTask<String, Void, ArrayList<Calendar>>{

    private Context context;
    private Calendar date;
    private String typeEvent;

    public QueryCalendar(Context c,Calendar date,String typeEvent){
        this.context = c;
        this.typeEvent = typeEvent;
        this.date =  date;
    }

    @Override
    protected ArrayList<Calendar> doInBackground(String... urls) {
        Cursor cur = null;
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse("content://com.android.calendar/events");
        String selection = "(";
        selection += "("+CalendarContract.Events.DTSTART + " >= " + date.getTimeInMillis() + ")";
        selection += " AND (" + CalendarContract.Events.TITLE + " = '" + typeEvent+"%')" ;
        selection+=")";
        Log.i("Selection",selection);
        cur = cr.query(uri,null,selection,null,null);
        while(cur.moveToNext()){
            Log.i("OK",cur.getString(cur.getColumnIndex("title")));
        }
        return null;
    }
}
