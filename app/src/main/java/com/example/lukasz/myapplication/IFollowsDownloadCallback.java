package com.example.lukasz.myapplication;

import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;

/**
 * Created by lukasz on 15.12.15.
 */
public interface IFollowsDownloadCallback {
    void onResponse(FollowsResponse data);
    void onError(Throwable t);
}
