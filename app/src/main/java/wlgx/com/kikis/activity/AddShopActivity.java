package wlgx.com.kikis.activity;

import wlgx.com.kikis.R;

import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/21.
 */
public class AddShopActivity extends InitActivity {
    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.add_shop_layout);
        setTitles(this,"添加分店");
    }

    @Override
    protected void initData() {

    }
}
