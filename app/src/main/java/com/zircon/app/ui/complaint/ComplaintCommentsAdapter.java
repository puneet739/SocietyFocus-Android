package com.zircon.app.ui.complaint;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.model.Comment;
import com.zircon.app.model.Complaint;
import com.zircon.app.model.response.BaseResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class ComplaintCommentsAdapter extends RecyclerView.Adapter<ComplaintCommentsAdapter.ViewHolder> {

    private ArrayList<Comment> commentsList = new ArrayList<Comment>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_complaint_detail,null,false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setComment(commentsList.get(position));

    }

    public void addAll(ArrayList<Comment> comments){
        int i = commentsList.size();
        commentsList.addAll(comments);
        notifyItemRangeInserted(i-1,comments.size());
    }

    public void add(Comment comment){
        commentsList.add(comment);
        notifyItemInserted(commentsList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView dateTextView;
        TextView userTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
            userTextView = (TextView) itemView.findViewById(R.id.comment_by);

        }

        public void setComment(Comment comment) {
            titleTextView.setText(comment.comment);
            try {
                Date date = BaseResponse.API_SDF.parse(comment.creationdate);
                dateTextView.setText(new SimpleDateFormat("dd MMM yyyy").format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userTextView.setText(comment.user.firstname);
        }
    }

}
