package com.example.travellernotebook.util;

import android.content.Intent;

import com.nguyenhoanglam.imagepicker.ui.camera.CameraActivty;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity;

import androidx.fragment.app.Fragment;

public class ImagePickerBuilderExtended extends ImagePicker.Builder {

    int RequestCode = 0 ;
    Fragment mFragment;
    public ImagePickerBuilderExtended(Fragment fragment) {
        super(fragment);
        mFragment = fragment;
    }

    public void setRequestCode(int requestCode ) {
        this.RequestCode = requestCode;
    }

    @Override
    public void start() {
        Intent intent = getIntent();
        if (!this.config.isCameraOnly()) {
            this.mFragment.startActivityForResult(intent, RequestCode);
        } else {
            this.mFragment.getActivity().overridePendingTransition(0, 0);
            this.mFragment.startActivityForResult(intent, RequestCode);
        }
    }

    @Override
    public Intent getIntent() {
        Intent intent;
        if (!this.config.isCameraOnly()) {
            intent = new Intent(this.mFragment.getActivity(), ImagePickerActivity.class);
            intent.putExtra("ImagePickerConfig", this.config);
        } else {
            intent = new Intent(this.mFragment.getActivity(), CameraActivty.class);
            intent.putExtra("ImagePickerConfig", this.config);
        }
        return intent;
    }
}
