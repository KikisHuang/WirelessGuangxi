package wlgx.com.kikis.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.save.SPreferences;

import static wlgx.com.kikis.utils.IntentUtils.goLoginPage;

/**
 * Created by lian on 2017/6/7.
 * 杂项方法工具类;
 */
public class SynUtils {
    private static final String TAG = getTAG(SynUtils.class);
    public static Timer timer;
    private static String[] Sex = {"女", "男"};
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * String中取出int类型;
     *
     * @param str
     * @return
     */
    public static String StrGetInt(String str) {
        str = str.trim();
        String str2 = "";
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }
        }
        return str2;
    }


    public static Object getSex(Object object) {
        if (object instanceof Integer) {
            if (Sex.length > 0)
                return Sex[(int) object];
            else
                Sex = new String[]{"女", "男"};
            return Sex[(int) object];
        }
        if (object instanceof String) {
            if (Sex.length > 0) {
                for (int i = 0; i < Sex.length; i++) {
                    if (object.equals(Sex[i]))
                        return i;
                }
            } else {
                Sex = new String[]{"女", "男"};
                for (int i = 0; i < Sex.length; i++) {
                    if (object.equals(Sex[i]))
                        return i;
                }
            }
        }
        return null;
    }

    /**
     * 子页面标题设置;
     *
     * @param ac
     * @param str
     */
    public static void setTitles(final Activity ac, String str) {
        TextView textView = (TextView) ac.findViewById(R.id.title_tv);

        textView.setText(str);
        ac.findViewById(R.id.return_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Finish(ac);
            }
        });
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                Log.i(TAG, "qq pn = " + pn);
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                Log.i(TAG, "wechat pn = " + pn);
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static synchronized int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();//获取包管理器
        try {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);//获取包对象信息
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static synchronized String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            //第二个参数代表额外的信息，例如获取当前应用中的所有的Activity
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Wifi环境判断
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

//    /**
//     * 判断路径后缀;
//     */
//    public static boolean getSuffix(String name) {
//        String a = name.substring(name.lastIndexOf(".") + 1);
//        Log.i(TAG, "gif =======" + a);
//        if (a.equals("gif"))
//            return false;
//        else
//            return true;
//    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 网络连接判断
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取包名.类名 通用方法;
     *
     * @param Class 类
     * @return
     */
    public static String getTAG(Class Class) {
        return Class.getName();
    }

    /**
     * 过千简略显示方法;
     *
     * @param num
     * @return
     */
    public static String KswitchWay(double num) {
        DecimalFormat df = new DecimalFormat("######0.0");
        DecimalFormat df1 = new DecimalFormat("######0");

        String a = "";
        if (num > 1000) {
            double c = num / 1000;
            a = String.valueOf(df.format(c)) + "k";
        } else
            a = String.valueOf(df1.format(num));

        return a;
    }

    /**
     * 过千解析方法;
     *
     * @param str
     * @return
     */
    public static String ParseK(String str, boolean add) {
        if (add) {
            if (str.indexOf("k") != -1)
                return KswitchWay(Double.valueOf(Double.valueOf(str.replace("k", "")) * 1000 + 1));
            else
                return String.valueOf(Integer.valueOf(str.replace("k", "")) + 1);
        } else {
            if (str.indexOf("k") != -1)
                return KswitchWay(Double.valueOf(Double.valueOf(str.replace("k", "")) * 1000 - 1));
            else
                return String.valueOf(Integer.valueOf(str.replace("k", "")) - 1);
        }
    }

    /**
     * 获取通知栏权限是否开启
     */

    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 进入设置系统应用权限界面;
     * 方法1;
     *
     * @param context
     */
    public static void requestPermission(Context context) {
        // TODO Auto-generated method stub
        // 6.0以上系统才可以判断权限
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 进入设置系统应用权限界面;
     * 方法2;
     *
     * @param context
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 登录判断通用方法;
     */
    public static boolean LoginStatusQuery() {
        if (SPreferences.getUserToken() == null || SPreferences.getUserToken().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Popup登录通用方法;
     */
    public static void Login(Context context) {
        if (SPreferences.getUserToken() == null || SPreferences.getUserToken().isEmpty()) {
            goLoginPage(context);
        }
    }

    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     */
    public static int getAppSatus(Context context, String pageName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }


    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
            Log.i(TAG, "删除成功");
        } else {
            Log.i(TAG, "文件不存在");
        }
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    public static void Finish(Activity activity) {
        activity.finish();
    }


    public static void JPushAliasInit(final Context context) {
        if (LoginStatusQuery()) {
            /**
             * 获取自己id;
             */
            OkHttpUtils
                    .get()
                    .url(MzFinal.URL + MzFinal.GETMYID)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .tag(context)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(context, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                Log.i(TAG, "推送alias ===" + response);
                                JPushInterface.setAlias(context, response, new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Set<String> set) {
                                        Log.i(TAG, " i === " + i + "  s === " + s);
                                        if (i != 0) {
                                            //0成功  ,6002 设置超时,-994 网络连接超时,6020 tag/alias操作暂停,建议过一段时间再设置,6004 alias超长。最多 40个字节,6009 位置错误;
                                            if (i == 6002)
                                                ThreadStart(context);
                                            if (i == -994)
                                                ThreadStart(context);
                                            if (i == 6020)
                                                ThreadStart(context);
                                            if (i == 6004)
                                                ToastUtil.toast2_bottom(context, "无法接受推送，错误码6004");
                                            if (i == 6009)
                                                ToastUtil.toast2_bottom(context, "无法接受推送，错误码6009");
                                        } else
                                            MzFinal.ALIAS = false;
                                    }
                                });

                            } catch (Exception e) {
                                Log.i(TAG, "推送Error === " + e);
                                ToastUtil.toast2_bottom(context, "获取推送id异常...");
                            }
                        }
                    });
        }
    }

    private static void ThreadStart(Context context) {
        JPushAliasThread jt = JPushAliasThread.getPushInstance();
        if (jt == null) {
            jt = new JPushAliasThread(context);
            jt.start();
        }
    }
}