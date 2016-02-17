package com.zircon.app.ui.profile;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.LoginCredentials;
import com.zircon.app.model.User;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.model.response.UserResponse;
import com.zircon.app.ui.common.activity.AbsBaseDialogFormActivity;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 09/02/16.
 */
public class ProfileActivity extends AbsBaseDialogFormActivity {

    TextView mFirstNameView;
    TextView mLastNameView;
    TextView mContactNoView;
    TextView mAddressView;


    TextView mOldPassView;
    TextView mNewPassView;
    TextView mConfirmNewPassView;

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

        setTitle("Edit Profile");

        mFirstNameView = (TextView) findViewById(R.id.firstname);
        mLastNameView = (TextView) findViewById(R.id.lastname);
        mContactNoView = (TextView) findViewById(R.id.mobile);
        mAddressView = (TextView) findViewById(R.id.address);

        user = SessionManager.getLoggedInUser();

        mFirstNameView.setText(user.firstname);
        mLastNameView.setText(user.lastname);
        mContactNoView.setText(user.contactNumber);
        mAddressView.setText(user.address);

    }

    public void onChangeProfileSubmit(View view) {
        firstName = mFirstNameView.getText().toString().trim();
        lastName = mLastNameView.getText().toString().trim();
        contactNo = mContactNoView.getText().toString().trim();
        address = mAddressView.getText().toString().trim();


        boolean isDataChanged = false;
        if (firstName != null && firstName.length() > 0 && !firstName.equals(user.firstname)) {
            user.firstname = firstName;
            isDataChanged = true;
        }
        if (lastName != null && lastName.length() > 0 && !lastName.equals(user.lastname)) {
            user.lastname = lastName;
            isDataChanged = true;
        }
        if (contactNo != null && contactNo.length() > 0 && !contactNo.equals(user.contactNumber)) {
            user.contactNumber = contactNo;
            isDataChanged = true;
        }
        if (address != null && address.length() > 0 && !address.equals(user.address)){
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
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }

    public void onChangePassword(View view){
        mOldPassView = (TextView) findViewById(R.id.old_pass);
        mNewPassView = (TextView) findViewById(R.id.new_pass);
        mConfirmNewPassView = (TextView) findViewById(R.id.confirm_new_pass);

        oldPass = mOldPassView.getText().toString();
        newPass = mNewPassView.getText().toString();
        newPassConfirm = mConfirmNewPassView.getText().toString();

        if (!oldPass.equals(SessionManager.getLoginCredentials().password)){
            mOldPassView.setError("Invalid Password");
            mOldPassView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(oldPass)){
            mOldPassView.setError("Enter this field");
            mOldPassView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newPass)){
            mNewPassView.setError("Enter this field");
            mNewPassView.requestFocus();
            return;
        }

        if (!newPass.equals(newPassConfirm)){
            mConfirmNewPassView.setError("Passwords donot match");
            mConfirmNewPassView.requestFocus();
            return;
        }

        Call<BaseResponse> call = HTTP.getAPI().modifyPassword(SessionManager.getToken(),oldPass,newPass,SessionManager.getLoggedInUser().email);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response) {
                if (response.isSuccess()) {
                    LoginCredentials credentials = SessionManager.getLoginCredentials();
                    credentials.password = newPass;
                    SessionManager.setLoginCredentials(credentials);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }
}
