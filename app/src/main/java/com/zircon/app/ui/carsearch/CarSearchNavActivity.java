package com.zircon.app.ui.carsearch;

import android.support.v4.app.Fragment;
import android.view.View;

import com.zircon.app.ui.common.activity.nav.BaseCABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

public class CarSearchNavActivity extends BaseCABNavActivity {

    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, CarSearchFragment.class.getName());
    }

    @Override
    protected String getPageTitle() {
        return "Car Search";
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
