package com.zircon.app.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zircon.app.model.response.NotificationResponse;
import com.zircon.app.ui.common.Notification.notificationPanel;
import com.zircon.app.ui.common.activity.nav.BaseABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.SessionManager;
import com.zircon.app.utils.datapasser.UserPasser;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MainNavActivity extends BaseABNavActivity {

    NotificationResponse response;
    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(SessionManager.getLoggedInSociety().name);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }

    @Override
    protected AbsFragment getFragment() {
        response = UserPasser.getInstance().getNotificationResponse();
        if (response != null) {
            notify = response.isnotify();
            setTitle("Notification Panel");
        }
        if (notify) {
            return (AbsFragment) Fragment.instantiate(this, notificationPanel.class.getName());
        } else
            return (AbsFragment) Fragment.instantiate(this, HomeFragment.class.getName());
    }
}
