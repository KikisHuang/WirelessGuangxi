package wlgx.com.kikis.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.BankBean;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.IntentUtils.goAddShopPage;
import static wlgx.com.kikis.utils.IntentUtils.goShopChangePage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/21.
 */
public class ShopListActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private CircleImageView icon_img;
    private RelativeLayout layout;
    private TextView add_tv,name_tv,address_tv;

    @Override
    protected void click() {
        layout.setOnClickListener(this);
        add_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.shop_list_layout);
        setTitles(this,"门店管理");
        icon_img = f(R.id.icon_img);
        layout = f(R.id.layout);
        add_tv = f(R.id.add_tv);
        name_tv = f(R.id.name_tv);
        address_tv = f(R.id.address_tv);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShopData();
    }

    private void getShopData() {

        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ShopListActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                BankBean bb = new Gson().fromJson(String.valueOf(ob), BankBean.class);

                                if (bb.getShopImgs().size() > 0)
                                    Glide.with(getApplicationContext()).load(bb.getLogoUrl()).into(icon_img);
                                else
                                    Glide.with(getApplicationContext()).load(R.mipmap.test_icon).into(icon_img);

                                name_tv.setText(bb.getName());
                                address_tv.setText(bb.getAddress());
                            } else
                                ToastUtil.ToastErrorMsg(ShopListActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout:
                goShopChangePage(this);
                break;
            case R.id.add_tv:
                goAddShopPage(this);
                break;
        }
    }
}
