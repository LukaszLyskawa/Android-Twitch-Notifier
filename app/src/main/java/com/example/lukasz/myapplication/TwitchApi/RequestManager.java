package com.example.lukasz.myapplication.TwitchApi;

import android.util.Log;

import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;
import com.example.lukasz.myapplication.TwitchApiJson.FollowsStream_Type;
import com.example.lukasz.myapplication.TwitchApiJson.OAuthResponse;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by lukasz on 12.12.15.
 */
public class RequestManager {

    private static final String _url="https://api.twitch.tv/kraken";

    private IRequestManagerCallback _callback;



    private Callback<OAuthResponse> oAuthCallback = new Callback<OAuthResponse>() {
        @Override
        public void onResponse(Response<OAuthResponse> response, Retrofit retrofit) {
            _callback.onResponse(response.body());
        }

        @Override
        public void onFailure(Throwable t) {
            _callback.onError(t);
        }
    };

    private Callback<FollowsResponse> followsResponseCallback = new Callback<FollowsResponse>() {
        @Override
        public void onResponse(Response<FollowsResponse> response, Retrofit retrofit) {
            Log.e("RetrofitCallback",response.message());
            _callback.onResponse(response.body());
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("RetrofitCallbackError",t.getMessage());
            _callback.onError(t);
        }
    };



    public void getDataFromOAuth(String token, IRequestManagerCallback callback)
    {
        _callback=callback;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITwitchApiService service = retrofit.create(ITwitchApiService.class);
        Call<OAuthResponse> call = service.getOAuthTokenInfo(token);
        call.enqueue(oAuthCallback);
    }

    public void getLiveFollowedStreams(String token, IRequestManagerCallback callback)
    {
        _callback=callback;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITwitchApiService service = retrofit.create(ITwitchApiService.class);
        Log.e("Token in ReqMan", token);
        Call<FollowsResponse> call = service.getFollowedStreams("OAuth "+token,100,0, FollowsStream_Type.Live);
        call.enqueue(followsResponseCallback);
    }
}
