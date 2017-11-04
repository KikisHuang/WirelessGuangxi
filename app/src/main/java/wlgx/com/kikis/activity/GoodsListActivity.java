package wlgx.com.kikis.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.PictureSlidePagerAdapter;
import wlgx.com.kikis.fragment.son.GoodsListFragment;
import wlgx.com.kikis.fragment.son.GoodsListThreeFragment;
import wlgx.com.kikis.fragment.son.GoodsListTwoFragment;
import wlgx.com.kikis.view.CustomViewPager;

import static wlgx.com.kikis.utils.IntentUtils.goGoodsUpLoadPage;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/10/18.
 */
public class GoodsListActivity extends InitActivity implements View.OnClickListener {

    private TextView create_tv;
    private CustomViewPager viewPager;
    private TabLayout mTab;
    private List<Fragment> fragment;
    private PictureSlidePagerAdapter page_adapter;
    private List<String> list;

    @Override
    protected void click() {
        create_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.goods_list_layout);
        setTitles(this, "商品列表");
        mTab = f(R.id.tab_layout);
        create_tv = f(R.id.create_tv);
        viewPager = f(R.id.viewPager);
        fragment = new ArrayList<>();
        list = new ArrayList<>();
        mTab.setTabMode(TabLayout.MODE_FIXED);

        fragment.add(new GoodsListFragment());
        ((GoodsListFragment) fragment.get(0)).setTag(0);

        fragment.add(new GoodsListTwoFragment());
        ((GoodsListTwoFragment) fragment.get(1)).setTag(1);

        fragment.add(new GoodsListThreeFragment());
        ((GoodsListThreeFragment) fragment.get(2)).setTag(2);

        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initData() {
        list.add(getResources().getString(R.string.putaway));
        list.add(getResources().getString(R.string.sold_out));
        list.add(getResources().getString(R.string.unputaway));
        mTab.addTab(mTab.newTab().setText(list.get(0)));
        mTab.addTab(mTab.newTab().setText(list.get(1)));
        mTab.addTab(mTab.newTab().setText(list.get(2)));
        setPager();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_tv:
                goGoodsUpLoadPage(GoodsListActivity.this, 0, "");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
