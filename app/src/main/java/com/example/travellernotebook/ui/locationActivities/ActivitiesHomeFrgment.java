package com.example.travellernotebook.ui.locationActivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.factory.LocationActivityFactory;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.MainFragment;
import com.example.travellernotebook.ui.base.views.PagerAdapter;
import com.example.travellernotebook.factory.TripFactory;
import com.example.travellernotebook.ui.locationActivities.viewModels.LocationViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesHomeFrgment extends MainFragment {

    MainActivity mMainActivity;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.scrnTabs)
    TabLayout scrnTabs;
    @Inject
    LocationActivityFactory mLocationActivityFactory;
    LocationViewModel mLocationViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_activities_home,container,false);
         ButterKnife.bind(this,mView);
          mMainActivity = ((MainActivity) getActivity());
        Toolbar mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mMainActivity.setSupportActionBar(mToolbar);
        mMainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMainActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        mMainActivity.mMainActivityComponent.inject(this);
        mLocationViewModel = new ViewModelProvider(mMainActivity, mLocationActivityFactory).get(LocationViewModel.class);
        List<MainFragment> lst = new ArrayList<>();
        MainFragment mMainFragment = new ActivitiesListFrgment();
        mMainFragment.setScreenTitle(getString(R.string.txt_list));
        lst.add(mMainFragment);
        mMainFragment = new PhotosGalleryFrgment();
        mMainFragment.setScreenTitle(getString(R.string.txt_media));
        lst.add(mMainFragment);
        setHasOptionsMenu(true);
        PagerAdapter mPagerAdapter = new PagerAdapter(getChildFragmentManager(),lst);
        pager.setAdapter(mPagerAdapter);
        scrnTabs.setupWithViewPager(pager);
        mToolbar.setTitle(getString(R.string.txt_location_activities));
    return mView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.location_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuRemove: {
                if (mMainActivity.activeLocation != null) {
                    mLocationViewModel.removeTripLocation(mMainActivity.activeLocation);
                }
                mMainActivity.onBackPressed();
                return true;
            }
            case android.R.id.home : {
                mMainActivity.onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
