package com.example.travellernotebook.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.travellernotebook.R;
import com.example.travellernotebook.factory.UserFactory;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.MainFragment;
import com.example.travellernotebook.ui.trip.HomeFrgment;
import com.example.travellernotebook.ui.user.viewModels.UserViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SavingsFrgment extends MainFragment {

    @Inject
    UserFactory mUserFactory;
    @BindView(R.id.edtSavings)
    EditText edtSavings;
    @BindView(R.id.btnSave)
    Button btnSave;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_savings,container,false);
        ButterKnife.bind(this,mView);
        MainActivity mMainActivity = (MainActivity)getActivity();
        mMainActivity.mMainActivityComponent.inject(this);
        UserViewModel mUserViewModel = new ViewModelProvider(mMainActivity, mUserFactory).get(UserViewModel.class);
        String savings = mUserViewModel.getUserSavings();
        if(savings!=null && !savings.isEmpty())
        edtSavings.setText(savings);
        btnSave.setOnClickListener(view -> {
            if(edtSavings.getText().toString()==null || edtSavings.getText().toString().isEmpty())
                edtSavings.setError(getString(R.string.msg_this_field_required));
            else {
                mUserViewModel.setUserSavings(edtSavings.getText().toString());
                mMainActivity.transitionToFragment(new HomeFrgment());
            }
        });
        return mView;
    }

}
