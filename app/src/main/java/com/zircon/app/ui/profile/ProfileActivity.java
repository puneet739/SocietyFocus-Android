package com.zircon.app.ui.profile;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zircon.app.R;
import com.zircon.app.model.LoginCredentials;
import com.zircon.app.model.User;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.model.response.UserResponse;
import com.zircon.app.ui.common.activity.nonav.BaseABNoNavActivity;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.Log;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 09/02/16.
 */
public class ProfileActivity extends BaseABNoNavActivity {

    EditText mFirstNameView;
    EditText mAddressView;
    EditText mPhoneNoView;
    EditText mAboutYourselfView;

    EditText mOldPassView;
    EditText mNewPassView;
    EditText mConfirmNewPassView;

    String firstName;
    String address;
    String phoneNo;
    String aboutyourself;

    String oldPass;
    String newPass;
    String newPassConfirm;
    ImageButton back_button;


    User user;

    @Override
    protected int getContentViewResID() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initViews() {

        setTitle("Edit Profile");

        mFirstNameView = (EditText) findViewById(R.id.profile_name);
        mAddressView = (EditText) findViewById(R.id.profile_address);
        mPhoneNoView = (EditText) findViewById(R.id.profile_phoneno);
        mAboutYourselfView = (EditText) findViewById(R.id.profile_aboutyourself);

        user = SessionManager.getLoggedInUser();

        mFirstNameView.setText("" + user.firstname);
        mAddressView.setText("" + (user.address==null?"":user.address));
        mPhoneNoView.setText("" + (user.contactNumber==null?"":user.contactNumber));
        mAboutYourselfView.setText("" + (user.description==null?"":user.description));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.temp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action:
                //  this.finish();
                onChangeProfileSubmit(item.getActionView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onChangeProfileSubmit(View view) {
        firstName = mFirstNameView.getText().toString().trim();
        address = mAddressView.getText().toString().trim();
        phoneNo = mPhoneNoView.getText().toString().trim();
        aboutyourself = mAboutYourselfView.getText().toString().trim();

        boolean isDataChanged = false;
        if ((firstName != null) && (firstName.length() > 0) && (!firstName.equals(user.firstname))) {
            user.firstname = firstName;
            isDataChanged = true;
        }
        if ((address != null) && (address.length() > 0) && (!address.equals(user.lastname))) {
            user.address = address;
            isDataChanged = true;
        }
        if ((phoneNo != null) && (phoneNo.length() > 0) && (!phoneNo.equals(user.contactNumber))) {
            user.contactNumber = phoneNo;
            isDataChanged = true;
        }
        if ((aboutyourself != null) && (aboutyourself.length() > 0) && (!aboutyourself.equals(user.address))) {
            user.description = aboutyourself;
            isDataChanged = true;
        }

        if (!isDataChanged)
            return;

        Call<UserResponse> call = HTTP.getAPI().modifyUser(SessionManager.getToken(), user);
        call.enqueue(new AuthCallBack<UserResponse>() {
            @Override
            protected void onAuthError() {
                //TODO handle this
            }

            @Override
            protected void parseSuccessResponse(Response<UserResponse> response) {
                if (response.isSuccess()) {
                    SessionManager.setLoggedInUser(user);
                    Toast.makeText(ProfileActivity.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }

   /* public void onChangePassword(View view) {
        mOldPassView = (EditText) findViewById(R.id.profile_old_pass);
        mNewPassView = (EditText) findViewById(R.id.profile_new_pass);
        mConfirmNewPassView = (EditText) findViewById(R.id.profile_confirm_new_pass);

        oldPass = mOldPassView.getText().toString();
        newPass = mNewPassView.getText().toString();
        newPassConfirm = mConfirmNewPassView.getText().toString();

        if (TextUtils.isEmpty(oldPass)) {
            mOldPassView.setError("Enter this field");
            mOldPassView.requestFocus();
            return;
        } else if (!oldPass.equals(SessionManager.getLoginCredentials().password)) {
            mOldPassView.setError("Invalid Password");
            mOldPassView.requestFocus();
            return;
        } else if (TextUtils.isEmpty(newPass)) {
            mNewPassView.setError("Enter this field");
            mNewPassView.requestFocus();
            return;
        } else if (!newPass.equals(newPassConfirm)) {
            mConfirmNewPassView.setError("Passwords donot match");
            mConfirmNewPassView.requestFocus();
            return;
        } else {

            Call<BaseResponse> call = HTTP.getAPI().modifyPassword(SessionManager.getToken(), oldPass, newPass, SessionManager.getLoggedInUser().email);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Response<BaseResponse> response) {
                    if (response.isSuccess()) {
                        LoginCredentials credentials = SessionManager.getLoginCredentials();
                        credentials.password = newPass;
                        SessionManager.setLoginCredentials(credentials);
                        Toast.makeText(ProfileActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.e("Profile","test "+response.message()+" "+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.getLocalizedMessage();
                    Log.e("Profile","test "+t.getLocalizedMessage());
                }
            });

        }
    }*/

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
