package com.zircon.app.ui.assets.booking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zircon.app.model.Asset;
import com.zircon.app.ui.common.activity.nonav.BaseCABNoNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 19/02/16.
 */
public class AssetBookingActivity extends BaseCABNoNavActivity {

    public interface IARGS {
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
    protected View.OnClickListener getImageViewClickListener() {
        return null;
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }

    @Override
    protected String getCircleImageURL() {
        return asset.img;
    }

    @Override
    protected String getExpandedTagLineText() {
        return "₹ "+asset.charges;
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
        args.putParcelable(IARGS.ASSET,asset);
//        args.putString(IARGS.ASSET, asset.toString());
        return (AbsFragment) Fragment.instantiate(AssetBookingActivity.this, AssetBookingFragment.class.getName(), args);
    }
}
