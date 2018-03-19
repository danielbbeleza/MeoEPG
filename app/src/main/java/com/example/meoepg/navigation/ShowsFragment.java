package com.example.meoepg.navigation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.meoepg.NetworkReachability;
import com.example.meoepg.R;
import com.example.meoepg.databinding.FragmentNavigationBinding;
import com.example.meoepg.model_objects.Card;
import com.example.meoepg.utils.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielbeleza on 15/03/2018.
 */

public class ShowsFragment extends Fragment {

    private FragmentNavigationBinding mBinding;

    private ShowsRecyclerViewAdapter mRecyclerAdapter;

    private ShowsViewModel mShowsViewModel;

    private String mNextPageURL;

    private String mCurrentURL;

    private List<String> mPageHistoryList = new ArrayList<>();

    private int mCurrentURLPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_navigation, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CustomViewModelFactory factory = InjectorUtils.provideCustomViewModelFactory();

        mShowsViewModel = ViewModelProviders.of(getActivity(), factory).get(ShowsViewModel.class);

        setRecyclerViewConfigs();

        setObservables();

        setOnClickListeners();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.channels_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next_page:

                // add current url to list so that when "previous" button is pressed, the url can be used again without having to get it
                // from web service again
                addUrlToHistory(mCurrentURL);

                mCurrentURL = mNextPageURL;

                setObservables();

                break;

            case R.id.previous_page:

                mCurrentURL = getPreviousUrlHistory();

                // update list size/content and "mCurrentURLPosition" variable value
                updateListAndURLPosition();

                setObservables();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setOnClickListeners(){
        mBinding.refreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setObservables();
            }
        });
    }

    private NetworkReachability getNetworkReachabilityInstance(){
        return NetworkReachability.getInstance(getContext().getApplicationContext());
    }

    private void setObservables() {

        // get all data in current page
        mShowsViewModel.getShowsData(mCurrentURL, getNetworkReachabilityInstance()).observeForever(new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {

                if (cards != null) {
                    setRecyclerViewVisibility(false);
                    mRecyclerAdapter.updateData(cards);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), getContext().getApplicationContext().
                            getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    setRecyclerViewVisibility(true);
                }
            }
        });

        // Observe/get next page url (string)
        mShowsViewModel.getNextPageUrl(mCurrentURL, getNetworkReachabilityInstance()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String nextPageURL) {
                mNextPageURL = nextPageURL;
            }
        });

    }

    private void setRecyclerViewConfigs() {

        mBinding.navigationRecyclerView.setHasFixedSize(true);

        mRecyclerAdapter = new ShowsRecyclerViewAdapter(getContext().getApplicationContext());

        mBinding.navigationRecyclerView.setAdapter(mRecyclerAdapter);

        mBinding.navigationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addUrlToHistory(String currentURL) {

        if(currentURL != null) {

            mPageHistoryList.add(currentURL);

            mCurrentURLPosition = mPageHistoryList.size()-1;
        }

    }

    private String getPreviousUrlHistory() {

            if(mPageHistoryList.size()>0) {
                return mPageHistoryList.get(mCurrentURLPosition);
            } else {
                return null;
            }
    }

    private void updateListAndURLPosition(){

        if(mPageHistoryList.size()>0) {
            mPageHistoryList.remove(mCurrentURLPosition);
            mCurrentURLPosition = mPageHistoryList.size()-1;

        }
    }

    private void setRecyclerViewVisibility(boolean isRecyclerViewEmpty){
        if(isRecyclerViewEmpty){
            mBinding.navigationRecyclerView.setVisibility(View.GONE);
            mBinding.refreshIcon.setVisibility(View.VISIBLE);
        } else {
            mBinding.navigationRecyclerView.setVisibility(View.VISIBLE);
            mBinding.refreshIcon.setVisibility(View.GONE);

        }
    }
}
