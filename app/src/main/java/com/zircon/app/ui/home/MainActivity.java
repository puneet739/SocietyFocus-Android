package com.zircon.app.ui.home;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.zircon.app.ui.common.AbsBaseNormalActionBar;
import com.zircon.app.ui.common.AbsFragment;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MainActivity extends AbsBaseNormalActionBar implements HomeFragment.OnFragmentInteractionListener {


    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment)Fragment.instantiate(this,HomeFragment.class.getName());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
