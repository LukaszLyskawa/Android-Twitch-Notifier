package com.example.lukasz.myapplication.TwitchApiJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lukasz on 12.12.15.
 */
public class OAuthToken {
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("authorization")
    @Expose
    private OAuthAuthorization authorization;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public OAuthAuthorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(OAuthAuthorization authorization) {
        this.authorization = authorization;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}