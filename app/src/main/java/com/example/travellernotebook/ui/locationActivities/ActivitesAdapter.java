package com.example.travellernotebook.ui.locationActivities;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travellernotebook.R;
import com.example.travellernotebook.domain.Activity;
import com.example.travellernotebook.domain.Quote;
import com.example.travellernotebook.ui.base.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ActivitesAdapter extends RecyclerView.Adapter<ActivitesAdapter.AdapterHolder> {


    List<Activity> Datasource;
    MainActivity mMainActivity;
    ActivitiesListFrgment mActivitiesListFrgment;

    public ActivitesAdapter(List<Activity> datasource, ActivitiesListFrgment mActivitiesListFrgment) {
        Datasource = datasource;
        this.mActivitiesListFrgment = mActivitiesListFrgment;
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
                mActivitiesListFrgment.mActivityViewModel.removeActivity(mActivity);
            }
        });
        holder.txtAddQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivitiesListFrgment.showAddQuoteEditor(mActivity.getId());
            }
        });


        if(mActivity.getLstQuotes()!=null){
            holder.lnrQuotes.removeAllViews();
            for (Quote mQuote : mActivity.getLstQuotes()){
                View mView =  LayoutInflater.from(mMainActivity).inflate(R.layout.quote_item,holder.constContainer,false);
                TextView txtQuote = mView.findViewById(R.id.txtQuote);
                TextView txtDeleteQuote = mView.findViewById(R.id.txtDeleteQuote);
                txtQuote.setText(mQuote.getQuote());
                holder.lnrQuotes.addView(mView);
                txtDeleteQuote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     mActivitiesListFrgment.mActivityViewModel.removeActivityQuote(mQuote);
                        holder.lnrQuotes.removeView(mView);
                    }
                });
            }
        }

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
       LinearLayout lnrQuotes;
     public AdapterHolder(@NonNull View itemView) {
         super(itemView);
         txtDeleteActivity = itemView.findViewById(R.id.txtDeleteActivity);
         txtActivityName = itemView.findViewById(R.id.txtActivityName);
         txtAddQuote = itemView.findViewById(R.id.txtAddQuote);
         lnrQuotes =  itemView.findViewById(R.id.lnrQuotes);
         constContainer = itemView.findViewById(R.id.constContainer);
     }
 }
}
