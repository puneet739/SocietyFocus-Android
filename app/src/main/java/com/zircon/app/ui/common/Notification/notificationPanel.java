package com.zircon.app.ui.common.Notification;

import android.view.View;

import com.zircon.app.R;
import com.zircon.app.ui.common.activity.nonav.BaseABNoNavActivity;


public class notificationPanel extends BaseABNoNavActivity {
    @Override
    protected int getContentViewResID() {
        return R.layout.notification_panel;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
