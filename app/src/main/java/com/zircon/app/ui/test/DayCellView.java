package com.zircon.app.ui.test;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zircon.app.R;

/**
 * Created by jikoobaruah on 04/02/16.
 */
public class DayCellView extends LinearLayout {

    private String date;

    private TextView dateTextView;

    public DayCellView(Context context) {
        super(context);
        init();
    }

    public DayCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DayCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(VERTICAL);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_day_view,null,false);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(v);

        dateTextView = (TextView)findViewById(R.id.date);

    }


    public void setDate(String date) {
        this.date = date;
        dateTextView.setText(date);
    }

    public void setDateContentView(View view){
        addView(view);
        invalidate();
    }

    @Override
    public void setSelected(boolean selected) {
        dateTextView.setSelected(selected);
    }
}
