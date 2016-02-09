package com.zircon.app.ui.assets;

import android.support.v4.app.Fragment;

import com.zircon.app.ui.common.AbsBaseCollapsableActionBarNavActivity;
import com.zircon.app.ui.common.AbsFragment;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AssetsNavActivity extends AbsBaseCollapsableActionBarNavActivity {
    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, AssetsListFragment.class.getName());
    }
}
