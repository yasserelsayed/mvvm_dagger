package com.example.travellernotebook.domain;

import android.app.Application;

import com.example.travellernotebook.R;
import com.example.travellernotebook.di.components.AppComponent;
import com.example.travellernotebook.di.components.DaggerAppComponent;
import com.example.travellernotebook.di.modules.AppModule;
import com.google.android.libraries.places.api.Places;

public class App extends Application {
    AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        Places.initialize(getApplicationContext(), getString(R.string.maps_api_key));
    }

    public AppComponent getAppComponent()
    {
        return   mAppComponent;
    }
}
