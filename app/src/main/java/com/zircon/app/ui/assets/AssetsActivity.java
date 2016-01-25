package com.zircon.app.ui.assets;

import android.support.v4.app.Fragment;

import com.zircon.app.ui.common.AbsBaseCollapsableActionBar;
import com.zircon.app.ui.common.AbsFragment;
import com.zircon.app.ui.residents.MembersFragment;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AssetsActivity extends AbsBaseCollapsableActionBar {
    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, AssetsListFragment.class.getName());
    }
}
