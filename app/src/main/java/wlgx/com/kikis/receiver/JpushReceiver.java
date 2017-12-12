package wlgx.com.kikis.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import wlgx.com.kikis.R;
import wlgx.com.kikis.activity.MainActivity;
import wlgx.com.kikis.activity.MyOrderActivity;
import wlgx.com.kikis.fragment.HomeFragment;
import wlgx.com.kikis.fragment.son.AllOrderFragment;
import wlgx.com.kikis.fragment.son.OverOrderFragment;
import wlgx.com.kikis.fragment.son.OverRefundOrderFragment;
import wlgx.com.kikis.fragment.son.RefundOrderFragment;
import wlgx.com.kikis.fragment.son.WaitOrderFragment;
import wlgx.com.kikis.utils.ExampleUtil;
import wlgx.com.kikis.utils.Logger;
import wlgx.com.kikis.utils.SynUtils;

/**
 * Created by lian on 2017/7/5.
 */
public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JpushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        try {
//        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        } catch (Exception e) {

        }
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (HomeFragment.listener != null)
                HomeFragment.listener.onUpdata();

            Log.i(TAG, "接受到的数据 === " + extras);
            try {

                JSONObject ob = new JSONObject(extras);
                String id = ob.optString("id");
                int type = ob.optInt("type");

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            receivingNotification(context, bundle);

            if (AllOrderFragment.updata != null)
                AllOrderFragment.updata.onUpData();
            if (WaitOrderFragment.updata != null)
                WaitOrderFragment.updata.onUpData();
            if (OverOrderFragment.updata != null)
                OverOrderFragment.updata.onUpData();
            if (RefundOrderFragment.updata != null)
                RefundOrderFragment.updata.onUpData();
            if (OverRefundOrderFragment.updata != null)
                OverRefundOrderFragment.updata.onUpData();

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
//            ToastUtil.toast2_bottom(context, "用户点击打开了通知");
//            Intent in = new Intent(context, MainActivity.class);
//            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(in);

            try {
                Intent main = new Intent();
                //判断app进程是否存活
                switch (SynUtils.getAppSatus(context, "example.com.fan")) {
                    case 1:
                        Log.i(TAG, "应用在前台运行,直接启动页面");
                        main.setClass(context, MyOrderActivity.class);
                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(main);
                        break;
                    case 2:
                        //打开自定义的Activity
                        Log.i(TAG, "应用在前后台运行,启动页面");
                        main.setClass(context, MyOrderActivity.class);
                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(main);

                        break;
                    case 3:
                        Log.i(TAG, "应用未运行,启动页面");
                        //打开自定义的Activity
                        main.setClass(context, MainActivity.class);
//                    main.putExtras(bundle);
                        main.putExtra("order_flag", 10010 + "");
                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(main);

                        break;
                }
            } catch (Exception e) {
                Log.i(TAG, "ERROR ===" + e);
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }


    //自定义通知栏;
    private void receivingNotification(Context context, Bundle bundle) {
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        // 使用notification
        // 使用广播或者通知进行内容的显示
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        try {
            JSONObject ob = new JSONObject(extras);
            String id = ob.optString("id");
            int type = ob.optInt("type");
            String titile = ob.optString("title");
            String content = ob.optString("content");

            builder.setContentText(content).setSmallIcon(R.mipmap.wxgx_icon).setContentTitle(titile);
            builder.setDefaults(Notification.DEFAULT_SOUND);
            manager.notify(1, builder.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }
}
