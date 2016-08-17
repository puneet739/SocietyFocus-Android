package com.zircon.app.ui.common.activity.nonav;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.zircon.app.R;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by Cbc-03 on 07/13/16.
 */
public abstract class BaseABNoNavActivity extends BaseNoNavActivity {

    protected abstract int getContentViewResID();

    protected abstract void initViews();

    /*   protected AbsFragment mFragment;

       protected abstract AbsFragment getFragment();
   */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResID());
        setupFAB(getFABClickListener());

        AdView mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("E272D0E02F9646D08082058A0B814575")
                .build();
        if (mAdView != null)
            mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        //Add back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

       /* mFragment = getFragment();

        if (mFragment != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();*/
        initViews();
    }

}
