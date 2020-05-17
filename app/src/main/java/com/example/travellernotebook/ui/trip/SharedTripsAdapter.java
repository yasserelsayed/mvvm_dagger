package com.example.travellernotebook.ui.trip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.locationActivities.LocationsHomeFrgment;
import com.example.travellernotebook.ui.trip.viewModels.TripViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SharedTripsAdapter extends RecyclerView.Adapter<SharedTripsAdapter.AdapterHolder> {


    List<Trip> Datasource;
    MainActivity mMainActivity;
    TripViewModel mTripViewModel;

    public SharedTripsAdapter(List<Trip> datasource, TripViewModel mTripViewModel) {
        Datasource = datasource;
        this.mTripViewModel = mTripViewModel;
    }


    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View mView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_trip_item,parent,false);
        mMainActivity = (MainActivity) parent.getContext();
        return new AdapterHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
       Trip mTrip = Datasource.get(position);
        holder.txtUserName.setText(mTrip.getUserName());
        holder.txtPhone.setText(mTrip.getPhone());
        holder.txtEmail.setText(mTrip.getEmail());
        holder.txtFrom.setText(mTrip.getStartDate());
        holder.txtTo.setText(mTrip.getEndDate());
        holder.txtTripName.setText(mTrip.getTripName());
    }

    @Override
    public int getItemCount() {
        return Datasource.size();
    }

    public class AdapterHolder extends  RecyclerView.ViewHolder{
       TextView txtUserName;
       TextView txtPhone;
       TextView txtEmail;
       TextView txtFrom;
       TextView txtTo;
       TextView txtTripName;
       ConstraintLayout constContainer;
     public AdapterHolder(@NonNull View itemView) {
         super(itemView);
         txtUserName = (TextView)itemView.findViewById(R.id.txtUserName);
         txtPhone = (TextView)itemView.findViewById(R.id.txtPhone);
         txtFrom = (TextView)itemView.findViewById(R.id.txtFrom);
         txtTo= (TextView)itemView.findViewById(R.id.txtTo);
         txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
         txtTripName = (TextView) itemView.findViewById(R.id.txtTripName);
         constContainer = (ConstraintLayout)itemView.findViewById(R.id.constContainer);
     }
 }
}
