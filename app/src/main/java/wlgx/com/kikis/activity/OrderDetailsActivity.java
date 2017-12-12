package wlgx.com.kikis.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.GoodsDetailsBean;
import wlgx.com.kikis.bean.McOrderGoodsMappingsBean;
import wlgx.com.kikis.fragment.son.OverOrderFragment;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.StringUtil;
import wlgx.com.kikis.utils.TextViewColorUtils;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.SynUtils.setTitles;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;

/**
 * Created by lian on 2017/9/13.
 */
public class OrderDetailsActivity extends InitActivity implements View.OnClickListener {

    private TextView address_tv, shop_name_tv, goods_price_tv, express_fee_name, express_fee_tv, total_price, order_number, order_time, total_prices;
    private String id = "";
    private LinearLayout goods_info_layout;
    private Button pass_bt;

    @Override
    protected void click() {
        pass_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.goods_details_layout);

        address_tv = f(R.id.address_tv);

        shop_name_tv = f(R.id.shop_name_tv);
        goods_info_layout = f(R.id.goods_info_layout);
        goods_price_tv = f(R.id.goods_price_tv);
        express_fee_name = f(R.id.express_fee_name);
        express_fee_tv = f(R.id.express_fee_tv);
        total_price = f(R.id.total_price);
        pass_bt = f(R.id.pass_bt);

        order_number = f(R.id.order_number);
        order_time = f(R.id.order_time);
        total_prices = f(R.id.total_prices);

        getOrderId();
        setTitles(this, "订单详情");
    }

    private void getOrderId() {
        try {
            id = getIntent().getStringExtra("order_id");
            if (id.isEmpty()) {
                ToastUtil.toast2_bottom(this, "id为空");
                finish();
            }
        } catch (NullPointerException e) {
            ToastUtil.toast2_bottom(this, "未接收到id");
            finish();
        }
    }

    @Override
    protected void initData() {
        getOrderDetials();
    }

    private void getOrderDetials() {
        /**
         * 订单获取;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.ORDERDETAILS)
                .addParams("id", id)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(OrderDetailsActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                GoodsDetailsBean gb = new Gson().fromJson(String.valueOf(ob), GoodsDetailsBean.class);

                                if (gb.getStatus() == 1)
                                    pass_bt.setVisibility(View.VISIBLE);

                                address_tv.setText(getResources().getString(R.string.take_address) + "：" + StringUtil.checkNull(gb.getAddressInfo()) + " " + StringUtil.checkNull(gb.getAddressInfoName()) + " " + StringUtil.checkNull(gb.getAddressInfoPhone()));
                                shop_name_tv.setText(StringUtil.checkNull(gb.getMcShop().getName()));
                                goods_price_tv.setText(String.valueOf(gb.getPrice()));
                                order_number.setText(getResources().getString(R.string.order_number) + "：" + StringUtil.checkNull(gb.getOrderNumber()));
                                order_time.setText(getResources().getString(R.string.order_time) + "：" + StringUtil.checkNull(gb.getCreateTime()));

                                List<McOrderGoodsMappingsBean> mc = gb.getMcOrderGoodsMappings();
                                int freight = 0;
                                for (int i = 0; i < mc.size(); i++) {

                                    View view = LayoutInflater.from(OrderDetailsActivity.this).inflate(R.layout.goods_info_include, null);
                                    TextView name = (TextView) view.findViewById(R.id.goods_name);
                                    name.setText(mc.get(i).getGoods().getTitle());
                                    TextView now = (TextView) view.findViewById(R.id.goods_now_price);
                                    now.setText("¥" + String.valueOf(mc.get(i).getGoods().getPrice()));
                                    TextView type = (TextView) view.findViewById(R.id.goods_type);
                                    type.setVisibility(View.GONE);

                                    TextView num = (TextView) view.findViewById(R.id.goods_number);
                                    num.setText("X" + mc.get(i).getCount());
                                    Glide.with(OrderDetailsActivity.this).load(mc.get(i).getGoods().getLogoUrl()).into((ImageView) view.findViewById(R.id.goods_head));

                                    freight += mc.get(i).getGoods().getFreight();

                                    goods_info_layout.addView(view);

                                }

                                String all = String.valueOf(gb.getPrice() + freight);

                                total_price.setText("¥" + all);

                                TextViewColorUtils.setTextColor(total_prices, getResources().getString(R.string.total) + "：", "¥ " + all, "#FF4979");

                                express_fee_tv.setText(String.valueOf("¥" + freight));
                            } else
                                ToastUtil.ToastErrorMsg(OrderDetailsActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pass_bt:
                Show(OrderDetailsActivity.this, "", true, null);
                OrderReceiving();
                break;
        }
    }

    private void OrderReceiving() {
        /**
         * 商家接单;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.RECEIVEENTITY)
                .addParams("id", id)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(OrderDetailsActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(OrderDetailsActivity.this, "已接单！");
                                if(OverOrderFragment.updata!=null)
                                    OverOrderFragment.updata.onUpData();
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(OrderDetailsActivity.this, response, code);

                            Cancle();
                        } catch (Exception e) {
                            Cancle();
                        }
                    }
                });
    }
}
