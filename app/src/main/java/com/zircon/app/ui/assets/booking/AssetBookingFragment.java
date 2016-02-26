package com.zircon.app.ui.assets.booking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.zircon.app.R;
import com.zircon.app.model.request.BookAsset;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.model.response.BookAssetResponse;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by jikoobaruah on 19/02/16.
 */
public class AssetBookingFragment extends AbsFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Snackbar snackBar;

    interface IARGS {
        String ASSET_ID = "asset_id";
    }

    EditText dateEditText;
    EditText timeEditText;
    EditText descriptionEditText;
    Button submitButton;

    String date;
    String time;
    String description;

    Calendar bookingTime;

    String assetID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetID = getArguments().getString(IARGS.ASSET_ID,null);
        if (assetID == null || assetID.trim().length()==0)
            throw new NullPointerException("assetid is null");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_asset_booking, null, false);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookingTime = Calendar.getInstance();

        dateEditText = (EditText) view.findViewById(R.id.date);
        timeEditText = (EditText) view.findViewById(R.id.time);
        descriptionEditText = (EditText) view.findViewById(R.id.description);
        submitButton = (Button) view.findViewById(R.id.book_now);


        dateEditText.setClickable(true);
        timeEditText.setClickable(true);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDPD();
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTPD();
            }
        });


        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {showDPD();}}});

        timeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) showTPD();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidInput()) {
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)(getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    submitAssetBooking();
                }
            }
        });
    }

    private void submitAssetBooking() {
        BookAsset request = new BookAsset(assetID, BaseResponse.API_SDF_2.format(bookingTime.getTime()),false,description,BookAsset.STATUS.ACTIVE);

        System.out.println(new Gson().toJson(request));
        Call<BookAssetResponse> call = HTTP.getAPI().saveAssetBooking(SessionManager.getToken(),request);
        call.enqueue(new AuthCallBack<BookAssetResponse>() {
            @Override
            protected void onAuthError() {
                //TODO handle this
            }

            @Override
            protected void parseSuccessResponse(Response<BookAssetResponse> response) {
                if (response.isSuccess()) {


                    timeEditText.setText("");
                    dateEditText.setText("");
                    descriptionEditText.setText("");

                    snackBar = Snackbar.make(submitButton, "Your request to book asset has been registered.", Snackbar.LENGTH_INDEFINITE).setAction("Continue.", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
                        }
                    });
                    snackBar.show();
                } else {
                    showRegistrationError();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
                showRegistrationError();

            }
        });
    }

    private void showRegistrationError() {
        snackBar = Snackbar.make(submitButton,"Sorry! There was a problem in registering your booking request.",Snackbar.LENGTH_INDEFINITE ).setAction("Continue.", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        snackBar.show();
    }

    private boolean isValidInput() {
        boolean isValid = true;

        date = dateEditText.getText().toString().trim();
        time = timeEditText.getText().toString().trim();
        description = descriptionEditText.getText().toString().trim();

        if (date == null || date.length() == 0 ){
            isValid = false;
            dateEditText.setError("Please fill this field");
        }

        if (time == null || time.length() == 0 ){
            if (isValid)
                dateEditText.setError("Please fill this field");
            isValid = false;
        }

        if (description == null || description.length() == 0 ){
            if (isValid)
                descriptionEditText.setError("Please fill this field");
            isValid = false;
        }

        return isValid;
    }

    DatePickerDialog dpd = null;
    private void showDPD() {
        if (dpd != null && dpd.isVisible()) {
            dpd.dismiss();
            dpd = null;
        }
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                AssetBookingFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));

        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

    }

    TimePickerDialog tpd = null;
    private void showTPD() {
        if (tpd != null && tpd.isVisible()){
            tpd.dismiss();
            tpd = null;
        }
        Calendar now = Calendar.getInstance();
        tpd = TimePickerDialog.newInstance(AssetBookingFragment.this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));

        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateEditText.setText(new SimpleDateFormat("dd-MMM-yyyy").format(c.getTime()));


        bookingTime.set(Calendar.YEAR, year);
        bookingTime.set(Calendar.MONTH, monthOfYear);
        bookingTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE, minute);
        timeEditText.setText(new SimpleDateFormat("hh:mm a").format(c.getTime()));

        bookingTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        bookingTime.set(Calendar.MINUTE, minute);
        bookingTime.set(Calendar.SECOND, 0);


    }
}
