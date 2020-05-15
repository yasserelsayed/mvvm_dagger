package com.example.travellernotebook.ui.trip.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Activity;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.MainFragment;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.ActivityViewModel;
import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFrgment extends MainFragment implements    View.OnClickListener{

    @Inject
    TripViewModelsFactory mTripViewModelsFactory;
    ActivityViewModel mActivityViewModel;


    @BindView(R.id.edtActivity)
    EditText edtActivity;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    MainActivity mMainActivity;
    Activity mActivity;
    public ActivityFrgment(){
        mActivity = new Activity();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_activity,container,false);
        ButterKnife.bind(this,mView);
        mMainActivity = ((MainActivity) getActivity());
        mMainActivity.mMainActivityComponent.inject(this);
        mActivityViewModel = new ViewModelProvider(this,mTripViewModelsFactory).get(ActivityViewModel.class);
        btnSubmit.setOnClickListener(this);
        if(mMainActivity.activeTripLocation!=null)
            mActivity.setParent(mMainActivity.activeTripLocation.getId());

        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:{
                String activityName = edtActivity.getText().toString();
                if(activityName!=null && !activityName.isEmpty()){
                    mActivity.setActivityName(activityName);
                    mActivityViewModel.addActivity(mActivity);
                    mMainActivity.onBackPressed();
                }else {
                    edtActivity.setError(getString(R.string.msg_this_field_required));
                    edtActivity.requestFocus();
                }
                break;
            }
        }
    }
}
