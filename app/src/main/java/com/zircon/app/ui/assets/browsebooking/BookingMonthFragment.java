package com.zircon.app.ui.assets.browsebooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.AssetBooking;
import com.zircon.app.model.response.BookAssetListResponse;
import com.zircon.app.ui.common.fragment.AbsMonthFragment;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 04/02/16.
 */
public class BookingMonthFragment extends AbsMonthFragment {

    private ArrayList<AssetBooking> assetBookings;

    private Call<BookAssetListResponse> call;
    private String assetID;
    private AssetMonthInteractionListener assetMonthInteractionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetID = getArguments().getString(BookingCalendarFragment.ARGS.ASSET_ID,null);
    }

    private void setAssetBookings(ArrayList<AssetBooking> assetBookings) {


        this.assetBookings = assetBookings;
        if (assetBookings == null || assetBookings.size() == 0)
            return;
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_asset_day_view,null,false);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView assetInfo = (TextView) v.findViewById(R.id.asset_slot_info);
//        for(A as : assetBookings){
//            DayCellView dayCellView = dateViewIDMap.get(assetDaySlot.date);
//            assetInfo.setText(assetDaySlot.openSlots+"/"+assetDaySlot.totalAvailableSlots);
//            dayCellView.setDateContentView(v);
//        }


    }

    @Override
    public void fetchData(final Calendar calendar, final int position){
        if (call != null)
            call.cancel();
        call = getEventCALL(calendar);
        if (call == null) {
            return;
        }
        call.enqueue(new AuthCallBack<BookAssetListResponse>() {
            @Override
            protected void onAuthError() {
                //TODO handle this
            }

            @Override
            protected void parseSuccessResponse(Response<BookAssetListResponse> response) {
                if (response.body() != null && response.body().body != null && response.body().body.size() > 0){
                    setAssetBookings(response.body().body);
                    assetMonthInteractionListener.onAssetDataFetched(response.body().body, calendar);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });


    }



    private Call<BookAssetListResponse> getEventCALL(Calendar calendar) {
        return HTTP.getAPI().getAssetBooking(SessionManager.getToken());//, assetID, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));

    }


    public void setAssetMonthInteractionListener(AssetMonthInteractionListener assetMonthInteractionListener) {
        this.assetMonthInteractionListener = assetMonthInteractionListener;
    }

    interface AssetMonthInteractionListener {

        void onAssetDataFetched(ArrayList<AssetBooking> assetBookings, Calendar calendar);
    }
}
