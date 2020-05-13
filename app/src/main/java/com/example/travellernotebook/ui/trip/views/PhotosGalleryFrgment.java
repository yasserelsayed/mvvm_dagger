package com.example.travellernotebook.ui.trip.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Media;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.trip.TripViewModelsFactory;
import com.example.travellernotebook.ui.trip.viewModels.MediaViewModel;
import com.example.travellernotebook.ui.trip.views.adapters.MediaAdapter;
import com.example.travellernotebook.util.ImagePickerBuilderExtended;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nguyenhoanglam.imagepicker.model.Config;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class PhotosGalleryFrgment extends Fragment{

    @Inject
    TripViewModelsFactory mTripViewModelsFactory;
    MediaViewModel mMediaViewModel;
    ImagePickerBuilderExtended mImagePickerBuilderExtended;
    int insuranceBackPicRequestCode = 111;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;
    @BindView(R.id.rclItems)
    RecyclerView rclItems;



    MainActivity mMainActivity;
    public PhotosGalleryFrgment(){
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == insuranceBackPicRequestCode && resultCode == RESULT_OK && null != data) {
                List<com.nguyenhoanglam.imagepicker.model.Image> lstImages = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                Uri imageUri = Uri.parse("file://" + lstImages.get(0).getPath());

               if(mMainActivity.activeTripLocation !=null) {
                   Media mMedia = new Media();
                   mMedia.setPath(imageUri.toString());
                   mMedia.setParent(mMainActivity.activeTripLocation.getId());
                   mMediaViewModel.addMediaContent(mMedia);
               }

        }
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
        View mView = inflater.inflate(R.layout.fragment_gallery_list,container,false);
        ButterKnife.bind(this,mView);
        mMainActivity = ((MainActivity) getActivity());
        mMainActivity.mMainActivityComponent.inject(this);
        mImagePickerBuilderExtended = new ImagePickerBuilderExtended(this);
        mMediaViewModel = new ViewModelProvider(this,mTripViewModelsFactory).get(MediaViewModel.class);

        if(mMainActivity.activeTripLocation!=null) {
            mMediaViewModel.getAllMediaContents(mMainActivity.activeTripLocation.getId()).observe(getViewLifecycleOwner(), new Observer<List<Media>>() {
                @Override
                public void onChanged(List<Media> mediaContents) {
                    MediaAdapter mMediaAdapter = new MediaAdapter(mediaContents,mMediaViewModel);
                    rclItems.setLayoutManager(new GridLayoutManager(mMainActivity,2));
                    rclItems.setAdapter(mMediaAdapter);
                }
            });
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImagePickerBuilderExtended.setRequestCode(insuranceBackPicRequestCode);
                mImagePickerBuilderExtended.start();
            }
        });
        return mView;
    }
}
