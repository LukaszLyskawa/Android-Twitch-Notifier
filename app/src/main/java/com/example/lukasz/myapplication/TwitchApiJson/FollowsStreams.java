package com.example.lukasz.myapplication.TwitchApiJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lukasz on 14.12.15.
 */
public class FollowsStreams {
    public FollowsChannel getChannel() {
        return channel;
    }

    public void setChannel(FollowsChannel channel) {
        this.channel = channel;
    }

    @SerializedName("channel")
    @Expose
    private FollowsChannel channel;

    public FollowsPreview getPreview() {
        return preview;
    }

    public void setPreview(FollowsPreview preview) {
        this.preview = preview;
    }

    @SerializedName("preview")
    @Expose
    private FollowsPreview preview;

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    @SerializedName("viewers")
    @Expose
    private int viewers;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @SerializedName("game")
    @Expose
    private String game;

}
