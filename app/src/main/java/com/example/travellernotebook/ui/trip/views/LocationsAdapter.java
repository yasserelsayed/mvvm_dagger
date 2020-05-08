package com.example.travellernotebook.ui.trip.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.TripLocation;
import com.example.travellernotebook.ui.base.MainActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.AdapterHolder> {


    List<TripLocation> Datasource;
    MainActivity mMainActivity;

    public LocationsAdapter(List<TripLocation> datasource) {
        Datasource = datasource;
    }


    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View mView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item,parent,false);
        mMainActivity = (MainActivity) parent.getContext();
        return new AdapterHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
        TripLocation mTripLocationp = Datasource.get(position);
        holder.txtLocationName.setText(mTripLocationp.getLocationName());
        holder.txtBudget.setText(mTripLocationp.getBudget().toString() + " $");
        holder.txtDate.setText(mTripLocationp.getStartDate());
        holder.constContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.activeTripLocation = mTripLocationp;
                mMainActivity.transitionToFragment(new LocationActivitiesFrgment());
            }
        });
        if(mTripLocationp.getMainPhoto()!=null) {
            try {
                InputStream imageStream  = mMainActivity.getContentResolver().openInputStream(Uri.parse(mTripLocationp.getMainPhoto()));
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                holder.imgLocationMain.setImageBitmap(bitmap);
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
       TextView txtLocationName;
       TextView txtBudget;
       TextView txtDate;
       ImageView imgLocationMain;
       ConstraintLayout constContainer;
     public AdapterHolder(@NonNull View itemView) {
         super(itemView);
         txtLocationName = (TextView)itemView.findViewById(R.id.txtLocationName);
         txtBudget = (TextView)itemView.findViewById(R.id.txtBudget);
         txtDate = (TextView)itemView.findViewById(R.id.txtDate);
         imgLocationMain = (ImageView) itemView.findViewById(R.id.imgLocationMain);
         constContainer = (ConstraintLayout)itemView.findViewById(R.id.constContainer);
     }
 }
}
