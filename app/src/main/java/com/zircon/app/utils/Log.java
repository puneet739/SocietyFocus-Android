package com.zircon.app.utils;

import com.zircon.app.BuildConfig;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class Log {

    public static void i(String tag,String message){
        if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE.equals("debug"))
            android.util.Log.i(tag,message);
    }

    public static void d(String tag,String message){
        if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE.equals("debug"))
            android.util.Log.d(tag,message);
    }

    public static void e(String tag,String message){
        if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE.equals("debug"))
            android.util.Log.e(tag,message);
    }
}
