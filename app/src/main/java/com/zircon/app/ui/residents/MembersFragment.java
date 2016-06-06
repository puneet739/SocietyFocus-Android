package com.zircon.app.ui.residents;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zircon.app.model.response.MembersResponse;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.fragment.AbsBaseListFragment;
import com.zircon.app.ui.login.LoginActivity;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MembersFragment extends AbsBaseListFragment {

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = 15;
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
        return new MembersListAdapter();
    }

    @Override
    public void fetchList() {
        Call<MembersResponse> call = HTTP.getAPI().getAllUsers(SessionManager.getToken());
        call.enqueue(new AuthCallBack<MembersResponse>() {
            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }

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
            protected void parseSuccessResponse(Response<MembersResponse> response) {
                ((MembersListAdapter) mListAdapter).addAll(response.body().body);
            }
        });
    }
}
