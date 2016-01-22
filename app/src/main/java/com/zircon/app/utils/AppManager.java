package com.zircon.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zircon.app.ZirconApp;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class AppManager {

    private  static final String PREF_FILE = "app.zircon.com.APP_MANAGER";
    private  static final String PREF_FIRST_LAUNCH = "first_launch";

    public static boolean isFirstTimeLaunchAfterInstall(){

        boolean isFirstLaunch;

        SharedPreferences sharedPref = ZirconApp.getAppContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        isFirstLaunch = sharedPref.getBoolean(PREF_FIRST_LAUNCH,true);

        if (isFirstLaunch){
            sharedPref.edit().putBoolean(PREF_FIRST_LAUNCH,false).commit();
        }
        return isFirstLaunch;
    }
}
