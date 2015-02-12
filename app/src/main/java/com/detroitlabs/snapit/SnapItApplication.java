package com.detroitlabs.snapit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by andrewgiang on 1/27/15.
 */
public class SnapItApplication extends Application {

    public static final String APPLICATION_ID = "";
    public static final String CLIENT_KEY = "";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseObject.registerSubclass(Snap.class);
    }
}
