package com.example.travellernotebook.ui.trip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.factory.TripFactory;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.MainFragment;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SharedTripsListFrgment extends MainFragment {

    @Inject
    TripFactory mTripFactory;

    @BindView(R.id.rclItems)
    RecyclerView rclTrips;
    MainActivity mMainActivity;

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_shared_trips,container,false);
         ButterKnife.bind(this,mView);
         mMainActivity =((MainActivity) getActivity());
        mMainActivity.mMainActivityComponent.inject(this);
        Toolbar myToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mMainActivity.setSupportActionBar(myToolbar);
        mMainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMainActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setTitle(R.string.txt_upcoming_trips);
        setHasOptionsMenu(true);
        TripViewModel mTripViewModel = new ViewModelProvider(mMainActivity, mTripFactory).get(TripViewModel.class);
        mTripViewModel.getSharedTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                SharedTripsAdapter mSharedTripsAdapter = new SharedTripsAdapter(trips,mTripViewModel);
                rclTrips.setLayoutManager(new LinearLayoutManager(mMainActivity));
                rclTrips.setAdapter(mSharedTripsAdapter);
            }
        });
        return mView;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                mMainActivity.onBackPressed();
                return true;
            }default:
                return super.onOptionsItemSelected(item);
        }

    }
}
