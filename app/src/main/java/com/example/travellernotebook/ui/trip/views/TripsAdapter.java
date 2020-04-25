package com.example.travellernotebook.ui.trip.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Trip;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.AdapterHolder> {


    List<Trip> Datasource;

    public TripsAdapter(List<Trip> datasource) {
        Datasource = datasource;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item,parent,false);
        return new AdapterHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
       Trip mTrip = Datasource.get(position);
        holder.txtTripName.setText(mTrip.getTripName());

    }

    @Override
    public int getItemCount() {
        return Datasource.size();
    }

    public class AdapterHolder extends  RecyclerView.ViewHolder{
       TextView txtTripName;
     public AdapterHolder(@NonNull View itemView) {
         super(itemView);
         txtTripName = (TextView)itemView.findViewById(R.id.txtTripName);
     }
 }
}
