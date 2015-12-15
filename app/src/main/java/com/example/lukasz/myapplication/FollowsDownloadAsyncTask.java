package com.example.lukasz.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lukasz.myapplication.TwitchApi.IRequestManagerCallback;
import com.example.lukasz.myapplication.TwitchApi.RequestManager;
import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;

/**
 * Created by lukasz on 15.12.15.
 */
public class FollowsDownloadAsyncTask extends AsyncTask<String,Void,Void> {

    private IFollowsDownloadCallback callback;

    public FollowsDownloadAsyncTask(IFollowsDownloadCallback callback) {
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(String... token) {
        Log.i("AsyncTask","Start");
        RequestManager requestManager = new RequestManager();
        requestManager.getLiveFollowedStreams(token[0], new IRequestManagerCallback() {
            @Override
            public void onResponse(Object body) {
                Log.i("AsyncTaskCallback",((FollowsResponse)body!=null)?"Good response":"null response");
                callback.onResponse((FollowsResponse)body);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
        return null;
    }
}
