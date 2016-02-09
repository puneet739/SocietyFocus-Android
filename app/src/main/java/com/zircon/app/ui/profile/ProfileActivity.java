package com.zircon.app.ui.profile;

import android.os.Bundle;
import android.view.ViewGroup;

import com.zircon.app.R;
import com.zircon.app.ui.common.AbsBaseActivity;

/**
 * Created by jikoobaruah on 09/02/16.
 */
public class ProfileActivity extends AbsBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_profile);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    }
}
