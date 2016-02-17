package com.zircon.app.ui.residents;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.User;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.utils.HTTPUtils;

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
        return new ViewHolder(view);
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


            itemView.setOnTouchListener(new SwipeTouchListener());
        }

        public void setUser(User user) {
            if (user.profilePic != null && user.profilePic.trim().length()>0 && HTTPUtils.isValidUrl(user.profilePic))
            Picasso.with(profileImageView.getContext()).setIndicatorsEnabled(true);
            Picasso.with(profileImageView.getContext()).load(user.profilePic).into(profileImageView);

            nameTextView.setText(user.firstname + " " + (user.lastname != null ? user.lastname : ""));
            addressTextView.setText(user.description);
            emailTextView.setText(user.email);
            phoneTextView.setText(user.contactNumber);
        }

        private class SwipeTouchListener implements View.OnTouchListener {
            float historicX = Float.NaN, historicY = Float.NaN;
            static final int TRIGGER_DELTA = 50; // Number of pixels to travel till trigger

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        historicX = e.getX();
                        historicY = e.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (e.getX() - historicX > TRIGGER_DELTA) {
                            System.out.println("call");
                            ((AbsBaseActivity)v.getContext()).call((String) ((TextView) v.findViewById(R.id.phone)).getText());

                            return true;
                        }
                        else if (historicX - e.getX() > TRIGGER_DELTA)  {
//                            onSlideComplete(Direction.RIGHT);
                            System.out.println("sms");

                            return true;
                        } break;
                    default:
                        return false;
                }
                return false;
            }
        }
    }

}
