package com.starostinvlad.professional_1c;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.starostinvlad.professional_1c.Database.AppDatabase;

import androidx.room.Room;

public class App extends Application {
    private static App instance;
    private static AppDatabase database;
    SharedPreferences preferences;

    ConnectivityManager connectivityManager;

    public static App getInstance() {
        return instance;
    }

    public static AppDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .createFromAsset("database.db")
                .fallbackToDestructiveMigration()
                .build();
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    public boolean isNetworkAvailable() {
        if (connectivityManager.getActiveNetworkInfo() != null)
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        return false;
    }

    public void putScore(int score) {
        preferences.edit().putInt("SCORE", score).apply();
    }

    int getScore() {
        return preferences.getInt("SCORE", 0);
    }
}
