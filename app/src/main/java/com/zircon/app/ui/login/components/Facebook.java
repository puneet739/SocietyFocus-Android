package com.zircon.app.ui.login.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zircon.app.R;
import com.zircon.app.ZirconApp;
import com.zircon.app.model.response.LoginResponse;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.activity.AbsLoginActivity;
import com.zircon.app.ui.home.MainNavActivity;
import com.zircon.app.ui.login.LoginActivity;
import com.zircon.app.utils.API;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.Log;
import com.zircon.app.utils.SessionManager;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jikoobaruah on 07/03/16.
 */
/*public class Facebook implements FacebookCallback<LoginResult> {

    private static Facebook instance;


    private WeakReference<Activity> hostActivity;*/
//        private CallbackManager callbackManager;

//        private LoginButton loginButton;

//    private Facebook() {
//        if (!FacebookSdk.isInitialized())
//            FacebookSdk.sdkInitialize(ZirconApp.getAppContext());
//    }
//
//    public static Facebook getInstance() {
//        if (instance == null)
//            instance = new Facebook();
//        return instance;
//    }
//
//    public void onCreate(Activity activity) {
////        clearMemory();
////        hostActivity = new WeakReference<Activity>(activity);
////
////        callbackManager = CallbackManager.Factory.create();
////
////        loginButton = (LoginButton) hostActivity.get().findViewById(R.id.fb_login);
////        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
////
////        loginButton.registerCallback(callbackManager, this);
//    }
//
//    public void onActivityResult(int requestCode, int code, Intent data) {
//        callbackManager.onActivityResult(requestCode, code, data);
//    }
//
//    public void onDestroy() {
//        clearMemory();
//    }
//
//    private void clearMemory() {
//        if (hostActivity != null && hostActivity.get() != null)
//            hostActivity.clear();
//        hostActivity = null;
//        loginButton = null;
//        callbackManager = null;
//    }

/*    @Override
    public void onSuccess(LoginResult loginResult) {
//        Log.e("Profile","User first name "+ Profile.getCurrentProfile().getFirstName());
//        if(Profile.getCurrentProfile()!=null){
//            Log.e("Pofile",Profile.getCurrentProfile().getName()+Profile.getCurrentProfile().getFirstName()+Profile.getCurrentProfile().getLastName()+Profile.getCurrentProfile().getId());
//        }else{
//            Profile.fetchProfileForCurrentAccessToken();
//            Log.e("Pofile",Profile.getCurrentProfile().getName()+Profile.getCurrentProfile().getFirstName()+Profile.getCurrentProfile().getLastName()+Profile.getCurrentProfile().getId());
//        }
        Log.e("Temp", "FB GrantedPermission " + loginResult.getRecentlyGrantedPermissions().toString() +
                " Access Token " + loginResult.getAccessToken().getToken());
        fblogin(loginResult.getAccessToken().getToken());
    }*/

//    protected Call<LoginResponse> mFBLoginCall;

//    private String FBAccessToken;
    /*protected void fblogin(String FBAccessToken) {
        this.FBAccessToken = FBAccessToken;
//        showProgress(true);
        API api = HTTP.getAPI();
        if (api == null) {
            //TODO handle no inernet scenario;
            return;
        }
        mFBLoginCall = HTTP.getAPI().fblogin(FBAccessToken);
        mFBLoginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response) {
//                showProgress(false);
                mFBLoginCall = null;
                if (response.isSuccess()) {
                    SessionManager.setFBLoggedInUser(response.body().body.userDetails.user, response.body().body.token, response.body().body.society);
                    Intent intent = new Intent(Facebook.this, MainNavActivity.class);
                    if (getIntent().getIntExtra("requestcode", -1) == AbsBaseActivity.REQUEST_LOGIN) {
                        setResult(RESULT_OK);
                    } else {
                        startActivity(intent);
                    }
                    finish();
                    Log.e("TEMP",response.body().body.userDetails.toString());

                } else {
                    Log.e("TEMP",response.message());

//                    showLoginError(response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
//                showProgress(false);
                mFBLoginCall = null;
                Log.e(getClass().getSimpleName(),t.getMessage());

//                showLoginError(t.getMessage());
            }
        });
    }
*/
/*
    @Override
    public void onCancel() {
        Log.d(Facebook.class.getName(), "FB login cancelled");
    }

    @Override
    public void onError(FacebookException error) {
        error.printStackTrace();
        Log.d(Facebook.class.getName(), "FB login onError ");

    }
*/


//}
