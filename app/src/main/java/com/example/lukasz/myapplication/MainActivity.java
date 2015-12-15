package com.example.lukasz.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukasz.myapplication.TwitchApiJson.FollowsResponse;
import com.example.lukasz.myapplication.TwitchApiJson.OAuthResponse;

import java.net.CookieManager;

public class MainActivity extends Activity
{

    private Button _buttonTwitchConnect;
    private Button _buttonTwitchLogout;
    private TextView _textviewUsername;
    private SharedPreferences preferences;
    private String _accessToken=null;
    private RequestManager requestManager;
    private RelativeLayout _usernameLayout;


    private String _username=null;

    private Dialog webDialog;

    private Switch _switchSchedule;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        requestManager=new RequestManager();

        _buttonTwitchConnect= (Button) findViewById(R.id.buttonTwitchConnect);
        _buttonTwitchConnect.setOnClickListener(_buttonTwitchConnectListener);
        _buttonTwitchLogout= (Button) findViewById(R.id.buttonTwitchLogout);
        _buttonTwitchLogout.setOnClickListener(_buttonTwitchLogoutListener);
        _textviewUsername = (TextView) findViewById(R.id.textviewConnectedUsername);
        _usernameLayout=(RelativeLayout) findViewById(R.id.layoutUsername);
        _switchSchedule = (Switch)findViewById(R.id.switchRunning);
        _switchSchedule.setChecked(isScheduled());
        _switchSchedule.setOnCheckedChangeListener(checkedChangeListener);
        if(_switchSchedule.isChecked())
        {
            startSchedule();
        }


        _accessToken= preferences.getString("accessToken", null);
        getUsername();

    }


    private IWebViewCallback _callback = new IWebViewCallback() {
        @Override
        public void Called(String url) {

            String accessToken = url.split("&")[0].split("=")[1];
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("accessToken", accessToken);
            editor.commit();
            _accessToken=preferences.getString("accessToken",null);
            getUsername();
            webDialog.dismiss();
        }

        @Override
        public void onError(String message) {

            //http://localhost/?error=access_denied&error_description=The+user+denied+you+access

            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
            webDialog.dismiss();
        }
    };

    private void getUsername() {
        if(_accessToken!=null)
        {
            requestManager.getDataFromOAuth(_accessToken, new IRequestManagerCallback() {
                @Override
                public void onOAuthResponse(final OAuthResponse body) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _username=body.getToken().getUserName();
                            _textviewUsername.setText(_username);
                            _usernameLayout.setVisibility(RelativeLayout.VISIBLE);
                        }
                    });
                }

                @Override
                public void onLiveFollowedStreamResponse(FollowsResponse body) {

                }

                @Override
                public void onError(Throwable t) {

                }
            });

        }
    }

    private Switch.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                startSchedule();
            }
            else {
                stopSchedule();
            }
        }
    };

    private View.OnClickListener _buttonTwitchConnectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            webDialog = new Dialog(MainActivity.this);
            webDialog.setContentView(R.layout.web_dialog);

            android.webkit.CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {

                }
            });

            WebView wb = (WebView) webDialog.findViewById(R.id.webView);
            wb.getSettings().setJavaScriptEnabled(true);
            wb.setWebViewClient(new FakeWebViewClient(_callback));
            wb.loadUrl("https://api.twitch.tv/kraken/oauth2/authorize?response_type=token&client_id=rj9uoo27zo1h5qehdcfderm4v87avur&redirect_uri=http://localhost&scope=user_read");
            webDialog.setTitle("Connect with Twitch");
            webDialog.setCancelable(true);
            webDialog.show();

        }
    };
    private View.OnClickListener _buttonTwitchLogoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("accessToken");
            editor.commit();
            _username=null;
            _textviewUsername.setText("");
            _usernameLayout.setVisibility(RelativeLayout.GONE);
        }
    };

    private void startSchedule()
    {
        Log.e("SCHEDULE","Started");

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("scheduled",true);

        editor.commit();

        Intent intent = new Intent(getApplicationContext(),AlarmReceiver.class);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis(),pendingIntent);
    }
    private void stopSchedule()
    {
        Log.e("SCHEDULE","Stopped");

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("scheduled", false);

        editor.commit();

        Intent intent = new Intent(getApplicationContext(),AlarmReceiver.class);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }
    private boolean isScheduled()
    {
        return preferences.getBoolean("scheduled",false);
    }

}
