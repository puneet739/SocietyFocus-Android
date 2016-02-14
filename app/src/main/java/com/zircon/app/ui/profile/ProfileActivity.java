package com.zircon.app.ui.profile;

import com.zircon.app.R;
import com.zircon.app.ui.common.AbsBaseDialogFormActivity;

/**
 * Created by jikoobaruah on 09/02/16.
 */
public class ProfileActivity extends AbsBaseDialogFormActivity {


    @Override
    protected int getContentViewResID() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initViews() {

        setTitle("Edit Profile");

    }
}
