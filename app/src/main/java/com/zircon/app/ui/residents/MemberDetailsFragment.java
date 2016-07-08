package com.zircon.app.ui.residents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.BuildConfig;
import com.zircon.app.R;
import com.zircon.app.model.User;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.SessionManager;
import com.zircon.app.utils.datapasser.UserPasser;

/**
 * Created by jyotishman on 20/03/16.
 */
public class MemberDetailsFragment extends AbsFragment {

    private TextView mUseDdescriptionView;
    private TextView mUserAddressView;
    private TextView mUserEmailView;
    private TextView mUserPhoneView;

    private View mUserDescriptionLayout;
    private View mUserAddressLayout;
    private View mUserEmailLayout;
    private View mUserPhoneLayout;


    private ImageView mUserCallButton;
    private ImageView mUserSmsButton;
    private ImageView mUserEmailButton;
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user = UserPasser.getInstance().getUser();
        if (user == null) {
            throw new NullPointerException("user is null");
        }

        return inflater.inflate(R.layout.fragment_member_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUserAddressView = (TextView) view.findViewById(R.id.address);
        mUserPhoneView = (TextView) view.findViewById(R.id.phone);
        mUseDdescriptionView = (TextView) view.findViewById(R.id.description);
        mUserEmailView = (TextView) view.findViewById(R.id.email);

        mUserDescriptionLayout=view.findViewById(R.id.description_layout);
        mUserEmailLayout=view.findViewById(R.id.email_layout);
        mUserPhoneLayout=view.findViewById(R.id.phone_layout);
        mUserAddressLayout=view.findViewById(R.id.address_layout);

        mUserCallButton = (ImageView) view.findViewById(R.id.callbtn);
        mUserSmsButton = (ImageView) view.findViewById(R.id.smsbtn);
        mUserEmailButton = (ImageView) view.findViewById(R.id.emailbtn);

        mUseDdescriptionView.setText("" + (user.description == null ? "" : user.description));
        mUserPhoneView.setText("" + (user.contactNumber == null ? "" : user.contactNumber));
        mUserAddressView.setText("" + (user.address == null ? "" : user.address));
        mUserEmailView.setText(""+(user.email==null?"":user.email));

        mUserDescriptionLayout.setVisibility((user.description==null?View.GONE:View.VISIBLE));
        mUserPhoneLayout.setVisibility((user.contactNumber==null?View.GONE:View.VISIBLE));
        mUserEmailLayout.setVisibility((user.email==null?View.GONE:View.VISIBLE));
        mUserAddressLayout.setVisibility((user.address==null?View.GONE:View.VISIBLE));

        mUserCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AbsBaseActivity) getActivity()).call(user.contactNumber);
            }
        });
        mUserSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AbsBaseActivity) getActivity()).sms(user.contactNumber);
            }
        });
        mUserEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AbsBaseActivity) getActivity()).sendEmail(user.email);
            }
        });
    }
}
