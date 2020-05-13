package com.example.travellernotebook.ui.trip.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.views.PagerAdapter;
import com.example.travellernotebook.ui.profile.views.ProfileFrgment;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFrgment extends Fragment{

    MainActivity mMainActivity;
    @Inject
    TripViewModelsFactory mTripViewModelsFactory;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.scrnTabs)
    TabLayout scrnTabs;
    @BindView(R.id.txtBudget)
    TextView txtBudget;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_home,container,false);
         ButterKnife.bind(this,mView);
          mMainActivity = ((MainActivity) getActivity());
        Toolbar myToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mMainActivity.setSupportActionBar(myToolbar);
        mMainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMainActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setTitle(R.string.app_name);
        List<Fragment> lst = new ArrayList<>();
        lst.add(new TripsListFrgment());
        lst.add(new TripsMapFrgment());
        PagerAdapter mPagerAdapter = new PagerAdapter(getChildFragmentManager(),lst);
        pager.setAdapter(mPagerAdapter);
        scrnTabs.setupWithViewPager(pager);
        setHasOptionsMenu(true);
        mMainActivity.mMainActivityComponent.inject(this);
        TripViewModel mTripViewModel = new ViewModelProvider(mMainActivity,mTripViewModelsFactory).get(TripViewModel.class);
        mTripViewModel.getAllTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                double totalBudget = 0;
                for (Trip mTrip :trips)
                    if(mTrip.isUpcoming())
                       totalBudget += mTrip.getBudget();
                txtBudget.setText(totalBudget + " $");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.transitionToFragment(new TripFrgment());
            }
        });
    return mView;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuProfile:
                mMainActivity.transitionToFragment(new ProfileFrgment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}
