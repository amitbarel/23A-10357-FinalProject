package com.amitb.a23a_10357_soccerwager;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("onCreate: ","asd");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
