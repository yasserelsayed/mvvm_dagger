package com.example.travellernotebook.ui.user.views;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.User;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.MainFragment;
import com.example.travellernotebook.ui.user.UserViewModelsFactory;
import com.example.travellernotebook.ui.user.viewModels.UserViewModel;
import com.example.travellernotebook.ui.trip.views.HomeFrgment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class SplashFrgment extends MainFragment {

    @Inject
    UserViewModelsFactory mUserViewModelsFactory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_splash,container,false);
         MainActivity mMainActivity = (MainActivity)getActivity();
        mMainActivity.mMainActivityComponent.inject(this);
        UserViewModel mUserViewModel = new ViewModelProvider(mMainActivity, mUserViewModelsFactory).get(UserViewModel.class);
        mUserViewModel.getUserProfile().observe(getViewLifecycleOwner(),new Observer<User>() {
            @Override
            public void onChanged(User user) {
                new Handler().postDelayed(() ->{
                    if(user.getPassword()==null || user.getPassword().isEmpty() )
                    mMainActivity.transitionToFragment(new HomeFrgment());
                    else   mMainActivity.transitionToFragment(new LoginFrgment());
                }, 100);
            }
        });

        return mView;
    }

}
