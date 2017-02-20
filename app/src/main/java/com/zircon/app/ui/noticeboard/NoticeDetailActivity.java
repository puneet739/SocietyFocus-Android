package com.zircon.app.ui.noticeboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zircon.app.model.NoticeBoard;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.ui.common.activity.nonav.BaseCABNoNavActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.datapasser.UserPasser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeDetailActivity extends BaseCABNoNavActivity {

    private NoticeBoard notice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        notice = UserPasser.getInstance().getSingleNotice();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }

    @Override
    protected View.OnClickListener getImageViewClickListener() {
        return null;
    }

    @Override
    protected String getCircleImageURL() {
        return notice.getImage_url1();
    }

    @Override
    protected String getExpandedTagLineText() {
        String time=null;
        try {
            Date date = BaseResponse.API_SDF.parse(notice.getCreationdate());
            time=new SimpleDateFormat("dd MMM HH:mm").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Updated at "+time+" by "+notice.getUser().firstname + " " + (notice.getUser().lastname == "" ? "" : notice.getUser().lastname);
    }

    @Override
    protected String getExpandedHeaderText() {
        return null;
    }

    @Override
    protected String getMainTitleText() {
        return null;
    }

    @Override
    protected AbsFragment getFragment() {
        return (AbsFragment) Fragment.instantiate(this, NoticeDetailFragment.class.getName());
    }
}
