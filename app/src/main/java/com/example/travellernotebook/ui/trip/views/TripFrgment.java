package com.example.travellernotebook.ui.trip.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;
    Trip mTrip;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_trip,container,false);
         ButterKnife.bind(this,mView);
         mCalendar = Calendar.getInstance();
         ((MainActivity) getActivity()).mAppComponent.inject(this);
          mTripViewModel = new ViewModelProvider(this,mTripViewModelsFactory).get(TripViewModel.class);
          mTrip = new Trip();
//        mTripViewModel.addTrip(mTrip);

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
            }
        }

    }
}
