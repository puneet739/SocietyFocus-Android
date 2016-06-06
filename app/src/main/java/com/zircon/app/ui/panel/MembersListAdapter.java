package com.zircon.app.ui.panel;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.BuildConfig;
import com.zircon.app.R;
import com.zircon.app.model.Panel;
import com.zircon.app.model.User;
import com.zircon.app.ui.common.widget.SwipeView;
import com.zircon.app.ui.residents.MemberDetaisActivity;
import com.zircon.app.utils.datapasser.UserPasser;

import java.util.ArrayList;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MembersListAdapter extends RecyclerView.Adapter<MembersListAdapter.ViewHolder> {

    private ArrayList<Panel> membersList = new ArrayList<Panel>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_panel,null,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder((SwipeView)view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setPanel(membersList.get(position));

    }

    public void addAll(ArrayList<Panel> panels){
        membersList.addAll(panels);
        notifyDataSetChanged();
    }

    public void add(Panel panel){
        membersList.add(panel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImageView;
        TextView nameTextView;
        TextView addressTextView;
        TextView emailTextView;
        TextView phoneTextView;
        TextView designationTextView;

        Panel panel;

        public ViewHolder(final View itemView) {
            super(itemView);

            profileImageView = (ImageView) itemView.findViewById(R.id.profile_pic);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            addressTextView = (TextView) itemView.findViewById(R.id.address);
            emailTextView = (TextView) itemView.findViewById(R.id.email);
            phoneTextView = (TextView) itemView.findViewById(R.id.phone);
            designationTextView = (TextView) itemView.findViewById(R.id.designation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserPasser.getInstance().setUser(panel.user);
                    Intent intent = new Intent(itemView.getContext(),MemberDetaisActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });


        }

        public void setPanel(Panel panel) {
            this.panel = panel;

            Picasso.with(profileImageView.getContext()).setIndicatorsEnabled(BuildConfig.DEBUG);
            Picasso.with(profileImageView.getContext()).load(panel.user.profilePic).placeholder(R.drawable.ic_avatar).into(profileImageView);

            designationTextView.setText(panel.designation);
            nameTextView.setText(panel.user.firstname + " " + (panel.user.lastname != null ? panel.user.lastname : ""));
            addressTextView.setText(panel.user.email);
            emailTextView.setText(panel.user.email);
            phoneTextView.setText(panel.user.contactNumber);
        }
    }

}
