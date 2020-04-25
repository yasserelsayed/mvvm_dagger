package com.example.travellernotebook.domain;

import android.app.Application;

import com.example.travellernotebook.di.components.AppComponent;
import com.example.travellernotebook.di.components.DaggerAppComponent;
import com.example.travellernotebook.di.modules.AppModule;

public class App extends Application {
    AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent()
    {
        return   mAppComponent;
    }
}
