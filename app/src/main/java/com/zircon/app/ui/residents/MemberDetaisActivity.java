package com.zircon.app.ui.residents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;

import com.zircon.app.model.User;
import com.zircon.app.ui.common.activity.nonav.BaseCABNoNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.datapasser.UserPasser;

/**
 * Created by jyotishman on 20/03/16.
 */
public class MemberDetaisActivity extends BaseCABNoNavActivity {

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        user = UserPasser.getInstance().getUser();
        if (user == null){
            throw new NullPointerException("user is null");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }

    @Override
    protected String getCircleImageURL() {
        return user.profilePic;
    }

    @Override
    protected String getExpandedTagLineText() {
        return user.occupation;
    }

    @Override
    protected String getExpandedHeaderText() {
        return user.firstname + " " + (user.lastname != null ? user.lastname : "");
    }

    @Override
    protected String getMainTitleText() {
        return user.firstname + " " + (user.lastname != null ? user.lastname : "");
    }

    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, MemberDetailsFragment.class.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
