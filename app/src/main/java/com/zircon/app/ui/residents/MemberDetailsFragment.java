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

    private TextView mUserAddressView;
    private TextView mUseDdescriptionView;

    private TextView mUserPhoneView;
    private ImageView mUserCallView;
    private ImageView mUserSmsView;
    private ImageView mUserEmailView;
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
        mUserPhoneView = (TextView) view.findViewById(R.id.phonenumber);
        mUserCallView = (ImageView) view.findViewById(R.id.call);
        mUserSmsView = (ImageView) view.findViewById(R.id.sms);
        mUserEmailView = (ImageView) view.findViewById(R.id.email);

        mUseDdescriptionView = (TextView) view.findViewById(R.id.description);

        mUseDdescriptionView.setText(""+(user.description==null?"":user.description));
        mUserPhoneView.setText(""+(user.contactNumber==null?"":user.contactNumber));
        mUserAddressView.setText(""+(user.address==null?"":user.address));

        mUserCallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AbsBaseActivity) getActivity()).call(user.contactNumber);
            }
        });
        mUserSmsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AbsBaseActivity)getActivity()).sms(user.contactNumber);
            }
        });
        mUserEmailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AbsBaseActivity)getActivity()).sendEmail(user.email);
            }
        });
    }
}
