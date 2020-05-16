package com.example.travellernotebook.ui.locationActivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Activity;
import com.example.travellernotebook.domain.Quote;
import com.example.travellernotebook.factory.LocationActivityFactory;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.MainFragment;
import com.example.travellernotebook.factory.TripFactory;
import com.example.travellernotebook.ui.locationActivities.viewModels.ActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class ActivitiesListFrgment extends MainFragment {

    @Inject
    LocationActivityFactory mLocationActivityFactory;

    @BindView(R.id.rclItems)
    RecyclerView rclLocations;
    MainActivity mMainActivity;
    @BindView(R.id.frmAddQuote)
    FrameLayout frmAddQuote;
    @BindView(R.id.imgClose)
    ImageView imgClose;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.edtAddQuote)
    EditText edtAddQuote;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;
    public  ActivityViewModel mActivityViewModel;




    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_activities_list,container,false);
         ButterKnife.bind(this,mView);
         mMainActivity =((MainActivity) getActivity());
        mMainActivity.mMainActivityComponent.inject(this);
        mActivityViewModel = new ViewModelProvider(mMainActivity, mLocationActivityFactory).get(ActivityViewModel.class);
        if(mMainActivity.activeLocation !=null) {
            mActivityViewModel.getAllActivities(mMainActivity.activeLocation.getId()).observe(getViewLifecycleOwner(), new Observer<List<Activity>>() {
                @Override
                public void onChanged(List<Activity> activities) {
                    ActivitesAdapter mActivitesAdapter = new ActivitesAdapter(activities,ActivitiesListFrgment.this);
                    rclLocations.setLayoutManager(new LinearLayoutManager(mMainActivity));
                    rclLocations.setAdapter(mActivitesAdapter);
                }
            });
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.transitionToFragment(new ActivityFrgment());
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frmAddQuote.setVisibility(View.GONE);
                edtAddQuote.setTag(null);
                edtAddQuote.setText(null);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String quote =  edtAddQuote.getText().toString();
                if(quote.isEmpty())
                    edtAddQuote.setError(getString(R.string.msg_this_field_required));
                else {
                    Quote mQuote = new Quote();
                    mQuote.setQuote(quote);
                    mQuote.setLocation(mMainActivity.activeLocation.getId());
                    Object  mObject = edtAddQuote.getTag().toString();
                    if(mObject!=null) {
                       int parentID = Integer.parseInt(mObject.toString());
                        mQuote.setParent(parentID);
                        mActivityViewModel.addActivityQuote(mQuote);

                        edtAddQuote.setText(null);
                        edtAddQuote.setTag(null);
                        edtAddQuote.requestFocus();
                        frmAddQuote.setVisibility(View.GONE);

                    }
                }

            }
        });

        return mView;
    }


    public void showAddQuoteEditor(int activityId){
        frmAddQuote.setVisibility(View.VISIBLE);
        edtAddQuote.setTag(activityId);
    }
}
