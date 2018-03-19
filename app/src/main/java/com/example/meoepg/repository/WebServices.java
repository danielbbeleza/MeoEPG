package com.example.meoepg.repository;

import com.example.meoepg.NetworkReachability;
import com.example.meoepg.model_objects.Channel;
import com.example.meoepg.model_objects.EPG;

import java.util.List;

/**
 * Created by danielbeleza on 16/03/2018.
 */

public interface WebServices {

    interface CallbackChannels{
        void onChannelSuccess(List<Channel> channelList);

        void onError();
    }

    interface CallbackShows{
        void onShowSuccess(EPG epg);

        void onError();
    }

    interface CallbackURL{
        void onSuccess(String url);

        void onError();
    }

    void getChannels(String nextURL, NetworkReachability networkReachability,CallbackChannels callbackGetData);

    void getShows(Channel channel, CallbackShows callbackShows);

    void getURL(String nextURL, NetworkReachability networkReachability, CallbackURL callbackURL);
}
