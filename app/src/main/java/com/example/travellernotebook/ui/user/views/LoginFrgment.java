package com.example.travellernotebook.ui.user.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.User;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.base.MainFragment;
import com.example.travellernotebook.ui.trip.views.HomeFrgment;
import com.example.travellernotebook.ui.user.UserViewModelsFactory;
import com.example.travellernotebook.ui.user.viewModels.UserViewModel;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFrgment extends MainFragment implements View.OnClickListener {

    @Inject
    UserViewModelsFactory mUserViewModelsFactory;

    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.imgFingerPrint)
    ImageView imgFingerPrint;
    @BindView(R.id.txtReset)
    TextView txtReset;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    User mUser;

    BiometricManager mBiometricManager;
    Executor mExecutor;
    BiometricPrompt mBiometricPrompt;
    BiometricPrompt.PromptInfo mPromptInfo;
    MainActivity mMainActivity;
    UserViewModel mUserViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_lock,container,false);
        ButterKnife.bind(this,mView);
        mMainActivity = (MainActivity)getActivity();
        mMainActivity.mMainActivityComponent.inject(this);
        mUserViewModel = new ViewModelProvider(mMainActivity, mUserViewModelsFactory).get(UserViewModel.class);
        mUserViewModel.getUserProfile().observe(getViewLifecycleOwner(),new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser = user;
            }
        });
        mBiometricManager = BiometricManager.from(mMainActivity);
        if(mBiometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_SUCCESS) {
            imgFingerPrint.setVisibility(View.VISIBLE);
            mExecutor = ContextCompat.getMainExecutor(mMainActivity);
            mPromptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle(getString(R.string.app_name))
                    .setSubtitle(getString(R.string.msg_login_with_biometric))
                    .build();
            mBiometricPrompt = new BiometricPrompt(mMainActivity, mExecutor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode,
                                                  @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(mMainActivity, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                       mMainActivity.transitionToFragment(new HomeFrgment());
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(mMainActivity, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        imgFingerPrint.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        txtReset.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFingerPrint: {
                if(mBiometricPrompt!=null)
                mBiometricPrompt.authenticate(mPromptInfo);
               break;
            }case R.id.btnSubmit: {
                 String pass =  edtPassword.getText().toString();
                 if(pass.isEmpty()) {
                     edtPassword.setError(getString(R.string.msg_this_field_required));
                     edtPassword.requestFocus();
                 }else if(mUser.getPassword().equals(pass))
                     mMainActivity.transitionToFragment(new HomeFrgment());
                 else {
                     edtPassword.setError(getString(R.string.msg_auth_error));
                     edtPassword.requestFocus();
                 }
                break;
            }case R.id.txtReset: {
                mUserViewModel.resetApp();
                mMainActivity.transitionToFragment(new HomeFrgment());
                break;
            }
        }
    }

}
