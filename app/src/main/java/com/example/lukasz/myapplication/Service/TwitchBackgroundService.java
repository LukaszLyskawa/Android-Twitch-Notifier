package com.example.lukasz.myapplication.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.lukasz.myapplication.TwitchApi.IRequestManagerCallback;
import com.example.lukasz.myapplication.TwitchApi.RequestManager;
import com.example.lukasz.myapplication.Activities.MainActivity;
import com.example.lukasz.myapplication.R;
import com.example.lukasz.myapplication.Activities.StreamsActivity;
import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;
import com.example.lukasz.myapplication.TwitchApiJson.FollowsStreams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukasz on 26.11.15.
 */
public class TwitchBackgroundService extends IntentService
{
    private SharedPreferences preferences;
    private RequestManager requestManager;

    private IRequestManagerCallback _callback= new IRequestManagerCallback() {

        @Override
        public void onResponse(Object body) {
            Log.e(TwitchBackgroundService.class.getName(), "DataReceived");
            FollowsResponse response = (FollowsResponse) body;
            CreateOrUpdateNotification(response.getTotal(),response.getStreams());
        }

        @Override
        public void onError(Throwable t) {

        }
    };

    public TwitchBackgroundService()
    {
        super(TwitchBackgroundService.class.getName());
        requestManager = new RequestManager();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.e("TwitchBackgroundService", "Service started");
        //DownloadTwitchData();
        String accessToken=preferences.getString("accessToken",null);
        if(accessToken!=null){
            //Log.e("TwitchBackgroundService","accessToken:"+accessToken);
            requestManager.getLiveFollowedStreams(accessToken,_callback);
        }

    }

    private void CreateOrUpdateNotification(int total, List<FollowsStreams> streams)
    {
        List<String> channelNames = new ArrayList<>();

        for (FollowsStreams stream:streams) {
            channelNames.add(stream.getChannel().getName());
        }

        Intent settingsIntent = new Intent(getApplicationContext(),MainActivity.class);

        PendingIntent settingsPendingIntent = PendingIntent.getActivity(getApplicationContext(),0,settingsIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent streamsList = new Intent(getApplicationContext(),StreamsActivity.class);


        PendingIntent streamsPendingIntent = PendingIntent.getActivity(getApplicationContext(),0,streamsList,PendingIntent.FLAG_UPDATE_CURRENT);



        Intent startMainActivity = new Intent(getApplicationContext(),MainActivity.class);
        startMainActivity.setAction(Intent.ACTION_MAIN);
        startMainActivity.addCategory(Intent.CATEGORY_LAUNCHER);

        //Log.e("TwitchBackgroundService","Create Notification Called, total="+total);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_widget);
        remoteViews.setTextViewText(R.id.notificationStreamsCount, "" + total);
        remoteViews.setOnClickPendingIntent(R.id.notificationSettingsButton, settingsPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.buttonOpenStreams,streamsPendingIntent);



        Notification.Builder mNotifyBuilder = new Notification.Builder(this);
        Notification foregroundNote = mNotifyBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        foregroundNote.bigContentView = remoteViews;


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(731768, foregroundNote);


//        List<String> channelNames = new ArrayList<>();
//
//        for (FollowsStreams stream:streams) {
//            channelNames.add(stream.getChannel().getName());
//        }
//
//        //Log.e("TwitchBackgroundService","Create Notification Called, total="+total);
//        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_widget);
//        remoteViews.setTextViewText(R.id.notificationStreamsCount, "" + total);
//
//        Notification.Builder mNotifyBuilder = new Notification.Builder(this);
//        Notification foregroundNote = mNotifyBuilder
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setOngoing(true)
//                .setPriority(Notification.PRIORITY_MAX)
//                .build();
//
//        foregroundNote.bigContentView = remoteViews;
//
////        Notification.Builder mNotifyBuilder = new Notification.Builder(this)
////                .setSmallIcon(R.mipmap.ic_launcher)
////                .setOngoing(true)
////                .setPriority(Notification.PRIORITY_MAX);
////        Notification.InboxStyle foregroundNote = new Notification.InboxStyle(mNotifyBuilder);
////
////        for (String channelName: channelNames) {
////            foregroundNote=foregroundNote.addLine(channelName);
////        }
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(731768, foregroundNote);

    }
}
