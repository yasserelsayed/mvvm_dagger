package com.example.travellernotebook.ui.trip.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;
import com.example.travellernotebook.ui.trip.views.adapters.TripsAdapter;

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

public class TripsListFrgment extends Fragment {

    @Inject
    TripViewModelsFactory mTripViewModelsFactory;

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
         View mView = inflater.inflate(R.layout.fragment_list,container,false);
         ButterKnife.bind(this,mView);
         mMainActivity =((MainActivity) getActivity());
        mMainActivity.mMainActivityComponent.inject(this);
        TripViewModel mTripViewModel = new ViewModelProvider(mMainActivity,mTripViewModelsFactory).get(TripViewModel.class);
        mTripViewModel.getAllTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                TripsAdapter mTripsAdapter = new TripsAdapter(trips);
                rclTrips.setLayoutManager(new LinearLayoutManager(mMainActivity));
                rclTrips.setAdapter(mTripsAdapter);
            }
        });
        return mView;
    }
}
