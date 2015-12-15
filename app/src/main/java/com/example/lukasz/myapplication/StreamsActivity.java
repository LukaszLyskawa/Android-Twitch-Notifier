package com.example.lukasz.myapplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.okhttp.internal.http.RequestException;

public class StreamsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private RequestManager requestManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streams);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



    }


}
