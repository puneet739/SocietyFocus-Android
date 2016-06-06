package com.zircon.app.ui.common.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zircon.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jikoobaruah on 10/02/16.
 */
public abstract class AbsSearchListViewFragment extends DialogFragment{

    private View mParentView;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(getLayoutResID(),null,false);
        return mParentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = (SearchView) mParentView.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((ListAdapter)mRecyclerView.getAdapter()).filter(newText);
                return true;
            }
        });
        
        mRecyclerView = (RecyclerView) mParentView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(2));
        mRecyclerView.setAdapter(getListAdapter());

        fetchList();
    }

    protected abstract void fetchList();

    protected abstract RecyclerView.Adapter getListAdapter();

    protected abstract int getLayoutResID();

    protected abstract class ListAdapter<T> extends RecyclerView.Adapter{

        private ArrayList<T> displayItems = new ArrayList<>();
        protected ArrayList<T> masterItems = new ArrayList<>();

        protected String query;

        public final void filter(String query){
            this.query = query;
            animateTo(getFilteredList(query));
        }

        protected abstract List<T> getFilteredList(String query);

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return createViewholder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            bindViewholder(holder,position);
        }

        protected abstract RecyclerView.ViewHolder createViewholder(ViewGroup parent, int viewType);

        protected abstract void bindViewholder(RecyclerView.ViewHolder holder, int position);

        @Override
        public int getItemCount() {
            return displayItems.size();
        }

        protected T getItem(int position) {
            return displayItems.get(position);
        }

        public void animateTo(List<T> newList) {
            applyAndAnimateRemovals(newList);
            applyAndAnimateAdditions(newList);
            applyAndAnimateMovedItems(newList);
            for (int i =0 ; i < displayItems.size(); i++){
                notifyItemChanged(i);
            }
        }

        private void applyAndAnimateRemovals(List<T> newList) {
            for (int i = displayItems.size() - 1; i >= 0; i--) {
                final T t = displayItems.get(i);
                if (!newList.contains(t)) {
                    removeItem(i);
                }
            }
        }

        private void applyAndAnimateAdditions(List<T> newList) {
            for (int i = 0, count = newList.size(); i < count; i++) {
                final T t = newList.get(i);
                if (!displayItems.contains(t)) {
                    addItem(i, t);
                }
            }
        }

        private void applyAndAnimateMovedItems(List<T> newList) {
            for (int toPosition = newList.size() - 1; toPosition >= 0; toPosition--) {
                final T t = newList.get(toPosition);
                final int fromPosition = displayItems.indexOf(t);
                if (fromPosition >= 0 && fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
            }
        }

        public T removeItem(int position) {
            final T t = displayItems.remove(position);
            notifyItemRemoved(position);
            return t;
        }

        public void addAllItems(ArrayList<T> models) {
            int i = displayItems.size();
            masterItems.addAll(models);
            displayItems.addAll(models);
            notifyItemRangeInserted(i, models.size());
        }

        public void addItem(int position, T model) {
            displayItems.add(position, model);
            notifyItemInserted(position);
        }

        public void moveItem(int fromPosition, int toPosition) {
            final T t = displayItems.remove(fromPosition);
            displayItems.add(toPosition, t);
            notifyItemMoved(fromPosition, toPosition);
        }

    }

    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }
}
