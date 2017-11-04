package wlgx.com.kikis.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import wlgx.com.kikis.bean.VersionBean;
import wlgx.com.kikis.listener.VersionCheckListener;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;


/**
 * Created by lian on 2017/7/7.
 */
public class getVersionUtils {
    private static final String TAG = "getVersionUtils";
    /**
     * 获取版本号、客服信息接口通用方法;
     *
     * @param context  上下文;
     * @param listener 回调监听;
     */
    public static void getVersionInfo(final Context context, final VersionCheckListener listener) {

        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETSETTING)
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
                                JSONObject ob = getJsonOb(response);

                                VersionBean vb = new Gson().fromJson("" + ob, VersionBean.class);
                                Log.i(TAG, "最新版本号为" + vb.getAndroidVersion());
                                listener.onVersion(vb);
                            } else {
                                listener.onFail();
                                ToastUtil.ToastErrorMsg(context, response, code);
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
