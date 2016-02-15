package com.zircon.app.ui.complaint;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zircon.app.R;
import com.zircon.app.model.request.Complaint;
import com.zircon.app.model.User;
import com.zircon.app.model.response.ComplaintResponse;
import com.zircon.app.ui.common.activity.AbsBaseDialogFormActivity;
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
                    User loogedInUser = SessionManager.getLoggedInUser();
                    Complaint complaint = new Complaint();
                    complaint.complainerName = loogedInUser.firstname + (loogedInUser.lastname != null ? " "+loogedInUser.lastname : "");
                    complaint.complainerContactNo = loogedInUser.contactNumber;
                    complaint.complainerEmail=loogedInUser.email;
                    complaint.title = mcomplaintTitleView.getText().toString().trim();
                    complaint.description = mcomplaintView.getText().toString().trim();

                    Call<ComplaintResponse> call = HTTP.getAPI().saveComplaint(SessionManager.getToken() , complaint);
                    call.enqueue(new Callback<ComplaintResponse>() {
                        @Override
                        public void onResponse(Response<ComplaintResponse> response) {
                            response.isSuccess();
                            finish();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            t.getLocalizedMessage();
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
