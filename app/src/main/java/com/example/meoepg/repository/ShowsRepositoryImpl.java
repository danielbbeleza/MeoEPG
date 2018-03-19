package com.example.meoepg.repository;

import android.util.Log;

import com.example.meoepg.NetworkReachability;
import com.example.meoepg.model_objects.Card;
import com.example.meoepg.model_objects.Channel;
import com.example.meoepg.model_objects.EPG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public class ShowsRepositoryImpl implements ShowsRepository {

    private static final String LOG_TAG = ShowsRepositoryImpl.class.getName();

    private static final int CURRENT_SHOW =0;

    final WebServicesImpl webServices = new WebServicesImpl();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static ShowsRepositoryImpl sInstance;

    public synchronized static ShowsRepositoryImpl getInstance() {

        Log.d(LOG_TAG, "Getting the repository");

        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ShowsRepositoryImpl();

                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    @Override
    public void getNextPageUrl(String nextURL, NetworkReachability networkReachability, CallbackGetNextPageUrl callbackGetNextPageUrl) {
        getWebServiceNextPageUrl(nextURL, networkReachability, callbackGetNextPageUrl);
    }

    @Override
    public void getData(String nextURL, NetworkReachability networkReachability, CallbackGetData callbackGetData) {
        getWebServiceData(nextURL, networkReachability, callbackGetData);
    }

    private void getWebServiceNextPageUrl(String nextUrl, NetworkReachability networkReachability, final CallbackGetNextPageUrl callbackGetNextPageUrl){
        // get list of all channels from current page
        webServices.getURL(nextUrl, networkReachability, new WebServices.CallbackURL() {
            @Override
            public void onSuccess(String url) {
                callbackGetNextPageUrl.onSuccess(url);
            }

            @Override
            public void onError() {
                callbackGetNextPageUrl.onError();
            }
        });

    }


    private void getWebServiceData(String currentURL,NetworkReachability networkReachability, final CallbackGetData callbackGetData) {

        final List<Card> mCardList = new ArrayList<>();

        // get list of all channels from current page
        webServices.getChannels(currentURL, networkReachability, new WebServices.CallbackChannels() {
            @Override
            public void onChannelSuccess(final List<Channel> channelList) {

                // For each channel get respective shows
                for (final Channel channel : channelList) {

                    webServices.getShows(channel, new WebServices.CallbackShows() {
                        @Override
                        public void onShowSuccess(final EPG epg) {

                            mCardList.add(new Card(channel.getChannelPosition(), epg, channel, createImageURL(epg)));

                            if (mCardList.size() == channelList.size()) {

                                Collections.sort(mCardList);
                                callbackGetData.onSuccess(mCardList);
                            }
                        }

                        @Override
                        public void onError() {
                            callbackGetData.onError();
                        }
                    });
                }
            }

            @Override
            public void onError() {
                callbackGetData.onError();
            }
        });
    }


    private String formatShowTitle(String showTitle) {
        showTitle = showTitle.replace(" ", "+");
        return showTitle;
    }

    // CUSTOM BASE URL (1) + SHOW TITLE + CUSTOM BASE URL (2) + CALL LETTER + CUSTOM BASE URL (3)
    // http://proxycache.app.iptv.telecom.pt:8080/eemstb/ImageHandler.ashx?evTitle=[SHOW_TITLE]&chCallLetter=[CALL_LETTER]&profile=16_9&width=320
    private String createImageURL(EPG epg) {

        String baseUrl1 = "http://proxycache.app.iptv.telecom.pt:8080/eemstb/ImageHandler.ashx?evTitle=";

        String baseUrl2 = "&chCallLetter=";

        String baseUrl3 = "&profile=16_9&width=320";

        // replace blank spaces for "+" signs. Get index
        String showTitle = formatShowTitle(epg.getShowList().get(CURRENT_SHOW).getTitle());

        String showCallLetter = epg.getShowList().get(CURRENT_SHOW).getCallLetter();

        return baseUrl1 + showTitle + baseUrl2 + showCallLetter + baseUrl3;
    }

}






