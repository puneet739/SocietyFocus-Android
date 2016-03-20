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


    int FORCE_TRIGGER_DELTA = 400; // Number of pixels to travel till trigger
    int TRIGGER_DELTA = 200; // Number of pixels to travel till trigger
    int TRACK_DELTA = 30; // Number of pixels to travel till trigger

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


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        System.out.println("jyo:::: " + swipeLength);

        FORCE_TRIGGER_DELTA = getWidth()*3/4;
        TRIGGER_DELTA = getWidth()/2;
        TRACK_DELTA = getWidth()/6;

        Paint paint = new Paint();
        Paint text = new Paint();
        text.setColor(Color.WHITE);


        if (swipeLength == 0) {
            paint.setColor(Color.TRANSPARENT);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        }else {

            // blue bg
            paint.setColor(Color.BLUE);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

            //call
            if (swipeLength > 0) {
                canvas.drawText("CALL", getWidth() / 2, getHeight() / 2, text);
                paint.setColor(Color.GREEN);

                canvas.drawRect(0, 0, swipeLength > TRIGGER_DELTA ? getWidth() : swipeLength, getHeight(), paint);
            }
            //message
            else if (swipeLength < 0) {
                paint.setColor(Color.RED);
                canvas.drawRect(Math.abs(swipeLength) > TRIGGER_DELTA ? 0 : getWidth() + swipeLength, 0, getWidth(), getHeight(), paint);
                canvas.drawText("Message", getWidth() / 2, getHeight() / 2, text);

            }
        }
    }

    private class SwipeTouchListener implements View.OnTouchListener {
        float historicX = Float.NaN, historicY = Float.NaN;

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("jyo MotionEvent.ACTION_DOWN");

                    historicX = e.getX();
                    historicY = e.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    System.out.println("jyo MotionEvent.ACTION_MOVE");

                    if (Math.abs(e.getX() - historicX) > FORCE_TRIGGER_DELTA) {
                        swipeLength = 0;
                        invalidate();

                         if (e.getX() > historicX){
                             System.out.println("jyo call");
                             ((AbsBaseActivity)v.getContext()).call((String) ((TextView) v.findViewById(R.id.phone)).getText());
                         }else{
                             System.out.println("jyo sms");
                             ((AbsBaseActivity)v.getContext()).sms((String) ((TextView) v.findViewById(R.id.phone)).getText());
                         }

                    }else
                    if (Math.abs(e.getX() - historicX) > TRACK_DELTA){
                        swipeLength = e.getX() - historicX;
                        SwipeView.this.invalidate();
                    }else {
                        swipeLength = 0;
                        invalidate();
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("jyo MotionEvent.ACTION_UP");

                    swipeLength = 0;
                    invalidate();
                    if (e.getX() - historicX > TRIGGER_DELTA) {
                        System.out.println("jyo call");
                        ((AbsBaseActivity)v.getContext()).call((String) ((TextView) v.findViewById(R.id.phone)).getText());
                        return true;
                    }
                    else if (historicX - e.getX() > TRIGGER_DELTA)  {
                        System.out.println("jyo sms");
                        ((AbsBaseActivity) v.getContext()).sms((String) ((TextView) v.findViewById(R.id.phone)).getText());
                        return true;
                    }

                    break;
                default:
                    swipeLength = 0;
                    invalidate();
                    return true;
            }
            return true;
        }
    }

}
