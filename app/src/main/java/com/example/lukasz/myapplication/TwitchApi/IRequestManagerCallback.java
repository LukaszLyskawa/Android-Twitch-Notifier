package com.example.lukasz.myapplication.TwitchApi;

import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;
import com.example.lukasz.myapplication.TwitchApiJson.OAuthResponse;

/**
 * Created by lukasz on 12.12.15.
 */
public interface IRequestManagerCallback {
    void onResponse(Object body);

    void onError(Throwable t);
}
