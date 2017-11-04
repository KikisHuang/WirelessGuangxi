package wlgx.com.kikis.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import wlgx.com.kikis.activity.AddShopActivity;
import wlgx.com.kikis.activity.AdminActivity;
import wlgx.com.kikis.activity.ApplicationInActivity;
import wlgx.com.kikis.activity.ChangeActivity;
import wlgx.com.kikis.activity.ChangeShopDataActivity;
import wlgx.com.kikis.activity.CouponSettingActivity;
import wlgx.com.kikis.activity.GoodsDetailActivity;
import wlgx.com.kikis.activity.GoodsListActivity;
import wlgx.com.kikis.activity.GoodsTypeActivity;
import wlgx.com.kikis.activity.GoodsUploadActivity;
import wlgx.com.kikis.activity.LocationActivity;
import wlgx.com.kikis.activity.LoginActivity;
import wlgx.com.kikis.activity.MainActivity;
import wlgx.com.kikis.activity.MyActivity;
import wlgx.com.kikis.activity.MyOrderActivity;
import wlgx.com.kikis.activity.OrderDetailsActivity;
import wlgx.com.kikis.activity.RefundOrderDetailsActivity;
import wlgx.com.kikis.activity.ShopCheckActivity;
import wlgx.com.kikis.activity.ShopListActivity;
import wlgx.com.kikis.activity.ShopPreviewActivity;
import wlgx.com.kikis.activity.ShopTypeActivity;
import wlgx.com.kikis.activity.StatisActivity;
import wlgx.com.kikis.activity.UploadPhotoActivity;
import wlgx.com.kikis.activity.WithdrawActivity;
import wlgx.com.kikis.activity.forgetPassActivity;

/**
 * Created by lian on 2017/5/26.
 * 跳转工具类;
 */
public class IntentUtils {
    private static final String TAG = "IntentUtils";

    /**
     * 主页面页面;
     *
     * @param context 上下文;
     */
    public static void goMainPage(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        startPage(context, intent);
    }

    /**
     * 登录页面;
     *
     * @param context 上下文;
     */
    public static void goLoginPage(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        startPage(context, intent);
    }

    /**
     * 订单页面;
     *
     * @param context 上下文;
     */
    public static void goOrderPage(Context context) {
        Intent intent = new Intent(context, MyOrderActivity.class);
        startPage(context, intent);
    }
    /**
     * 获取退款订单页面;
     *
     * @param context 上下文;
     */
    public static void goRefundOrderPage(Context context,String id) {
        Intent intent = new Intent(context, RefundOrderDetailsActivity.class);
        intent.putExtra("refund_id",id);
        startPage(context, intent);
    }

    /**
     * 商品列表页面;
     *
     * @param context 上下文;
     */
    public static void goGoodsListPage(Context context) {
        Intent intent = new Intent(context, GoodsListActivity.class);
        startPage(context, intent);
    }

    /**
     * 商品内容页面;
     *
     * @param context 上下文;
     * @param content
     * @param tag
     */
    public static void goGoodsContentPage(Context context, String content, int tag) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goods_content", content);
        intent.putExtra("goods_content_flag", tag + "");
        ((Activity) context).startActivityForResult(intent, 1214);
//        startPage(context, intent);

    }

    /**
     * 商品编辑页面;
     *
     * @param context 上下文;
     * @param tag
     */
    public static void goGoodsUpLoadPage(Context context, int tag, String goodsid) {
        Intent intent = new Intent(context, GoodsUploadActivity.class);
        intent.putExtra("status_tag", tag + "");
        intent.putExtra("goods_id", goodsid + "");
//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, 3656);
    }

    /**
     * 修改店铺资料页面;
     *
     * @param context 上下文;
     */
    public static void goShopChangePage(Context context) {
        Intent intent = new Intent(context, ChangeShopDataActivity.class);
        startPage(context, intent);
    }
    /**
     * 资质认证页面;
     *
     * @param context 上下文;
     */
    public static void goShopCheckPage(Context context) {
        Intent intent = new Intent(context, ShopCheckActivity.class);
        startPage(context, intent);
    }

    /**
     * 商品分类页面;
     *
     * @param context 上下文;
     * @param tag
     */
    public static void goGoodsTypePage(Context context, int tag) {
        Intent intent = new Intent(context, GoodsTypeActivity.class);
        intent.putExtra("type_tag", tag + "");
//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, 1213);
    }

    /**
     * 添加门店页面;
     *
     * @param context 上下文;
     */
    public static void goAddShopPage(Context context) {
        Intent intent = new Intent(context, AddShopActivity.class);
        startPage(context, intent);
    }

    /**
     * 店铺管理页面;
     *
     * @param context 上下文;
     */
    public static void goShopListPage(Context context) {
        Intent intent = new Intent(context, ShopListActivity.class);
        startPage(context, intent);
    }

    /**
     * 订单商品详情页面;
     *
     * @param context 上下文;
     */
    public static void goGoodsDetailsPage(Context context, String id) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra("order_id", id);
        startPage(context, intent);
    }

    /**
     * 统计页面;
     *
     * @param context 上下文;
     */
    public static void goStatisPage(Context context) {
        Intent intent = new Intent(context, StatisActivity.class);
        startPage(context, intent);
    }

    /**
     * 我的页面;
     *
     * @param context 上下文;
     */
    public static void goMyPage(Context context) {
        Intent intent = new Intent(context, MyActivity.class);
        startPage(context, intent);
    }

    /**
     * 申请入驻页面;
     *
     * @param context 上下文;
     */
    public static void goApplicationInPage(Context context) {
        Intent intent = new Intent(context, ApplicationInActivity.class);
        startPage(context, intent);
    }

    /**
     * 店员管理页面;
     *
     * @param context 上下文;
     */
    public static void goAdminPage(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        startPage(context, intent);
    }

    /**
     * 优惠券页面;
     *
     * @param context 上下文;
     */
    public static void goCouponPage(Context context) {
        Intent intent = new Intent(context, CouponSettingActivity.class);
        startPage(context, intent);
    }

    /**
     * 店员管理页面;
     *
     * @param context 上下文;
     */
    public static void goPreviewPage(Context context) {
        Intent intent = new Intent(context, ShopPreviewActivity.class);
        startPage(context, intent);
    }

    /**
     * 我的管理员页面;
     *
     * @param context 上下文;
     */
    public static void goShopTypePage(Context context) {
        Intent intent = new Intent(context, ShopTypeActivity.class);
        ((Activity) context).startActivityForResult(intent, 720);
    }


    /**
     * 修改信息页面;
     *
     * @param context 上下文;
     */
    public static void goChangePage(Context context, String tag) {
        Intent intent = new Intent(context, ChangeActivity.class);
        intent.putExtra("change_tag", tag);
//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, 718);
    }

    /**
     * 提现资料修改信息页面;
     *
     * @param context 上下文;
     */
    public static void goWithdrawPage(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        startPage(context, intent);
    }

    /**
     * 忘记密码信息页面;
     *
     * @param context 上下文;
     */
    public static void goForgetPassPage(Context context) {
        Intent intent = new Intent(context, forgetPassActivity.class);
        ((Activity) context).startActivityForResult(intent, 721);
    }

    /**
     * 地图获取地址页面;
     *
     * @param context 上下文;
     */
    public static void goLocationPage(Context context) {
        Intent intent = new Intent(context, LocationActivity.class);
        ((Activity) context).startActivityForResult(intent, 719);
    }

    /**
     * 上传照片页面;
     *
     * @param context 上下文;
     * @param tag     相册\拍照标识符;
     * @param page    页面标识符;
     */
    public static void goUploadPhotoPage(Context context, String tag, String page) {
        Intent intent = new Intent(context, UploadPhotoActivity.class);
        intent.putExtra("photo_flag", tag);
        intent.putExtra("page_flag", page);
        startPage(context, intent);
    }

    /**
     * 跳转通用方法;
     *
     * @param context
     * @param intent
     */
    private static void startPage(Context context, Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            context.startActivity(intent);
//            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
        else
            context.startActivity(intent);
    }
}
