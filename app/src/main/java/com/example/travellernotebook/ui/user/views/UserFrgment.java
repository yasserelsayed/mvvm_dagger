package com.example.travellernotebook.ui.user.views;

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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.User;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.user.UserViewModelsFactory;
import com.example.travellernotebook.ui.user.viewModels.UserViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class UserFrgment extends Fragment implements View.OnClickListener{

    @Inject
    UserViewModelsFactory mUserViewModelsFactory;
    MainActivity mMainActivity;
    UserViewModel mUserViewModel;

    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.imgProfilePhoto)
    ImageView imgProfilePhoto;
    @BindView(R.id.imgBack)
    ImageView imgBack;

    User mUser;
    public UserFrgment() {
        mUser = new User();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11 && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();
            try {
                mUser.setProfilePhoto(imageUri.toString());
                InputStream imageStream  = mMainActivity.getContentResolver().openInputStream(imageUri);
                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                roundedBitmapDrawable.setAntiAlias(true);
                imgProfilePhoto.setImageDrawable(roundedBitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View mView = inflater.inflate(R.layout.fragment_profile,container,false);
        ButterKnife.bind(this,mView);
        mMainActivity =((MainActivity) getActivity());
        mMainActivity.mMainActivityComponent.inject(this);
        mUserViewModel = new ViewModelProvider(mMainActivity, mUserViewModelsFactory).get(UserViewModel.class);

        mUserViewModel.getUserProfile().observe(getViewLifecycleOwner(),new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.getEmail()!=null && !user.getEmail().isEmpty())
                    edtEmail.setText(user.getEmail());
                if(user.getPhone()!=null && !user.getPhone().isEmpty())
                    edtPhone.setText(user.getPhone());
                if(user.getUserName()!=null && !user.getUserName().isEmpty())
                    edtUserName.setText(user.getUserName());
                if(user.getProfilePhoto()!=null && !user.getProfilePhoto().isEmpty())
                    setProfilePhoto(user.getProfilePhoto());

            }
        });

        imgProfilePhoto.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgProfilePhoto:{
                String action;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) action = Intent.ACTION_OPEN_DOCUMENT;
                else action = Intent.ACTION_PICK;
                Intent mIntent = new Intent(action,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(mIntent, 11);
                break;
            }case R.id.btnSubmit:{
                mUser.setEmail(edtEmail.getText().toString());
                mUser.setPassword(edtPassword.getText().toString());
                mUser.setPhone(edtPhone.getText().toString());
                mUser.setUserName(edtUserName.getText().toString());
                mUserViewModel.updateUserProfile(mUser);
                mMainActivity.onBackPressed();
                break;
            }
            case R.id.imgBack:{
                mMainActivity.onBackPressed();
                break;
            }


        }
    }

    public void setProfilePhoto(String photo) {
        super.onResume();

        if(photo!=null && !photo.isEmpty()){
            try {
                InputStream imageStream  = mMainActivity.getContentResolver().openInputStream(Uri.parse(photo));
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                roundedBitmapDrawable.setAntiAlias(true);
                imgProfilePhoto.setImageDrawable(roundedBitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
