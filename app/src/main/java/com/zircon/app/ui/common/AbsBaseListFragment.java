package com.zircon.app.ui.common;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zircon.app.R;
import com.zircon.app.model.response.MembersResponse;
import com.zircon.app.ui.residents.MembersListAdapter;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 25/01/16.
 */
public abstract class AbsBaseListFragment extends AbsFragment {

    private RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,null,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        if (getItemDecoration() != null)
            mRecyclerView.addItemDecoration(getItemDecoration());

        if (getLayoutManager() != null){
            mRecyclerView.setLayoutManager(getLayoutManager());
        }else{
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
        }

        mListAdapter = getAdapter();
        mRecyclerView.setAdapter(mListAdapter);

        fetchList();

    }

    public abstract RecyclerView.ItemDecoration getItemDecoration();
    public abstract RecyclerView.LayoutManager getLayoutManager();
    public abstract RecyclerView.Adapter getAdapter();
    public abstract void fetchList();
}
