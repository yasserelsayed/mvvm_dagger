package com.example.travellernotebook.ui.trip.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Activity;
import com.example.travellernotebook.domain.TripLocation;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.views.LocationPickerFragment;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.ActivityViewModel;
import com.example.travellernotebook.ui.trip.viewModels.LocationViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class ActivityFrgment extends Fragment implements    View.OnClickListener{

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
        mMainActivity.mAppComponent.inject(this);
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
