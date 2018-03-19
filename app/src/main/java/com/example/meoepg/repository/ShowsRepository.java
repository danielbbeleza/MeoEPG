package com.example.meoepg.repository;

import com.example.meoepg.NetworkReachability;
import com.example.meoepg.model_objects.Card;

import java.util.List;

/**
 * Created by danielbeleza on 16/03/2018.
 */

public interface ShowsRepository {

    interface CallbackGetData {
        void onSuccess(List<Card> channelList);

        void onError();
    }

    interface CallbackGetNextPageUrl {
        void onSuccess(String url);

        void onError();
    }

    void getData(String nextURL, NetworkReachability networkReachability, CallbackGetData callbackGetData);

    void getNextPageUrl(String nextURL, NetworkReachability networkReachability, CallbackGetNextPageUrl callbackGetNextPageUrl);
}
