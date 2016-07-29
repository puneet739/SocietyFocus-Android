package com.zircon.app.ui.login;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zircon.app.BuildConfig;
import com.zircon.app.R;
import com.zircon.app.model.Society;
import com.zircon.app.model.response.SocietyListResponse;
import com.zircon.app.ui.common.fragment.AbsSearchListViewFragment;
import com.zircon.app.utils.API;
import com.zircon.app.utils.HTTP;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 10/02/16.
 */
public class SocietySelectionFragment extends AbsSearchListViewFragment {

    private SocietyAdapter mAdapter;
    private ISocietySelectionListener selectionListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof ISocietySelectionListener)) {
            throw new IllegalArgumentException(activity.getLocalClassName() + " must implement ISocietySelectionListener");
        }
        selectionListener = (ISocietySelectionListener) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Choose society");
//        TextView Text=(TextView)getDialog().getWindow().findViewById(android.R.id.title);
//        getDialog().getWindow().findViewById(R.layout.textview);
//        getDialog().getWindow().findViewById(android.R.id.title).setBackgroundColor(Color.BLUE);
//        TextView textView= (TextView) getDialog().getWindow().findViewById(android.R.id.title);
//        textView.setBackgroundColor(Color.BLUE);
    }

    @Override
    protected void fetchList() {
        API api = HTTP.getAPI();
        if (api == null)
            return;
        Call<SocietyListResponse> call = api.getSocietyList();
        call.enqueue(new Callback<SocietyListResponse>() {
            @Override
            public void onResponse(Response<SocietyListResponse> response) {
                if (response.isSuccess()) {
                    mAdapter.addAllItems(response.body().body);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    protected RecyclerView.Adapter getListAdapter() {
        mAdapter = new SocietyAdapter();
        return mAdapter;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_society_selection;
    }

    private class SocietyAdapter extends AbsSearchListViewFragment.ListAdapter<Society> {

        @Override
        protected List<Society> getFilteredList(String query) {
            ArrayList<Society> filteredList = new ArrayList<>();
            if (query == null || query.trim().length() == 0)
                filteredList = masterItems;
            else {
                String societyName;
                int size = masterItems.size();
                for (int i = 0; i < size; i++) {
                    societyName = masterItems.get(i).name;
                    if (societyName.toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(masterItems.get(i));
                    }
                }
            }
            return filteredList;
        }

        @Override
        protected RecyclerView.ViewHolder createViewholder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(getContext()).inflate(R.layout.list_item_society, null, false);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            return new ViewHolder(v);


        }

        @Override
        protected void bindViewholder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder societyHolder = (ViewHolder) holder;
            societyHolder.setSociety(getItem(position));

        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private TextView societyAddressTextView;
            private ImageView societyImgView;
            private TextView societyNameTextView;
            private String key = null;
            private Society society;

            public ViewHolder(View itemView) {
                super(itemView);
                societyNameTextView = (TextView) itemView.findViewById(R.id.society_name);
                societyAddressTextView = (TextView) itemView.findViewById(R.id.society_address);
                societyImgView = (ImageView) itemView.findViewById(R.id.society_img);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectionListener != null && society != null)
                            selectionListener.onSocietySelected(society.name, society.societyId);
                        SocietySelectionFragment.this.dismiss();
                    }
                });
            }

            public void setSociety(Society society) {
                this.society = society;

                if (query != null && query.trim().length() > 0) {
                    Spannable wordtoSpan = new SpannableString(society.name);
                    int spanStartIndex = society.name.toLowerCase().indexOf(query.toLowerCase());
                    if (spanStartIndex >= 0) {
                        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), spanStartIndex, spanStartIndex + query.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        wordtoSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), spanStartIndex, spanStartIndex + query.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    societyNameTextView.setText(wordtoSpan);
                } else {
                    societyNameTextView.setText(society.name);
                }

                societyAddressTextView.setText(society.address);

                Picasso.with(getContext()).load(society.societypic).placeholder(R.drawable.ic_1_2).into(societyImgView);
                key = society.societyId;
            }
        }
    }

    public interface ISocietySelectionListener {
        public void onSocietySelected(String societyName, String societyValue);
    }

}
