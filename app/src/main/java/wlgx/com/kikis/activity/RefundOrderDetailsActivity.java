package wlgx.com.kikis.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.math.BigDecimal;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.RefundBean;
import wlgx.com.kikis.fragment.son.RefundOrderFragment;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.TextViewColorUtils;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.AlertDialog;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.StringUtil.checkNull;
import static wlgx.com.kikis.utils.SynUtils.setTitles;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;

/**
 * Created by lian on 2017/9/13.
 */
public class RefundOrderDetailsActivity extends InitActivity implements View.OnClickListener {

    private TextView address_tv, shop_name_tv, goods_price_tv, express_fee_name, express_fee_tv, total_price, order_number, order_time, total_prices;
    private String id = "";
    private LinearLayout goods_info_layout;
    //refund;
    private TextView refund_username_tv, refund_contact_tv, refund_status_tv, refund_message_tv, refund_date_tv;

    private Button pass_bt, refuse_bt;
    private String msg = "";
    private String uid = "";

    @Override
    protected void click() {
        refuse_bt.setOnClickListener(this);
        pass_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.refund_details_layout);

        address_tv = f(R.id.address_tv);

        shop_name_tv = f(R.id.shop_name_tv);
        goods_info_layout = f(R.id.goods_info_layout);
        goods_price_tv = f(R.id.goods_price_tv);
        express_fee_name = f(R.id.express_fee_name);
        express_fee_tv = f(R.id.express_fee_tv);
        total_price = f(R.id.total_price);

        order_number = f(R.id.order_number);
        order_time = f(R.id.order_time);
        total_prices = f(R.id.total_prices);

        refuse_bt = f(R.id.refuse_bt);
        pass_bt = f(R.id.pass_bt);

        refund_status_tv = f(R.id.refund_status_tv);
        refund_username_tv = f(R.id.refund_username_tv);
        refund_contact_tv = f(R.id.refund_contact_tv);
        refund_message_tv = f(R.id.refund_message_tv);
        refund_date_tv = f(R.id.refund_date_tv);

        getOrderId();
        setTitles(this, "退款订单");
    }

    private void getOrderId() {
        try {
            id = getIntent().getStringExtra("refund_id");
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
                .url(MzFinal.URL + MzFinal.GETBYORDERID)
                .addParams("id", id)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(RefundOrderDetailsActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {


                                JSONObject ob = getJsonOb(response);
                                RefundBean gb = new Gson().fromJson(String.valueOf(ob), RefundBean.class);

                                address_tv.setText(getResources().getString(R.string.take_address) + "：" + checkNull(gb.getOrder().getAddressInfo()) + " " + checkNull(gb.getOrder().getAddressInfoName() + " " + checkNull(gb.getOrder().getAddressInfoPhone())));

                                shop_name_tv.setText(checkNull(gb.getOrder().getMcShop().getName()));
                                goods_price_tv.setText(String.valueOf(gb.getOrder().getPrice()));
                                order_number.setText(getResources().getString(R.string.order_number) + "：" + checkNull(gb.getOrder().getOrderNumber()));
                                order_time.setText(getResources().getString(R.string.order_time) + "：" + checkNull(gb.getCreateTime()));

                                String a = String.valueOf(gb.getOrder().getTotalFee() + gb.getOrder().getFreight());
                                BigDecimal b = new BigDecimal(a);
                                String all = b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

                                total_price.setText("¥" + all);

                                TextViewColorUtils.setTextColor(total_prices, getResources().getString(R.string.total) + "：", "¥ " + all, "#FF4979");

                                express_fee_tv.setText(String.valueOf("¥" + gb.getOrder().getFreight()));

                                uid = String.valueOf(gb.getId());

                                refund_username_tv.setText(gb.getUserName());
                                refund_contact_tv.setText(gb.getUserPhone());
                                //0未审核 1已通过 -1已拒绝
                                switch (gb.getStatus()) {
                                    case 0:
                                        refund_status_tv.setText("未审核");
                                        break;
                                    case 1:
                                        refund_status_tv.setText("已通过");
                                        break;
                                    case -1:
                                        refund_status_tv.setText("已拒绝");
                                        break;
                                }
                                refund_message_tv.setText(gb.getUserMsg());
                                refund_date_tv.setText(gb.getCreateTime());

                            } else
                                ToastUtil.ToastErrorMsg(RefundOrderDetailsActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pass_bt:
                if (!uid.isEmpty())
                    Refund(1);
                else
                    ToastUtil.toast2_bottom(this, "没有获取到订单id,请重新进入..");

                break;
            case R.id.refuse_bt:
                if (!uid.isEmpty()) {
                    final AlertDialog ad = new AlertDialog(RefundOrderDetailsActivity.this);
                    ad.builder().ShowEdittext("拒绝原由").setTitle("提示").setCancelable(true).setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            msg = ad.getEdittextMsg();
                            Refund(-1);
                        }
                    }).show();
                } else
                    ToastUtil.toast2_bottom(this, "没有获取到订单id,请重新进入..");


                break;
        }
    }

    private void Refund(int status) {

        Show(RefundOrderDetailsActivity.this, "提交中..", true, null);
        /**
         * 订单支付\退款通用;
         * msg=留言信息,status=审核状态(通过传1，拒绝传-1)
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.EXAMINEREFUND)
                .addParams("id", uid)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("msg", msg)
                .addParams("status", String.valueOf(status))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(RefundOrderDetailsActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {

                                ToastUtil.toast2_bottom(RefundOrderDetailsActivity.this, "操作成功...");

                                if (RefundOrderFragment.updata != null)
                                    RefundOrderFragment.updata.onUpData();

                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(RefundOrderDetailsActivity.this, response, code);
                        } catch (Exception e) {
                            Cancle();
                        }
                        Cancle();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
    }
}