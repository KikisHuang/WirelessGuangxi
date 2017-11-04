package wlgx.com.kikis.utils;

import android.content.Context;
import android.util.Log;

import static wlgx.com.kikis.utils.SynUtils.JPushAliasInit;

/**
 * Created by lian on 2017/10/27.
 */
public class JPushAliasThread extends Thread {
    private static final String TAG = "JPushAliasThread";
    private Context context;
    private static JPushAliasThread instance;

    public JPushAliasThread(Context context) {
        this.context = context;
        instance = this;
    }

    @Override
    public void run() {
        try {
            while (MzFinal.ALIAS) {
                Log.i(TAG, "JPushAliasThread run");
                Thread.sleep(5000);
                JPushAliasInit(context);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized JPushAliasThread getPushInstance() {
        return instance;
    }
}
