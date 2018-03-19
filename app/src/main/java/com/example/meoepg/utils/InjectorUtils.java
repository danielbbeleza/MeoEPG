package com.example.meoepg.utils;

import com.example.meoepg.navigation.CustomViewModelFactory;
import com.example.meoepg.repository.ShowsRepositoryImpl;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public class InjectorUtils {

    public static CustomViewModelFactory provideCustomViewModelFactory(){
        ShowsRepositoryImpl repository = new ShowsRepositoryImpl();
        return new CustomViewModelFactory(repository);
    }
}
