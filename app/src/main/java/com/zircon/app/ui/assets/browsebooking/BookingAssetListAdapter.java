package com.zircon.app.ui.assets.browsebooking;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.response.AssetBookingList;
import com.zircon.app.model.response.BaseResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cbc-03 on 07/12/16.
 */
public class BookingAssetListAdapter extends RecyclerView.Adapter<BookingAssetListAdapter.ViewHolder> {

    private ArrayList<AssetBookingList> assetlist = new ArrayList<AssetBookingList>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_asset_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setPanel(assetlist.get(position));
    }

    public void addAll(ArrayList<AssetBookingList> assetbookinglist) {
        assetlist.addAll(assetbookinglist);
        notifyDataSetChanged();
    }

    public void add(AssetBookingList assetbookinglist) {
        assetlist.add(assetbookinglist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return assetlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView contactNoTextView, contactNotextTextview;
        TextView nameTextView, descriptionTextView;
        TextView durationTextview;
        TextView bookingchargesTextView, bookingchargestextTextView;
        TextView dateTextView;
        AssetBookingList assetbookinglist;
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
            descriptionTextView = (TextView) itemView.findViewById(R.id.assetdescription);
            dateTextView = (TextView) itemView.findViewById(R.id.assetdate);
        }

        public void setPanel(AssetBookingList assetbookinglist) {
            this.assetbookinglist = assetbookinglist;

            if (!TextUtils.isEmpty(assetbookinglist.asset.description)) {
                nameTextView.setVisibility(View.VISIBLE);
                nameTextView.setText("[ID:"+assetbookinglist.id+"] "+assetbookinglist.asset.description);
            }
            if (!TextUtils.isEmpty(assetbookinglist.description)) {
                descriptionTextView.setVisibility(View.VISIBLE);
                descriptionTextView.setText("" + assetbookinglist.description);
            }
            if (assetbookinglist.asset.charges != 0) {
                bookingchargesTextView.setVisibility(View.VISIBLE);
                bookingchargestextTextView.setVisibility(View.VISIBLE);
                bookingchargesTextView.setText("₹ " + assetbookinglist.asset.charges);
            }
            if (!TextUtils.isEmpty(assetbookinglist.asset.contactno)) {
                contactNoTextView.setVisibility(View.VISIBLE);
                contactNotextTextview.setVisibility(View.VISIBLE);
                contactNoTextView.setText(" " + assetbookinglist.asset.contactno);
            }
            if (assetbookinglist.asset.duration != 0) {
                durationTextview.setVisibility(View.VISIBLE);
                durationTextview.setText("/" + assetbookinglist.asset.duration + " Hours");
            }
            if (!TextUtils.isEmpty(assetbookinglist.startTime)) {
                dateTextView.setVisibility(View.VISIBLE);
                try {
                    Date date = BaseResponse.API_SDF.parse(assetbookinglist.startTime);
                    dateTextView.setText("Booking date " + new SimpleDateFormat("dd MMM yyyy").format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (!TextUtils.isEmpty(assetbookinglist.asset.img_url)) {
                imageImageView.setVisibility(View.VISIBLE);
                Picasso.with(itemView.getContext()).load(assetbookinglist.asset.img_url).placeholder(R.drawable.ic_1_2).into(imageImageView);
            }
        }
    }

}