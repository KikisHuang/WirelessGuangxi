package wlgx.com.kikis.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import wlgx.com.kikis.utils.ListenerManager;
import wlgx.com.kikis.utils.SynUtils;

import static cn.jpush.android.api.JPushInterface.isPushStopped;
import static cn.jpush.android.api.JPushInterface.resumePush;

/**
 * Created by lian on 2017/7/5.
 * 网络监听广播;
 */
public class MyNetworkReceiver extends BroadcastReceiver {
    private static final String TAG = "MyNetworkReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //网络状态判断;
            if (SynUtils.isNetworkConnected(context)) {
                //当前网络状态可用
//                JpushInit(context);
                if (isPushStopped(context)) {
                    resumePush(context);
                    Log.i(TAG, "JPush Stopped true");
                } else
                    Log.i(TAG, "JPush Stopped false");
                ListenerManager.getInstance().sendBroadCast(true);
                Log.i(TAG, "sendBroadCast  = true");
            } else {
                //当前网络不可用
                ListenerManager.getInstance().sendBroadCast(false);
                Log.i(TAG, "sendBroadCast  = false");
            }
        }
    }
}
