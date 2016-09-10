package com.zircon.app;

import android.app.Application;
import android.content.Context;

import com.zircon.app.utils.KeyUtils;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


/**
 * Created by jikoobaruah on 19/01/16.
 */
public class ZirconApp extends Application{

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        appContext = this;
    }



    public static Context getAppContext(){
        return appContext;
    }
}
