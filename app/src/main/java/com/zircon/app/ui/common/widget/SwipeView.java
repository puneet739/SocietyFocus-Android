package com.zircon.app.ui.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.ui.common.activity.AbsBaseActivity;

/**
 * Created by jyotishman on 20/03/16.
 */
public class SwipeView extends LinearLayout  {

    private float swipeLength = 0;

    public SwipeView(Context context) {
        super(context);
        init();
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        setWillNotDraw(false);
        setOnTouchListener(new SwipeTouchListener());


    }
//
//    public void trackGesture(){
//
//    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//        System.out.println("jyo :::: " + swipeLength );
//        if(swipeLength > 0) {
//            Paint paint = new Paint();
//            paint.setColor(Color.BLUE);
//            canvas.drawRect(0, 0, swipeLength, getHeight(), paint);
//        }
//    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        System.out.println("jyo :::: " + swipeLength);
//        if(swipeLength > 0) {
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            canvas.drawRect(0, 0, swipeLength, getHeight(), paint);
//        }
    }

    private class SwipeTouchListener implements View.OnTouchListener {
        float historicX = Float.NaN, historicY = Float.NaN;
        static final int TRIGGER_DELTA = 50; // Number of pixels to travel till trigger

        Drawable bg ;


        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    historicX = e.getX();
                    historicY = e.getY();
                    bg = v.getBackground();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (e.getX() - historicX > TRIGGER_DELTA){
                        swipeLength = e.getX() - historicX;
                        SwipeView.this.invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    v.setBackground(bg);
                    if (e.getX() - historicX > TRIGGER_DELTA) {
                        System.out.println("call");
                        swipeLength = 0;
                        invalidate();
                        ((AbsBaseActivity)v.getContext()).call((String) ((TextView) v.findViewById(R.id.phone)).getText());

                        return true;
                    }
                    else if (historicX - e.getX() > TRIGGER_DELTA)  {
                        System.out.println("sms");

                        return true;
                    } break;
                default:
                    return false;
            }
            return false;
        }
    }

}
