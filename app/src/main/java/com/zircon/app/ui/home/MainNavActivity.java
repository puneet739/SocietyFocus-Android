package com.zircon.app.ui.home;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.zircon.app.ui.common.activity.nav.BaseABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MainNavActivity extends BaseABNavActivity implements HomeFragment.OnFragmentInteractionListener {


    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment)Fragment.instantiate(this,HomeFragment.class.getName());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
