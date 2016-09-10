package com.zircon.app.ui.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AbsFragment extends Fragment{

    private static final String FRAG_NAME = "frag_name";
    private static final String FRAG_LOAD = "frag_load";

    protected FirebaseAnalytics firebaseAnalytics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle args = new Bundle();
        args.putString(FRAG_NAME,getClass().getSimpleName());
        firebaseAnalytics.logEvent(FRAG_LOAD,args);
    }
}
