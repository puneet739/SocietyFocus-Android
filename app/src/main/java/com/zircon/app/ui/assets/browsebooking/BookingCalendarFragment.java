package com.zircon.app.ui.assets.browsebooking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.zircon.app.model.AssetBooking;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.ui.common.fragment.AbsCalendarFragment;
import com.zircon.app.ui.common.fragment.AbsMonthFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Created by jikoobaruah on 04/02/16.
 */
public class BookingCalendarFragment extends AbsCalendarFragment implements BookingMonthFragment.AssetMonthInteractionListener {

    interface ARGS{
        String ASSET_ID ="id";
    }


    private String assetID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null)
            throw new NullPointerException("BookingCalendarFragment must be instantiated with a not null value for argument id");

        assetID = getArguments().getString(ARGS.ASSET_ID,null);
        if (assetID == null || assetID.trim().length()==0)
            throw new NullPointerException("BookingCalendarFragment must be instantiated with a not null value for argument id");
    }


    @Override
    protected RecyclerView.Adapter getCalendarRecycleViewAdapter() {
        return new AssetCalendarListAdapter();
    }

    @Override
    protected AbsMonthFragment getMonthFragment(Bundle args, int position) {
                args.putString(ARGS.ASSET_ID, assetID);
//        AbsMonthFragment fragment = (AbsMonthFragment) Fragment.instantiate(getContext(),BookingMonthFragment.class.getName(),args);
//        ((BookingMonthFragment)fragment).setAssetMonthInteractionListener(BookingCalendarFragment.this);
        return null;
    }

    boolean isFirstTime = true;
    @Override
    public void onAssetDataFetched(ArrayList<AssetBooking> assetBookings,Calendar c) {
        for (AssetBooking assetBooking : assetBookings){
            /*try {
//                c.setTime(BaseResponse.API_SDF.parse(assetBooking.startTime));
                ((AssetCalendarListAdapter)mRecyclerView.getAdapter()).put(c, assetBooking);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
        }

        if (isFirstTime){
            c = Calendar.getInstance();
            onDayCellClicked(c.get(Calendar.MONTH), c.get(Calendar.DATE));
            isFirstTime = false;
        }

    }

    private class AssetCalendarListAdapter extends AbsCalendarRecycleViewAdapter{

        private HashMap<String,HashSet<AssetBooking>> assetBookingHashMap = new HashMap<>();

        public void put(Calendar c, AssetBooking assetBooking){
            HashSet<AssetBooking> originalList = assetBookingHashMap.get(new SimpleDateFormat("ddMMyyyy").format(c.getTime()));
            HashSet<AssetBooking> newList = originalList == null ? new HashSet<AssetBooking>() : originalList;
            newList.add(assetBooking);
            assetBookingHashMap.put(new SimpleDateFormat("ddMMyyyy").format(c.getTime()),newList);
            notifyItemChanged(getPositionForDate(c));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            HashSet<AssetBooking> assetBookings = assetBookingHashMap.get(new SimpleDateFormat("ddMMyyyy").format(getCalendarForPosition(position).getTime()));
            //TODO populate list view
            holder.infotextView.setText((assetBookings==null?0:assetBookings.size())+"");
        }
    }
}
