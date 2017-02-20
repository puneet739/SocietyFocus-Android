package com.zircon.app.ui.noticeboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.NoticeBoard;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.utils.datapasser.UserPasser;


public class NoticeDetailFragment extends AbsFragment {

    TextView mDescriptionView;
    private NoticeBoard notice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notice = UserPasser.getInstance().getSingleNotice();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notice_detail, container, false);
    }


}
