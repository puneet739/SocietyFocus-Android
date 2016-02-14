package com.zircon.app.ui.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zircon.app.R;
import com.zircon.app.utils.SessionManager;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public abstract class AbsBaseCollapsableActionBarNavActivity extends AbsBaseNavActivity {

    private ImageView mCollapseImageView;

    @Override
    int getLayoutResID() {
        return R.layout.activity_collapsable_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mCollapseImageView = (ImageView) findViewById(R.id.society_bg);
        String bgUrl = SessionManager.getLoggedInSociety().societypic;
        String title = SessionManager.getLoggedInSociety().name;

        ImageLoader.getInstance().displayImage(bgUrl, mCollapseImageView);

        setTitle(title);


    }

}
