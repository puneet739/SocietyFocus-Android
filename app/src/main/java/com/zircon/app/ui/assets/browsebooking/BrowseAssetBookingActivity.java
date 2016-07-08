package com.zircon.app.ui.assets.browsebooking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zircon.app.ui.common.activity.nav.BaseABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 03/02/16.
 */
public class BrowseAssetBookingActivity extends BaseABNavActivity {

    @Override
    protected AbsFragment getFragment() {
        Bundle args = new Bundle();
        args.putString(BookingCalendarFragment.ARGS.ASSET_ID , 100+"");
        return (AbsFragment) Fragment.instantiate(this,BookingCalendarFragment.class.getName(),args);
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
