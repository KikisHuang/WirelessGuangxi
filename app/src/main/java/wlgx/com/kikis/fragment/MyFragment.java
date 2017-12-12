package wlgx.com.kikis.fragment;

import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.activity.MainActivity;
import wlgx.com.kikis.bean.BankBean;
import wlgx.com.kikis.bean.VersionBean;
import wlgx.com.kikis.listener.MyFragmetnListener;
import wlgx.com.kikis.listener.OverallRefreshListener;
import wlgx.com.kikis.listener.VersionCheckListener;
import wlgx.com.kikis.listener.onPhotoCutListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.DownLoadUtils;
import wlgx.com.kikis.utils.ListenerManager;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.StringUtil;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.ActionSheetDialog;
import wlgx.com.kikis.view.AlertDialog;

import static wlgx.com.kikis.utils.IntentUtils.goLoginPage;
import static wlgx.com.kikis.utils.IntentUtils.goMyPage;
import static wlgx.com.kikis.utils.IntentUtils.goPreviewPage;
import static wlgx.com.kikis.utils.IntentUtils.goUploadPhotoPage;
import static wlgx.com.kikis.utils.IntentUtils.goWithdrawPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.JsonUtils.getJsonSring;
import static wlgx.com.kikis.utils.SynUtils.Login;
import static wlgx.com.kikis.utils.SynUtils.LoginStatusQuery;
import static wlgx.com.kikis.utils.SynUtils.getVersionCode;
import static wlgx.com.kikis.utils.SynUtils.isWifi;
import static wlgx.com.kikis.utils.getVersionUtils.getVersionInfo;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;
import static wlgx.com.kikis.view.PhotoProgress.LoadingCancle;
import static wlgx.com.kikis.view.PhotoProgress.LoadingShow;


/**
 * Created by lian on 2017/4/22.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, MyFragmetnListener, VersionCheckListener, OverallRefreshListener, onPhotoCutListener {
    private static final String TAG = "MyFragment";
    private RelativeLayout preview_ll, statis_ll, agreement_ll, return_login, me_rl, upload_rl, change_bank_ll;
    private LinearLayout login_ll;
    public static MyFragmetnListener listener;
    private TextView shop_status_tv, shop_name;
    private int ShopStatus = -1;
    private ImageView on_off_img;
    private CircleImageView shop_icon;
    public static onPhotoCutListener photolistener;

    @Override
    protected int initContentView() {
        return R.layout.my_fragment;
    }

    @Override
    protected void click() {
        preview_ll.setOnClickListener(this);
        statis_ll.setOnClickListener(this);
        agreement_ll.setOnClickListener(this);
        me_rl.setOnClickListener(this);
        return_login.setOnClickListener(this);
        upload_rl.setOnClickListener(this);
        change_bank_ll.setOnClickListener(this);
        on_off_img.setOnClickListener(this);
        shop_icon.setOnClickListener(this);
    }

    @Override
    protected void init() {
        ListenerManager.getInstance().registerListtener(this);
        listener = this;
        photolistener = this;
        login_ll = f(R.id.login_ll);
        preview_ll = f(R.id.preview_ll);
        statis_ll = f(R.id.statis_ll);
        upload_rl = f(R.id.upload_rl);
        change_bank_ll = f(R.id.change_bank_ll);
        shop_status_tv = f(R.id.shop_status_tv);
        on_off_img = f(R.id.on_off_img);

        shop_icon = f(R.id.shop_icon);
        shop_name = f(R.id.shop_name);

        agreement_ll = f(R.id.agreement_ll);
        me_rl = f(R.id.me_rl);
        return_login = f(R.id.return_login);

        CheckLoginState();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            CheckLoginState();
            if (SPreferences.getUserToken() != null && !SPreferences.getUserToken().isEmpty())
                getData(MzFinal.GETDETAILS, false);
        }

    }

    @Override
    protected void initData() {
        if (SPreferences.getUserToken() != null && !SPreferences.getUserToken().isEmpty())
            getData(MzFinal.GETDETAILS, false);
    }

    private void CheckLoginState() {
        if (SPreferences.getUserToken() == null || SPreferences.getUserToken().isEmpty()) {
            goLoginPage(getActivity());
        } else
            login_ll.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
        photolistener = null;
        ListenerManager.getInstance().unRegisterListener(this);
    }

    @Override
    public void onClick(View v) {
        if (LoginStatusQuery()) {
            switch (v.getId()) {
                //开业、停业按钮;
                case R.id.on_off_img:
                    Show(getActivity(), "提交中..", true, null);
                    if (ShopStatus == 1)
                        getData(MzFinal.SHOPCLOSE, true);
                    if (ShopStatus == 0)
                        getData(MzFinal.SHOPOPEN, true);
                    break;
                //商户协议;
                case R.id.agreement_ll:

                    break;
                //店铺logo;
                case R.id.shop_icon:
                    if (SPreferences.getUserToken() != null && !SPreferences.getUserToken().isEmpty()) {
                        new ActionSheetDialog(getActivity()).builder().
                                addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        goUploadPhotoPage(getActivity(), "0", "1");
                                    }
                                }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goUploadPhotoPage(getActivity(), "1", "1");
                            }
                        }).show();
                    }
                    break;
                //银行账户;
                case R.id.change_bank_ll:
                    goMyPage(getActivity());
                    break;
                //检查更新;
                case R.id.upload_rl:
                    if (SPreferences.getUserToken() != null && !SPreferences.getUserToken().isEmpty())
                        getVersionInfo(getActivity(), this);

                    break;
                //预览店铺;
                case R.id.preview_ll:
                    if (MzFinal.SHOPURL.isEmpty())
                        getURL();
                    else
                        goPreviewPage(getActivity());
                    break;
                //银行账户;
                case R.id.statis_ll:
                    goWithdrawPage(getActivity());
                    break;
                //关于我们;
                case R.id.me_rl:

                    break;
                //退出登录;
                case R.id.return_login:
                    new AlertDialog(getActivity()).builder().setTitle("提示").setCancelable(true).setMsg("是否退出登录？").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SPreferences.saveUserToken("");
                            AliasClear();
//                            ToastUtil.toast2_bottom(getActivity(),"已退出登录。");
                            goLoginPage(getActivity());
                            if (MainActivity.switchlistener != null)
                                MainActivity.switchlistener.onSwitch(0);
                        }
                    }).show();
                    break;
            }
        } else
            Login(getActivity());
    }

    private void AliasClear() {
        JPushInterface.setAlias(getActivity(), "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i(TAG, " 退出登录Alias状态码 === " + i + "  s === " + s);
                MzFinal.ALIAS = false;
            }
        });
    }

    private void getURL() {
        /**
         * 获取店铺路径;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                BankBean bb = new Gson().fromJson(String.valueOf(ob), BankBean.class);
                                MzFinal.SHOPID = bb.getId();
                                if (bb.getDecorationFlag() == 0)
                                    MzFinal.SHOPURL = MzFinal.SHOPURL1 + MzFinal.SHOPID;
                                else
                                    MzFinal.SHOPURL = MzFinal.SHOPURL2 + MzFinal.SHOPID;
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getData(final String getdetails, final boolean flag) {
        OkHttpUtils
                .get()
                .url(MzFinal.URL + getdetails)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                    }

                    //已1开业 0未开业
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (flag) {
                                    if (ShopStatus == 0)
                                        ToastUtil.toast2_bottom(getActivity(), "店铺已申请开业,请等待审核通过!");
                                    if (ShopStatus == 1)
                                        ToastUtil.toast2_bottom(getActivity(), "店铺已停业！");
                                }
                                JSONObject ob = getJsonOb(response);
                                BankBean bb = new Gson().fromJson(String.valueOf(ob), BankBean.class);
                                ShopStatus = bb.getStatus();
                                shop_name.setText(bb.getName());
                                String imurl = bb.getLogoUrl();

                                if (StringUtil.cleanNull(imurl))
                                    Glide.with(getActivity()).load(R.mipmap.test_icon).into(shop_icon);
                                else
                                    Glide.with(getActivity()).load(bb.getLogoUrl()).into(shop_icon);

                                String text = "";
                                //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                                ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.red3));
                                if (ShopStatus == 1) {
                                    text = getResources().getString(R.string.open_shop);
                                    SpannableStringBuilder builder = new SpannableStringBuilder(text);
                                    builder.setSpan(redSpan, 4, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    Glide.with(getActivity()).load(R.mipmap.switch_on).into(on_off_img);
                                    shop_status_tv.setText(builder);
                                } else if (ShopStatus == 0) {
                                    text = getResources().getString(R.string.closen_shop);
                                    SpannableStringBuilder builder = new SpannableStringBuilder(text);
                                    builder.setSpan(redSpan, 4, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    Glide.with(getActivity()).load(R.mipmap.switch_off).into(on_off_img);
                                    shop_status_tv.setText(builder);
                                }

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                        Cancle();
                    }
                });
    }

    @Override
    public void onUpdata() {
        CheckLoginState();
    }

    @Override
    public void onHide(int f) {
        switch (f) {
            case 0:
                CheckLoginState();
                break;
        }
    }

    @Override
    public void onVersion(VersionBean vb) {
        int old = getVersionCode(getActivity());

        if (vb.getAndroidVersion() > old) {
            LoadingShow(getActivity(), false, getResources().getString(R.string.VersonUp));
            /**
             * 获取apk数据;
             */

            OkHttpUtils
                    .get()
                    .url(MzFinal.URL + MzFinal.DOWNLOADPATH)
                    .addParams("version", String.valueOf(getVersionCode(getActivity())))
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                int code = getCode(response);
                                if (code == 1) {
                                    final String url = getJsonSring(response);
                                    if (!url.isEmpty()) {
                                        /**
                                         * 判断是否Wifi环境;
                                         */
                                        if (isWifi(getActivity())) {
                                            DownLoadUtils du = new DownLoadUtils(getActivity());
                                            du.download(url);
                                        } else {
                                            new AlertDialog(getActivity()).builder().setTitle("提示").setCancelable(true).setMsg("检测到您不是Wifi环境,是否还继续下载?").setNegativeButton("下次再说", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    LoadingCancle();
                                                }
                                            }).setPositiveButton("下载", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    DownLoadUtils du = new DownLoadUtils(getActivity());
                                                    du.download(url);
                                                }
                                            }).show();
                                        }
                                    } else
                                        ToastUtil.toast2_bottom(getActivity(), "没有获取到新版本下载路径");

                                } else {
                                    ToastUtil.ToastErrorMsg(getActivity(), response, code);
                                    LoadingCancle();
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
        } else {
            ToastUtil.toast2_bottom(getActivity(), "您已经是最新版本，无需更新!");
        }
    }

    @Override
    public void onFail() {

    }

    @Override
    public void notifyAllActivity(boolean net) {
        if (net) {
            CheckLoginState();
            if (SPreferences.getUserToken() != null && !SPreferences.getUserToken().isEmpty())
                getData(MzFinal.GETDETAILS, false);
        }
    }

    @Override
    public void PhotoLBitmapistener(String path, Bitmap bitmap, int page) {
        if (page == 99)
            UpLoadIcon(new File(path));
    }

    private void UpLoadIcon(File file) {
        PostFormBuilder post = OkHttpUtils.post();
        post.url(MzFinal.URL + MzFinal.MODIFYENTITY);
        if (file != null)
            post.addFile("file1", file.getName(), file);
        post.addParams(MzFinal.KEY, SPreferences.getUserToken());
        RequestCall build = post.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                int code = 0;
                try {
                    code = getCode(response);
                    if (code == 1) {
                        ToastUtil.toast2_bottom(getActivity(), "修改成功...");
                        getData(MzFinal.GETDETAILS, false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
