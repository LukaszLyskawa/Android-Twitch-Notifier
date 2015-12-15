package com.example.lukasz.myapplication.TwitchApiJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lukasz on 14.12.15.
 */
public class FollowsLinks {

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @SerializedName("self")
    @Expose
    private String self;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @SerializedName("next")
    @Expose
    private String next;
}
