package com.example.meoepg.repository;

import android.util.Log;

import com.example.meoepg.NetworkReachability;
import com.example.meoepg.model_objects.Channel;
import com.example.meoepg.model_objects.EPG;
import com.example.meoepg.model_objects.Feed;
import com.example.meoepg.network.ApiService;
import com.example.meoepg.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meoepg.network.RetrofitClient.BASE_URL;

/**
 * Created by danielbeleza on 16/03/2018.
 */

public class WebServicesImpl implements WebServices{

    public final String LOG_TAG = getClass().getName();
    private ApiService mApiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);;

    @Override
    public void getChannels(String currentURL, NetworkReachability networkReachability, CallbackChannels callbackChannel) {
        getServiceChannels(currentURL, networkReachability, callbackChannel);
    }

    @Override
    public void getShows(Channel channel, CallbackShows callbackShows) {
        getServiceShows(callbackShows, channel);
    }

    @Override
    public void getURL(String nextUrl, NetworkReachability networkReachability, CallbackURL callbackURL) {
        getServiceNextURL(nextUrl, networkReachability, callbackURL);
    }

    /************************************************* PRIVATE METHODS *************************************************/

    /** GET NEXT URL **/
    private void getServiceNextURL(String nextURL, final NetworkReachability networkReachability, final CallbackURL callbackURL){

        if(nextURL ==null) {
            mApiService.getChannels().enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(Call<Feed> call, Response<Feed> response) {
                    if (response.isSuccessful()) {
                        callbackURL.onSuccess(response.body().getNextLink());
                    }
                }

                @Override
                public void onFailure(Call<Feed> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            mApiService.getNextPageChannels(nextURL).enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(Call<Feed> call, Response<Feed> response) {
                    if (response.isSuccessful()) {
                        callbackURL.onSuccess(response.body().getNextLink());
                    }
                }

                @Override
                public void onFailure(Call<Feed> call, Throwable t) {
                    t.printStackTrace();
                    if(networkReachability.checkInternetStatus()) {
                        callbackURL.onError();
                    }
                }
            });
        }
    }

    private void getServiceShows(final CallbackShows callbackShows, Channel channel){

        String url = createShowURL(channel.getCallLetter());

        mApiService.getShow(url).enqueue(new Callback<EPG>() {
            @Override
            public void onResponse(Call<EPG> call, Response<EPG> response) {
                if(response.isSuccessful()) {
                    callbackShows.onShowSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<EPG> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getServiceChannels(String nextURL, final NetworkReachability networkReachability, final CallbackChannels callbackGetData) {

        if(nextURL == null) {

            mApiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);
            mApiService.getChannels().enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(Call<Feed> call, Response<Feed> response) {
                    if (response.isSuccessful()) {
                        callbackGetData.onChannelSuccess(response.body().getChannels());
                    } else {
                        Log.d(LOG_TAG, response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Feed> call, Throwable t) {
                    t.printStackTrace();
                    if(networkReachability.checkInternetStatus()) {
                        callbackGetData.onError();
                    }
                }
            });
        } else {
            mApiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);
            mApiService.getNextPageChannels(nextURL).enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(Call<Feed> call, Response<Feed> response) {
                    if (response.isSuccessful()) {
                        callbackGetData.onChannelSuccess(response.body().getChannels());
                    } else {
                        Log.d(LOG_TAG, response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Feed> call, Throwable t) {
                    t.printStackTrace();
                    callbackGetData.onError();
                }
            });
        }
    }

    private String createShowURL(String callLetter){

        String filter = "http://ott.online.meo.pt/Program/v7/Programs/NowAndNextLiveChannelPrograms?UserAgent=" +
                "AND&$filter=CallLetter%20eq%20%27" + callLetter + "%27&$orderby=StartDate%20asc%27&$orderby=StartDate%20asc";

        return filter;
    }
}
