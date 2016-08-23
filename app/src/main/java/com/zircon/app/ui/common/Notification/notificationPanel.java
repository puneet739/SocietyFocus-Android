package com.zircon.app.ui.common.Notification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.response.NotificationResponse;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.activity.nonav.BaseABNoNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.SessionManager;
import com.zircon.app.utils.datapasser.UserPasser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class notificationPanel extends AbsFragment {

    interface IARGS {
        String BODY = "body";
        String TITLE = "title";
        String IMAGE = "main_picture";
        String ICON = "icon";
    }

    ImageView mImageView;
    String ImageURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_panel, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageURL = UserPasser.getInstance().getNotificationResponse().getMain_picture();
        UserPasser.getInstance().clear();

        mImageView = (ImageView) view.findViewById(R.id.image);
        if (!TextUtils.isEmpty(ImageURL))
            Picasso.with(getContext()).load(ImageURL).into(mImageView);
    }
}
