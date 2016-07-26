package com.zircon.app.ui.assets.browse;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zircon.app.model.response.AssetsResponse;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.fragment.AbsBaseListFragment;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class AssetsListFragment extends AbsBaseListFragment {

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                    outRect.bottom = 50;

                if (parent.getChildAdapterPosition(view) % 2 == 0) {
                    outRect.right = 50;
                }
            }
        };
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        return layoutManager;


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        return gridLayoutManager;

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new AssetsListAdapter();
    }

    @Override
    public void fetchList() {

        Call<AssetsResponse> call = HTTP.getAPI().getAllSocietyAssets(SessionManager.getToken());
        call.enqueue(new AuthCallBack<AssetsResponse>() {
            @Override
            protected void onAuthError() {
                ((AbsBaseActivity)getActivity()).onAuthError(new AbsBaseActivity.IAuthCallback() {
                    @Override
                    public void onAuthSuccess() {
                        fetchList();
                    }
                });
            }

            @Override
            protected void parseSuccessResponse(Response<AssetsResponse> response) {
                ((AssetsListAdapter) mListAdapter).addAll(response.body().body);
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });



    }
}
