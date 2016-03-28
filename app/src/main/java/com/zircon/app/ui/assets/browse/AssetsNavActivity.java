package com.zircon.app.ui.assets.browse;

import android.support.v4.app.Fragment;
import android.view.View;

import com.zircon.app.ui.common.activity.nav.BaseCABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AssetsNavActivity extends BaseCABNavActivity {


    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, AssetsListFragment.class.getName());
    }

    @Override
    protected String getPageTitle() {
        return "Assets";
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
