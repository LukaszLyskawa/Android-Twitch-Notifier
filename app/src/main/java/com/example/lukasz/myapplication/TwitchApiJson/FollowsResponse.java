package com.example.lukasz.myapplication.TwitchApiJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lukasz on 14.12.15.
 */
public class FollowsResponse {

    public FollowsLinks getLinks() {
        return links;
    }

    public void setLinks(FollowsLinks links) {
        this.links = links;
    }

    @SerializedName("_links")
    @Expose
    private FollowsLinks links;

    public List<FollowsStreams> getStreams() {
        return streams;
    }

    public void setStreams(List<FollowsStreams> streams) {
        this.streams = streams;
    }

    @SerializedName("streams")
    @Expose
    private List<FollowsStreams> streams;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @SerializedName("_total")
    @Expose
    private int total;
}
