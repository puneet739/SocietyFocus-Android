package com.zircon.app.ui.carsearch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.CarSearch;
import com.zircon.app.model.User;
import com.zircon.app.model.response.CarSearchResponse;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Response;

public class CarSearchFragment extends AbsFragment {

    ImageButton mSearchButton;
    ImageButton mRemoveButton;
    EditText mCarNumberEditText;
    TextView mCarNumber, mParkingSlot, mName, mEmail, mPhone, mOccupation;

    View mView1, mView2;

    ImageView mImageView;

    int CarNumber = 0;
    InputMethodManager inputManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 70);
        lp.alignWithParent = true;
        mSearchButton = (ImageButton) view.findViewById(R.id.searchcar);
        mRemoveButton = (ImageButton) view.findViewById(R.id.removebutton);
        mCarNumberEditText = (EditText) view.findViewById(R.id.carnumberedit);
        mName = (TextView) view.findViewById(R.id.name);
        mEmail = (TextView) view.findViewById(R.id.email);
        mPhone = (TextView) view.findViewById(R.id.phone);
        mOccupation = (TextView) view.findViewById(R.id.occupation);
        mParkingSlot = (TextView) view.findViewById(R.id.parkingslot);
        mCarNumber = (TextView) view.findViewById(R.id.carno);

        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mView1 = view.findViewById(R.id.view1);
        mView2 = view.findViewById(R.id.view2);

        mImageView = (ImageView) view.findViewById(R.id.image);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateInputs();
            }
        });

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeText();
            }
        });

        mCarNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mRemoveButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mRemoveButton.setVisibility(View.GONE);
                }
            }
        });
    }

    protected void removeText() {
        mCarNumberEditText.setText("");
        mView2.setVisibility(View.GONE);
    }

    protected void ValidateInputs() {
        CarNumber = Integer.parseInt(mCarNumberEditText.getText().toString().trim());
        if (CarNumber == 0) {
            mCarNumberEditText.setError("This field is Required");
        } else {
            GetCarDetails(CarNumber);
        }
    }


    protected void GetCarDetails(int carnumber) {
        Call<CarSearchResponse> call = HTTP.getAPI().searchVehicleNumber(SessionManager.getToken(), carnumber);
        call.enqueue(new AuthCallBack<CarSearchResponse>() {
            @Override
            protected void onAuthError() {
                mView2.setVisibility(View.GONE);

            }

            @Override
            protected void parseSuccessResponse(Response<CarSearchResponse> response) {
                if (response.isSuccess()) {
                    if (response.body().body != null) {
                        setUpFields(response.body().body);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mView2.setVisibility(View.GONE);
            }
        });
    }

    protected void setUpFields(CarSearch CarDetails) {
        mView2.setVisibility(View.VISIBLE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        User user = CarDetails.user;
        if (user.profilePic != null) {
            Picasso.with(getContext()).load(user.profilePic).placeholder(R.drawable.ic_avatar).into(mImageView);
        }
        mName.setText(user.firstname + " " + (user.lastname == "" ? "" : user.lastname));
        mEmail.setText(user.email);
        mOccupation.setText(Html.fromHtml("(<i>" + user.occupation + "</i>)"));
        mPhone.setText(user.contactNumber);
        mCarNumber.setText(Html.fromHtml("Vehicle Number: <b>" + CarDetails.VehicleNumber + "</b>"));
        mParkingSlot.setText(Html.fromHtml("Parking Slot: <b>" + CarDetails.ParkingSlot + "</b>"));
    }
}
