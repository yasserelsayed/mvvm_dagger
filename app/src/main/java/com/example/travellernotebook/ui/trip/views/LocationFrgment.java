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
import com.example.travellernotebook.domain.TripLocation;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.views.LocationPickerFragment;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
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

public class LocationFrgment extends Fragment implements    View.OnClickListener{

    @Inject
    TripViewModelsFactory mTripViewModelsFactory;
    LocationViewModel mLocationViewModel;
    Calendar mCalendar;

    @BindView(R.id.edtLocationName)
    EditText edtLocationName;
    @BindView(R.id.txtLocation)
    TextView txtLocation;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.edtBudget)
    EditText edtBudget;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.imgLocationMain)
    ImageView imgLocationMain;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    MainActivity mMainActivity;

    DatePickerDialog datePicker;
    TripLocation mTripLocation;
    public LocationFrgment()
    {
        mTripLocation = new TripLocation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11 && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();
            try {
                mTripLocation.setMainPhoto(imageUri.toString());
                InputStream imageStream  = mMainActivity.getContentResolver().openInputStream(imageUri);
                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                roundedBitmapDrawable.setAntiAlias(true);
                imgLocationMain.setImageDrawable(roundedBitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        if(mTripLocation.getLocationAddress()!=null)
            txtLocation.setText(mTripLocation.getLocationAddress());

        if(mTripLocation.getMainPhoto()!=null && !mTripLocation.getMainPhoto().isEmpty()){
            try {
                InputStream imageStream  = mMainActivity.getContentResolver().openInputStream(Uri.parse(mTripLocation.getMainPhoto()));
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                roundedBitmapDrawable.setAntiAlias(true);
                imgLocationMain.setImageDrawable(roundedBitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_location,container,false);
        ButterKnife.bind(this,mView);
        mCalendar = Calendar.getInstance();
        mMainActivity = ((MainActivity) getActivity());
        mMainActivity.mAppComponent.inject(this);
        mLocationViewModel = new ViewModelProvider(this,mTripViewModelsFactory).get(LocationViewModel.class);

        if(mMainActivity.activeTrip!=null)
            mTripLocation.setParentId(mMainActivity.activeTrip.getId());

        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        datePicker =new DatePickerDialog(getActivity(),R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mCalendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                mTripLocation.setStartDate(mCalendar.getTime());
                txtDate.setText(mTripLocation.getStartDate());
            }
        },year,month,day);
        txtDate.setText(mTripLocation.getStartDate());
        txtDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        imgLocationMain.setOnClickListener(this);
        txtLocation.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtDate:{
                datePicker.show();
                break;
            }  case R.id.imgLocationMain:{
                String action;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) action = Intent.ACTION_OPEN_DOCUMENT;
                else action = Intent.ACTION_PICK;
                Intent mIntent = new Intent(action,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(mIntent, 11);
                break;
            }case R.id.txtLocation:{
                mMainActivity.transitionToFragment(new LocationPickerFragment(mTripLocation));
                break;
            }case R.id.btnSubmit: {
                if (validate()) {
                    mTripLocation.setLocationName(edtLocationName.getText().toString());
                    if (edtBudget.getText().toString() == null || edtBudget.getText().toString().isEmpty())
                        mTripLocation.setBudget(0.0);
                    else
                        mTripLocation.setBudget(Double.parseDouble(edtBudget.getText().toString()));
                    mLocationViewModel.addTripLocation(mTripLocation);
                    mMainActivity.onBackPressed();
                }
                break;
            }
                case R.id.imgBack:{
                    mMainActivity.onBackPressed();
                    break;
            }
        }
    }

    private boolean validate(){
        boolean res = true;
        String locationName = edtLocationName.getText().toString();
        String budget =  edtBudget.getText().toString();

        if(locationName==null || locationName.isEmpty()) {
            edtLocationName.setError(getString(R.string.msg_this_field_required));
            edtLocationName.requestFocus();
            res = false;
        }

        if(mTripLocation.getLocationAddress()==null || mTripLocation.getLocationAddress().isEmpty()) {
            txtLocation.setError(getString(R.string.msg_this_field_required));
            txtLocation.requestFocus();
            res = false;
        }

        if(!budget.isEmpty() && Double.parseDouble(budget)>mMainActivity.activeTrip.getBudget()){
            edtBudget.setError(getString(R.string.msg_location_budget_invalid));
            edtBudget.requestFocus();
            res = false;
        }

        return res;
    }



}
