package wlgx.com.kikis.activity;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.PictureSlidePagerAdapter;
import wlgx.com.kikis.fragment.son.AllOrderFragment;
import wlgx.com.kikis.fragment.son.OverOrderFragment;
import wlgx.com.kikis.fragment.son.OverRefundOrderFragment;
import wlgx.com.kikis.fragment.son.RefundOrderFragment;
import wlgx.com.kikis.fragment.son.WaitOrderFragment;
import wlgx.com.kikis.view.CustomViewPager;

import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/11.
 */
public class MyOrderActivity extends InitActivity {
    private CustomViewPager viewPager;
    private TabLayout mTab;
    private List<String> list;
    private List<Fragment> fragment;
    private PictureSlidePagerAdapter page_adapter;
    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.my_order_activity);
        setTitles(this, "订单管理");
        mTab = f(R.id.tab_layout);
        viewPager = f(R.id.viewPager);
        list = new ArrayList<>();
        fragment = new ArrayList<>();
        mTab.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < 5; i++) {
            switch (i){
                case 0:
                    fragment.add(new AllOrderFragment());
                    ((AllOrderFragment) fragment.get(i)).setTag(i);
                    break;
                case 1:
                    fragment.add(new WaitOrderFragment());
                    ((WaitOrderFragment) fragment.get(i)).setTag(i);
                    break;
                case 2:
                    fragment.add(new OverOrderFragment());
                    ((OverOrderFragment) fragment.get(i)).setTag(i);
                    break;
                case 3:
                    fragment.add(new RefundOrderFragment());
                    ((RefundOrderFragment) fragment.get(i)).setTag(i);
                    break;
                case 4:
                    fragment.add(new OverRefundOrderFragment());
                    ((OverRefundOrderFragment) fragment.get(i)).setTag(i);
                    break;
            }
        }
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initData() {
        list.add(getResources().getString(R.string.all));
        list.add(getResources().getString(R.string.wait_pay));
        list.add(getResources().getString(R.string.over_pay));
        list.add(getResources().getString(R.string.wait_refund));
        list.add(getResources().getString(R.string.over_refund));

        mTab.addTab(mTab.newTab().setText(list.get(0)));
        mTab.addTab(mTab.newTab().setText(list.get(1)));
        mTab.addTab(mTab.newTab().setText(list.get(2)));
        mTab.addTab(mTab.newTab().setText(list.get(3)));
        mTab.addTab(mTab.newTab().setText(list.get(4)));
        setPager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTab.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTab, 10, 10);
            }
        });
    }

    private void setPager() {

        page_adapter = new PictureSlidePagerAdapter(getSupportFragmentManager(), fragment, list);

        viewPager.setAdapter(page_adapter);
        mTab.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}