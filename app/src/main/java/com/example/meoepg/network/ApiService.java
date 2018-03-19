package com.example.meoepg.network;

import com.example.meoepg.model_objects.EPG;
import com.example.meoepg.model_objects.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public interface ApiService {

    @GET("catalog/v7/Channels?UserAgent=AND&$filter=substringof(%27MEO_Mobile%27,AvailableOnChannels)%20and%20IsAdult%20eq%20false&$orderby=ChannelPosition%20asc&$inlinecount=allpages")
    Call<Feed> getChannels();

    @GET
    Call<EPG> getShow(@Url String callLetter);

    @GET()
    Call<Feed> getNextPageChannels(@Url String nextPageUrl);


}
