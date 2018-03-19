package com.example.meoepg.navigation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.meoepg.NetworkReachability;
import com.example.meoepg.model_objects.Card;
import com.example.meoepg.repository.ShowsRepository;
import com.example.meoepg.repository.ShowsRepositoryImpl;

import java.util.List;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public class ShowsViewModel extends ViewModel {

    private MutableLiveData<List<Card>> cardLiveData = new MutableLiveData<>();

    private MutableLiveData<String> urlLiveData = new MutableLiveData<>();

    private ShowsRepositoryImpl mRepository;

    public ShowsViewModel(ShowsRepositoryImpl repository){
        this.mRepository = repository;
    }

    // Get channels list
    public LiveData<List<Card>> getShowsData(String nextURL, NetworkReachability networkReachability){

        mRepository.getData(nextURL, networkReachability, new ShowsRepository.CallbackGetData() {
            @Override
            public void onSuccess(List<Card> channelList) {
                cardLiveData.postValue(channelList);
            }

            @Override
            public void onError() {
                // on connection error
            }
        });

        return cardLiveData;
    }

    public LiveData<String> getNextPageUrl(String nextURL, NetworkReachability networkReachability){

        mRepository.getNextPageUrl(nextURL, networkReachability, new ShowsRepository.CallbackGetNextPageUrl() {
            @Override
            public void onSuccess(String url) {
                urlLiveData.postValue(url);
            }

            @Override
            public void onError() {
                // on connection error
            }
        });

        return urlLiveData;
    }

}
