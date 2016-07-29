package com.zircon.app.ui.residents;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.R;
import com.zircon.app.model.User;
import com.zircon.app.model.response.MembersResponse;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.fragment.AbsResidentsSearchListViewFragment;
import com.zircon.app.ui.common.widget.SwipeView;
import com.zircon.app.ui.login.LoginActivity;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;
import com.zircon.app.utils.datapasser.UserPasser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 24/01/16.
 */
public class MembersFragment extends AbsResidentsSearchListViewFragment {

    private MemberListAdapter mAdapter;

    @Override
    protected void fetchList() {
        Call<MembersResponse> call = HTTP.getAPI().getAllUsers(SessionManager.getToken());
        call.enqueue(new AuthCallBack<MembersResponse>() {
            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }

            @Override
            protected void onAuthError() {
                ((AbsBaseActivity) getActivity()).onAuthError(new AbsBaseActivity.IAuthCallback() {
                    @Override
                    public void onAuthSuccess() {
                        fetchList();
                    }
                });
            }

            @Override
            protected void parseSuccessResponse(Response<MembersResponse> response) {
                mAdapter.addAllItems(response.body().body);
//                ((MembersListAdapter) mAdapter).addAll(response.body().body);
            }
        });
    }

    @Override
    protected RecyclerView.Adapter getListAdapter() {
        mAdapter = new MemberListAdapter();
        return mAdapter;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_society_selection;
    }

    protected class MemberListAdapter extends AbsResidentsSearchListViewFragment.ListAdapter<User> {

        @Override
        protected List<User> getFilteredList(String query) {
            ArrayList<User> filteredList = new ArrayList<>();
            if (query == null || query.trim().length() == 0)
                filteredList = masterItems;
            else {
                String FirstName, LastName;
                int size = masterItems.size();
                for (int i = 0; i < size; i++) {
                    FirstName = masterItems.get(i).firstname;
                    LastName = (masterItems.get(i).lastname == null ? "" : masterItems.get(i).lastname);
                    if ((FirstName.toLowerCase().contains(query.toLowerCase())) || (LastName.toLowerCase().contains(query.toLowerCase()))) {
                        filteredList.add(masterItems.get(i));
                    }
                }
            }
            return filteredList;
        }

        @Override
        protected RecyclerView.ViewHolder createViewholder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
            return new ViewHolder((SwipeView) view);
        }

        @Override
        protected void bindViewholder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder UserHolder = (ViewHolder) holder;
            UserHolder.setUser(getItem(position));

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private User user;

            ImageView profileImageView;
            TextView nameTextView;
            TextView addressTextView;
            TextView emailTextView;
            TextView phoneTextView;

            SwipeView itemview;

            public ViewHolder(final SwipeView itemView) {
                super(itemView);

                itemview = itemView;
                profileImageView = (ImageView) itemView.findViewById(R.id.profile_pic);
                nameTextView = (TextView) itemView.findViewById(R.id.name);
                addressTextView = (TextView) itemView.findViewById(R.id.address);
                emailTextView = (TextView) itemView.findViewById(R.id.email);
                phoneTextView = (TextView) itemView.findViewById(R.id.phone);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserPasser.getInstance().setUser(user);
                        Intent intent = new Intent(itemView.getContext(), MemberDetaisActivity.class);
                        itemView.getContext().startActivity(intent);

                    }
                });

            }

            public void setUser(User user) {
                this.user = user;
                if (!TextUtils.isEmpty(user.profilePic)) {
                    Picasso.with(profileImageView.getContext()).load(user.profilePic).placeholder(R.drawable.ic_avatar).into(profileImageView);
                } else {
                    profileImageView.setImageDrawable(itemview.getContext().getResources().getDrawable(R.drawable.ic_avatar));
                }
                nameTextView.setText(user.firstname + " " + (user.lastname != null ? user.lastname : ""));
                addressTextView.setText(user.description);
                emailTextView.setText(user.email);
                phoneTextView.setText(user.contactNumber);
            }

        }
    }


/*
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
    }*/
}
