package com.zircon.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zircon.app.ui.common.activity.nonav.BaseABNoNavActivity;

public class Changepassword extends BaseABNoNavActivity {

    EditText mOldPassView;
    EditText mNewPassView;
    EditText mConfirmNewPassView;

    Button mChangePasswordButton;
    String oldPass;
    String newPass;
    String newPassConfirm;


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

        mChangePasswordButton= (Button) findViewById(R.id.changepassword);
        mOldPassView= (EditText) findViewById(R.id.oldpassword);
        mNewPassView= (EditText) findViewById(R.id.newpassword);
        mConfirmNewPassView= (EditText) findViewById(R.id.confirmpassword);

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputValid()){

                }
            }
        });
    }
    public boolean isInputValid(){
        boolean isValid = true;
        oldPass=mOldPassView.getText().toString().trim();
        newPass=mNewPassView.getText().toString().trim();
        newPassConfirm=mConfirmNewPassView.getText().toString().trim();
        if(TextUtils.isEmpty(oldPass))
        {
            isValid=false;
            mOldPassView.setError("This field is necessary");
        }
        else if(TextUtils.isEmpty(newPass)){
            isValid=false;
            mNewPassView.setError("This field is necessary");
        }
        else if(TextUtils.isEmpty(newPassConfirm)){
            isValid=false;
            mNewPassView.setError("This field is necessary");
        }
        return isValid;
    }
}
