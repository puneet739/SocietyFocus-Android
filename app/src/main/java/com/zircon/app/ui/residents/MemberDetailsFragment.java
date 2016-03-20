package com.zircon.app.ui.residents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zircon.app.R;
import com.zircon.app.ui.common.fragment.AbsFragment;

/**
 * Created by jyotishman on 20/03/16.
 */
public class MemberDetailsFragment extends AbsFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_detail, container, false);
    }
}
