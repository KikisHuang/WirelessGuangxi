package wlgx.com.kikis.bean;

import java.util.List;

/**
 * Created by lian on 2017/9/14.
 */
public class GoodsDetailsBean {

    /**
     * addressInfo : 10
     * addressInfoName : 8
     * addressInfoPhone : 9
     * contentText : 7
     * couponId : 3
     * createTime : 2017-08-11 00:00:00
     * customerIp : 124.227.190.130
     * deliveryTime : 2017-08-11 02:04:00
     * id : 1111
     * mcOrderGoodsMappings : [{"count":3,"goods":{"area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","browseCount":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","commentCount":0,"createTime":"2017-08-09 19:22:39","freight":20,"goodsTypeId":"test","hotFlag":1,"id":"c1bfbc9c039343cb83277bfd5f8c4a09","keyword":"护肤液","logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038511310315e975e.jpg","num":4,"price":1080,"province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shop":{"address":"南宁民族大道梦之岛百货","aliAccount":"alipay","avgEvaluate":4.7778,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038506953655bdac2.jpg","bankCardAccount":"bank","browseCount":9,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":0,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":0,"hotFlag":0,"id":"testSHOP","info":"以一流的食材服务广大食客","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1503850695337554777.jpg","longitude":108.369102,"name":"whoo后旗舰店","num":0,"phone":"5425000","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"status":1,"sumCollection":0,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeFlag":0,"uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","workingTimeDescription":"全天营业","workingTimeEnd":24,"workingTimeStart":0,"wxAccount":"wx"},"shopId":"testSHOP","status":1,"stock":100,"sumCollection":0,"title":"Whoo后 天气丹花献水乳护肤套装","uid":3},"goodsId":"c1bfbc9c039343cb83277bfd5f8c4a09","id":"111","orderId":"1111","uid":1},{"count":2,"goods":{"browseCount":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","commentCount":0,"createTime":"2017-08-14 02:59:43","freight":0,"goodsTypeId":"b1753088a574442195e149fc32337493","hotFlag":0,"id":"a3af7a5d75904c9eaf604c1ed6ae6abe","keyword":"长效舒适 轻盈缓震 ","logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1502708382835e42302.png","num":0,"price":499,"province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shop":{"address":"南宁青秀万达广场","avgEvaluate":0,"avgPrice":0,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1503849531286bb58d4.jpg","browseCount":7,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":1,"createTime":"2017-08-14 01:37:05","homeFlag":0,"hotFlag":0,"id":"48b019f4eaf94f529276e273be8f9e57","info":"耐克专卖店主营运动产品","level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/150384953126437b480.jpg","name":"耐克专卖","phone":"5888888","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","shopImgs":[],"status":1,"sumCollection":1,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeFlag":0,"uid":1,"wifiEquipmentId":"ed009a9046774a9cb83b1533858e8058","workingTimeDescription":"全天24小时营业","workingTimeEnd":24,"workingTimeStart":0},"shopId":"48b019f4eaf94f529276e273be8f9e57","status":1,"stock":0,"sumCollection":0,"title":"Nike 耐克官方 NIKE ROSHE ONE 女子运动休闲鞋 844994 ","uid":10},"goodsId":"a3af7a5d75904c9eaf604c1ed6ae6abe","id":"112","orderId":"1111","uid":2}]
     * mcShop : {"address":"南宁民族大道梦之岛百货","aliAccount":"alipay","avgEvaluate":4.7778,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038506953655bdac2.jpg","bankCardAccount":"bank","browseCount":9,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":0,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":0,"hotFlag":0,"id":"testSHOP","info":"以一流的食材服务广大食客","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1503850695337554777.jpg","longitude":108.369102,"name":"whoo后旗舰店","num":0,"phone":"5425000","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"status":1,"sumCollection":0,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeFlag":0,"uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","workingTimeDescription":"全天营业","workingTimeEnd":24,"workingTimeStart":0,"wxAccount":"wx"}
     * mcUser : {"area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","balance":0,"balanceFrozen":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","headImgUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/150373430288640c761.gif","id":"1","idcard":"450111111","loginTime":"2017-09-13 14:20:51","name":"test","phone":"13878141913","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sex":1,"status":true,"uid":1,"wxNumber":"456"}
     * number : 6
     * orderNumber : 1111
     * outTradeNo : 4
     * payTime : 2017-08-02 16:56:20
     * price : 2.0
     * shopId : testSHOP
     * status : 10
     * totalFee : 1.0
     * uid : 1
     * userLocation : 11
     * wlCode : 13
     * wlType : 12
     */
    private double freight;

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    private String addressInfo;
    private String addressInfoName;
    private String addressInfoPhone;
    private String contentText;
    private String couponId;
    private String createTime;
    private String customerIp;
    private String deliveryTime;
    private String id;
    private McShopBean mcShop;
    private McUserBean mcUser;
    private int number;
    private String orderNumber;
    private String outTradeNo;
    private String payTime;
    private double price;
    private String shopId;
    private int status;
    private float totalFee;
    private String uid;
    private String ukId;

    public String getUkId() {
        return ukId;
    }

    public void setUkId(String ukId) {
        this.ukId = ukId;
    }

    private String userLocation;
    private String wlCode;
    private String wlType;

    private List<McOrderGoodsMappingsBean> mcOrderGoodsMappings;

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getAddressInfoName() {
        return addressInfoName;
    }

    public void setAddressInfoName(String addressInfoName) {
        this.addressInfoName = addressInfoName;
    }

    public String getAddressInfoPhone() {
        return addressInfoPhone;
    }

    public void setAddressInfoPhone(String addressInfoPhone) {
        this.addressInfoPhone = addressInfoPhone;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public void setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public McShopBean getMcShop() {
        return mcShop;
    }

    public void setMcShop(McShopBean mcShop) {
        this.mcShop = mcShop;
    }

    public McUserBean getMcUser() {
        return mcUser;
    }

    public void setMcUser(McUserBean mcUser) {
        this.mcUser = mcUser;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getWlCode() {
        return wlCode;
    }

    public void setWlCode(String wlCode) {
        this.wlCode = wlCode;
    }

    public String getWlType() {
        return wlType;
    }

    public void setWlType(String wlType) {
        this.wlType = wlType;
    }

    public List<McOrderGoodsMappingsBean> getMcOrderGoodsMappings() {
        return mcOrderGoodsMappings;
    }

    public void setMcOrderGoodsMappings(List<McOrderGoodsMappingsBean> mcOrderGoodsMappings) {
        this.mcOrderGoodsMappings = mcOrderGoodsMappings;
    }
}
