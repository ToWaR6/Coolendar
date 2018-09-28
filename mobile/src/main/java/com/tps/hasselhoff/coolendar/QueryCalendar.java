package com.tps.hasselhoff.coolendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.widget.ListView;

import java.util.Calendar;

public class QueryCalendar extends AsyncTask<String, Void, Cursor>{

    private Activity activity;
    private Calendar date;
    private String typeEvent;

    public QueryCalendar(Activity activity, Calendar date, String typeEvent){
        this.activity = activity;
        this.typeEvent = typeEvent;
        this.date =  date;
    }

    @Override
    protected Cursor doInBackground(String... urls) {
        ContentResolver cr = activity.getContentResolver();
        Uri uri = Uri.parse("content://com.android.calendar/events");
        String selection = "(";
        selection += "("+CalendarContract.Events.DTSTART + " >= " + date.getTimeInMillis() + ")";
        selection += " AND (" + CalendarContract.Events.TITLE + " LIKE '" + typeEvent+"%%%%')" ;
        selection +=")";
        return cr.query(uri,null,selection,null,null);
    }

    protected void onPostExecute(Cursor result){
        ListView lvItems = activity.findViewById(R.id.listview);
        CalendarCursorAdapter todoAdapter = new CalendarCursorAdapter(activity, result);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);

    }
}
