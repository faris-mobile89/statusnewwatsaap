package com.example.statusnewwatsaap;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class BaseApplication extends Application {
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}