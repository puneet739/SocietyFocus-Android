package com.zircon.app.ui.panel;

import android.support.v4.app.Fragment;

import com.zircon.app.ui.common.activity.nav.BaseCABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MembersNavActivity extends BaseCABNavActivity {


    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, MembersFragment.class.getName());
    }

    @Override
    protected String getPageTitle() {
        return "RWA Panel";
    }
}
