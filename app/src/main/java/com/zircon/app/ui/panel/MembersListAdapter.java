package com.zircon.app.ui.panel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.User;

import java.util.ArrayList;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MembersListAdapter extends RecyclerView.Adapter<MembersListAdapter.ViewHolder> {

    private ArrayList<User> membersList = new ArrayList<User>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user,null,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setUser(membersList.get(position));

    }

    public void addAll(ArrayList<User> users){
        membersList.addAll(users);
        notifyDataSetChanged();
    }

    public void add(User user){
        membersList.add(user);
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

        public void setUser(User user) {
            nameTextView.setText(user.firstname);
            addressTextView.setText(user.email);
            emailTextView.setText(user.email);
            phoneTextView.setText(user.userid);
        }
    }

}
