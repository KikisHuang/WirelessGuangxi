package wlgx.com.kikis.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.PhoneCodeUtils.getPhoneCode;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/19.
 */
public class forgetPassActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "forgetPassActivity";

    private RippleView submit_info, code_tv;
    private EditText photo_ed, pass_ed, code_ed;
    private int page = 20;
    private Handler handler;
    private boolean sflag;
    private Runnable regisRunnable;

    @Override
    protected void click() {
        code_tv.setOnClickListener(this);
        submit_info.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.forgetpass_layout);
        setTitles(this, "找回密码");
        pass_ed = f(R.id.pass_ed);
        photo_ed = f(R.id.photo_ed);
        code_tv = f(R.id.code_tv);
        code_ed = f(R.id.code_ed);
        submit_info = f(R.id.submit_info);
    }

    @Override
    protected void initData() {
        hand();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.code_tv:
                if (!photo_ed.getText().toString().isEmpty()) {
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

                    getPhoneCode(this, photo_ed.getText().toString(), MzFinal.PHONE);

                } else
                    ToastUtil.toast2_bottom(forgetPassActivity.this, "手机号不能为空!!");
                break;

            case R.id.submit_info:
                if (!code_ed.getText().toString().isEmpty() && !photo_ed.getText().toString().isEmpty())
                    if (photo_ed.getText().toString().length() == 11)
                        Retrieve();
                    else
                        ToastUtil.toast2_bottom(this, "请输入正确的手机号！！");
                break;
        }
    }

    private void Retrieve() {
        /**
         * 找回密码;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.RESETPASSWORD)
                .addParams("phone", photo_ed.getText().toString())
                .addParams("code", code_ed.getText().toString())
                .addParams("password", pass_ed.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(forgetPassActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {

                                SPreferences.saveUserToken(new JSONObject(response).optString("data"));
                                Log.i(TAG, "token Log======" + new JSONObject(response).optString("data"));
                                ToastUtil.toast2_bottom(forgetPassActivity.this, "新密码设置成功，并自动登录!!");
                                setResult(101);

                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(forgetPassActivity.this, response, code);
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
