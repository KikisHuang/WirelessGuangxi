package wlgx.com.kikis.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.IntentUtils.goLoginPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.PhoneCodeUtils.getPhoneCode;
import static wlgx.com.kikis.utils.PwdCheckUtil.isLetterOrDigit;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/15.
 */
public class ChangeActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = "ChangeActivity";
    private LinearLayout pass1, pass2, pass3, pass_layout;

    private ImageView clear_img1, clear_img2, clear_img3, clear_img4;
    private EditText et1, et2, et3;
    private RippleView change_pass_ri, code_tv, change_phone_ri;

    private LinearLayout change_pass, change_phone;

    private EditText code_ed, phone_ed;

    private int page = 60;
    private Handler handler;
    private boolean sflag;
    private Runnable regisRunnable;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.change_layout);
        change_phone = f(R.id.change_phone);
        change_pass = f(R.id.change_pass);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (getIntent().getStringExtra("change_tag").equals("0")) {
            change_phone.setVisibility(View.GONE);
            setTitles(this, "修改密码");
            PassLayoutInit();
        } else {
            setTitles(this, "修改手机");
            change_pass.setVisibility(View.GONE);
            PhoneLayoutInit();
        }
    }

    private void PhoneLayoutInit() {
        code_ed = f(R.id.code_ed);
        phone_ed = f(R.id.phone_ed);
        code_tv = f(R.id.code_tv);
        clear_img4 = f(R.id.clear_img4);
        change_phone_ri = f(R.id.change_phone_ri);
        change_phone_ri.setOnClickListener(this);
        clear_img4.setOnClickListener(this);
        code_tv.setOnClickListener(this);
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
                            page = 60;
                        } else {
                            code_tv.setEnabled(false);
                            page--;
                        }

                        break;
                }

            }
        };
    }

    private void PassLayoutInit() {
        pass1 = f(R.id.pass1);
        et1 = (EditText) pass1.findViewById(R.id.pass_ed);
        clear_img1 = (ImageView) pass1.findViewById(R.id.clear_img);
        clear_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear(et1);
            }
        });
        et1.setHint("原密码");
        pass2 = f(R.id.pass2);
        et2 = (EditText) pass2.findViewById(R.id.pass_ed);
        clear_img2 = (ImageView) pass2.findViewById(R.id.clear_img);
        clear_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear(et2);
            }
        });
        et2.setHint("新密码");
        pass3 = f(R.id.pass3);
        et3 = (EditText) pass3.findViewById(R.id.pass_ed);
        clear_img3 = (ImageView) pass3.findViewById(R.id.clear_img);
        et3.setHint("再次输入");
        clear_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear(et3);
            }
        });
        change_pass_ri = f(R.id.change_pass_ri);

        pass_layout = f(R.id.pass_layout);

        pass_layout.setOnClickListener(this);
        change_pass_ri.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void clear(EditText et) {
        et.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_pass_ri:
                if (pass_layout.getVisibility() == View.VISIBLE) {
                    String old = et1.getText().toString();
                    String newp = et2.getText().toString();
                    String again = et3.getText().toString();
                    if (old.equals(newp))
                        ToastUtil.toast2_bottom(ChangeActivity.this, "新密码不能跟原密码一样!!");
                    else if (!newp.equals(again))
                        ToastUtil.toast2_bottom(ChangeActivity.this, "二次输入新密码不一致!!");
                    else if (old.isEmpty())
                        ToastUtil.toast2_bottom(ChangeActivity.this, "原密码不能为空!!");
                    else if (newp.isEmpty())
                        ToastUtil.toast2_bottom(ChangeActivity.this, "新密码不能为空!!");
                    else if (again.isEmpty())
                        ToastUtil.toast2_bottom(ChangeActivity.this, "请再次输入新密码!!");
                    else if (newp.length() < 8)
                        ToastUtil.toast2_bottom(ChangeActivity.this, "密码应为8位的字母与数字组合");
                    else if (!isLetterOrDigit(newp))
                        ToastUtil.toast2_bottom(ChangeActivity.this, "密码应为8位的字母与数字组合");
                    else
                        ChangePassWord(old, newp);
                }
                break;
            case R.id.code_tv:
                if (!phone_ed.getText().toString().isEmpty() && phone_ed.getText().toString().length() == 11) {
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

                } else {
                    if (phone_ed.getText().toString().length() < 11)
                        ToastUtil.toast2_bottom(ChangeActivity.this, "请填写正确的手机号!");
                    else
                        ToastUtil.toast2_bottom(ChangeActivity.this, "手机号不能为空!");
                }

                break;

            case R.id.change_phone_ri:
                String code = code_ed.getText().toString();
                String phone = phone_ed.getText().toString();
                if (code.isEmpty())
                    ToastUtil.toast2_bottom(ChangeActivity.this, "验证码不能为空!");
                else if (phone.isEmpty())
                    ToastUtil.toast2_bottom(ChangeActivity.this, "手机号不能为空!");
                else if (phone.length() < 11)
                    ToastUtil.toast2_bottom(ChangeActivity.this, "请填写正确的手机号!");
                else
                    ChangePhoen(phone, code);
                break;

            case R.id.clear_img4:
                phone_ed.setText("");
                break;
        }
    }

    private void ChangePhoen(final String phone, String code) {

        /**
         * 修改手机获取;
         */
        OkHttpUtils
                .post()
                .url(MzFinal.URL + MzFinal.MODIFYPHONE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("phone", phone)
                .addParams("code", code)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ChangeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(ChangeActivity.this, "修改手机成功！！");
                                phone_ed.setText("");
                                code_ed.setText("");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(ChangeActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }


    private void ChangePassWord(String oldpass, String newpass) {
        /**
         * 修改密码;
         */
        OkHttpUtils
                .post()
                .url(MzFinal.URL + MzFinal.MODIFYPASSWORD)
                .addParams("password", oldpass)
                .addParams("newPassword", newpass)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ChangeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(ChangeActivity.this, "修改密码成功,请重新登录！");
                                SPreferences.saveUserToken("");
                                goLoginPage(ChangeActivity.this);
                                et1.setText("");
                                et2.setText("");
                                et3.setText("");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(ChangeActivity.this, response, code);
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
