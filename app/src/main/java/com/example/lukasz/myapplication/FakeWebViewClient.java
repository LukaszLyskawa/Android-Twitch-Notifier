package com.example.lukasz.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

/**
 * Created by lukasz on 12.12.15.
 */
public class FakeWebViewClient extends WebViewClient {

    private IWebViewCallback _callback;

    public FakeWebViewClient(IWebViewCallback callback)
    {
        _callback=callback;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().equals("localhost"))
        {

            //http://localhost/?error=access_denied&error_description=The+user+denied+you+access
            Uri parsedUrl = Uri.parse(url);
            if(parsedUrl.getFragment()==null || parsedUrl.getFragment().contains("error"))
            {
                _callback.onError("User denied access");
            }
            else
            {
                _callback.Called(parsedUrl.getFragment());
            }
            return true;
        }
        if(Uri.parse(url).getHost().equals("api.twitch.tv"))
        {
            return false;
        }
        return true;
    }
}
