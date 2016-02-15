package com.zircon.app.ui.complaint;

import android.support.v4.app.Fragment;

import com.zircon.app.ui.common.activity.AbsBaseCollapsableActionBarNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AllComplaintsActivity extends AbsBaseCollapsableActionBarNavActivity {


    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, AllComplaintsFragment.class.getName());
    }
}
