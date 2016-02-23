package com.zircon.app.ui.assets;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zircon.app.model.Asset;
import com.zircon.app.ui.common.activity.nonav.AbsCABNoNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 19/02/16.
 */
public class AssetBookingActivity extends AbsCABNoNavActivity{

    interface IARGS {
        String ASSET = "asset";
        String ASSET_ID = "asset_id";
    }

    private Asset asset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        asset = getIntent().getParcelableExtra(IARGS.ASSET);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected String getExpandedTagLineText() {
        return asset.charges;
    }

    @Override
    protected String getExpandedHeaderText() {
        return asset.description;
    }

    @Override
    protected String getMainTitleText() {
        return asset.description;
    }

    @Override
    protected AbsFragment getFragment() {
        Bundle args = new Bundle();
        args.putString(IARGS.ASSET_ID,asset.id);
        return (AbsFragment) Fragment.instantiate(AssetBookingActivity.this,AssetBookingFragment.class.getName(),args);
    }
}
