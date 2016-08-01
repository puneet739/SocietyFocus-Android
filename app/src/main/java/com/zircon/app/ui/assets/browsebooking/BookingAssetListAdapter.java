package com.zircon.app.ui.assets.browsebooking;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.AssetBooking;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.ui.residents.MemberDetaisActivity;
import com.zircon.app.utils.datapasser.UserPasser;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_asset_detail, parent, false);
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

        TextView contactNoTextView, contactNotextTextview;
        TextView nameTextView;
        TextView durationTextview;
        TextView bookingchargesTextView, bookingchargestextTextView;
        AssetBooking assetbookinglist;
        ImageView imageImageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            imageImageView = (ImageView) itemView.findViewById(R.id.image);
            durationTextview = (TextView) itemView.findViewById(R.id.duration);
            nameTextView = (TextView) itemView.findViewById(R.id.assetname);
            bookingchargesTextView = (TextView) itemView.findViewById(R.id.assetcharges);
            bookingchargestextTextView = (TextView) itemView.findViewById(R.id.assetchargestext);
            contactNotextTextview = (TextView) itemView.findViewById(R.id.contactnotext);
            contactNoTextView = (TextView) itemView.findViewById(R.id.contactno);

        }

        public void setPanel(AssetBooking assetbookinglist) {
            this.assetbookinglist = assetbookinglist;

            if (!TextUtils.isEmpty(assetbookinglist.description)) {
                nameTextView.setVisibility(View.VISIBLE);
                nameTextView.setText(assetbookinglist.description);
            }
            if (assetbookinglist.charges != 0) {
                bookingchargesTextView.setVisibility(View.VISIBLE);
                bookingchargestextTextView.setVisibility(View.VISIBLE);
                bookingchargesTextView.setText("₹ " + assetbookinglist.charges);
            }
            if (!TextUtils.isEmpty(assetbookinglist.contactno)) {
                contactNoTextView.setVisibility(View.VISIBLE);
                contactNotextTextview.setVisibility(View.VISIBLE);
                contactNoTextView.setText(" " + assetbookinglist.contactno);
            }

            if (assetbookinglist.duration != 0) {
                durationTextview.setVisibility(View.VISIBLE);
                durationTextview.setText("" + assetbookinglist.duration + " Hours");
            }

            if (!TextUtils.isEmpty(assetbookinglist.img_url)) {
                Picasso.with(itemView.getContext()).load(assetbookinglist.img_url).placeholder(R.drawable.ic_1_2).into(imageImageView);
            }
        }
    }

}