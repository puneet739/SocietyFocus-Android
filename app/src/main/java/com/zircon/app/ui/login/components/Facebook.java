package com.zircon.app.ui.login.components;

import android.app.Activity;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zircon.app.R;
import com.zircon.app.ZirconApp;
import com.zircon.app.ui.login.LoginActivity;
import com.zircon.app.utils.Log;

import java.lang.ref.WeakReference;


/**
 * Created by jikoobaruah on 07/03/16.
 */
public class Facebook implements FacebookCallback<LoginResult> {

    private static Facebook instance;


    private WeakReference<Activity> hostActivity;
    private CallbackManager callbackManager;

    private LoginButton loginButton;

    private Facebook(){
        if (!FacebookSdk.isInitialized())
            FacebookSdk.sdkInitialize(ZirconApp.getAppContext());

    }

    public static Facebook getInstance(){
        if (instance == null)
            instance = new Facebook();
        return instance;
    }

    public void onCreate(Activity activity){
        clearMemory();
        hostActivity = new WeakReference<Activity>(activity);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) hostActivity.get().findViewById(R.id.fb_login);
        if (loginButton != null){
            loginButton.registerCallback(callbackManager,this);
        }

    }

    public void onActivityResult(int requestCode, int code, Intent data){
        callbackManager.onActivityResult(requestCode, code, data);
    }

    public void onDestroy(){
       clearMemory();
    }

    private void clearMemory(){
        if (hostActivity != null && hostActivity.get() != null)
            hostActivity.clear();
        hostActivity = null;
        loginButton = null;
        callbackManager = null;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        loginResult.getAccessToken();
        loginResult.getRecentlyGrantedPermissions();
    }


    @Override
    public void onCancel() {
        Log.d(Facebook.class.getName(),"FB login cancelled");
    }

    @Override
    public void onError(FacebookException error) {
        error.printStackTrace();
    }
}
