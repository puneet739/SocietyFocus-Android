package com.zircon.app.ui.complaint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.ui.common.activity.nav.BaseCABNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jikoobaruah on 15/02/16.
 */
public class ComplaintDetailsActivity extends BaseCABNavActivity {

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

        createCollapseContent();

    }

    private void createCollapseContent() {
        View v = LayoutInflater.from(this).inflate(R.layout.layout_complaint_collapse_header,null,false);

        mCollapseHeader = (TextView) v.findViewById(R.id.header);
        mCollapseTitle = (TextView) v.findViewById(R.id.title);
        mCollapseDescription = (TextView) v.findViewById(R.id.description);

        mCollapseHeader.setText("Complaint id : "+mID);
        mCollapseTitle.setText(mTitle);
        mCollapseDescription.setText(mDescription);
//        setCollapsingContent(v);
    }

    @Override
    protected AbsFragment getFragment() {

        Bundle args = new Bundle();
        args.putString(IBundle.ID,mID);
        return (AbsFragment) Fragment.instantiate(this, ComplaintDetailsFragment.class.getName(),args);

    }
}
