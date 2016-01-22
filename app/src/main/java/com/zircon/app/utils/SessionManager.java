package com.zircon.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zircon.app.ZirconApp;
import com.zircon.app.model.LoginCredentials;
import com.zircon.app.model.User;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class SessionManager {

    private  static final String PREF_FILE = "app.zircon.com.SESSION_MANAGER";
    private  static final String PREF_LOG_IN_USER = "user";
    private  static final String PREF_LOG_IN_CREDENTIALS = "credentials";
    private  static final String PREF_IS_LOGGED_IN = "is_logged_in";
    private  static final String PREF_LOG_IN_TOKEN = "token";

    public static void setLoggedInUser(User user,LoginCredentials loginCredentials,String token){
        SharedPreferences sharedPreferences = ZirconApp.getAppContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_IS_LOGGED_IN,true);
        editor.putString(PREF_LOG_IN_USER, new Gson().toJson(user));
        editor.putString(PREF_LOG_IN_CREDENTIALS,new Gson().toJson(loginCredentials));
        editor.putString(PREF_LOG_IN_TOKEN, token);
        editor.commit();
    }

    public static User getLoggedInUser(){
        User user = null;
        SharedPreferences sharedPreferences = ZirconApp.getAppContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString(PREF_LOG_IN_USER,null);
        if (userJson != null && userJson.trim().length()>0)
            user = new Gson().fromJson(userJson,User.class);
        return user;
    }

    public static LoginCredentials getLoginCredentials(){
        LoginCredentials loginCredentials = null;
        SharedPreferences sharedPreferences = ZirconApp.getAppContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String loginJson = sharedPreferences.getString(PREF_LOG_IN_CREDENTIALS,null);
        if (loginJson != null && loginJson.trim().length()>0)
            loginCredentials = new Gson().fromJson(loginJson,LoginCredentials.class);
        return loginCredentials;
    }

    public static boolean isUserLoggedIn(){
        SharedPreferences sharedPreferences = ZirconApp.getAppContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREF_IS_LOGGED_IN,false);
    }

    public static String getToken(){
        SharedPreferences sharedPreferences = ZirconApp.getAppContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREF_LOG_IN_TOKEN,null);
    }

}
