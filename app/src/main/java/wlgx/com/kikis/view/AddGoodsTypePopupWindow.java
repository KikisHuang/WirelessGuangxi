package wlgx.com.kikis.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.listener.UpDataListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.JsonUtils.getCode;


/**
 * Created by lian on 2017/6/5.
 * 客服页面；
 */
public class AddGoodsTypePopupWindow implements View.OnClickListener {
    private static final String TAG = "AdminInfoPopupWindow";


    private EditText add_ed;
    private RippleView add_bt;
    private PopupWindow popupWindow;
    private Context mContext;
    private UpDataListener mylistener;

    public AddGoodsTypePopupWindow(Context mContext, UpDataListener mylistener) {
        this.mContext = mContext;
        this.mylistener = mylistener;
    }

    public void ScreenPopupWindow() {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.addgoods_type_popu_layout, null);

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

                    add_ed = null;
                    add_bt = null;
                }
            });
            popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.admin_layout, null), Gravity.CENTER, 0, 0);
        }
    }

    private void click() {
        add_bt.setOnClickListener(this);
    }

    private void init(View contentView) {
        add_bt = (RippleView) contentView.findViewById(R.id.add_bt);
        add_ed = (EditText) contentView.findViewById(R.id.input_ed);
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
            case R.id.add_bt:
                if (add_bt.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(mContext, "类型名称不能为空！！");
                else
                    AddGoodsType();
                break;
        }
    }

    private void AddGoodsType() {
        /**
         * 添加商品分类;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.ADDGOODSENTITY)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("title", add_ed.getText().toString())
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
                                ToastUtil.toast2_bottom(mContext, "添加成功！！");
                                mylistener.onUpData();
                                popupWindow.dismiss();
                            } else
                                ToastUtil.ToastErrorMsg(mContext, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
