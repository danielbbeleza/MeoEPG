package com.example.meoepg;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by danielbeleza on 18/03/2018.
 */

public class NetworkReachability {

    private static Context mContext;

    private static final String LOG_TAG = NetworkReachability.class.getName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NetworkReachability sInstance;

    public synchronized static NetworkReachability getInstance(Context context) {

        Log.d(LOG_TAG, "Getting the repository");

        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkReachability();
                mContext = context;
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }


    public boolean checkInternetStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo == null || activeNetworkInfo.isConnected();
    }
}

