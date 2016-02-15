package com.zircon.app.ui.complaint;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.Complaint;

import java.util.ArrayList;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class ComplaintCommentsAdapter extends RecyclerView.Adapter<ComplaintCommentsAdapter.ViewHolder> {

    private ArrayList<Complaint> complaintsList = new ArrayList<Complaint>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,null,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setComplaint(complaintsList.get(position));

    }

    public void addAll(ArrayList<Complaint> complaints){
        int i = complaintsList.size();
        complaintsList.addAll(complaints);
        notifyItemRangeInserted(i-1,complaints.size());
    }

    public void add(Complaint complaint){
        complaintsList.add(complaint);
        notifyItemInserted(complaintsList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return complaintsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(android.R.id.text1);

        }

        public void setCommment(String comment) {
            titleTextView.setText(complaint.title);
        }
    }

}
