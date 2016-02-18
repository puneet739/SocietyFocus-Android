package com.zircon.app.ui.assets;

import android.support.v4.app.Fragment;

import com.zircon.app.ui.common.activity.nonav.AbsCABNoNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 19/02/16.
 */
public class AssetBookingActivity extends AbsCABNoNavActivity{
    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(AssetBookingActivity.this,AssetBookingFragment.class.getName());
    }
}
