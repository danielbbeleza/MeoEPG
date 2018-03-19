package com.example.meoepg.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public class Channel {

    @SerializedName("Id")
    @Expose
    private int ID;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("ChannelPosition")
    @Expose
    private int channelPosition;

    @SerializedName("CallLetter")
    @Expose
    private String callLetter;

    public Channel(int ID, String title, int channelPosition, String callLetter) {
        this.ID = ID;
        this.title = title;
        this.channelPosition = channelPosition;
        this.callLetter = callLetter;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChannelPosition() {
        return channelPosition;
    }

    public void setChannelPosition(int channelPosition) {
        this.channelPosition = channelPosition;
    }

    public String getCallLetter() {
        return callLetter;
    }

    public void setCallLetter(String callLetter) {
        this.callLetter = callLetter;
    }
}
