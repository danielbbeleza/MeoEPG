package com.example.meoepg.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public class Feed {

    @SerializedName("odata.metadata")
    @Expose
    private String metadata;

    @SerializedName("odata.count")
    @Expose
    private String count;

    @SerializedName("value")
    @Expose
    private List<Channel> channels;

    @SerializedName("odata.nextLink")
    @Expose
    private String nextLink;

    public Feed(String metadata, String count, List<Channel> channels, String nextLink) {
        this.metadata = metadata;
        this.count = count;
        this.channels = channels;
        this.nextLink = nextLink;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }
}
