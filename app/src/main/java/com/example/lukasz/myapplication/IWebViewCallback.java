package com.example.lukasz.myapplication;

import android.net.Uri;

/**
 * Created by lukasz on 12.12.15.
 */
public interface IWebViewCallback {

    void Called(String url);
    void onError(String fragment);

}
