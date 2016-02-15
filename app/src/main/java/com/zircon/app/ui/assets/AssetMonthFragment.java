package com.zircon.app.ui.assets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.AssetDaySlot;
import com.zircon.app.model.response.AssetSlotResponse;
import com.zircon.app.ui.common.fragment.AbsMonthFragment;
import com.zircon.app.ui.common.widget.DayCellView;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 04/02/16.
 */
public class AssetMonthFragment extends AbsMonthFragment {

    private ArrayList<AssetDaySlot> assetDaySlots;

    private Call<AssetSlotResponse> call;
    private String assetID;
    private AssetMonthInteractionListener assetMonthInteractionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetID = getArguments().getString(AssetCalendarFragment.ARGS.ASSET_ID,null);
    }

    private void setAssetDaySlots(ArrayList<AssetDaySlot> assetDaySlots) {


        this.assetDaySlots = assetDaySlots;
        if (assetDaySlots == null || assetDaySlots.size() == 0)
            return;
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_asset_day_view,null,false);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView assetInfo = (TextView) v.findViewById(R.id.asset_slot_info);
        for(AssetDaySlot assetDaySlot : assetDaySlots){
            DayCellView dayCellView = dateViewIDMap.get(assetDaySlot.date);
            assetInfo.setText(assetDaySlot.openSlots+"/"+assetDaySlot.totalAvailableSlots);
            dayCellView.setDateContentView(v);
        }

    }

    @Override
    public void fetchEvents(final Calendar calendar, final int position){
        if (call != null)
            call.cancel();
        call = getEventCALL(calendar);
        if (call == null) {
            fetchDummyResponse(calendar,position);
            return;
        }
        call.enqueue(new Callback<AssetSlotResponse>() {
            @Override
            public void onResponse(Response<AssetSlotResponse> response) {

//                setAssetDaySlots(response.body().body);

                if (assetMonthInteractionListener!=null)
                    assetMonthInteractionListener.onAssetDataFetched(response.body().body,calendar);

            }

            @Override
            public void onFailure(Throwable t) {
                fetchDummyResponse(calendar, position);
            }
        });
    }

    private void fetchDummyResponse(Calendar calendar,int position){
        ArrayList<AssetDaySlot> assetDaySlots = new ArrayList<>();
        AssetDaySlot assetDaySlot = new AssetDaySlot();
        assetDaySlot.totalAvailableSlots=100;
        assetDaySlot.date=4;
        assetDaySlot.openSlots=30;
        assetDaySlots.add(assetDaySlot);
        setAssetDaySlots(assetDaySlots);
        if (assetMonthInteractionListener!=null)
            assetMonthInteractionListener.onAssetDataFetched(assetDaySlots,calendar);


    }

    private Call<AssetSlotResponse> getEventCALL(Calendar calendar) {
        return HTTP.getAPI().getAssetCalendar(SessionManager.getToken(), assetID, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));

    }


    public void setAssetMonthInteractionListener(AssetMonthInteractionListener assetMonthInteractionListener) {
        this.assetMonthInteractionListener = assetMonthInteractionListener;
    }

    interface AssetMonthInteractionListener {

        void onAssetDataFetched(ArrayList<AssetDaySlot> body, Calendar calendar);
    }
}
