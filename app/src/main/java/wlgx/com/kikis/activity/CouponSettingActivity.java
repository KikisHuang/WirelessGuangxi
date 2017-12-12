package wlgx.com.kikis.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.CouponAdapter;
import wlgx.com.kikis.bean.CouponBean;
import wlgx.com.kikis.listener.ItemClickListener;
import wlgx.com.kikis.listener.UpDataListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.ActionSheetDialog;
import wlgx.com.kikis.view.AddCouponPopupWindow;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/10/20.
 */
public class CouponSettingActivity extends InitActivity implements View.OnClickListener, UpDataListener, ItemClickListener {
    private static final String TAG = "CouponSettingActivity";
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private CouponAdapter adapter;
    private RelativeLayout null_laouyt;
    private TextView add_tv;
    private ImageView add_icon;
    private List<CouponBean> list;
    private ItemClickListener listener;
    private AddCouponPopupWindow add;

    @Override
    protected void click() {
        add_tv.setOnClickListener(this);
        add_icon.setOnClickListener(this);
    }

    private void DeleteCouPon(String id) {
        /**
         * 优惠券删除;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.COUPONDELETEBYIDS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("ids", id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(CouponSettingActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);

                            Log.i(TAG, "delete response  ===" + response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(CouponSettingActivity.this, "删除成功！！！");
                                getData();
                            } else
                                ToastUtil.ToastErrorMsg(CouponSettingActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void init() {
        setContentView(R.layout.coupon_setting_layout);
        setTitles(this, "设置优惠券");
        listener = this;
        add_tv = f(R.id.add_tv);
        recyclerView = f(R.id.recycler);
        null_laouyt = f(R.id.null_laouyt);
        add_icon = f(R.id.add_icon);
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        // 使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        /**
         * 优惠券列表获取;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.DISCOUNTSGETBYPAGE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("page", 0 + "")
                .addParams("pageSize", "999")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(CouponSettingActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);

                            Log.i(TAG, "response  ===" + response);
                            if (code == 1) {
                                list.clear();
                                JSONArray ar = getJsonAr(response);
                                if (ar.length() <= 0)
                                    null_laouyt.setVisibility(View.VISIBLE);
                                else {
                                    null_laouyt.setVisibility(View.GONE);

                                    for (int i = 0; i < ar.length(); i++) {
                                        CouponBean cb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), CouponBean.class);
                                        list.add(cb);
                                    }
                                    if (adapter == null) {
                                        adapter = new CouponAdapter(CouponSettingActivity.this, list, listener);
                                        recyclerView.setAdapter(adapter);
                                    } else
                                        adapter.notifyDataSetChanged();
                                }
                            } else
                                ToastUtil.ToastErrorMsg(CouponSettingActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_icon:
                add_icon.setEnabled(false);
                AddCoupon();
                break;
            case R.id.add_tv:
                add_tv.setEnabled(false);
                AddCoupon();
                break;
        }
    }

    private void AddCoupon() {
        add = new AddCouponPopupWindow(this, this, 0);
        add.ScreenPopupWindow();

        add_tv.setEnabled(true);
        add_icon.setEnabled(true);
    }

    @Override
    public void onUpData() {
        getData();
    }

    @Override
    public void onItemClickListener(int position, final String id, String params) {
        if (position == 10086) {
            new ActionSheetDialog(CouponSettingActivity.this).builder().addSheetItem("删除", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    DeleteCouPon(id);
                }
            }).addSheetItem("取消", ActionSheetDialog.SheetItemColor.Blue, null).show();
        } else {
            AddCouponPopupWindow add = new AddCouponPopupWindow(this, this, 1, list.get(position), list.get(position).getId());
            add.ScreenPopupWindow();
        }
    }
}
