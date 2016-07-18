package com.zircon.app.ui.assets.browsebooking;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.AssetBooking;
import com.zircon.app.model.response.BaseResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cbc-03 on 07/12/16.
 */
public class BookingAssetListAdapter extends RecyclerView.Adapter<BookingAssetListAdapter.ViewHolder> {

    private ArrayList<AssetBooking> assetlist = new ArrayList<AssetBooking>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_asset_detail, null, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setPanel(assetlist.get(position));

    }

    public void addAll(ArrayList<AssetBooking> assetbookinglist) {
        assetlist.addAll(assetbookinglist);
        notifyDataSetChanged();
    }

    public void add(AssetBooking assetbookinglist) {
        assetlist.add(assetbookinglist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return assetlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextview;
        TextView nametextTextView;
        TextView bookingchargesTextView;
        TextView bookingchargestextTextView;
        AssetBooking assetbookinglist;

        public ViewHolder(final View itemView) {
            super(itemView);

            nameTextview = (TextView) itemView.findViewById(R.id.assetname);
            nametextTextView = (TextView) itemView.findViewById(R.id.assetnametext);
            bookingchargesTextView = (TextView) itemView.findViewById(R.id.assetcharges);
            bookingchargestextTextView = (TextView) itemView.findViewById(R.id.assetchargestext);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   *//* UserPasser.getInstance().setUser(panel.user);
                    Intent intent = new Intent(itemView.getContext(),MemberDetaisActivity.class);
                    itemView.getContext().startActivity(intent);*//*
                }
            });*/

        }

        public void setPanel(AssetBooking assetbookinglist) {
            this.assetbookinglist = assetbookinglist;

            if (!TextUtils.isEmpty(assetbookinglist.description)) {
                nameTextview.setVisibility(View.VISIBLE);
                nametextTextView.setVisibility(View.VISIBLE);
                nameTextview.setText(assetbookinglist.description);
            }
            if (assetbookinglist.charges!=0) {
                bookingchargesTextView.setVisibility(View.VISIBLE);
                bookingchargestextTextView.setVisibility(View.VISIBLE);
                bookingchargesTextView.setText("₹ "+assetbookinglist.charges);
            }

        }
    }

}