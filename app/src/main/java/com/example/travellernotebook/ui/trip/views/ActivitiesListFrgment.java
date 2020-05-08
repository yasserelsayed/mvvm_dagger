package com.example.travellernotebook.ui.trip.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Activity;
import com.example.travellernotebook.domain.TripLocation;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.ActivityViewModel;
import com.example.travellernotebook.ui.trip.viewModels.LocationViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesListFrgment extends Fragment {

    @Inject
    TripViewModelsFactory mTripViewModelsFactory;

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
        mMainActivity.mAppComponent.inject(this);
        ActivityViewModel mActivityViewModel = new ViewModelProvider(mMainActivity,mTripViewModelsFactory).get(ActivityViewModel.class);
        if(mMainActivity.activeTripLocation!=null) {
            mActivityViewModel.getAllActivities(mMainActivity.activeTripLocation.getId()).observe(getViewLifecycleOwner(), new Observer<List<Activity>>() {
                @Override
                public void onChanged(List<Activity> activities) {
                    ActivitesAdapter mActivitesAdapter = new ActivitesAdapter(activities,mActivityViewModel);
                    rclLocations.setLayoutManager(new LinearLayoutManager(mMainActivity));
                    rclLocations.setAdapter(mActivitesAdapter);
                }
            });
        }
        return mView;
    }
}
