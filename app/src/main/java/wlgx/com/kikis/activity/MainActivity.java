package wlgx.com.kikis.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import wlgx.com.kikis.R;
import wlgx.com.kikis.fragment.HomeFragment;
import wlgx.com.kikis.fragment.MyFragment;
import wlgx.com.kikis.listener.SwitchListener;
import wlgx.com.kikis.receiver.MyNetworkReceiver;
import wlgx.com.kikis.server.MyJobService;
import wlgx.com.kikis.utils.SynUtils;
import wlgx.com.kikis.view.AlertDialog;

import static wlgx.com.kikis.utils.AnimationUtil.ShakeAnima;
import static wlgx.com.kikis.utils.IntentUtils.goOrderPage;
import static wlgx.com.kikis.utils.SynUtils.JPushAliasInit;
import static wlgx.com.kikis.utils.SynUtils.isNotificationEnabled;

public class MainActivity extends BaseActivity implements View.OnClickListener, SwitchListener {
    private static final String TAG = "MainActivity";
    private LinearLayout home_ll, my_ll;
    private FragmentManager fm = getSupportFragmentManager();
    private HomeFragment homeFragment = null;
    private MyFragment myFragment = null;
    private FragmentTransaction ft;
    private ImageView my_img, home_img;
    private TextView my_tv, home_tv;
    private FrameLayout main_ll;
    public static SwitchListener switchlistener;
    private MyNetworkReceiver receiver;

    /**
     * JPush;
     */
    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void click() {
        home_ll.setOnClickListener(this);
        my_ll.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_main);

        JPushInterface.init(getApplicationContext());
        JPushAliasInit(getApplicationContext());
        switchlistener = this;
        main_ll = f(R.id.main_ll);
        home_ll = f(R.id.home_ll);
        my_ll = f(R.id.my_ll);

        my_tv = f(R.id.my_tv);
        my_img = f(R.id.my_img);

        home_img = f(R.id.home_img);
        home_tv = f(R.id.home_tv);
        addAction();

        CheckePermission();
        initFragment();
        checkNotify();
        JobServerInit();
        if (getIntent().getStringExtra("order_flag") != null && getIntent().getStringExtra("order_flag").equals("10010"))
            goOrderPage(MainActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 检查通知栏权限;
     */
    private void checkNotify() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isNotificationEnabled(getApplicationContext())) {
                Log.i(TAG, "通知栏消息推送权限已获得..");
            } else {
                new AlertDialog(this).builder().setCancelable(true).setTitle("提示").setMsg("您未开启通知，一些最新动态将无法及时通知您，是否去开启？").setNegativeButton("取消", null).setPositiveButton("去开启", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SynUtils.getAppDetailSettingIntent(MainActivity.this);
                    }
                }).show();
            }
        }
    }

    /**
     * 动态注册网络监听广播;
     */
    private void addAction() {
        IntentFilter intentFilter = new IntentFilter();
        //addAction
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        receiver = new MyNetworkReceiver();
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 5.0以上进程拉起服务器初始化;
     */
    private void JobServerInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobService.class.getName()))
                    .setPeriodic(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            jobScheduler.schedule(jobInfo);
        }
    }

    private void CheckePermission() {
        /**
         * 申请6.0权限
         * 最新版本检查;
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);//自定义的code
        }
    }

    @Override
    protected void initData() {
    }

    private void initFragment() {
        ft = fm.beginTransaction();
        setSelected(home_ll);
        Home();
    }

    private void Home() {
        ObjectAnimator anima = ShakeAnima(home_img);
        anima.start();
        // 提交事务
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            ft.add(R.id.main_ll, homeFragment).show(homeFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(homeFragment);
            Log.i(TAG, "show");
        }
        ft.commit();
    }

    private void My() {
        ObjectAnimator anima = ShakeAnima(my_img);
        anima.start();
        if (myFragment == null) {
            myFragment = new MyFragment();
            ft.add(R.id.main_ll, myFragment).show(myFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(myFragment);
            Log.i(TAG, "show");
        }
        ft.commit();
    }

    private void setSelected(LinearLayout ll) {
        if (ll.equals(my_ll)) {
            my_tv.setTextColor(getResources().getColor(R.color.red3));
            home_tv.setTextColor(getResources().getColor(R.color.gray4));
            my_img.setImageResource(R.mipmap.my_img);
            home_img.setImageResource(R.mipmap.home_un_img);
        } else {
            my_tv.setTextColor(getResources().getColor(R.color.gray4));
            my_img.setImageResource(R.mipmap.my_un_img);
            home_tv.setTextColor(getResources().getColor(R.color.red3));
            home_img.setImageResource(R.mipmap.home_img);
        }

        if (homeFragment != null) {
            // 隐藏fragment
            ft.hide(homeFragment);
        }
        if (myFragment != null) {
            ft.hide(myFragment);
        }
    }

    @Override
    public void onClick(View v) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.home_ll:
                setSelected(home_ll);
                Home();
                break;
            case R.id.my_ll:
                setSelected(my_ll);
                My();
                break;
        }
    }

    @Override
    public void onSwitch(int s) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (s) {
            case 0:
                setSelected(home_ll);
                Home();
                break;
            case 1:
                setSelected(my_ll);
                My();
                break;
        }
    }

}
