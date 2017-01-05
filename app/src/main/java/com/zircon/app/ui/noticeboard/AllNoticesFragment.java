package com.zircon.app.ui.noticeboard;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zircon.app.model.response.NoticeBoardResponse;
import com.zircon.app.ui.common.fragment.AbsBaseListFragment;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AllNoticesFragment extends AbsBaseListFragment {

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
        mListAdapter = new NoticeListAdapter();
        return mListAdapter;
    }

    @Override
    public void fetchList() {
        Call<NoticeBoardResponse> call = HTTP.getAPI().getAllNotices(SessionManager.getToken());
        call.enqueue(new AuthCallBack<NoticeBoardResponse>() {
            @Override
            protected void onAuthError() {
            }

            @Override
            protected void parseSuccessResponse(Response<NoticeBoardResponse> response) {
                mRecyclerView.setVisibility(View.VISIBLE);
                ((NoticeListAdapter) mListAdapter).addAll(response.body().body);
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }
}
