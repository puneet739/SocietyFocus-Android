package com.zircon.app.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;
/*
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.iid.FirebaseInstanceId;*/
import com.google.android.gms.ads.MobileAds;
import com.zircon.app.R;
import com.zircon.app.ZirconApp;
import com.zircon.app.model.LoginCredentials;
import com.zircon.app.ui.common.activity.AbsLoginActivity;
import com.zircon.app.utils.AppManager;
import com.zircon.app.utils.Log;
import com.zircon.app.utils.SessionManager;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class SplashActivity extends AbsLoginActivity {

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7208785423388103~4681070926");
        setContentView(R.layout.activity_splash);

        /*new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                return FirebaseInstanceId.getInstance().getToken();
            }

            @Override
            protected void onPostExecute(String s) {
                Log.e("Token","Token "+s);
            }
        }.execute(null,null,null);*/

        mProgressView = findViewById(R.id.login_progress);

        if (AppManager.isFirstTimeLaunchAfterInstall()) {
            //TODO configuration logic;
            showLogin();
        } else
            // check if user is logged in already
            if (SessionManager.isUserLoggedIn()) {
                if ((SessionManager.isUserSocialLoggedIn())&&(SessionManager.getLogInSocial().equalsIgnoreCase("Facebook"))) {
                    fblogin(SessionManager.getLogInSocialToken());
                } else {
                    LoginCredentials loginCredentials = SessionManager.getLoginCredentials();
                    login(loginCredentials);
                }
            } else {
                CountDownTimer timer = new CountDownTimer(3000, 3000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        showLogin();

                    }
                };
                showProgress(true);
                timer.start();
            }
    }

    private void showLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent,
                ActivityOptions.makeCustomAnimation(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right).toBundle());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showProgress(false);
    }

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

//    private boolean checkPlayServices() {
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Toast.makeText(ZirconApp.getAppContext(), "This device doesn't support Play services, App will not work normally",
//                        Toast.LENGTH_LONG).show();
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void showLoginError(String errormessage) {
        showLogin();
    }
}
