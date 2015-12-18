package com.example.lukasz.myapplication.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.lukasz.myapplication.FollowsDownloadAsyncTask;
import com.example.lukasz.myapplication.IFollowsDownloadCallback;
import com.example.lukasz.myapplication.MyArrayAdapter;
import com.example.lukasz.myapplication.R;
import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;

import java.util.Timer;
import java.util.TimerTask;


public class StreamsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private ListView listView;
    private FollowsResponse response;
    private IFollowsDownloadCallback callback = new IFollowsDownloadCallback() {
        @Override
        public void onResponse(FollowsResponse data) {
            response=data;
            MyArrayAdapter adapter = new MyArrayAdapter(StreamsActivity.this,R.layout.item_layout);
            adapter.setItems(data.getStreams());
            listView.setAdapter(adapter);
        }

        @Override
        public void onError(Throwable t) {

        }
    };

    private ListView.OnItemClickListener itemClickListener= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String url = response.getStreams().get(position).getChannel().getUrl();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streams);
        listView= (ListView) findViewById(R.id.streamsListView);
        listView.setOnItemClickListener(itemClickListener);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        repeatAsyncTask(10000);

        Log.i("StreamsActivity","Created");
    }


    public void repeatAsyncTask(int timeout) {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        FollowsDownloadAsyncTask performBackgroundTask = new FollowsDownloadAsyncTask(callback);
                        performBackgroundTask.execute(preferences.getString("accessToken", null));
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, timeout);
    }
}
