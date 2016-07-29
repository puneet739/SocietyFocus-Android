package com.zircon.app.ui.complaint;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.Complaint;
import com.zircon.app.model.response.BaseResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class ComplaintListAdapter extends RecyclerView.Adapter<ComplaintListAdapter.ViewHolder> {

    private ArrayList<Complaint> complaintsList = new ArrayList<Complaint>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_complaint,null,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
        TextView descriptionTextView;
        TextView dateTextView;
        TextView statusTextView;

        public ViewHolder(final View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            statusTextView = (TextView) itemView.findViewById(R.id.status);
            dateTextView = (TextView) itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),ComplaintDetailsActivity.class);
                    intent.putExtra(ComplaintDetailsActivity.IBundle.ID,(String) titleTextView.getTag());
                    intent.putExtra(ComplaintDetailsActivity.IBundle.TITLE,titleTextView.getText().toString());
                    intent.putExtra(ComplaintDetailsActivity.IBundle.DESCRIPTION,descriptionTextView.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        public void setComplaint(Complaint complaint) {
            titleTextView.setTag(complaint.complaintid);
            titleTextView.setText(complaint.title);
            descriptionTextView.setText(complaint.description);
            statusTextView.setText((complaint.status==null?"":"["+complaint.status+"]"));

            try {
                Date date = BaseResponse.API_SDF.parse(complaint.creationdate);
                dateTextView.setText("created on "+new SimpleDateFormat("dd MMM yyyy").format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
