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
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class LocationsHomeFrgment extends MainFragment {

    MainActivity mMainActivity;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.scrnTabs)
    TabLayout scrnTabs;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;
    @Inject
    LocationActivityFactory mLocationActivityFactory;
    TripViewModel mTripViewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_locations_home,container,false);
         ButterKnife.bind(this,mView);
          mMainActivity = ((MainActivity) getActivity());
        Toolbar mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mMainActivity.setSupportActionBar(mToolbar);
        mMainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMainActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        mMainActivity.mMainActivityComponent.inject(this);
        mTripViewModel = new ViewModelProvider(mMainActivity, mLocationActivityFactory).get(TripViewModel.class);
        List<MainFragment> lst = new ArrayList<>();
        MainFragment mMainFragment = new LocationsListFrgment();
        mMainFragment.setScreenTitle(getString(R.string.txt_list));
        lst.add(mMainFragment);
         mMainFragment = new LocationsMapFrgment();
        mMainFragment.setScreenTitle(getString(R.string.txt_map));
        lst.add(mMainFragment);
        setHasOptionsMenu(true);
        PagerAdapter mPagerAdapter = new PagerAdapter(getChildFragmentManager(),lst);
        pager.setAdapter(mPagerAdapter);
        scrnTabs.setupWithViewPager(pager);
        if(mMainActivity.activeTrip!=null)
        mToolbar.setTitle(mMainActivity.activeTrip.getTripName());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.transitionToFragment(new LocationFrgment());
            }
        });
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
                if (mMainActivity.activeTrip != null)
                    mTripViewModel.removeTrip(mMainActivity.activeTrip);
                mMainActivity.onBackPressed();
                return true;
            }
            case android.R.id.home : {
                mMainActivity.onBackPressed();
                return true;
            }default:
                return super.onOptionsItemSelected(item);
        }

    }

}
