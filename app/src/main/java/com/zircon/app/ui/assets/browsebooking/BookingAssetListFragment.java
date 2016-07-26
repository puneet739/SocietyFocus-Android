package com.zircon.app.ui.assets.browsebooking;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zircon.app.model.AssetBooking;
import com.zircon.app.model.response.BookAssetListResponse;
import com.zircon.app.ui.assets.browse.AssetsListAdapter;
import com.zircon.app.ui.common.fragment.AbsBaseListFragment;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;


public class BookingAssetListFragment extends AbsBaseListFragment {
    private Call<BookAssetListResponse> call;

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = 50;
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
        return new BookingAssetListAdapter();
    }

    @Override
    public void fetchList() {
        if (call != null)
            call.cancel();
        call = HTTP.getAPI().getAssetBooking(SessionManager.getToken());
        if (call == null) {
            return;
        }
        call.enqueue(new AuthCallBack<BookAssetListResponse>() {
            @Override
            protected void onAuthError() {
                //TODO handle this
            }

            @Override
            protected void parseSuccessResponse(Response<BookAssetListResponse> response) {
                if (response.body() != null && response.body().body != null && response.body().body.size() > 0){
                    ((BookingAssetListAdapter)mListAdapter).addAll(response.body().body);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

   /* private void setAssetBookings(ArrayList<AssetBooking> assetBookings) {
        this.assetBookings = assetBookings;
        if (assetBookings == null || assetBookings.size() == 0)
            return;

        for (AssetBooking assetBooking : assetBookings) {
            ((BookingAssetListAdapter)mListAdapter).add(assetBooking);
        }
//        ((BookingAssetListAdapter) mListAdapter).addAll(assetBookings);

    }*/

 /*   @Override
    public void fetchList() {
        Call<BookAssetListResponse> call = HTTP.getAPI().getAssetBooking(SessionManager.getToken());
        call.enqueue(new AuthCallBack<BookAssetListResponse>() {
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
            protected void parseSuccessResponse(Response<PanelResponse> response) {
                ((MembersListAdapter) mListAdapter).addAll(response.body().body);
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }*/
}
