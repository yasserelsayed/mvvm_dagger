package com.example.travellernotebook.ui.trip.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.ui.base.MainActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.AdapterHolder> {


    List<Trip> Datasource;
    MainActivity mMainActivity;
    int archived = 0;
    int upcoming = 1;

    public TripsAdapter(List<Trip> datasource) {
        Datasource = datasource;
    }

    @Override
    public int getItemViewType(int position) {
         super.getItemViewType(position);
         Trip mTrip = Datasource.get(position);
         if(mTrip.isUpcoming())
             return  upcoming;
         else return archived;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View mView = null;
       if(viewType==upcoming)
              mView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_upcoming_item,parent,false);
       else   mView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item,parent,false);
        mMainActivity = (MainActivity) parent.getContext();
        return new AdapterHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
       Trip mTrip = Datasource.get(position);
        holder.txtTripName.setText(mTrip.getTripName());
        holder.txtBudget.setText(mTrip.getBudget().toString() + " $");
        holder.txtFrom.setText(mTrip.getStartDate());
        holder.txtTo.setText(mTrip.getEndDate());
        holder.constContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.activeTrip = mTrip;
                mMainActivity.transitionToFragment(new LocationsHomeFrgment());
            }
        });
        if(mTrip.getMainPhoto()!=null) {
            try {
                InputStream imageStream  = mMainActivity.getContentResolver().openInputStream(Uri.parse(mTrip.getMainPhoto()));
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                holder.imgTripMainPic.setImageBitmap(bitmap);
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
       TextView txtTripName;
       TextView txtBudget;
       TextView txtFrom;
       TextView txtTo;
       ImageView imgTripMainPic;
       ConstraintLayout constContainer;
     public AdapterHolder(@NonNull View itemView) {
         super(itemView);
         txtTripName = (TextView)itemView.findViewById(R.id.txtTripName);
         txtBudget = (TextView)itemView.findViewById(R.id.txtBudget);
         txtFrom = (TextView)itemView.findViewById(R.id.txtFrom);
         txtTo= (TextView)itemView.findViewById(R.id.txtTo);
         imgTripMainPic = (ImageView) itemView.findViewById(R.id.imgTripMainPic);
         constContainer = (ConstraintLayout)itemView.findViewById(R.id.constContainer);
     }
 }
}
