package com.zircon.app.ui.noticeboard;

import android.support.v4.app.Fragment;
import android.view.View;

import com.zircon.app.ui.common.activity.nav.BaseCABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by Cbc-03 on 10/25/16.
 */
public class AllNoticesActivity extends BaseCABNavActivity {
    @Override
    protected String getPageTitle() {
        return "Notice Board";
    }

    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, AllNoticesFragment.class.getName());
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
