package com.zircon.app.ui.residents;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.User;
import com.zircon.app.ui.common.activity.nonav.BaseABNoNavActivity;
import com.zircon.app.utils.datapasser.UserPasser;

public class ImageViewActivity extends BaseABNoNavActivity {

    @Override
    protected int getContentViewResID() {
        return R.layout.activity_image_view;
    }

    private User user;
    ImageView mImageView;

    @Override
    protected void initViews() {

        user = UserPasser.getInstance().getUser();
        if (user == null) {
            throw new NullPointerException("user is null");
        }
        setTitle(user.firstname);

        mImageView = (ImageView) findViewById(R.id.image_view);
        String circleURL = user.profilePic;
        if (circleURL != null && circleURL.trim().length() > 0) {

            Picasso.with(this).load(circleURL).into(mImageView);
        }
    }


    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
