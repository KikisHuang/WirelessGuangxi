package wlgx.com.kikis.save;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by on 2017/5/13
 * Sp存储类;
 */
public class SPreferences {

    public static Context context;
    /**
     * 用户登录token;
     */
    private static final String KEY_USER_TOKEN = "Guangxi_token";
    /**

    /**
     * 保存登录Token;
     */
    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    /**
     * 获取登录Token;
     */
    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }


    /**
     * 读取String方法;
     *
     * @param key
     * @return String
     */
    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    /**
     * 读取boolean方法;
     *
     * @param key
     * @return boolean
     */
    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, true);
    }

    /**
     * 保存boolean方法;
     *
     * @param key
     * @param value
     */
    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存String方法;
     *
     * @param key
     * @param value
     */
    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 通用SharedPreferences方法;
     */
    static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("WlGx", Context.MODE_PRIVATE);
    }

    /**
     * 获取全局Context方法;
     *
     * @param context
     */
    public static void setContext(Context context) {
        SPreferences.context = context.getApplicationContext();
    }
}
