package com.zircon.app.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zircon.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by jikoobaruah on 03/02/16.
 */
public abstract class AbsMonthFragment extends AbsFragment {

    protected HashMap<String,DayCellView> dateViewMap= new HashMap<>();
    private IMonthInteractionListener monthInteractionListener;

    public void setMonthInteractionListener(IMonthInteractionListener monthInteractionListener) {
        this.monthInteractionListener = monthInteractionListener;
    }

    interface ARGS {
        String POSITION = "position";
        String MONTH = "month";
        String YEAR = "year";
    }

    protected int position;
    protected int month;
    protected int year;
    protected String mTitle;

    protected HashMap<Integer,DayCellView> dateViewIDMap= new HashMap<>();

    protected View mParentView;

    protected TextView mMonthYear;

    protected RecyclerView mRecyclerView;
    protected GridLayoutManager mLayoutManager;

    protected ArrayList<String> monthItems;


    protected int monthDays;
    protected String monthStartDay;

    protected int monthStartTextViewID;
    protected int[] dayViews ={
            R.id.d_1_1,R.id.d_1_2,R.id.d_1_3,R.id.d_1_4,R.id.d_1_5,R.id.d_1_6,R.id.d_1_7,
            R.id.d_2_1,R.id.d_2_2,R.id.d_2_3,R.id.d_2_4,R.id.d_2_5,R.id.d_2_6,R.id.d_2_7,
            R.id.d_3_1,R.id.d_3_2,R.id.d_3_3,R.id.d_3_4,R.id.d_3_5,R.id.d_3_6,R.id.d_3_7,
            R.id.d_4_1,R.id.d_4_2,R.id.d_4_3,R.id.d_4_4,R.id.d_4_5,R.id.d_4_6,R.id.d_4_7,
            R.id.d_5_1,R.id.d_5_2,R.id.d_5_3,R.id.d_5_4,R.id.d_5_5,R.id.d_5_6,R.id.d_5_7,
            R.id.d_6_1,R.id.d_6_2,R.id.d_6_3,R.id.d_6_4,R.id.d_6_5,R.id.d_6_6,R.id.d_6_7,
    };

    protected Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARGS.POSITION,-1);
        month = getArguments().getInt(ARGS.MONTH,-1);
        year = getArguments().getInt(ARGS.YEAR, -1);

        if (month < Calendar.JANUARY || month >Calendar.DECEMBER || year < 2000  || year  > 2050){
            throw new IllegalArgumentException("MonthFragment must be instantiated with args month(0-11) and year(2016-2050).");
        }

        Calendar calendar =Calendar.getInstance();
        calendar.set(year, month, 1,0,0,0);


        switch (month){
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                monthDays = 31;
                break;
            case Calendar.FEBRUARY:
                monthDays = (year % 4 == 0 && year % 100 != 0)|| (year % 400 == 0)?29:28;
                break;
            default:
                monthDays = 30;
        }

        monthStartDay = new SimpleDateFormat("E").format(calendar.getTime());

        switch (monthStartDay){
            case "Sun":
                monthStartTextViewID=R.id.d_1_1;
                break;

            case "Mon":
                monthStartTextViewID=R.id.d_1_2;
                break;

            case "Tue":
                monthStartTextViewID=R.id.d_1_3;
                break;

            case "Wed":
                monthStartTextViewID=R.id.d_1_4;
                break;

            case "Thu":
                monthStartTextViewID=R.id.d_1_5;
                break;

            case "Fri":
                monthStartTextViewID=R.id.d_1_6;
                break;

            case "Sat":
                monthStartTextViewID=R.id.d_1_7;
                break;
        }

        mTitle = new SimpleDateFormat("MMMM,yyyy").format(calendar.getTime());




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_month, container, false);
        mParentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mParentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMonthYear = (TextView) mParentView.findViewById(R.id.month_year);
        mMonthYear.setText(mTitle);

        int date = 1;
        boolean isMonthStarted = false;
        for (int id : dayViews) {
            if (date <= monthDays) {
                if (id == monthStartTextViewID || isMonthStarted) {
                    DayCellView day = ((DayCellView) mParentView.findViewById(id));
                    day.setDate(date + "");
                    dateViewIDMap.put(date, day);

                    day.setClickable(true);
                    final int finalDate = date;
                    day.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (monthInteractionListener != null){
                                monthInteractionListener.onDayCellClicked(month, finalDate);
                            }
                        }
                    });

                    if (isToday(date)){
                        day.setSelected(true);
                    }else {
                        day.setSelected(false);
                    }
                    date++;
                    isMonthStarted = true;
                }
            }
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,1,0,0,0);
        fetchEvents(calendar, position);

    }

    public boolean isToday(int dateOfMonth) {
         return (Calendar.getInstance().get(Calendar.YEAR) == year && Calendar.getInstance().get(Calendar.MONTH) == month && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == dateOfMonth);
    }

    public int getPosition() {
        return position;
    }


    protected abstract void fetchEvents(Calendar calendar, int position);

    interface IMonthInteractionListener {

        void onDayCellClicked(int month,int date);
    }


}
