package com.zircon.app.ui.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zircon.app.R;
import com.zircon.app.utils.SessionManager;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public abstract class AbsBaseCollapsableActionBarNavActivity extends AbsBaseNavActivity {

    private ImageView mCollapseImageView;
    private LinearLayout mCollapsingLayout;

    @Override
    int getLayoutResID() {
        return R.layout.activity_collapsable_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mCollapsingLayout = (LinearLayout) findViewById(R.id.collapse_content);

        mCollapseImageView = (ImageView) findViewById(R.id.society_bg);
        String bgUrl = SessionManager.getLoggedInSociety().societypic;
        String title = SessionManager.getLoggedInSociety().name;

        ImageLoader.getInstance().displayImage(bgUrl, mCollapseImageView);

        setTitle(title);

    }


    protected void setCollapsingContent(View view){
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,800));
        mCollapsingLayout.addView(view);
        mCollapsingLayout.removeViewAt(0);
        mCollapsingLayout.requestLayout();
    }

}
