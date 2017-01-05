package com.zircon.app.ui.profile;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zircon.app.R;
import com.zircon.app.model.LoginCredentials;
import com.zircon.app.model.User;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.ui.common.activity.nonav.BaseABNoNavActivity;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.Log;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Response;

public class Changepassword extends BaseABNoNavActivity {

    EditText mOldPassView;
    EditText mNewPassView;
    EditText mConfirmNewPassView;

    Button mChangePasswordButton;
    String oldPass;
    String newPass;
    String newPassConfirm;

    User user;

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }

    @Override
    protected int getContentViewResID() {
        return R.layout.activity_changepassword;
    }

    @Override
    protected void initViews() {
        setTitle("Change Password");

        mChangePasswordButton = (Button) findViewById(R.id.changepassword);
        mOldPassView = (EditText) findViewById(R.id.oldpassword);
        mNewPassView = (EditText) findViewById(R.id.newpassword);
        mConfirmNewPassView = (EditText) findViewById(R.id.confirmpassword);
        user = SessionManager.getLoggedInUser();
        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ChangePassword ", "Clicked!!");
                if (isInputValid()) {

                    Call<BaseResponse> call = HTTP.getAPI().modifyPassword(SessionManager.getToken(), oldPass, newPass, user.email);
                    call.enqueue(new AuthCallBack<BaseResponse>() {
                        @Override
                        protected void onAuthError() {

                        }

                        @Override
                        protected void parseSuccessResponse(Response<BaseResponse> response) {
                            if (response.isSuccess()) {
                                LoginCredentials credentials = SessionManager.getLoginCredentials();
                                credentials.password = newPass;
                                SessionManager.setLoginCredentials(credentials);
                                ClearFields();
                                Toast.makeText(Changepassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("Profile", "test " + response.message() + " " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            t.getLocalizedMessage();
                            Log.e("Profile", "test " + t.getLocalizedMessage());
                        }
                    });
                }
            }
        });
    }

    public boolean isInputValid() {
        boolean isValid = true;
        oldPass = mOldPassView.getText().toString().trim();
        newPass = mNewPassView.getText().toString().trim();
        newPassConfirm = mConfirmNewPassView.getText().toString().trim();

        if (TextUtils.isEmpty(oldPass)) {
            isValid = false;
            mOldPassView.setError("This field is necessary");
        } else if (TextUtils.isEmpty(newPass)) {
            isValid = false;
            mNewPassView.setError("This field is necessary");
        } else if (TextUtils.isEmpty(newPassConfirm)) {
            isValid = false;
            mConfirmNewPassView.setError("This field is necessary");
        } else if (!TextUtils.equals(newPass, newPassConfirm)) {
            isValid = false;
            mConfirmNewPassView.setError("Does not match new password");
        } else if (TextUtils.equals(newPass, oldPass)) {
            isValid = false;
            mNewPassView.setError("Old and New Password cannot be same");
        } else if (TextUtils.getTrimmedLength(newPass) < 2) {
            isValid = false;
            mNewPassView.setError("Cannot be less than 2 characters");
        }
        return isValid;
    }

    public void ClearFields() {
        mOldPassView.requestFocus();
        mOldPassView.setText("");
        mNewPassView.setText("");
        mConfirmNewPassView.setText("");
    }
}
