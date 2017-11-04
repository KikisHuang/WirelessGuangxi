package wlgx.com.kikis.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.BankBean;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.PhoneCodeUtils.getPhoneCode;
import static wlgx.com.kikis.utils.StringUtil.checkNull;
import static wlgx.com.kikis.utils.SynUtils.Finish;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/16.
 * 修改账户资料页面;
 */
public class WithdrawActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = "MyActivity";

    private LinearLayout wechat_ll, alipay_ll, bank_ll, bank_name, bank_user_name, phone_ll;
    private TextView wx, ali, bank, bankname, bankusername, phone;
    private EditText wxed, alied, banked, banknamed, bankusered, phone_ed, code_ed;
    private RippleView submit_info, code_tv;

    private int page = 20;
    private Handler handler;
    private boolean sflag;
    private Runnable regisRunnable;

    @Override
    protected void click() {
        submit_info.setOnClickListener(this);
        code_tv.setOnClickListener(this);

    }

    @Override
    protected void init() {
        setContentView(R.layout.withdraw_layout);
        setTitles(this, "提现资料修改");
        wechat_ll = f(R.id.wechat_ll);
        alipay_ll = f(R.id.alipay_ll);
        bank_ll = f(R.id.bank_ll);
        phone_ll = f(R.id.phone_ll);
        bank_name = f(R.id.bank_name);
        bank_user_name = f(R.id.bank_user_name);

        submit_info = f(R.id.submit_info);
        code_tv = f(R.id.code_tv);

        code_ed = f(R.id.code_ed);
        phone = (TextView) phone_ll.findViewById(R.id.tag_tv);

        wx = (TextView) wechat_ll.findViewById(R.id.tag_tv);
        ali = (TextView) alipay_ll.findViewById(R.id.tag_tv);
        bank = (TextView) bank_ll.findViewById(R.id.tag_tv);
        bankname = (TextView) bank_name.findViewById(R.id.tag_tv);
        bankusername = (TextView) bank_user_name.findViewById(R.id.tag_tv);
        phone.setText("手机号：");

        ali.setText("支付宝账号：");
        bank.setText("银行账户：");
        bankname.setText("开户行名称：");
        bankusername.setText("开户人姓名：");


        phone_ed = (EditText) phone_ll.findViewById(R.id.ed);
        wxed = (EditText) wechat_ll.findViewById(R.id.ed);
        alied = (EditText) alipay_ll.findViewById(R.id.ed);
        banked = (EditText) bank_ll.findViewById(R.id.ed);
        banknamed = (EditText) bank_name.findViewById(R.id.ed);
        bankusered = (EditText) bank_user_name.findViewById(R.id.ed);

        phone_ed.setHint("修改账户资料必须手机验证");

        wxed.setHint("微信账号");
        alied.setHint("支付宝账号");
        banked.setHint("银行账户");
        banknamed.setHint("开户行名称");
        bankusered.setHint("开户人姓名");


    }

    @Override
    protected void initData() {
        hand();
        getData();
    }

    private void hand() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        code_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_corner2));
                        code_tv.setTextColor(getResources().getColor(R.color.white));
                        code_tv.setText("重发(" + page + ")");
                        if (page <= 0) {
                            sflag = false;
                            code_tv.setEnabled(true);
                            code_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.code_corner));
                            code_tv.setTextColor(getResources().getColor(R.color.red3));
                            code_tv.setText("获取验证码");
                            page = 20;
                        } else {
                            code_tv.setEnabled(false);
                            page--;
                        }

                        break;
                }

            }
        };
    }

    private void getData() {

        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(WithdrawActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                BankBean bb = new Gson().fromJson(String.valueOf(ob), BankBean.class);
                                wxed.setText(checkNull(bb.getWxAccount()));
                                alied.setText(checkNull(bb.getAliAccount()));
                                banked.setText(checkNull(bb.getBankCardAccount()));
                                banknamed.setText(checkNull(bb.getBankName()));
                                bankusered.setText(checkNull(bb.getBankUserName()));


                            } else
                                ToastUtil.ToastErrorMsg(WithdrawActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_info:
                ChangeShopInfo();
                break;
            case R.id.code_tv:
                if (!phone_ed.getText().toString().isEmpty()) {
                    code_tv.setEnabled(false);
                    Log.i(TAG, "click...");
                    sflag = true;
                    new Thread(regisRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "倒计时...");
                            while (sflag) {
                                Message msg = new Message();
                                msg.what = 0;
                                if (handler != null)
                                    handler.sendMessage(msg);

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                    getPhoneCode(this, phone_ed.getText().toString(), MzFinal.PHONE);

                } else
                    ToastUtil.toast2_bottom(WithdrawActivity.this, "手机号不能为空!");
                break;
        }
    }

    private void ChangeShopInfo() {

        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.MODIFYSENSITIVEINFO)
                .addParams("wxAccount", wxed.getText().toString())
                .addParams("aliAccount", alied.getText().toString())
                .addParams("bankCardAccount", banked.getText().toString())
                .addParams("bankName", banknamed.getText().toString())
                .addParams("bankUserName", bankusered.getText().toString())
                .addParams("code", code_ed.getText().toString())
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(WithdrawActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                BankBean bb = new Gson().fromJson(String.valueOf(ob), BankBean.class);
                                wxed.setText(checkNull(bb.getWxAccount()));
                                alied.setText(checkNull(bb.getAliAccount()));
                                banked.setText(checkNull(bb.getBankCardAccount()));
                                banknamed.setText(checkNull(bb.getBankName()));
                                bankusered.setText(checkNull(bb.getBankUserName()));
                                ToastUtil.toast2_bottom(WithdrawActivity.this, "修改成功!");
                                Finish(WithdrawActivity.this);
                            } else
                                ToastUtil.ToastErrorMsg(WithdrawActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        if (handler != null) {
            handler.removeCallbacks(regisRunnable);
            handler = null;
            sflag = false;
        }
    }
}
