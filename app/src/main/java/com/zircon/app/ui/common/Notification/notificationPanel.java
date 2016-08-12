package com.zircon.app.ui.common.Notification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.ui.common.activity.nonav.BaseABNoNavActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class notificationPanel extends BaseABNoNavActivity {

    interface IARGS {
        String BODY = "body";
        String TITLE = "title";
        String IMAGE = "main_picture";
        String ICON = "icon";
    }

    ImageView mImageView;
    String ImageURL;
    Bitmap ImageBitmap;

    @Override
    protected int getContentViewResID() {
        return R.layout.notification_panel;
    }

    @Override
    protected void initViews() {
        ImageURL = getIntent().getStringExtra(IARGS.IMAGE);
        mImageView = (ImageView) findViewById(R.id.image);
        if (!TextUtils.isEmpty(ImageURL)) {

            Picasso.with(notificationPanel.this).load(ImageURL).into(mImageView);
        }
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
