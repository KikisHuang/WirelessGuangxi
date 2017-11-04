package wlgx.com.kikis.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.listener.MyFragmetnListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.JsonUtils.getCode;


/**
 * Created by lian on 2017/6/5.
 * 客服页面；
 */
public class AdminInfoPopupWindow implements View.OnClickListener {
    private static final String TAG = "AdminInfoPopupWindow";

    private int tag = -1;

    private LinearLayout name_layout, status_layout, pass_layout, phone_layout;
    private TextView name, status, pass, title_tv, phone;
    private EditText name_ed, status_ed, pass_ed, phone_ed;
    private RippleView submit_info;
    private String id = "";
    private ImageView close_img;
    //status=状态(0未启用,1已启用);
    private int state = -1;
    private Context mContext;
    private String nickName = "";
    private MyFragmetnListener mylistener;
    private PopupWindow popupWindow;

    public AdminInfoPopupWindow(Context mContext, int tag, String nickName, int status, String id, MyFragmetnListener mylistener) {
        this.mContext = mContext;
        this.tag = tag;
        this.nickName = nickName;
        this.state = status;
        this.id = id;
        this.mylistener = mylistener;
    }

    public AdminInfoPopupWindow(Context mContext, int tag, MyFragmetnListener mylistener) {
        this.tag = tag;
        this.mylistener = mylistener;
        this.mContext = mContext;
    }

    public void ScreenPopupWindow() {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.admin_popu_layout, null);

            popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.AnimationPreview);

            init(contentView);
            click();
            backgroundAlpha(0.7f, mContext);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f, mContext);
                    popupWindow.dismiss();
                    popupWindow = null;
                    mContext = null;

                    close_img = null;

                    name_layout = null;
                    status_layout = null;
                    pass_layout = null;
                    phone_layout = null;
                    name = null;
                    status = null;
                    pass = null;
                    title_tv = null;
                    phone = null;
                    name_ed = null;
                    status_ed = null;
                    pass_ed = null;
                    phone_ed = null;
                    submit_info = null;
                    mylistener = null;
                }
            });
            popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.admin_layout, null), Gravity.CENTER, 0, 0);
        }
    }

    private void click() {
        submit_info.setOnClickListener(this);
        close_img.setOnClickListener(this);
        status_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tag == 1) {
                    if (status_ed.getText().toString().equals("已启用")) {
                        status_ed.setText("未启用");
                        status_ed.setTextColor(mContext.getResources().getColor(R.color.red2));
                        state = 0;
                    } else {
                        status_ed.setText("已启用");
                        status_ed.setTextColor(mContext.getResources().getColor(R.color.green5));
                        state = 1;
                    }
                }
            }
        });
    }

    private void init(View contentView) {
        close_img = (ImageView) contentView.findViewById(R.id.close_img);
        submit_info = (RippleView) contentView.findViewById(R.id.submit_info);
        title_tv = (TextView) contentView.findViewById(R.id.title_tv);
        name_layout = (LinearLayout) contentView.findViewById(R.id.name_layout);
        status_layout = (LinearLayout) contentView.findViewById(R.id.status_layout);
        pass_layout = (LinearLayout) contentView.findViewById(R.id.pass_layout);
        phone_layout = (LinearLayout) contentView.findViewById(R.id.phone_layout);

        name = (TextView) name_layout.findViewById(R.id.name_tv);
        status = (TextView) status_layout.findViewById(R.id.name_tv);
        pass = (TextView) pass_layout.findViewById(R.id.name_tv);
        phone = (TextView) phone_layout.findViewById(R.id.name_tv);

        name_ed = (EditText) name_layout.findViewById(R.id.info_tv);
        status_ed = (EditText) status_layout.findViewById(R.id.info_tv);
        pass_ed = (EditText) pass_layout.findViewById(R.id.info_tv);
        phone_ed = (EditText) phone_layout.findViewById(R.id.info_tv);
        pass_ed.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

        if (tag == 0) {
            title_tv.setText("新增员工信息");

            name.setText("员工昵称：");
            status.setText("员工账号：");
            phone.setText("员工手机号：");
            pass.setText("员工密码：");

            pass_ed.setHint("请填写员工密码");
            status_ed.setHint("填写员工账号");
            phone_ed.setHint("填写员工手机号");
            name_ed.setHint("请填写员工昵称");

        } else if (tag == 1) {
            title_tv.setText("修改员工信息");
            phone_layout.setVisibility(View.GONE);

            name.setText("昵称：");
            status.setText("状态：");
            pass.setText("重设密码：");

            status_ed.setFocusable(false);
            pass_ed.setHint("无需重设此项可不填。");
            name_ed.setText(nickName);

            if (state == 1) {
                status_ed.setText("已启用");
                status_ed.setTextColor(mContext.getResources().getColor(R.color.green5));
            } else {
                status_ed.setText("未启用");
                status_ed.setTextColor(mContext.getResources().getColor(R.color.red2));
            }


        } else
            popupWindow.dismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * @param mContext
     */
    public void backgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_info:
                if (tag == 1)
                    ChangeInfo();
                if (tag == 0)
                    AddInfo();
                break;
            case R.id.close_img:
                popupWindow.dismiss();
                break;
        }
    }

    boolean flag = false;

    private void AddInfo() {
        /**
         * 新建员工信息;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.ADDENTITY)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("account", status_ed.getText().toString())
                .addParams("phone", phone_ed.getText().toString())
                .addParams("nickname", name_ed.getText().toString())
                .addParams("password", pass_ed.getText().toString())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(mContext, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(mContext, "成功添加员工信息..");
                                mylistener.onUpdata();
                                popupWindow.dismiss();

                            } else
                                ToastUtil.ToastErrorMsg(mContext, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void ChangeInfo() {

        if (pass_ed.getText().toString().isEmpty())
            flag = true;
        else {
            /**
             * 重设密码;
             */
            OkHttpUtils
                    .get()
                    .url(MzFinal.URL + MzFinal.MODIFYSTAFFENPASSWORD)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .addParams("password", pass_ed.getText().toString())
                    .addParams("id", id)
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(mContext, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    if (flag) {
                                        ToastUtil.toast2_bottom(mContext, "成功修改员工信息..");
                                        mylistener.onUpdata();
                                        popupWindow.dismiss();

                                    } else
                                        flag = true;

                                } else
                                    ToastUtil.ToastErrorMsg(mContext, response, code);
                            } catch (Exception e) {

                            }
                        }
                    });
        }
        /**
         * 查询店员资料;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.MODIFYSTAFFENTITY)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("status", String.valueOf(state))
                .addParams("nickname", name_ed.getText().toString())
                .addParams("id", id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(mContext, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (flag) {
                                    ToastUtil.toast2_bottom(mContext, "成功修改员工信息..");
                                    mylistener.onUpdata();
                                    popupWindow.dismiss();

                                } else
                                    flag = true;
                            } else
                                ToastUtil.ToastErrorMsg(mContext, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
