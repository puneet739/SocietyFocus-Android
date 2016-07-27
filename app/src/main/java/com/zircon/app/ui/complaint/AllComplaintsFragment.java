package com.zircon.app.ui.complaint;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zircon.app.R;
import com.zircon.app.model.response.ComplaintListResponse;
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
public class AllComplaintsFragment extends AbsBaseListFragment {



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
        return new ComplaintListAdapter();
    }

    @Override
    public void fetchList() {
        Call<ComplaintListResponse> call = HTTP.getAPI().getUserComplaints(SessionManager.getToken());
        call.enqueue(new AuthCallBack<ComplaintListResponse>() {
            @Override
            protected void onAuthError() {
            }
            @Override
            protected void parseSuccessResponse(Response<ComplaintListResponse> response) {
                ((ComplaintListAdapter) mListAdapter).addAll(response.body().body);
            }
            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }
}
