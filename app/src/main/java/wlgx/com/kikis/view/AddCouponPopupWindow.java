package wlgx.com.kikis.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.CouponBean;
import wlgx.com.kikis.bean.GoodsCategory;
import wlgx.com.kikis.bean.GoodsType;
import wlgx.com.kikis.listener.UpDataListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.DeviceUtils;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.DateUtils.getDDate;
import static wlgx.com.kikis.utils.DateUtils.getDate;
import static wlgx.com.kikis.utils.DateUtils.getMDate;
import static wlgx.com.kikis.utils.DateUtils.getYDate;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;


/**
 * Created by lian on 2017/6/5.
 * 客服页面；
 */
public class AddCouponPopupWindow implements View.OnClickListener {
    private static final String TAG = "AdminInfoPopupWindow";


    private EditText cou_sum_ed;
    private EditText minimum_charge_ed;
    private EditText end_time_ed;
    private EditText coupon_type_ed;
    private EditText coupon_detail_ed;
    private EditText coupon_status_ed;
    private RippleView add_bt;
    private PopupWindow popupWindow;
    private Context mContext;
    private UpDataListener mylistener;
    private int Status = 0;
    private List<GoodsCategory> data;
    private String typeId = "";
    //0新增，1修改;
    private int flag = 99;
    private CouponBean couponBean;
    private String id = "";

    public AddCouponPopupWindow(Context mContext, UpDataListener mylistener, int flag) {
        this.mContext = mContext;
        this.mylistener = mylistener;
        this.flag = flag;

    }

    public AddCouponPopupWindow(Context mContext, UpDataListener mylistener, int flag, CouponBean couponBean, String id) {
        this.mContext = mContext;
        this.mylistener = mylistener;
        this.flag = flag;
        this.couponBean = couponBean;
        this.id = id;
    }

    public void ScreenPopupWindow() {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.coupon_popu_layout, null);

            popupWindow = new PopupWindow(contentView, DeviceUtils.getWindowWidth(mContext) * 8 / 10, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.AnimationPreview);

            init(contentView);
            click();
            getType();

            backgroundAlpha(0.7f, mContext);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f, mContext);
                    popupWindow.dismiss();
                    popupWindow = null;
                    mContext = null;

                    add_bt = null;
                    cou_sum_ed = null;
                    minimum_charge_ed = null;
                    end_time_ed = null;
                    coupon_type_ed = null;
                    coupon_detail_ed = null;
                    coupon_status_ed = null;
                }
            });
            popupWindow.showAtLocation(LayoutInflater.from(mContext).inflate(R.layout.admin_layout, null), Gravity.CENTER, 0, 0);
        }
    }

    private void getType() {
        /**
         * 商品列表;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GOODSTYPE)
                .addParams("page", String.valueOf(0))
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("pageSize", String.valueOf(999))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(mContext, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            JSONArray ar = getJsonAr(response);
                            if (code == 1) {
                                data.clear();
                                for (int i = 0; i < ar.length(); i++) {
                                    GoodsType gb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), GoodsType.class);
                                    GoodsCategory gs = new GoodsCategory();
                                    gs.setName(gb.getTitle());
                                    gs.setId(gb.getId());
                                    data.add(gs);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(mContext, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void click() {
        add_bt.setOnClickListener(this);
        coupon_status_ed.setOnClickListener(this);
        end_time_ed.setOnClickListener(this);
        coupon_type_ed.setOnClickListener(this);
    }

    private void init(View contentView) {
        add_bt = (RippleView) contentView.findViewById(R.id.add_bt);
        cou_sum_ed = (EditText) contentView.findViewById(R.id.cou_sum_ed);
        minimum_charge_ed = (EditText) contentView.findViewById(R.id.minimum_charge_ed);
        end_time_ed = (EditText) contentView.findViewById(R.id.end_time_ed);
        coupon_type_ed = (EditText) contentView.findViewById(R.id.coupon_type_ed);
        coupon_detail_ed = (EditText) contentView.findViewById(R.id.coupon_detail_ed);
        coupon_status_ed = (EditText) contentView.findViewById(R.id.coupon_status_ed);
        data = new ArrayList<>();
        if (flag == 1) {
            add_bt.setText("修改");
            if (couponBean.getGoodsType() == null) {
                coupon_type_ed.setText("全部");
                typeId = "";
            } else {
                coupon_type_ed.setText(couponBean.getGoodsType().getTitle());
                typeId = couponBean.getGoodsTypeId();
            }


            cou_sum_ed.setText(String.valueOf(couponBean.getPrice()));
            minimum_charge_ed.setText(String.valueOf(couponBean.getTriggerMoney()));
            end_time_ed.setText(getDate(couponBean.getEndTime()));
            coupon_detail_ed.setText(couponBean.getInfo());

            if (couponBean.getStatus() == 0)
                coupon_status_ed.setText("未上架");
            if (couponBean.getStatus() == 1)
                coupon_status_ed.setText("已上架");
            if (couponBean.getStatus() == -1)
                coupon_status_ed.setText("已选架");

            Status = couponBean.getStatus();
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * @param mContext
     */
    public void backgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_bt:
                if (!end_time_ed.getText().toString().isEmpty() && !coupon_status_ed.getText().toString().isEmpty() && !cou_sum_ed.getText().toString().isEmpty() && !minimum_charge_ed.getText().toString().isEmpty())
                    if (flag == 0)
                        AddCouPon();
                if (flag == 1)
                    ChangesCouPon();
                else if (end_time_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(mContext, "优惠券结束时间不能为空！！");
                else if (coupon_status_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(mContext, "优惠券状态不能为空！！");
                else if (minimum_charge_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(mContext, "最低消费金额不能为空！！");
                else if (cou_sum_ed.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(mContext, "优惠金额不能为空！！");
                break;
            case R.id.coupon_status_ed:
                ChangeStatus();
                break;
            case R.id.end_time_ed:
                onYearMonthDayPicker();
                break;
            case R.id.coupon_type_ed:
                if (data.size() > 0) {
                    if (flag == 0)
                        onSinglePicker(0);
                    if (flag == 1) {
                        if (!typeId.isEmpty()) {
                            for (int i = 0; i < data.size(); i++) {
                                if (couponBean.getGoodsTypeId().equals(data.get(i).getId()))
                                    onSinglePicker(i);
                            }
                        } else
                            onSinglePicker(0);
                    }
                }

                break;
        }
    }

    private void AddCouPon() {
        /**
         * 新增优惠券;
         */
        Map<String, String> params = new HashMap<>();
        params.put(MzFinal.KEY, SPreferences.getUserToken());
        if (!typeId.isEmpty())
            params.put("goodsTypeId", typeId);
        params.put("endTimeStr", end_time_ed.getText().toString() + " 23:59:59");
        params.put("status", Status + "");
        params.put("info", coupon_detail_ed.getText().toString());
        params.put("triggerMoney", minimum_charge_ed.getText().toString());
        params.put("price", cou_sum_ed.getText().toString());

        PostFormBuilder post = OkHttpUtils.post();
        post.url(MzFinal.URL + MzFinal.COUPONADDENTITY);
        post.params(params);

        RequestCall build = post.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.toast2_bottom(mContext, "网络不顺畅...");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    int code = getCode(response);
                    if (code == 1) {
                        ToastUtil.toast2_bottom(mContext, "优惠券添加成功！！");
                        mylistener.onUpData();
                        popupWindow.dismiss();
                    } else
                        ToastUtil.ToastErrorMsg(mContext, response, code);
                } catch (Exception e) {

                }
            }
        });

    }

    private void ChangesCouPon() {
        /**
         * 修改优惠券;
         */
        Map<String, String> params = new HashMap<>();
        params.put(MzFinal.KEY, SPreferences.getUserToken());
        if (!typeId.isEmpty())
            params.put("goodsTypeId", typeId);
        params.put("endTimeStr", end_time_ed.getText().toString() + " 23:59:59");
        params.put("status", Status + "");
        params.put("id", id);
        params.put("info", coupon_detail_ed.getText().toString());
        params.put("triggerMoney", minimum_charge_ed.getText().toString());
        params.put("price", cou_sum_ed.getText().toString());

        GetBuilder get = OkHttpUtils.get();
        get.url(MzFinal.URL + MzFinal.COUPONCHANGEENTITY);
        get.params(params);

        RequestCall build = get.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.toast2_bottom(mContext, "网络不顺畅...");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    int code = getCode(response);
                    if (code == 1) {
                        ToastUtil.toast2_bottom(mContext, "优惠券修改成功！！");
                        mylistener.onUpData();
                        popupWindow.dismiss();
                    } else
                        ToastUtil.ToastErrorMsg(mContext, response, code);
                } catch (Exception e) {

                }
            }
        });

    }

    private void ChangeStatus() {
        new ActionSheetDialog(mContext).builder().
                addSheetItem("已上架", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Status = 1;
                        coupon_status_ed.setText("已上架");
                    }
                }).addSheetItem("未上架", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                coupon_status_ed.setText("未上架");
                Status = 0;
            }
        }).addSheetItem("已下架", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                coupon_status_ed.setText("已下架");
                Status = -1;
            }
        }).show();
    }

    public void onSinglePicker(int num) {
        SinglePicker<GoodsCategory> picker = new SinglePicker<GoodsCategory>((Activity) mContext, data);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(num);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<GoodsCategory>() {
            @Override
            public void onItemPicked(int index, GoodsCategory gt) {
                coupon_type_ed.setText(gt.getName());
                typeId = gt.getId();
                Log.i(TAG, "type Id ==" + gt.getId());
            }
        });
        picker.show();
    }

    public void onYearMonthDayPicker() {
        final DatePicker picker = new DatePicker((Activity) mContext);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(mContext, 10));
        picker.setRangeEnd(2111, 1, 11);
        picker.setRangeStart(Integer.parseInt(getYDate()), Integer.parseInt(getMDate()), Integer.parseInt(getDDate()));
        picker.setSelectedItem(Integer.parseInt(getYDate()), Integer.parseInt(getMDate()), Integer.parseInt(getDDate()));
        picker.setResetWhileWheel(false);

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                end_time_ed.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }
}
