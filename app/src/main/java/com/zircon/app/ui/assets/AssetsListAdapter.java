package com.zircon.app.ui.assets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.Asset;
import com.zircon.app.ui.common.activity.AbsBaseActivity;

import java.util.ArrayList;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.ViewHolder> {

    private ArrayList<Asset> assetList = new ArrayList<Asset>();

    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_asset,null,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContext = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setAsset(assetList.get(position));

    }

    public void addAll(ArrayList<Asset> assets){
        assetList.addAll(assets);
        notifyDataSetChanged();
    }

    public void add(Asset asset){
        assetList.add(asset);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImageView;
        TextView nameTextView;
        ImageView emailImageView;
        ImageView phoneImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            profileImageView = (ImageView) itemView.findViewById(R.id.profile_pic);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            emailImageView = (ImageView) itemView.findViewById(R.id.email);
            phoneImageView = (ImageView) itemView.findViewById(R.id.phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("fjjjgj");
                }
            });

            profileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("profile");
                }
            });

            emailImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(v.getTag());
                }
            });

            phoneImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(v.getTag());
                }
            });


        }

        public void setAsset(Asset asset) {

            Picasso.with(profileImageView.getContext()).setIndicatorsEnabled(true);
            Picasso.with(profileImageView.getContext()).load(asset.img).into(profileImageView);


            nameTextView.setText(asset.description);
            emailImageView.setTag(asset.email);
            phoneImageView.setTag(asset.contactno);
        }
    }

}
