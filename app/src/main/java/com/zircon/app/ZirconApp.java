package com.zircon.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by jikoobaruah on 19/01/16.
 */
public class ZirconApp extends Application{

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static Context getAppContext(){
        return appContext;
    }
}
