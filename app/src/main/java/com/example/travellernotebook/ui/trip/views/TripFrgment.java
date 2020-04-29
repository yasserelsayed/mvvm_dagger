package com.example.travellernotebook.ui.trip.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.views.LocationPickerFragment;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;
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

public class TripFrgment extends Fragment implements    View.OnClickListener{

    @Inject
    TripViewModelsFactory mTripViewModelsFactory;
    TripViewModel mTripViewModel;
    Calendar mCalendar;

    @BindView(R.id.edtTripName)
    EditText edtTripName;
    @BindView(R.id.txtLocation)
    TextView txtLocation;
    @BindView(R.id.txtStartDate)
    TextView txtStartDate;
    @BindView(R.id.txtEndDate)
    TextView txtEndDate;
    @BindView(R.id.edtBudget)
    EditText edtBudget;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.imgTripMain)
    ImageView imgTripMain;

    MainActivity mMainActivity;
    public TripFrgment()
    {
        mTrip = new Trip();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11 && resultCode == RESULT_OK && null != data) {
             Uri imageUri = data.getData();
            try {
                mTrip.setMainPhoto(imageUri.toString());
                InputStream imageStream  = mMainActivity.getContentResolver().openInputStream(imageUri);
                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                roundedBitmapDrawable.setAntiAlias(true);
                imgTripMain.setImageDrawable(roundedBitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            }
    }


    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;
    Trip mTrip;


    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        mMainActivity.btnAdd.setVisibility(View.GONE);
        if(mTrip.getLocationName()!=null)
            txtLocation.setText(mTrip.getLocationName());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_trip,container,false);
         ButterKnife.bind(this,mView);
         mCalendar = Calendar.getInstance();
          mMainActivity = ((MainActivity) getActivity());
          mMainActivity.mAppComponent.inject(this);
          mTripViewModel = new ViewModelProvider(this,mTripViewModelsFactory).get(TripViewModel.class);

        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        startDatePicker =new DatePickerDialog(getActivity(),R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mCalendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                mTrip.setStartDate(mCalendar.getTime());
                txtStartDate.setText(mTrip.getStartDate());
            }
        },year,month,day);
        endDatePicker =new DatePickerDialog(getActivity(),R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mCalendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                mTrip.setEndDate(mCalendar.getTime());
                txtEndDate.setText(mTrip.getEndDate());
            }
        },year,month,day);
        txtStartDate.setText(mTrip.getStartDate());
        txtEndDate.setText(mTrip.getEndDate());
        txtStartDate.setOnClickListener(this);
        txtEndDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        imgTripMain.setOnClickListener(this);
        txtLocation.setOnClickListener(this);
    return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtStartDate:{
                startDatePicker.show();
                break;
            } case R.id.txtEndDate:{
                endDatePicker.show();
                break;
            } case R.id.imgTripMain:{
                Intent mIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(mIntent, 11);
                break;
            }case R.id.txtLocation:{
                mMainActivity.transitionToFragment(new LocationPickerFragment(mTrip));
                break;
            }case R.id.btnSubmit:{
                    if(validate()){
                        mTrip.setTripName(edtTripName.getText().toString());
                        if(edtBudget.getText().toString()==null || edtBudget.getText().toString().isEmpty())
                              mTrip.setBudget(0.0);
                        else  mTrip.setBudget(Double.parseDouble(edtBudget.getText().toString()));
                        mTripViewModel.addTrip(mTrip);
                        mMainActivity.onBackPressed();
                    }
                break;
            }
        }
    }

    private boolean validate(){
        boolean res = true;
        String tripName = edtTripName.getText().toString();
        String locationName = txtLocation.getText().toString();

        if(tripName==null || tripName.isEmpty()) {
            edtTripName.setError(getString(R.string.msg_this_field_required));
            edtTripName.requestFocus();
            res = false;
        }

        if(mTrip.getLocationName()==null || mTrip.getLocationName().isEmpty()) {
            txtLocation.setError(getString(R.string.msg_this_field_required));
            edtTripName.requestFocus();
            res = false;
        }

        return res;
    }
}
