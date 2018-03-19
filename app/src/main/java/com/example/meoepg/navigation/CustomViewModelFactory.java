package com.example.meoepg.navigation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.meoepg.repository.ShowsRepositoryImpl;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public class CustomViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ShowsRepositoryImpl mRepository;

    public CustomViewModelFactory(ShowsRepositoryImpl repository){
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ShowsViewModel.class)){
            return (T) new ShowsViewModel(mRepository);
        } else {
            throw new IllegalArgumentException("View model not found");
        }

    }
}
