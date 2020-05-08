package com.example.travellernotebook.ui.trip.views;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Activity;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.ui.trip.viewModels.ActivityViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ActivitesAdapter extends RecyclerView.Adapter<ActivitesAdapter.AdapterHolder> {


    List<Activity> Datasource;
    MainActivity mMainActivity;
    ActivityViewModel mActivityViewModel;

    public ActivitesAdapter(List<Activity> datasource, ActivityViewModel mActivityViewModel) {
        Datasource = datasource;
        this.mActivityViewModel = mActivityViewModel;
    }


    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View mView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
        mMainActivity = (MainActivity) parent.getContext();
        return new AdapterHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
        Activity mActivity = Datasource.get(position);
        holder.txtActivityName.setText(mActivity.getActivityName());
        holder.txtDeleteActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivityViewModel.removeActivity(mActivity);
            }
        });
        holder.txtAddQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return Datasource.size();
    }

    public class AdapterHolder extends  RecyclerView.ViewHolder{
       TextView txtDeleteActivity;
       TextView txtActivityName;
       TextView txtAddQuote;
       ConstraintLayout constContainer;
     public AdapterHolder(@NonNull View itemView) {
         super(itemView);
         txtDeleteActivity = (TextView)itemView.findViewById(R.id.txtDeleteActivity);
         txtActivityName = (TextView)itemView.findViewById(R.id.txtActivityName);
         txtAddQuote = (TextView)itemView.findViewById(R.id.txtAddQuote);
         constContainer = (ConstraintLayout)itemView.findViewById(R.id.constContainer);
     }
 }
}
