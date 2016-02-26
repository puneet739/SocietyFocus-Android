package com.zircon.app.ui.complaint;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.zircon.app.R;
import com.zircon.app.model.request.Complaint;
import com.zircon.app.model.User;
import com.zircon.app.model.response.ComplaintResponse;
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
public class ComplaintActivity  extends AbsBaseDialogFormActivity {
    private EditText mcomplaintTitleView;
    private EditText mcomplaintView;
    private Button mSubmitButton;
    private Button mCancelButton;
    private Snackbar snackBar;

    @Override
    protected int getContentViewResID() {
        return R.layout.activity_complaint_new;
    }

    @Override
    protected void initViews() {
        setTitle("Lodge a complaint");
        mcomplaintTitleView = (EditText) findViewById(R.id.complaint_input_title);
        mcomplaintView = (EditText) findViewById(R.id.complaint_input);

        mSubmitButton = (Button) findViewById(R.id.ok);
        mCancelButton = (Button) findViewById(R.id.cancel);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcomplaintTitleView.setError(null);
                mcomplaintView.setError(null);

                if (isValidInput()){

                    if(snackBar != null && snackBar.isShown()){
                        snackBar.dismiss();
                    }

                    View view = ComplaintActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        view.clearFocus();
                    }

                    User loggedInUser = SessionManager.getLoggedInUser();
                    String complainerName = loggedInUser.firstname + (loggedInUser.lastname != null ? " "+loggedInUser.lastname : "");
                    String complainerContactNo = loggedInUser.contactNumber;
                    String complainerEmail=loggedInUser.email;
                    String title = mcomplaintTitleView.getText().toString().trim();
                    String description = mcomplaintView.getText().toString().trim();
                    Complaint complaint = new Complaint(title,description,complainerName,complainerContactNo,complainerEmail);


                    Call<ComplaintResponse> call = HTTP.getAPI().saveComplaint(SessionManager.getToken() , complaint);
                    call.enqueue(new AuthCallBack<ComplaintResponse>() {
                        @Override
                        protected void onAuthError() {

                        }

                        @Override
                        protected void parseSuccessResponse(Response<ComplaintResponse> response) {
                            if (response.isSuccess()){

                                mcomplaintTitleView.setText("");
                                mcomplaintView.setText("");

                                snackBar = Snackbar.make(mSubmitButton,"Your complaint with COMPLAINT ID : "+response.body().body.complaintid + " has been registered.",Snackbar.LENGTH_INDEFINITE ).setAction("Continue.", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                });
                                snackBar.show();
                            }else{
                                showComplaintRegistrationError();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            showComplaintRegistrationError();
                        }
                    });

                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showComplaintRegistrationError() {
        snackBar = Snackbar.make(mSubmitButton,"Sorry! There was a problem in registering your complaint.",Snackbar.LENGTH_INDEFINITE ).setAction("Continue.", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        snackBar.show();
    }

    private boolean isValidInput() {
        boolean isValid = true;
        String title = mcomplaintTitleView.getText().toString();
        String description = mcomplaintView.getText().toString();

        if (title == null || title.trim().length() == 0){
            mcomplaintTitleView.setError("This field is necessary");
            isValid = false;
        }

        if (description == null || description.trim().length() == 0){
            mcomplaintView.setError("This field is necessary");
            if (isValid)
                mcomplaintView.requestFocus();
            isValid = false;
        }

        return  isValid;
    }


}
