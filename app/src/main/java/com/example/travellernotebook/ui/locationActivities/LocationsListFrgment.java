package com.example.travellernotebook.ui.locationActivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Location;
import com.example.travellernotebook.factory.LocationActivityFactory;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.MainFragment;
import com.example.travellernotebook.factory.TripFactory;
import com.example.travellernotebook.ui.locationActivities.viewModels.LocationViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsListFrgment extends MainFragment {

    @Inject
    LocationActivityFactory mLocationActivityFactory;

    @BindView(R.id.rclItems)
    RecyclerView rclLocations;
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
         View mView = inflater.inflate(R.layout.fragment_list,container,false);
         ButterKnife.bind(this,mView);
         mMainActivity =((MainActivity) getActivity());
        mMainActivity.mMainActivityComponent.inject(this);
        LocationViewModel mLocationViewModel = new ViewModelProvider(mMainActivity, mLocationActivityFactory).get(LocationViewModel.class);
        if(mMainActivity.activeTrip!=null) {
            mLocationViewModel.getAllLocations(mMainActivity.activeTrip.getId()).observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
                @Override
                public void onChanged(List<Location> locations) {
                    LocationsAdapter mLocationsAdapter = new LocationsAdapter(locations);
                    rclLocations.setLayoutManager(new LinearLayoutManager(mMainActivity));
                    rclLocations.setAdapter(mLocationsAdapter);
                }
            });
        }
        return mView;
    }
}
