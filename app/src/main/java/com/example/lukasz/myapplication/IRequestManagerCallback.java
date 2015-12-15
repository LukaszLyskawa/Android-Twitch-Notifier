package com.example.lukasz.myapplication;

import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;
import com.example.lukasz.myapplication.TwitchApiJson.OAuthResponse;

/**
 * Created by lukasz on 12.12.15.
 */
public interface IRequestManagerCallback {
    void onOAuthResponse(OAuthResponse body);

    void onLiveFollowedStreamResponse(FollowsResponse body);

    void onError(Throwable t);
}
