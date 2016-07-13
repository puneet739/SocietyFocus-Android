package com.zircon.app.ui.common.activity.nonav;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.zircon.app.R;

/**
 * Created by Cbc-03 on 07/13/16.
 */
public abstract class BaseABNoNavActivity extends BaseNoNavActivity {

    protected abstract int getContentViewResID();

    protected abstract void initViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResID());
        setupFAB(getFABClickListener());

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        //Add back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        initViews();
    }

}
