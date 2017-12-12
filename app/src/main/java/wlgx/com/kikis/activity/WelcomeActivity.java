//package wlgx.com.kikis.activity;
//
//import android.graphics.Bitmap;
//import android.os.Handler;
//import android.os.Message;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import okhttp3.Call;
//import wlgx.com.kikis.utils.AnimationUtil;
//import wlgx.com.kikis.utils.ToastUtil;
//
//import static wlgx.com.kikis.bean.ServerCode.getCodeStatusMsg;
//import static wlgx.com.kikis.utils.IntentUtils.goMainPage;
//import static wlgx.com.kikis.utils.JsonUtils.getCode;
//import static wlgx.com.kikis.utils.SynUtils.getVersionName;
//
//
///**
// * Created by lian on 2017/5/19.
// * 欢迎页面;
// */
//public class WelcomeActivity extends InitActivity {
//    private static final String TAG = "WelcomeActivity";
//    private ImageView login_img;
//    private TextView lead_tv;
//    private LinearLayout login_ll;
//    private ImageView welcome_img;
//    private ImageView welcome_img2;
//    private Handler handler;
//    private int time = 3;
//    private Timer tm;
//    private Bitmap bitmap = null;
//
//    private Runnable mrunnable;
//
//    private void Hand() {
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 2:
//                        lead_tv.setText("跳过");
//                        break;
//                    case 1:
//                        lead_tv.setText("跳过");
//                        break;
//                    case 0:
//                        lead_tv.setText("跳过");
//                        end();
//                        break;
//                    case 9:
//
//                        break;
//                    case 99:
//                        welcome_img2.setVisibility(View.GONE);
//                        bitmap = (Bitmap) msg.obj;    //在handler中接受从子线程发回来的数据
//                        welcome_img.setImageBitmap(bitmap);
//                        login_img.setEnabled(true);
//                        startAnima2();
//                        break;
//                    default:
//                        super.handleMessage(msg);
//                        break;
//                }
//            }
//        };
//    }
//
//    private void startAnima2() {
//        welcome_img.startAnimation(AnimationUtil.WelcomeAnimathree());
//
//        tm = new Timer();
//        tm.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (time == 0) {
//
//                } else {
//                    time--;
//                    handler.sendEmptyMessage(time);
//                }
//            }
//        }, 1500, 1000);
//
//    }
//
//    @Override
//    protected void click() {
//        login_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tm != null) {
//                    tm.cancel();
//                    tm = null;
//                }
//                end();
//            }
//        });
//    }
//
//    private void end() {
//        goMainPage(this);
//    }
//
//    private void getData() {
//
//        OkHttpUtils
//                .post()
////                .url(MzFinal.URl + MzFinal.GETWELCOMEIMGURL)
////                .addParams("IMEI", getIMEI(this))
//                .addParams("Phone", android.os.Build.BRAND)
//                .addParams("Molde", android.os.Build.MODEL)
//                .addParams("Android_Version", android.os.Build.VERSION.RELEASE)
//                .addParams("VersionName", getVersionName(this))
//                .tag(this)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        ToastUtil.toast2_bottom(WelcomeActivity.this, "网络不顺畅...");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        try {
//                            int code = getCode(response);
//                            if (code == 1) {
//                                Glide.with(getApplicationContext()).load(new JSONObject(response).optString("data")).asBitmap().into(new SimpleTarget<Bitmap>() {
//                                    @Override
//                                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                        handler.post(mrunnable = new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Message message = new Message();
//                                                message.what = 99;
//                                                message.obj = resource;
//                                                handler.sendMessage(message);
//                                            }
//                                        });
//                                    }
//                                });
//                            } else {
//                                ToastUtil.toast2_bottom(WelcomeActivity.this, getCodeStatusMsg(code));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//
//    @Override
//    protected void init() {
////        setContentView(R.layout.welcome_layout);
////
////        login_img = f(R.id.login_img);
////        welcome_img = f(R.id.welcome_img);
////        welcome_img2 = f(R.id.welcome_img2);
////        login_ll = f(R.id.login_ll);
////        lead_tv = f(R.id.lead_tv);
////        login_img.setEnabled(false);
//    }
//
//    @Override
//    protected void initData() {
//        Hand();
//        getData();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        OkHttpUtils.getInstance().cancelTag(this);
//
//        if (bitmap != null && !bitmap.isRecycled()) {
//            welcome_img.setImageBitmap(null);
//            handler.removeCallbacks(mrunnable);
//            handler.removeCallbacksAndMessages(null);
//            handler = null;
//            bitmap.recycle();
//            bitmap = null;
//            if (tm != null) {
//                tm.cancel();
//                tm = null;
//            }
//        }
//        System.gc();
////        MyAppcation.getRefWatcher(this).watch(this);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return false;
//    }
//
//
//}
