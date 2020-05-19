package com.example.travellernotebook.service;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.example.travellernotebook.R;
import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.data.database.entities.Tripdb;
import com.example.travellernotebook.domain.App;
import com.example.travellernotebook.ui.base.MainActivity;
import com.example.travellernotebook.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SavingsWidget extends AppWidgetProvider {

    public static void triggerWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        Date mToday = new Date();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.savings_widget);
        AppDatabase mAppDatabase = ((App)context.getApplicationContext()).getAppComponent().provideAppDatabase();
        SharedPreferences mPreferences = context.getSharedPreferences(context.getPackageName(),0);
        String totalSavings = mPreferences.getString(Constants.KeySavings,"0");
        Intent mIntent = new Intent(context, MainActivity.class);
        mIntent.putExtra(Constants.KeyToScreen,Constants.KeyToSavings);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btnUpdate,mPendingIntent);
        views.setTextViewText(R.id.txtSavings,totalSavings+"$");
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Date startDate = null;
                double budget = 0;
                List<Tripdb> lstTrips = mAppDatabase.tripDao().getAll();
                for (Tripdb mTripdb :lstTrips){
                    if(mTripdb.budget==null && mTripdb.budget.isEmpty())
                        continue;
                    try {
                        startDate = null;
                        startDate =  mSimpleDateFormat.parse(mTripdb.startDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(startDate!=null && startDate.compareTo(mToday) > 0)
                        budget+=Double.parseDouble(mTripdb.budget);
                }
                views.setTextViewText(R.id.txtBudget,budget+"$");
                for (int appWidgetId : appWidgetIds)
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                return null;
            }
        }.execute();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        triggerWidgetUpdate(context,appWidgetManager,appWidgetIds);
    }
}
