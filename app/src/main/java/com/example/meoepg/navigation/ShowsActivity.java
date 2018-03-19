package com.example.meoepg.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.meoepg.R;

public class ShowsActivity extends AppCompatActivity {

    public static String FRAGMENT_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment =fragmentManager.findFragmentById(R.id.frame_layout_navigation_activity);

        if(fragment == null){
            fragment = new ShowsFragment();
            FRAGMENT_TAG = fragment.getTag();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_navigation_activity, fragment)
                    .commit();
        }
    }
}
