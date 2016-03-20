package com.zircon.app.ui.residents;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.BuildConfig;
import com.zircon.app.R;
import com.zircon.app.model.User;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.widget.SwipeView;
import com.zircon.app.utils.HTTPUtils;
import com.zircon.app.utils.datapasser.UserPasser;

import java.util.ArrayList;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MembersListAdapter extends RecyclerView.Adapter<MembersListAdapter.ViewHolder> {

    private ArrayList<User> membersList = new ArrayList<User>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, null, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder((SwipeView)view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setUser(membersList.get(position));

    }

    public void addAll(ArrayList<User> users) {
        membersList.addAll(users);
        notifyDataSetChanged();
    }

    public void add(User user) {
        membersList.add(user);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private User user;

        ImageView profileImageView;
        TextView nameTextView;
        TextView addressTextView;
        TextView emailTextView;
        TextView phoneTextView;

        SwipeView itemview;

        public ViewHolder(final SwipeView itemView) {
            super(itemView);

            itemview = itemView;
            profileImageView = (ImageView) itemView.findViewById(R.id.profile_pic);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            addressTextView = (TextView) itemView.findViewById(R.id.address);
            emailTextView = (TextView) itemView.findViewById(R.id.email);
            phoneTextView = (TextView) itemView.findViewById(R.id.phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserPasser.getInstance().setUser(user);
                    Intent intent = new Intent(itemView.getContext(),MemberDetaisActivity.class);
                    itemView.getContext().startActivity(intent);

                }
            });

            profileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("profile");
                }
            });


        }

        public void setUser(User user) {
            this.user = user;
            Picasso.with(profileImageView.getContext()).setIndicatorsEnabled(BuildConfig.DEBUG);
            Picasso.with(profileImageView.getContext()).load(user.profilePic).placeholder(R.drawable.ic_avatar).into(profileImageView);

            nameTextView.setText(user.firstname + " " + (user.lastname != null ? user.lastname : ""));
            addressTextView.setText(user.description);
            emailTextView.setText(user.email);
            phoneTextView.setText(user.contactNumber);
//            itemview.trackGesture();
        }

    }

}
