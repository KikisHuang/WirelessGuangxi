package wlgx.com.kikis.utils;

import wlgx.com.kikis.R;

/**
 * Created by lian on 2017/9/9.
 */
public class MzFinal {
    //    public static final String SHOPURL1 = "http://wxgx.gonglipinglawyer.com/mobile-wifi/goodsStore.html?isReadOnly=1&id=";
//    public static final String SHOPURL2 = "http://wxgx.gonglipinglawyer.com/mobile-wifi/goodsStore_new.html?isReadOnly=1&id=";
    public static final String SHOPURL1 = "http://wx.gxchangtai.com/goodsStore.html?isReadOnly=1&id=";
    public static final String SHOPURL2 = "http://wx.gxchangtai.com/goodsStore_new.html?isReadOnly=1&id=";

    public static String SHOPID = "";
    public static String SHOPURL = "";

    public static final String URL = "http://wx.gxchangtai.com/mcLimitlessWifiInterface/";
    //刷新动画
    public static int[] refreshAnimSrcs = new int[]{R.drawable.mt_refreshing01, R.drawable.mt_refreshing02, R.drawable.mt_refreshing03, R.drawable.mt_refreshing04, R.drawable.mt_refreshing05, R.drawable.mt_refreshing06};
    //下拉动画
    public static int[] pullAnimSrcs = new int[]{R.drawable.mt_pull, R.drawable.mt_pull01, R.drawable.mt_pull02, R.drawable.mt_pull03, R.drawable.mt_pull04, R.drawable.mt_pull05};
    //上滑动画
    public static int[] loadingAnimSrcs = new int[]{R.drawable.mt_loading01, R.drawable.mt_loading02};

    //饼图颜色
    public static int[] piechartColors = new int[]{R.color.piecha1, R.color.piecha2, R.color.piecha3, R.color.piecha4, R.color.piecha5};
    //饼图圆点
    public static int[] piechartDrawable = new int[]{R.drawable.dot_pie1, R.drawable.dot_pie2, R.drawable.dot_pie3, R.drawable.dot_pie4, R.drawable.dot_pie5};
    public static String KEY = "key";

    public static String PHONE = "phone";
    public static String ACCOUNT = "account";

    public static boolean ALIAS = true;

    /**
     * 短信接口;
     */
    public static final String CODE = "shopAdmin/createCode.app?";
    /**
     * 登录;
     */
    public static final String LOGIN = "shopAdmin/login.app?";
    /**
     * 订单详情;
     */
    public static final String ORDERDETAILS = "shopAdmin/select/getDetails.app?";

    /**
     * 订单退款(废弃);
     */
    public static final String ORDERREFUND = "shopManage/order/modify/refundEntity.app?";
    /**
     * 订单确认付款(废弃);
     */
    public static final String ORDERPAY = "shopManage/order/modify/payEntity.app?";

    /**
     * 用户注册数;
     */
    public static final String REGISTER = "shopAdmin/statistics/userRegister.app?";
    /**
     * 店铺浏览数与订单的转化率(柱状图);
     */
    public static final String TRANSFORMATIONRATE = "shopAdmin/statistics/transformationRate.app?";
    /**
     * 店内会员消费排名(饼状图);
     */
    public static final String ORDERTOTALFEE = "shopAdmin/statistics/orderTotalFee.app?";
    /**
     * 获取浏览数;
     */
    public static final String BROWSE = "shopAdmin/statistics/browse.app?";
    /**
     * 店铺商品平均评级/评级排名(百分比);
     */
    public static final String EVALUATE = "shopAdmin/statistics/evaluate.app?";
    /**
     * 获取成交金额/月增长率 百分比为当前月的成交金额增长率;
     */
    public static final String ORDERRATIO = "shopAdmin/statistics/order.app?";

    /**
     * 修改密码;
     */
    public static final String MODIFYPASSWORD = "shopAdmin/modifyPassword.app?";
    /**
     * 找回密码接口;
     */
    public static final String RESETPASSWORD = "shopAdmin/modify/resetPassword.app?";

    /**
     * 修改手机;
     */
    public static final String MODIFYPHONE = "shopAdmin/modifyPhone?";

    /**
     * 修改银行账户接口;
     */
    public static final String MODIFYSENSITIVEINFO = "shopManage/modify/modifySensitiveInfo.app?";

    /**
     * 获取商铺信息接口;
     */
    public static final String GETDETAILS = "shopManage/select/getDetails.app?";
    /**
     * 修改商铺信息接口;
     */
    public static final String MODIFYENTITY = "shopManage/modify/modifyEntity.app?";

    /**
     * 修改商铺轮播图接口;
     */
    public static final String MODIFYSHOPIMG = "shopManage/modify/modifyShopIMG.app?";
    /**
     * 删除商铺轮播图接口;
     */
    public static final String DELETESHOPIMGBYIDS = "shopManage/delete/deleteShopIMGByIds.app?";
    /**
     * 新增商铺轮播图接口;
     */
    public static final String ADDSHOPIMG = "shopManage/add/addShopIMG.app?";

    /**
     * 商铺开业接口;
     */
    public static final String SHOPOPEN = "shopManage/modify/shopOpen.app?";
    /**
     * 商铺停业接口;
     */
    public static final String SHOPCLOSE = "shopManage/modify/shopClose.app?";

    /**
     * 获取行业类型信息接口;
     */
    public static final String GETALL = "shopType/select/getALL.app?";

    /**
     * 申请入驻接口;
     */
    public static final String APPLYSHOP = "shopApply/add/applyShop.app?";
    /**
     * 申请入驻上传图片接口;
     */
    public static final String APPAPPLYIMG = "shopApply/add/APPapplyImg.app";
    /**
     * 申请入驻删除图片接口;
     */
    public static final String APPDELETEAPPLYIMG = "shopApply/delete/APPdeleteapplyImg.app?";

    /**
     * 获取管理员列表接口;
     */
    public static final String GETADMINBYPAGE = "manageShopAdmin/select/getAdminByPage.app?";

    /**
     * 获取管理员权限接口;
     */
    public static final String GETSHOPADMINDETAILS = "shopManage/select/getShopAdminDetails.app?";

    /**
     * 删除员工信息接口;
     */
    public static final String DELETEBYIDS = "manageShopAdmin/delete/deleteByIds.app?";

    /**
     * 修改员工信息接口;
     */
    public static final String MODIFYSTAFFENTITY = "manageShopAdmin/modify/modifyEntity.app?";
    /**
     * 重设员工密码接口;
     */
    public static final String MODIFYSTAFFENPASSWORD = "manageShopAdmin/modify/modifyPassword.app?";

    /**
     * 新建员工信息接口;
     */
    public static final String ADDENTITY = "manageShopAdmin/add/addEntity.app?";

    /**
     * 获取客服联系方式、app版本号接口
     */
    public static final String GETSETTING = "common/getSetting.app";
    /**
     * 最新apk下载接口
     */
    public static final String DOWNLOADPATH = "common/getDownloadPath.app";
    /**
     * 商铺重名校验接口
     */
    public static final String CHECKSHOPNAME = "common/checkShopName.app";
    /**
     * 商品列表接口
     */
    public static final String GETBYPAGE = "shopManage/goods/select/getByPage.app";
    /**
     * 商品类型接口
     */
    public static final String GOODSTYPE = "shopManage/goodsType/select/getByPage.app";
    /**
     * 删除商品类型接口
     */
    public static final String DELETETYPEBYIDS = "shopManage/goodsType/delete/deleteByIds.app";
    /**
     * 添加商品分类接口
     */
    public static final String ADDGOODSENTITY = "shopManage/goodsType/add/addEntity.app";
    /**
     * 修改商品接口
     */
    public static final String CHANGEGOODS = "shopManage/goods/modify/modifyEntity.app";

    /**
     * 商品详情接口
     */
    public static final String GETGOODSDETAILS = "shopManage/goods/select/getDetails.app";
    /**
     * 富文本编辑图片上传接口
     */
    public static final String UPLOADIMG = "shopManage/goods/add/uploadImg.app";
    /**
     * 添加商品接口
     */
    public static final String ADDGOODS = "shopManage/goods/add/addEntity.app";

    /**
     * 查询店铺类的优惠券 接口
     */
    public static final String DISCOUNTSGETBYPAGE = "shopManage/coupon/select/getByPage.app";
    /**
     * 新增优惠券接口
     */
    public static final String COUPONADDENTITY = "shopManage/coupon/add/addEntity.app";
    /**
     * 优惠券修改接口
     */
    public static final String COUPONCHANGEENTITY = "shopManage/coupon/modify/modifyEntity.app";
    /**
     * 优惠券删除接口
     */
    public static final String COUPONDELETEBYIDS = "shopManage/coupon/delete/deleteByIds.app";
    /**
     * 资质认证接口
     */
    public static final String SHOPAUTHENTICATION = "shopAdmin/add/shopAuthentication.app";
    /**
     * 资质检测接口
     */
    public static final String LICENSEAPPLYSHOP = "shopAdmin/select/checkShopAuthentication.app";
    /**
     * 获取退款详情接口
     */
    public static final String GETBYORDERID = "refundManage/select/getByOrderId.app";
    /**
     * 商家退款管理接口
     */
    public static final String EXAMINEREFUND = "refundManage/modify/examineRefund.app";
    /**
     * 获取自己的id接口
     */
    public static final String GETMYID = "shopAdmin/getMyId.app";

    /**
     * 商家订单页面;
     * 0 全部
     * 1 待付款
     * 2 已付款
     * 3 待退款
     * 4 已退款
     */
    public static final String ORDER = "shopAdmin/select/getByPage.app?";

    public static final String br = "<br />";

}
