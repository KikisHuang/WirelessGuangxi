package wlgx.com.kikis.utils;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static wlgx.com.kikis.utils.JsonUtils.getCode;

/**
 * Created by lian on 2017/9/18.
 */
public class PhoneCodeUtils {

    public static void getPhoneCode(final Context context, String phone,String params) {
        /**
         * 验证码获取;
         */
        OkHttpUtils
                .post()
                .url(MzFinal.URL + MzFinal.CODE)
                .addParams(params, phone)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(context, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(context, "已发送验证码!");
                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
