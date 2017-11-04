package wlgx.com.kikis.activity;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.fragment.HomeFragment;
import wlgx.com.kikis.fragment.MyFragment;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.AlertDialog;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.IntentUtils.goApplicationInPage;
import static wlgx.com.kikis.utils.IntentUtils.goForgetPassPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.SynUtils.JPushAliasInit;
import static wlgx.com.kikis.utils.TextViewColorUtils.setTextColor3;
import static wlgx.com.kikis.view.AtyContainer.finishAllActivity;

/**
 * Created by lian on 2017/9/11.
 */
public class LoginActivity extends InitActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = "LoginActivity";
    private EditText photo_ed, pass_ed;
    private RippleView submit_info, enter_button;
    private TextView forget_pass_tv;

    @Override
    protected void click() {
        submit_info.setOnClickListener(this);
        enter_button.setOnClickListener(this);
        forget_pass_tv.setOnClickListener(this);

        photo_ed.setOnEditorActionListener(this);
        pass_ed.setOnEditorActionListener(this);

    }

    @Override
    protected void init() {
        setContentView(R.layout.login_activity);
//        setTitles(this, "登录");
        photo_ed = f(R.id.photo_ed);
        pass_ed = f(R.id.pass_ed);
        enter_button = f(R.id.enter_button);
        submit_info = f(R.id.submit_info);
        forget_pass_tv = f(R.id.forget_pass_tv);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        photo_ed.addTextChangedListener(new EditChangedListener(clear_img));
        editInit();
        //找回密码
        setTextColor3(forget_pass_tv, "找回密码", "忘记密码您可以尝试", "#c62f34");
    }

    private void editInit() {
        photo_ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });
        pass_ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL)
            return true;
        else
            finishAllActivity();
        return true;
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit_info:
                if (!photo_ed.getText().toString().isEmpty() && !pass_ed.getText().toString().isEmpty())
                    Login(String.valueOf(photo_ed.getText().toString()), pass_ed.getText().toString());
                else {
                    if (!photo_ed.getText().toString().isEmpty())
                        ToastUtil.toast2_bottom(LoginActivity.this, "请输入账号或手机号!!!");
                    if (!pass_ed.getText().toString().isEmpty())
                        ToastUtil.toast2_bottom(LoginActivity.this, "请输入密码!!!");
                }

                break;
            case R.id.enter_button:
                goApplicationInPage(this);
                break;
            case R.id.forget_pass_tv:
                goForgetPassPage(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 721 && resultCode == 101) {
            if (MyFragment.listener != null)
                MyFragment.listener.onUpdata();
            if (HomeFragment.listener != null)
                HomeFragment.listener.onUpdata();
//            goMainPage(this);

            finish();
        }
    }

    private void Login(String account, String pass) {

        OkHttpUtils
                .post()
                .url(MzFinal.URL + MzFinal.LOGIN)
                .addParams("account", account)
                .addParams("password", pass)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(LoginActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                SPreferences.saveUserToken(new JSONObject(response).optString("data"));
                                Log.i(TAG, "token Log======" + new JSONObject(response).optString("data"));
                                ToastUtil.toast2_bottom(LoginActivity.this, "登录成功！");
                                if (MyFragment.listener != null)
                                    MyFragment.listener.onUpdata();
                                if (HomeFragment.listener != null)
                                    HomeFragment.listener.onUpdata();
                                JPushAliasInit(getApplicationContext());
                                finish();

                            } else
                                new AlertDialog(LoginActivity.this).builder().setTitle("提示").setCancelable(true).setMsg(new JSONObject(response).optString("erroMsg")).setNegativeButton("确定", null).show();

                        } catch (Exception e) {

                        }
                    }
                });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (actionId) {

            case EditorInfo.IME_ACTION_NONE:
                break;
            case EditorInfo.IME_ACTION_GO:
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                break;
            case EditorInfo.IME_ACTION_SEND:
                break;
            case EditorInfo.IME_ACTION_NEXT:
                break;
            case EditorInfo.IME_ACTION_DONE:
                hideSoftInput(v.getWindowToken());
                break;
            default:
                break;
        }
        return false;
    }
}
