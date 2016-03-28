package com.zircon.app.ui.complaint;

import android.support.v4.app.Fragment;
import android.view.View;

import com.zircon.app.ui.common.activity.nav.BaseCABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AllComplaintsActivity extends BaseCABNavActivity {


    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, AllComplaintsFragment.class.getName());
    }

    @Override
    protected String getPageTitle() {
        return "Complaints";
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
