package com.example.travellernotebook.ui.locationActivities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Media;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.locationActivities.viewModels.MediaViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.AdapterHolder> {


    List<Media> Datasource;
    MainActivity mMainActivity;
    MediaViewModel mMediaViewModel;

    public MediaAdapter(List<Media> datasource, MediaViewModel mMediaViewModel) {
        Datasource = datasource;
        this.mMediaViewModel = mMediaViewModel;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View mView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item,parent,false);
        mMainActivity = (MainActivity) parent.getContext();
        return new AdapterHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
       Media mMedia = Datasource.get(position);
        if(mMedia.getPath()!=null) {
            try {
                InputStream imageStream  = mMainActivity.getContentResolver().openInputStream(Uri.parse(mMedia.getPath()));
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                holder.imgMediaContent.setImageBitmap(bitmap);
                holder.imgDeleteContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMediaViewModel.removeMediaContent(mMedia);
                        Datasource.remove(position);
                        notifyDataSetChanged();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return Datasource.size();
    }

    public class AdapterHolder extends  RecyclerView.ViewHolder{
       ImageView imgMediaContent;
       ConstraintLayout constContainer;
        ImageView imgDeleteContent;
     public AdapterHolder(@NonNull View itemView) {
         super(itemView);
         imgMediaContent = itemView.findViewById(R.id.imgMediaContent);
         constContainer = itemView.findViewById(R.id.constContainer);
         imgDeleteContent = itemView.findViewById(R.id.imgDeleteContent);
     }
 }
}
