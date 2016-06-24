package com.zircon.app.ui.common.activity.nav;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.zircon.app.BuildConfig;
import com.zircon.app.R;
import com.zircon.app.utils.SessionManager;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public abstract class BaseCABNavActivity extends BaseNavActivity {

    private ImageView mCollapseImageView;
    private LinearLayout mCollapsingLayout;

    @Override
    int getLayoutResID() {
        return R.layout.activity_cab_nav_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mCollapsingLayout = (LinearLayout) findViewById(R.id.collapse_content);

        mCollapseImageView = (ImageView) findViewById(R.id.society_bg);
        String bgUrl = SessionManager.getLoggedInSociety().societypic;


        Picasso.with(this).setIndicatorsEnabled(false);
        Picasso.with(this).load(bgUrl).into(mCollapseImageView);

        setTitle(getPageTitle());

    }

    protected abstract String getPageTitle();


    protected void setCollapsingContent(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800));
        mCollapsingLayout.addView(view);
        mCollapsingLayout.removeViewAt(0);
        mCollapsingLayout.requestLayout();
    }

}
