package com.example.lukasz.myapplication.TwitchApi;

import android.support.annotation.Nullable;

import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;
import com.example.lukasz.myapplication.TwitchApiJson.OAuthResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Url;

/**
 * Created by lukasz on 12.12.15.
 */
public interface ITwitchApiService {
    //https://api.twitch.tv/kraken?oauth_token=z2aype6plt0j34a89y01lebm3uwzcd                                                                         
    @GET("kraken")
    Call<OAuthResponse> getOAuthTokenInfo(@Query("oauth_token") String token);

    @GET("kraken/streams/followed")
    Call<FollowsResponse> getFollowedStreams(@Header("Authorization") String token, @Query("limit") int limit, @Query("offset") int offset, @Query("stream_type") String stream_type);
}
