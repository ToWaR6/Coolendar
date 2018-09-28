package com.tps.hasselhoff.coolendar;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarCursorAdapter extends CursorAdapter {

    public CalendarCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.item_calendar,parent,false);
    }

    public void bindView(View view, final Context context, final Cursor cursor){
        TextView tvDate = view.findViewById(R.id.date);
        TextView tvTitle = view.findViewById(R.id.titleDate);
        LinearLayout item = view.findViewById(R.id.item);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART)));
        String date = simpleDateFormat.format(cal.getTime());
        tvDate.setText(date);
        tvTitle.setText(title);
        final long eventID = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events._ID));
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(uri);
                context.startActivity(intent);
            }
        });
    }
}
