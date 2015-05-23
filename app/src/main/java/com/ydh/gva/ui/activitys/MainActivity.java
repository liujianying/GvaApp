package com.ydh.gva.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;

import com.alibaba.fastjson.JSON;
import com.ydh.gva.R;
import com.ydh.gva.base.BaseActivity;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.location.localtion.UserCityCacher;
import com.ydh.gva.location.ui.PinnedSectionListActivity;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.ui.Fresments.CenterFragment;
import com.ydh.gva.ui.Fresments.HomePageFragment;
import com.ydh.gva.ui.Fresments.MallPageFragment;
import com.ydh.gva.ui.Fresments.MorePageFragment;
import com.ydh.gva.util.net.url.HttpRequestUrl;
import com.ydh.gva.util.net.volley.FastjsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;
import com.ydh.gva.widget.ChangeColorIconWithTextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import gva.ydh.com.util.AppLog;

public class MainActivity extends BaseActivity implements OnPageChangeListener, OnClickListener {

    private ViewPager mViewPager;
    private Vector<Fragment> fragments = new Vector<Fragment>();
    /**区域ID*/
    private int region_cd;

    private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOverflowShowingAlways();
        mViewPager = $(R.id.id_viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        initTabIndicator();
        fragments.add(Fragment.instantiate(this, HomePageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MallPageFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, CenterFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MorePageFragment.class.getName()));
        mViewPager.setOnPageChangeListener(this);
    }


    private void initTabIndicator() {
        ChangeColorIconWithTextView one = $(R.id.id_indicator_one);
        ChangeColorIconWithTextView two = $(R.id.id_indicator_two);
        ChangeColorIconWithTextView three = $(R.id.id_indicator_three);
        ChangeColorIconWithTextView four = $(R.id.id_indicator_four);
        mTabIndicator.add(one);
        mTabIndicator.add(two);
        mTabIndicator.add(three);
        mTabIndicator.add(four);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        one.setIconAlpha(1.0f);
    }

    @Override
    public void onPageSelected(int arg0) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

        if (positionOffset > 0) {
            ChangeColorIconWithTextView left = mTabIndicator.get(position);
            ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {

        resetOtherTabs();

        switch (v.getId()) {
            case R.id.id_indicator_one:
                mTabIndicator.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;

            case R.id.id_indicator_two:
                mTabIndicator.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;

            case R.id.id_indicator_three:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;

            case R.id.id_indicator_four:
                mTabIndicator.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
        }

    }



    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator.size(); i++) {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"选项一", "选项二", "选项三", "选项四"};


        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }
    }


}
