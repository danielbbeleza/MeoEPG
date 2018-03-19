package com.example.meoepg.model_objects;

import android.support.annotation.NonNull;

/**
 * Created by danielbeleza on 16/03/2018.
 */

public class Card implements Comparable<Card>{

    private int orderNo;

    private EPG epg;

    private Channel channel;

    private String showImage;

    public Card(int orderNo, EPG epg, Channel channel, String showImage) {
        this.orderNo = orderNo;
        this.epg = epg;
        this.channel = channel;
        this.showImage = showImage;
    }

    public EPG getEpg() {
        return epg;
    }

    public void setEpg(EPG epg) {
        this.epg = epg;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    // Order cards
    @Override
    public int compareTo(@NonNull Card card) {
        return orderNo - card.orderNo;
    }
}
