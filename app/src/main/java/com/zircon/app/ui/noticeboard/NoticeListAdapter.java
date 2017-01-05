package com.zircon.app.ui.noticeboard;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.NoticeBoard;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.utils.datapasser.UserPasser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {

    private ArrayList<NoticeBoard> noticeLists = new ArrayList<NoticeBoard>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notice_layout, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setNotice(noticeLists.get(position));

    }

    public void addAll(ArrayList<NoticeBoard> notices) {
        int i = noticeLists.size();
        noticeLists.addAll(notices);
        notifyItemRangeInserted(i - 1, notices.size());
    }

    public void add(NoticeBoard notices) {
        noticeLists.add(notices);
        notifyItemInserted(noticeLists.size() - 1);
    }

    @Override
    public int getItemCount() {
        return noticeLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mNoticeDescriptionView;
        TextView mDateTimeView;
        TextView mCreatedByView;
        ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mNoticeDescriptionView = (TextView) itemView.findViewById(R.id.description);
            mCreatedByView = (TextView) itemView.findViewById(R.id.createdby);
            mDateTimeView = (TextView) itemView.findViewById(R.id.creationdate);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), NoticeDetailActivity.class);
                    UserPasser.getInstance().setSingleNotice(noticeLists.get(getAdapterPosition()));
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void setNotice(NoticeBoard notice) {
            mCreatedByView.setText("Created by - "+notice.getUser().firstname + " " + (notice.getUser().lastname == "" ? "" : notice.getUser().lastname));
            mNoticeDescriptionView.setText(Html.fromHtml("<b>" + notice.getTitle() + "-</b> " + notice.getDescription()));
            if (notice.getImage_url1() != null)
                Picasso.with(itemView.getContext()).load(notice.getImage_url1()).placeholder(R.drawable.ic_avatar).into(mImageView);
            try {
                Date date = BaseResponse.API_SDF.parse(notice.getCreationdate());
                mDateTimeView.setText(new SimpleDateFormat("dd MMM yyyy HH:mm").format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
