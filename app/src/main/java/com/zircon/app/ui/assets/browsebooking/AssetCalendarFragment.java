package com.zircon.app.ui.assets.browsebooking;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.zircon.app.model.AssetBooking;
import com.zircon.app.ui.common.fragment.AbsCalendarFragment;
import com.zircon.app.ui.common.fragment.AbsMonthFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Created by jikoobaruah on 04/02/16.
 */
public class AssetCalendarFragment extends AbsCalendarFragment implements AssetMonthFragment.AssetMonthInteractionListener {

    interface ARGS{
        String ASSET_ID ="id";
    }


    private String assetID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null)
            throw new NullPointerException("AssetCalendarFragment must be instantiated with a not null value for argument id");

        assetID = getArguments().getString(ARGS.ASSET_ID,null);
        if (assetID == null || assetID.trim().length()==0)
            throw new NullPointerException("AssetCalendarFragment must be instantiated with a not null value for argument id");
    }


    @Override
    protected RecyclerView.Adapter getCalendarRecycleViewAdapter() {
        return new AssetCalendarListAdapter();
    }

    @Override
    protected AbsMonthFragment getMonthFragment(Bundle args, int position) {
        args.putString(ARGS.ASSET_ID, assetID);
        AbsMonthFragment fragment = (AbsMonthFragment) Fragment.instantiate(getContext(),AssetMonthFragment.class.getName(),args);
        ((AssetMonthFragment)fragment).setAssetMonthInteractionListener(AssetCalendarFragment.this);
        return fragment;
    }

    boolean isFirstTime = true;
    @Override
    public void onAssetDataFetched(ArrayList<AssetBooking> assetBookings,Calendar c) {
        for (AssetBooking assetBooking : assetBookings){
            ((AssetCalendarListAdapter)mRecyclerView.getAdapter()).put(c,assetBookings);
        }

        if (isFirstTime){
            c = Calendar.getInstance();
            onDayCellClicked(c.get(Calendar.MONTH), c.get(Calendar.DATE));
            isFirstTime = false;
        }

    }

    private class AssetCalendarListAdapter extends AbsCalendarRecycleViewAdapter{

        private HashMap<String,ArrayList<AssetBooking>> assetBookingHashMap = new HashMap<>();

        public void put(Calendar c,ArrayList<AssetBooking> assetBookings){
            ArrayList<AssetBooking> originalList = assetBookingHashMap.get(new SimpleDateFormat("ddMMyyyy").format(c.getTime()));
            ArrayList<AssetBooking> newList = originalList == null ? new ArrayList<AssetBooking>() : originalList;
            assetBookingHashMap.put(new SimpleDateFormat("ddMMyyyy").format(c.getTime()),newList);
            notifyItemChanged(getPositionForDate(c));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            ArrayList<AssetBooking> assetBookings = assetBookingHashMap.get(new SimpleDateFormat("ddMMyyyy").format(getCalendarForPosition(position).getTime()));
            //TODO populate list view
        }
    }


}
