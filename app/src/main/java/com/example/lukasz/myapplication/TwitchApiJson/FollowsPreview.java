package com.example.lukasz.myapplication.TwitchApiJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lukasz on 15.12.15.
 */
public class FollowsPreview {
    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @SerializedName("medium")
    @Expose
    private String medium;
}
