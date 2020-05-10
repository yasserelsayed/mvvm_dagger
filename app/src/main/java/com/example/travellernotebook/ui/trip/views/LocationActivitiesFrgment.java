package com.example.travellernotebook.ui.trip.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.views.PagerAdapter;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.ActivityViewModel;
import com.example.travellernotebook.ui.trip.viewModels.LocationViewModel;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationActivitiesFrgment extends Fragment{

    MainActivity mMainActivity;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.scrnTabs)
    TabLayout scrnTabs;
    @Inject
    TripViewModelsFactory mTripViewModelsFactory;
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
        mMainActivity.mAppComponent.inject(this);
        mLocationViewModel = new ViewModelProvider(mMainActivity,mTripViewModelsFactory).get(LocationViewModel.class);
        List<Fragment> lst = new ArrayList<>();
        lst.add(new ActivitiesListFrgment());
        lst.add(new PhotosGalleryFrgment());
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
                if (mMainActivity.activeTripLocation != null)
                    mLocationViewModel.removeTripLocation(mMainActivity.activeTripLocation);
                mMainActivity.onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
