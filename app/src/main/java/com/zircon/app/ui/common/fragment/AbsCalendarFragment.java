package com.zircon.app.ui.common.fragment;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zircon.app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jikoobaruah on 03/02/16.
 */
public abstract class AbsCalendarFragment extends AbsFragment  implements AbsMonthFragment.IMonthInteractionListener {



    private boolean isViewPagerFullScreen = true;

    protected View mParentView;
    protected ViewPager mViewPager;
    protected RecyclerView mRecyclerView;
    LinearLayoutManager layoutManager;

    private int startYear = 2016;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_calendar,null,false) ;
        return mParentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager)mParentView.findViewById(R.id.calendar_viewpager);
        mViewPager.setAdapter(new CalendarAdapter(getFragmentManager(),startYear));

        mRecyclerView = (RecyclerView) mParentView.findViewById(R.id.calendar_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(getCalendarRecycleViewAdapter());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 50;
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int visibleItemCount = mRecyclerView.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                View firstVisibleView = layoutManager.getChildAt(firstVisibleItem);
                View lastVisibleView = layoutManager.getChildAt(firstVisibleItem+visibleItemCount);


            }
        });


        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int selection = (year - startYear)*12+month;
        mViewPager.setCurrentItem(selection);
        setTitle(selection);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(position);
                int recylerViewPos = ((AbsCalendarRecycleViewAdapter) mRecyclerView.getAdapter()).getPositionForDate(getCalendar(position));
                mRecyclerView.scrollToPosition(recylerViewPos);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setPageTransformer(true, new DepthPageTransformer());


    }

    protected abstract RecyclerView.Adapter getCalendarRecycleViewAdapter();


    private void setTitle(int position){
        getActivity().setTitle(new SimpleDateFormat("MMMM,yyyy").format(getCalendar(position).getTime()));
    }




    private Calendar getCalendar(int position){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, position % 12);
        calendar.set(Calendar.YEAR, startYear + position / 12);
        calendar.set(Calendar.DATE,1);
        return calendar;
    }


    @Override
    public void onDayCellClicked(int month, int date) {
        if (isViewPagerFullScreen){
            collapse();
        }

        Calendar c = getCalendar(mViewPager.getCurrentItem());
        c.set(c.get(Calendar.YEAR), month, date, 0, 0, 0);
        int recylerViewPos = ((AbsCalendarRecycleViewAdapter) mRecyclerView.getAdapter()).getPositionForDate(c);
        mRecyclerView.scrollToPosition(recylerViewPos);

    }

    private void collapse() {
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
//        lp.height =lp.height > 0? lp.height/2:mViewPager.getMeasuredHeight()/2;
        lp.height = point.y/2;
//        lp.height = lp.height;
        mViewPager.setLayoutParams(lp);
        mViewPager.requestLayout();

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        lp1.height =lp.height ;
        mRecyclerView.setLayoutParams(lp1);

        int recylerViewPos = ((AbsCalendarRecycleViewAdapter) mRecyclerView.getAdapter()).getPositionForDate(getCalendar(mViewPager.getCurrentItem()));
        mRecyclerView.smoothScrollToPosition(recylerViewPos);

        isViewPagerFullScreen = false;
    }

    private void expand() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
        lp.height =lp.height > 0? lp.height*2:mViewPager.getMeasuredHeight()*2;
        mViewPager.setLayoutParams(lp);
        mViewPager.requestLayout();

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        lp1.height = 0;
        mRecyclerView.setLayoutParams(lp1);

        isViewPagerFullScreen = true;
    }

    protected abstract AbsMonthFragment getMonthFragment(Bundle args, int position);


    private class CalendarAdapter extends FragmentStatePagerAdapter{

        private int startYear;

        public CalendarAdapter(FragmentManager fm,int startYear) {
            super(fm);
            this.startYear = startYear;
        }


        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putInt(AbsMonthFragment.ARGS.MONTH, position%12);
            args.putInt(AbsMonthFragment.ARGS.YEAR, startYear + position/12);
            args.putInt(AbsMonthFragment.ARGS.POSITION, position);
            AbsMonthFragment fragment = getMonthFragment(args, position);
            fragment.setMonthInteractionListener(AbsCalendarFragment.this);

            return fragment;
        }

        @Override
        public int getCount() {
            return (startYear+10)*12;
        }
    }

    private class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    protected abstract class AbsCalendarRecycleViewAdapter extends RecyclerView.Adapter<AbsCalendarRecycleViewAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.list_item_calendar_day,null,false);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Calendar c = Calendar.getInstance();
            c.set(startYear, 0, 1, 0, 0, 0);
            c.add(Calendar.DATE, position);

            holder.daytextView.setText(new SimpleDateFormat("EEE").format(c.getTime()));
            holder.datetextView.setText(new SimpleDateFormat("dd").format(c.getTime()));
            holder.monthtextView.setText(new SimpleDateFormat("MMM").format(c.getTime()));

        }

        @Override
        public int getItemCount() {
            Calendar c = Calendar.getInstance();
            c.set(startYear, 0, 1,0,0,0);


            Calendar c1 = Calendar.getInstance();
            c1.set(startYear + 10, 11, 31,0,0,0);


            return (int)((c1.getTimeInMillis() - c.getTimeInMillis())/(1000 * 60 * 60 * 24));
        }

        public int getPositionForDate(Calendar c){
            Calendar c1 = Calendar.getInstance();
            c1.set(startYear, 0, 1,0,0,0);
            return (int)((c.getTimeInMillis() - c1.getTimeInMillis())/(1000 * 60 * 60 * 24));

        }

        protected Calendar getCalendarForPosition(int position){
            Calendar c1 = Calendar.getInstance();
            c1.set(startYear, 0, 1,0,0,0);
            c1.add(Calendar.DATE,position);
            return c1;
        }


        protected class ViewHolder extends RecyclerView.ViewHolder{

            TextView datetextView ;
            TextView daytextView ;
            TextView monthtextView ;
            public TextView infotextView ;

            public ViewHolder(View itemView) {
                super(itemView);
                datetextView = (TextView) itemView.findViewById(R.id.date);
                monthtextView = (TextView) itemView.findViewById(R.id.date_month);
                daytextView = (TextView) itemView.findViewById(R.id.date_day);
                infotextView = (TextView) itemView.findViewById(R.id.date_info);
            }
        }
    }



}
