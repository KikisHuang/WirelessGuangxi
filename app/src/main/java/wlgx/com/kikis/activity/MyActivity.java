package wlgx.com.kikis.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import wlgx.com.kikis.R;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.IntentUtils.goChangePage;
import static wlgx.com.kikis.utils.IntentUtils.goLoginPage;
import static wlgx.com.kikis.utils.IntentUtils.goWithdrawPage;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/15.
 */
public class MyActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = "MyActivity";
    private LinearLayout change_pass, change_phone, change_user;

    @Override
    protected void click() {
        change_phone.setOnClickListener(this);
        change_pass.setOnClickListener(this);
        change_user.setOnClickListener(this);

    }

    @Override
    protected void init() {
        setContentView(R.layout.my_layout);
        setTitles(this, "个人中心");

        change_phone = f(R.id.change_phone);
        change_pass = f(R.id.change_pass);
        change_user = f(R.id.change_user);

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_pass:
                goChangePage(this, "0");
                break;
            case R.id.change_phone:
                goChangePage(this, "1");
                break;
            case R.id.change_user:
                goWithdrawPage(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 718) {
            switch (resultCode) {
                case 100:
                    goLoginPage(this);
                    break;
                case 101:
                    ToastUtil.toast2_bottom(this, "修改手机号成功！");
                    break;
            }
        }
    }
}
