package com.zircon.app.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zircon.app.ui.common.activity.nav.BaseABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.SessionManager;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MainNavActivity extends BaseABNavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(SessionManager.getLoggedInSociety().name);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment)Fragment.instantiate(this,HomeFragment.class.getName());
    }

}
