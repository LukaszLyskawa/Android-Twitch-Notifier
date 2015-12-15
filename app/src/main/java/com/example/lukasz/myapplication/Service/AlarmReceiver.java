package com.example.lukasz.myapplication.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lukasz on 26.11.15.
 */
public class AlarmReceiver extends BroadcastReceiver
{
    public static final int REQUEST_CODE=731768;
    public static final String ACTION= "com.example.lukasz.myapplication.alarm";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent i= new Intent(context,TwitchBackgroundService.class);
        i.putExtra("foo","bar");
        context.startService(i);


        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis() + 30000, pendingIntent);
    }
}
