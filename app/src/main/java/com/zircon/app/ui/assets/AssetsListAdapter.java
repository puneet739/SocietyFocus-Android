package com.zircon.app.ui.assets;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zircon.app.R;
import com.zircon.app.model.User;
import com.zircon.app.model.response.Asset;

import java.util.ArrayList;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.ViewHolder> {

    private ArrayList<Asset> assetList = new ArrayList<Asset>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_asset,null,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        TextView addressTextView;
        TextView emailTextView;
        TextView phoneTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            profileImageView = (ImageView) itemView.findViewById(R.id.profile_pic);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            addressTextView = (TextView) itemView.findViewById(R.id.address);
            emailTextView = (TextView) itemView.findViewById(R.id.email);
            phoneTextView = (TextView) itemView.findViewById(R.id.phone);

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
        }

        public void setAsset(Asset asset) {
            ImageLoader.getInstance().displayImage(asset.img,profileImageView);
            nameTextView.setText(asset.description);
            emailTextView.setText(asset.email);
            phoneTextView.setText(asset.contactno);
        }
    }

}
