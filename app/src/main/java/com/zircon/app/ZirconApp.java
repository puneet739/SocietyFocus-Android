package com.zircon.app;

import android.app.Application;
import android.content.Context;

import com.zircon.app.utils.KeyUtils;


/**
 * Created by jikoobaruah on 19/01/16.
 */
public class ZirconApp extends Application{

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        KeyUtils.getKeyHash();
        appContext = this;


    }



    public static Context getAppContext(){
        return appContext;
    }
}
