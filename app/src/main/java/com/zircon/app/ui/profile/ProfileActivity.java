package com.zircon.app.ui.profile;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zircon.app.R;
import com.zircon.app.model.LoginCredentials;
import com.zircon.app.model.User;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.model.response.UserResponse;
import com.zircon.app.ui.common.activity.AbsBaseDialogFormActivity;
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
public class ProfileActivity extends AbsBaseDialogFormActivity {

    EditText mFirstNameView;
    EditText mLastNameView;
    EditText mContactNoView;
    EditText mAddressView;

    EditText mOldPassView;
    EditText mNewPassView;
    EditText mConfirmNewPassView;

    String firstName;
    String lastName;
    String contactNo;
    String address;

    String oldPass;
    String newPass;
    String newPassConfirm;


    User user;

    @Override
    protected int getContentViewResID() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initViews() {

        setTitle("Profile Settings");

        mFirstNameView = (EditText) findViewById(R.id.profile_firstname);
        mLastNameView = (EditText) findViewById(R.id.profile_lastname);
        mContactNoView = (EditText) findViewById(R.id.profile_mobile);
        mAddressView = (EditText) findViewById(R.id.profile_address);

        user = SessionManager.getLoggedInUser();

        mFirstNameView.setText("" + user.firstname);
        mLastNameView.setText("" + user.lastname);
        mContactNoView.setText("" + user.contactNumber);
        mAddressView.setText("" + user.address);

    }

    public void onChangeProfileSubmit(View view) {
        firstName = mFirstNameView.getText().toString().trim();
        lastName = mLastNameView.getText().toString().trim();
        contactNo = mContactNoView.getText().toString().trim();
        address = mAddressView.getText().toString().trim();

        boolean isDataChanged = false;
        if ((firstName != null) && (firstName.length() > 0) && (!firstName.equals(user.firstname))) {
            user.firstname = firstName;
            isDataChanged = true;
        }
        if ((lastName != null) && (lastName.length() > 0) && (!lastName.equals(user.lastname))) {
            user.lastname = lastName;
            isDataChanged = true;
        }
        if ((contactNo != null) && (contactNo.length() > 0) && (!contactNo.equals(user.contactNumber))) {
            user.contactNumber = contactNo;
            isDataChanged = true;
        }
        if ((address != null) && (address.length() > 0) && (!address.equals(user.address))) {
            user.address = address;
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

    public void onChangePassword(View view) {
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
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
