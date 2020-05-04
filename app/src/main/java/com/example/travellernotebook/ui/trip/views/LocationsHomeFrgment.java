package com.example.travellernotebook.ui.trip.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.views.PagerAdapter;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsHomeFrgment extends Fragment{

    MainActivity mMainActivity;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.scrnTabs)
    TabLayout scrnTabs;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_locations_home,container,false);
         ButterKnife.bind(this,mView);
          mMainActivity = ((MainActivity) getActivity());
        Toolbar myToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mMainActivity.setSupportActionBar(myToolbar);
        mMainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMainActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        List<Fragment> lst = new ArrayList<>();
        lst.add(new LocationsListFrgment());
        lst.add(new TripsMapFrgment());
        PagerAdapter mPagerAdapter = new PagerAdapter(getChildFragmentManager(),lst);
        pager.setAdapter(mPagerAdapter);
        scrnTabs.setupWithViewPager(pager);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mMainActivity.transitionToFragment(new TripFrgment());
            }
        });
    return mView;
    }



}
