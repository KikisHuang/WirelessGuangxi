package wlgx.com.kikis.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;
import wlgx.com.kikis.listener.CheckListener;
import wlgx.com.kikis.save.SPreferences;

import static wlgx.com.kikis.utils.JsonUtils.getCode;

/**
 * Created by lian on 2017/9/30.
 */
public class CheckUtils {

    public static boolean CheckShopName(String URL, String name, final CheckListener listener) {

        OkHttpUtils.get()
                .url(URL)
                .addParams("name", name)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onError();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                listener.onSucceed();
                            } else if (code == -6)
                                listener.onFailed();
                            else
                                listener.onElse(response, code);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return false;
    }
}
