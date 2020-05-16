package com.example.travellernotebook.di.components;
import android.content.Context;

import com.example.travellernotebook.data.database.AppDatabase;
import com.example.travellernotebook.di.modules.AppModule;
import com.google.firebase.firestore.FirebaseFirestore;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    public Context provideContext();
    public FirebaseFirestore provideFirestoreDatabase();
    public AppDatabase provideAppDatabase();
}
