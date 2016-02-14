package com.zircon.app.ui.assets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.zircon.app.model.AssetDaySlot;
import com.zircon.app.ui.common.AbsCalendarFragment;
import com.zircon.app.ui.common.AbsMonthFragment;

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

    @Override
    public void onAssetDataFetched(ArrayList<AssetDaySlot> assetDaySlots,Calendar c) {
        for (AssetDaySlot daySlot : assetDaySlots){
            c.set(Calendar.DATE,daySlot.date);
            ((AssetCalendarListAdapter)mRecyclerView.getAdapter()).put(c,daySlot);
        }
    }

    private class AssetCalendarListAdapter extends AbsCalendarRecycleViewAdapter{

        private HashMap<String,AssetDaySlot> daySlotHashMap = new HashMap<>();

        public void put(Calendar c,AssetDaySlot assetDaySlot){
            daySlotHashMap.put(new SimpleDateFormat("ddMMyyyy").format(c.getTime()),assetDaySlot);
            notifyItemChanged(getPositionForDate(c));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            AssetDaySlot daySlot = daySlotHashMap.get(new SimpleDateFormat("ddMMyyyy").format(getCalendarForPosition(position).getTime()));
            if (daySlot != null)
                holder.infotextView.setText(daySlot.openSlots+"/"+daySlot.totalAvailableSlots);
            else
                holder.infotextView.setText("Book Now!");
        }
    }


}
