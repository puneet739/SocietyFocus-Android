package com.zircon.app.ui.common.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jikoobaruah on 09/02/16.
 */
public abstract class AbsBaseDialogFormActivity extends AbsBaseActivity {

    protected abstract int getContentViewResID();

    protected abstract void initViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResID());
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.Ma);

        initViews();
    }

}
