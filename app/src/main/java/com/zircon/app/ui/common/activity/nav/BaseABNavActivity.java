package com.zircon.app.ui.common.activity.nav;

import com.zircon.app.R;
import com.zircon.app.ui.common.activity.nav.BaseNavActivity;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public abstract class BaseABNavActivity extends BaseNavActivity {

    @Override
    int getLayoutResID() {
        return R.layout.activity_main;
    }
}
