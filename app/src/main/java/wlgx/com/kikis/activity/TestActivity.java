package wlgx.com.kikis.activity;

import android.view.View;

import wlgx.com.kikis.R;

/**
 * Created by lian on 2017/10/20.
 */
public class TestActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = "TestActivity";
    private String x = "小狼头";
    private String a = "阿里巴巴";
    private String g = "郭敬明";
    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                break;

        }
    }

}
