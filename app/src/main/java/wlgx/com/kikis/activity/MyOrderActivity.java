package wlgx.com.kikis.activity;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.PictureSlidePagerAdapter;
import wlgx.com.kikis.fragment.son.AllOrderFragment;
import wlgx.com.kikis.fragment.son.OverOrderFragment;
import wlgx.com.kikis.fragment.son.OverRefundOrderFragment;
import wlgx.com.kikis.fragment.son.RefundOrderFragment;
import wlgx.com.kikis.fragment.son.WaitOrderFragment;
import wlgx.com.kikis.listener.OrderRefreshListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.CustomViewPager;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonInt;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/11.
 */
public class MyOrderActivity extends InitActivity implements OrderRefreshListener {
    private CustomViewPager viewPager;
    private TabLayout mTab;
    private List<String> list;
    private List<Fragment> fragment;
    private PictureSlidePagerAdapter page_adapter;

    private int RefundCount = 0;
    private int PayOrderCount = 0;
    private static MyOrderActivity activity;

    @Override
    protected void click() {

    }

    public static MyOrderActivity getInstance() {
        return activity;
    }

    @Override
    protected void init() {
        setContentView(R.layout.my_order_activity);
        setTitles(this, "订单管理");
        activity = this;

        mTab = f(R.id.tab_layout);
        viewPager = f(R.id.viewPager);
        list = new ArrayList<>();
        fragment = new ArrayList<>();
        mTab.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < 5; i++) {
            switch (i) {
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
        getOrderCornerMark();
    }

    private void getOrderCornerMark() {
        /**
         * 获取待处理退款角标数量;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETMYREFUNDORDERCOUNT)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(MyOrderActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                 RefundCount = getJsonInt(response);
                                getOrderCornerMarkAgain();
                            } else
                                ToastUtil.ToastErrorMsg(MyOrderActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
    private void getOrderCornerMarkAgain() {
        /**
         *  待接单(已付款)订单数(角标数);
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETMYPAYORDERCOUNT)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(MyOrderActivity.this, "网络不顺畅...");
                        InitTabPage();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1)
                                PayOrderCount = getJsonInt(response);

                            InitTabPage();

                        } catch (Exception e) {
                            InitTabPage();
                        }
                    }
                });
    }

    private void InitTabPage() {
        list.add(getResources().getString(R.string.all)+"\n");
        list.add(getResources().getString(R.string.wait_pay)+"\n");
        if (PayOrderCount > 0 && PayOrderCount < 99)
            list.add(getResources().getString(R.string.over_pay) + "(" + PayOrderCount + ")");
        else if (PayOrderCount > 99)
            list.add(getResources().getString(R.string.over_pay) + "(" + 99 + "+)");
        else
            list.add(getResources().getString(R.string.over_pay)+"\n");

        if (RefundCount > 0 && RefundCount < 99)
            list.add(getResources().getString(R.string.wait_refund) + "(" + RefundCount + ")");
        else if (RefundCount > 99)
            list.add(getResources().getString(R.string.wait_refund) + "(" + 99 + "+)");
        else
            list.add(getResources().getString(R.string.wait_refund)+"\n");

        list.add(getResources().getString(R.string.over_refund)+"\n");

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

    @Override
    public void onRefresh() {

    }
}