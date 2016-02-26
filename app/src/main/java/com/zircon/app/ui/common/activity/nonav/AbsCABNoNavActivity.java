package com.zircon.app.ui.common.activity.nonav;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.BuildConfig;
import com.zircon.app.R;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.SessionManager;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public abstract class AbsCABNoNavActivity extends BaseNoNavActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;


    private static final float SCALE_MINIMUM = 0.5f;

    private ImageView mCollapseImageView;
    private TextView mExpandedHeaderView;
    private TextView mExpandedTagLineView;

    AppBarLayout appBarLayout;


    protected RelativeLayout mFragmentLayout;
    protected AbsFragment mFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cab_no_nav_main);



        mCollapseImageView = (ImageView) findViewById(R.id.main_imageview_placeholder);
        String bgUrl = SessionManager.getLoggedInSociety().societypic;
        String title = SessionManager.getLoggedInSociety().name;


        Picasso.with(this).setIndicatorsEnabled(BuildConfig.DEBUG);
        Picasso.with(this).load(bgUrl).into(mCollapseImageView);

        setTitle(title);

        mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);

        mExpandedHeaderView = (TextView) findViewById(R.id.main_expanded_title);
        mExpandedTagLineView = (TextView) findViewById(R.id.main_expanded_subtitle);

        mExpandedHeaderView.setText(getExpandedHeaderText());
        mExpandedTagLineView.setText(getExpandedTagLineText());
        mTitle.setText(getMainTitleText());


        mToolbar.setTitle("");
        mAppBarLayout.addOnOffsetChangedListener(this);

        setSupportActionBar(mToolbar);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);


        mFragmentLayout = (RelativeLayout)findViewById(R.id.fragment_container);

        mFragment = getFragment();

        if (mFragment != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();

    }

    protected abstract String getExpandedTagLineText();

    protected abstract String getExpandedHeaderText();

    protected abstract String getMainTitleText();

    protected abstract AbsFragment getFragment();

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

}
