package com.zircon.app.ui.complaint;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zircon.app.R;
import com.zircon.app.model.Comment;
import com.zircon.app.model.response.ComplaintCommentResponse;
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
public class ComplaintDetailsFragment extends AbsBaseListFragment {

    interface IARGS{
        String COMPLAINT_ID = "id";
    }

    private String mComplaintID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComplaintID = getArguments().getString(IARGS.COMPLAINT_ID,null);
        if (mComplaintID == null)
            throw new NullPointerException("mComplaintID is null");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_complaint,null,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        (getActivity().findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_complaint_comment_add,null,false);
//                v.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(dialogView);
                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = (EditText)dialogView.findViewById(R.id.et_comment);
                        String comment = et.getText().toString();
                        System.out.println("jyo :::: "+comment);

                        Comment commentObj = new Comment();
                        commentObj.comment = comment;
                        commentObj.complaintid = mComplaintID;
                        commentObj.status = Comment.Status.SENDING_TO_SERVER;
                        commentObj.user = SessionManager.getLoggedInUser();
                        ((ComplaintCommentsAdapter) mListAdapter).add(commentObj);
                    }
                });
                alertDialogBuilder.create().show();
            }
        });
    }

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
        return new ComplaintCommentsAdapter();
    }

    @Override
    public void fetchList() {
        Call<ComplaintCommentResponse> call = HTTP.getAPI().getComplaintDetails(SessionManager.getToken(),mComplaintID);
        call.enqueue(new AuthCallBack<ComplaintCommentResponse>() {
            @Override
            protected void onAuthError() {

            }

            @Override
            protected void parseSuccessResponse(Response<ComplaintCommentResponse> response) {
                if (response.isSuccess() && response.body() != null && response.body().body != null) {
//                    getmRecyclerView().setPadding(0,40,0,0);
                    ((ComplaintCommentsAdapter) mListAdapter).addAll(response.body().body.comments);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });


    }
}
