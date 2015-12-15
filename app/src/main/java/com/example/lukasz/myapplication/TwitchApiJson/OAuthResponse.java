package com.example.lukasz.myapplication.TwitchApiJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lukasz on 12.12.15.
 */
public class OAuthResponse {
    public OAuthLinks getLinks() {
        return links;
    }

    public void setLinks(OAuthLinks links) {
        this.links = links;
    }

    @SerializedName("_links")
    @Expose
    private OAuthLinks links;

    public OAuthToken getToken() {
        return token;
    }

    public void setToken(OAuthToken token) {
        this.token = token;
    }
    @SerializedName("token")
    @Expose
    private OAuthToken token;
}
