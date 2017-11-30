package com.footsy.footsy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PagerFragment extends Fragment {

    public static final int NUM_PAGES = 14;
    public ViewPager mPagerHandler;
    private final MainScreenFragment[] mViewFragments = new MainScreenFragment[NUM_PAGES];
    private MyPageAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        mPagerHandler = rootView.findViewById(R.id.pager);
        mPagerAdapter = new MyPageAdapter(getChildFragmentManager());

        for (int i = 0; i < NUM_PAGES; i++) {
            Date fragmentDate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            mViewFragments[i] = new MainScreenFragment();
            mViewFragments[i].setFragmentDate(dateFormat.format(fragmentDate));
        }

        mPagerHandler.setAdapter(mPagerAdapter);
        mPagerHandler.setCurrentItem(FixtureList.currentFragment);
        return rootView;
    }

    private class MyPageAdapter extends FragmentStatePagerAdapter {

        @Override
        public Fragment getItem(int i) {
            return mViewFragments[i];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            //Log.v(TAG, "getPageTitle");
            return getDate(System.currentTimeMillis() + ((position - 2) * 86400000));
        }

        public String getDate(long dateInMillis) {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM");
            return df.format(dateInMillis);
        }
    }
}
