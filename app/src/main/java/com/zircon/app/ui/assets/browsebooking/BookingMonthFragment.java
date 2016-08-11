package com.zircon.app.ui.assets.browsebooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.AssetBooking;
import com.zircon.app.model.response.AssetbookingByUserResponse;
import com.zircon.app.ui.common.fragment.AbsMonthFragment;
import com.zircon.app.ui.common.widget.DayCellView;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 04/02/16.
 */
public class BookingMonthFragment extends AbsMonthFragment {

    private ArrayList<AssetBooking> assetBookings;

    private Call<AssetbookingByUserResponse> call;
    private String assetID;
    private AssetMonthInteractionListener assetMonthInteractionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        assetID = getArguments().getString(BookingCalendarFragment.ARGS.ASSET_ID,null);
    }

    private void setAssetBookings(ArrayList<AssetBooking> assetBookings) {

        this.assetBookings = assetBookings;
        if (assetBookings == null || assetBookings.size() == 0)
            return;


        Calendar c = Calendar.getInstance();
        int date;
        HashMap<Integer,ArrayList<AssetBooking>> dateBookingMap = new HashMap<>();
        ArrayList<AssetBooking> dayBookingList= new ArrayList<>();
        for (AssetBooking assetBooking : assetBookings){
            /*try {
                c.setTime(BaseResponse.API_SDF.parse(assetBooking.startTime));
                if (c.get(Calendar.MONTH) == month && c.get(Calendar.YEAR) == year){
                    date = c.get(Calendar.DATE);
                    dayBookingList = dateBookingMap.get(date);
                    if (dayBookingList == null)
                        dayBookingList= new ArrayList<>();
                    dayBookingList.add(assetBooking);
                    dateBookingMap.put(date,dayBookingList);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
        }

        int size = 0;
        View v;
        for( Integer d : dateViewIDMap.keySet()){
            DayCellView dayCellView = dateViewIDMap.get(d);
            size = dateBookingMap.get(d) == null ? 0 : dateBookingMap.get(d).size();

            v = LayoutInflater.from(mContext).inflate(R.layout.layout_asset_day_view, null, false);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            TextView assetInfo = (TextView) v.findViewById(R.id.asset_slot_info);

            if (size > 0)
                assetInfo.setText(size+"");
            else
                assetInfo.setText("");


            dayCellView.setDateContentView(v);

            if (size < 3 ){
                dayCellView.setBackgroundResource(R.drawable.calendar_cell_green);
            }else if (size < 5 ) {
                dayCellView.setBackgroundResource(R.drawable.calendar_cell_blue);
            }else {
                dayCellView.setBackgroundResource(R.drawable.calendar_cell_red);

            }
        }


    }

    @Override
    public void fetchData(final Calendar calendar, final int position){
        if (call != null)
            call.cancel();
        call = getEventCALL(calendar);
        if (call == null) {
            return;
        }
        call.enqueue(new AuthCallBack<AssetbookingByUserResponse>() {
            @Override
            protected void onAuthError() {
                //TODO handle this
            }

            @Override
            protected void parseSuccessResponse(Response<AssetbookingByUserResponse> response) {
                if (response.body() != null && response.body().body != null && response.body().body.size() > 0){
//                    setAssetBookings(response.body().body);
//                    assetMonthInteractionListener.onAssetDataFetched(response.body().body, calendar);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

//        ArrayList<AssetBooking> bookings = new ArrayList<>();
//
//        AssetBooking assetBooking = new AssetBooking();
//        assetBooking.id= "10";
//        assetBooking.assetid= "1";
//        assetBooking.startTime = "2016-02-22T11:11:29 +0530";
//        assetBooking.prepaid = false;
//        assetBooking.status = 1;
//
//        bookings.add(assetBooking);
//        bookings.add(assetBooking);
//        bookings.add(assetBooking);
//        bookings.add(assetBooking);
//
//        AssetBooking assetBooking1 = new AssetBooking();
//        assetBooking1.id= "10";
//        assetBooking1.assetid= "1";
//        assetBooking1.startTime = "2016-02-25T11:11:29 +0530";
//        assetBooking1.prepaid = false;
//        assetBooking1.status = 1;
//
//
//        bookings.add(assetBooking1);
//        bookings.add(assetBooking1);
//        bookings.add(assetBooking1);
//        bookings.add(assetBooking1);
//        bookings.add(assetBooking1);
//        bookings.add(assetBooking1);
//        bookings.add(assetBooking1);
//
//
//        setAssetBookings(bookings);

//        assetMonthInteractionListener.onAssetDataFetched(bookings, calendar);


    }



    private Call<AssetbookingByUserResponse> getEventCALL(Calendar calendar) {
        return HTTP.getAPI().getAssetBooking(SessionManager.getToken());//, assetID, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));

    }


    public void setAssetMonthInteractionListener(AssetMonthInteractionListener assetMonthInteractionListener) {
        this.assetMonthInteractionListener = assetMonthInteractionListener;
    }

    interface AssetMonthInteractionListener {

        void onAssetDataFetched(ArrayList<AssetBooking> assetBookings, Calendar calendar);
    }
}
