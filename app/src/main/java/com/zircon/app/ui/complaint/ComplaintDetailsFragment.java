package com.zircon.app.ui.complaint;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zircon.app.model.response.ComplaintCommentResponse;
import com.zircon.app.ui.common.fragment.AbsBaseListFragment;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class ComplaintDetailsFragment extends AbsBaseListFragment {

    interface IARGS{
        String COMPLAINT_ID = "id";
    }

    private String mComplaintID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComplaintID = getArguments().getString(IARGS.COMPLAINT_ID,null);
        if (mComplaintID == null)
            throw new NullPointerException("mComplaintID is null");
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = 10;
                }
            }
        };
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new ComplaintCommentsAdapter();
    }

    @Override
    public void fetchList() {
        Call<ComplaintCommentResponse> call = HTTP.getAPI().getComplaintDetails(SessionManager.getToken(),mComplaintID);
        call.enqueue(new AuthCallBack<ComplaintCommentResponse>() {
            @Override
            protected void onAuthError() {

            }

            @Override
            protected void parseSuccessResponse(Response<ComplaintCommentResponse> response) {
                if (response.isSuccess() && response.body() != null && response.body().body != null) {
//                    getmRecyclerView().setPadding(0,40,0,0);
                    ((ComplaintCommentsAdapter) mListAdapter).addAll(response.body().body.comments);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });


    }
}
