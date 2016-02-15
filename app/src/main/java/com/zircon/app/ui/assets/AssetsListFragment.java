package com.zircon.app.ui.assets;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zircon.app.R;
import com.zircon.app.model.response.AssetsResponse;
import com.zircon.app.model.response.MembersResponse;
import com.zircon.app.ui.common.AbsBaseListFragment;
import com.zircon.app.ui.common.AbsFragment;
import com.zircon.app.ui.residents.MembersListAdapter;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
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
        call.enqueue(new Callback<AssetsResponse>() {
            @Override
            public void onResponse(Response<AssetsResponse> response) {
                if (response.isSuccess() && response.body() != null && response.body().body != null)
                    ((AssetsListAdapter) mListAdapter).addAll(response.body().body);
            }

            @Override
            public void onFailure(Throwable t) {

                t.getLocalizedMessage();
            }
        });

    }
}
