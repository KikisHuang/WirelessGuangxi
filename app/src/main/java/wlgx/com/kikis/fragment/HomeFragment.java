package wlgx.com.kikis.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.BankBean;
import wlgx.com.kikis.bean.BrowseBean;
import wlgx.com.kikis.bean.MakeBean;
import wlgx.com.kikis.bean.VersionBean;
import wlgx.com.kikis.listener.MyFragmetnListener;
import wlgx.com.kikis.listener.OverallRefreshListener;
import wlgx.com.kikis.listener.VersionCheckListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.DownLoadUtils;
import wlgx.com.kikis.utils.ListenerManager;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.AlertDialog;

import static wlgx.com.kikis.utils.IntentUtils.goAdminPage;
import static wlgx.com.kikis.utils.IntentUtils.goCouponPage;
import static wlgx.com.kikis.utils.IntentUtils.goGoodsListPage;
import static wlgx.com.kikis.utils.IntentUtils.goLoginPage;
import static wlgx.com.kikis.utils.IntentUtils.goOrderPage;
import static wlgx.com.kikis.utils.IntentUtils.goShopCheckPage;
import static wlgx.com.kikis.utils.IntentUtils.goShopListPage;
import static wlgx.com.kikis.utils.IntentUtils.goStatisPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.JsonUtils.getJsonSring;
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
public class HomeFragment extends BaseFragment implements View.OnClickListener, MyFragmetnListener, VersionCheckListener, OverallRefreshListener {
    private static final String TAG = "HomeFragment";
    private LinearLayout top_layout, bottom_layout, gathering_ll, withdraw_details_ll, reconciliation_ll;
    private TextView turnover_tv, goods_rate_tv, page_view_tv, shop_name;

    private ImageView img1, img2, img3, img4, img5, img6;
    private TextView name1, name2, name3, name4, name5, name6;
    private LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, statis_layout;
    public static MyFragmetnListener listener;
    private int level = -1;

    @Override
    protected int initContentView() {
        return R.layout.home_fragment;
    }

    @Override
    protected void click() {
        gathering_ll.setOnClickListener(this);
        withdraw_details_ll.setOnClickListener(this);
        reconciliation_ll.setOnClickListener(this);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goStatisPage(getActivity());
            }
        });
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCouponPage(getActivity());
            }
        });
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAuthe();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOrderPage(getActivity());
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level == 1)
                    goAdminPage(getActivity());
                else
                    ToastUtil.toast2_bottom(getActivity(), "权限不足!");
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShopListPage(getActivity());
            }
        });
        statis_layout.setOnClickListener(this);

    }

    private void CheckAuthe() {
        Show(getActivity(), "", true, null);
        /**
         * 资质认证检测接口;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.LICENSEAPPLYSHOP)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            Log.i(TAG, "delete response  ===" + response);
                            if (code == 1)
                                goGoodsListPage(getActivity());
                            else if (code == 0) {
                                ToastUtil.toast2_bottom(getActivity(), "通过资质认证后才可上传商品信息！！！");
                                goShopCheckPage(getActivity());
                            } else if (code == 2)
                                new AlertDialog(getActivity()).builder().setTitle("提示").setCancelable(true).setMsg("资质审核中").setNegativeButton("确定", null).show();

                            else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                        Cancle();
                    }
                });
    }

    @Override
    protected void init() {
        listener = this;
        ListenerManager.getInstance().registerListtener(this);
        top_layout = f(R.id.top_layout);
        bottom_layout = f(R.id.bottom_layout);

        shop_name = f(R.id.shop_name);
        page_view_tv = f(R.id.page_view_tv);
        withdraw_details_ll = f(R.id.withdraw_details_ll);
        reconciliation_ll = f(R.id.reconciliation_ll);
        gathering_ll = f(R.id.gathering_ll);
        goods_rate_tv = f(R.id.goods_rate_tv);
        turnover_tv = f(R.id.turnover_tv);
        statis_layout = f(R.id.statis_layout);

        layout1 = (LinearLayout) top_layout.findViewById(R.id.layout1);
        layout2 = (LinearLayout) top_layout.findViewById(R.id.layout2);
        layout3 = (LinearLayout) top_layout.findViewById(R.id.layout3);
        layout4 = (LinearLayout) bottom_layout.findViewById(R.id.layout1);
        layout5 = (LinearLayout) bottom_layout.findViewById(R.id.layout2);
        layout6 = (LinearLayout) bottom_layout.findViewById(R.id.layout3);

        img1 = (ImageView) top_layout.findViewById(R.id.img1);
        img2 = (ImageView) top_layout.findViewById(R.id.img2);
        img3 = (ImageView) top_layout.findViewById(R.id.img3);
        img4 = (ImageView) bottom_layout.findViewById(R.id.img1);
        img5 = (ImageView) bottom_layout.findViewById(R.id.img2);
        img6 = (ImageView) bottom_layout.findViewById(R.id.img3);

        Glide.with(getActivity()).load(R.mipmap.shop_manage).into(img1);
//      Glide.with(getActivity()).load(R.mipmap.goods_manage_icon).into(img2);
        Glide.with(getActivity()).load(R.mipmap.order_icon).into(img2);
        Glide.with(getActivity()).load(R.mipmap.clerk_manage).into(img3);
        Glide.with(getActivity()).load(R.mipmap.coupon_icon).into(img4);
        Glide.with(getActivity()).load(R.mipmap.goods_icon).into(img5);
        Glide.with(getActivity()).load(R.mipmap.statis_icon).into(img6);
        img6.setBackgroundResource(R.color.white);

        name1 = (TextView) top_layout.findViewById(R.id.name1);
        name2 = (TextView) top_layout.findViewById(R.id.name2);
        name3 = (TextView) top_layout.findViewById(R.id.name3);
        name4 = (TextView) bottom_layout.findViewById(R.id.name1);
        name5 = (TextView) bottom_layout.findViewById(R.id.name2);
        name6 = (TextView) bottom_layout.findViewById(R.id.name3);

        setName();
    }

    private void setName() {
        name1.setText("店铺资料");
//        name2.setText("商品管理");
        name2.setText("订单管理");
        name3.setText("店员管理");
        name4.setText("优惠券");
        name5.setText("商品管理");
        name6.setText("数据统计");
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        if (SPreferences.getUserToken() != null && !SPreferences.getUserToken().isEmpty()) {
            getVersionInfo(getActivity(), this);

            getShopName();
            getShopData(MzFinal.URL + MzFinal.ORDERRATIO, 1);
            getShopData(MzFinal.URL + MzFinal.BROWSE, 2);
            getShopData(MzFinal.URL + MzFinal.BROWSE, 3);
        }
    }

    private void getShopName() {
        /**
         * 获取店铺名称;
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
                                shop_name.setText(bb.getName() + " ");
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

        /**
         * 获取用户权限;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETSHOPADMINDETAILS)
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
                                level = bb.getLevel();
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getShopData(String url, final int tag) {

        OkHttpUtils
                .get()
                .url(url)
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

                                switch (tag) {
                                    case 1:
                                        MakeBean mb = new Gson().fromJson(String.valueOf(ob), MakeBean.class);
                                        page_view_tv.setText(String.valueOf(mb.getSum() / 100));
                                        break;
                                    case 2:
                                        BrowseBean b = new Gson().fromJson(String.valueOf(ob), BrowseBean.class);
                                        goods_rate_tv.setText(b.getBrowseGoods() + "");
                                        break;
                                    case 3:
                                        BrowseBean bb = new Gson().fromJson(String.valueOf(ob), BrowseBean.class);
                                        turnover_tv.setText(bb.getBrowseShop() + "");
                                        break;
                                }

                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ListenerManager.getInstance().unRegisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SPreferences.getUserToken() == null || SPreferences.getUserToken().isEmpty())
            goLoginPage(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.statis_layout:
                goStatisPage(getActivity());
                break;
            case R.id.gathering_ll:
                developing();
                break;
            case R.id.withdraw_details_ll:
                developing();
                break;
            case R.id.reconciliation_ll:
                developing();
                break;
        }
    }

    private void developing() {
        ToastUtil.toast2_bottom(getActivity(), "功能开发中，敬请期待。");
    }

    @Override
    public void onUpdata() {
        getData();
    }

    @Override
    public void onHide(int f) {

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
        }
    }

    @Override
    public void onFail() {

    }

    @Override
    public void notifyAllActivity(boolean net) {
        if (net)
            getData();
    }
}
