package com.zircon.app.ui.complaint;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.ui.common.activity.nonav.BaseCABNoNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 15/02/16.
 */
public class ComplaintDetailsActivity extends BaseCABNoNavActivity {

    interface IBundle{
        String ID = "id";
        String TITLE = "title";
        String DESCRIPTION = "description";
    }

    private String mID;
    private String mTitle;
    private String mDescription;

    private TextView mCollapseHeader;
    private TextView mCollapseTitle;
    private TextView mCollapseDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        mID = getIntent().getStringExtra(IBundle.ID);
        mTitle = getIntent().getStringExtra(IBundle.TITLE);
        mDescription = getIntent().getStringExtra(IBundle.DESCRIPTION);

        if (TextUtils.isEmpty(mID) || TextUtils.isEmpty(mTitle) || TextUtils.isEmpty(mDescription))
            throw new NullPointerException("ABCDEF ......");

        super.onCreate(savedInstanceState);

    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintDetailsActivity.this);
                builder.setIcon(R.drawable.ic_1_1).setTitle("Add comment").create().show();
            }
        };
    }

    @Override
    protected String getCircleImageURL() {
        return null;
    }

    @Override
    protected String getExpandedTagLineText() {
        return mTitle;
    }

    @Override
    protected String getExpandedHeaderText() {
        return "Complaint ID : "+mID;
    }

    @Override
    protected String getMainTitleText() {
        return mTitle;
    }

    @Override
    protected AbsFragment getFragment() {
        Bundle args = new Bundle();
        args.putString(ComplaintDetailsFragment.IARGS.COMPLAINT_ID,mID);
        return (AbsFragment) Fragment.instantiate(this, ComplaintDetailsFragment.class.getName(),args);

    }
}
