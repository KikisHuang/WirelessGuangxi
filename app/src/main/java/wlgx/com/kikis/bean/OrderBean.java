package wlgx.com.kikis.bean;

import java.util.List;

/**
 * Created by lian on 2017/9/13.
 */
public class OrderBean {


    /**
     * addressInfo : 佛子岭路
     * addressInfoName : 周某
     * addressInfoPhone : 13607717916
     * createTime : 2017-09-21 17:28:44
     * customerIp : 180.137.42.31
     * deliveryTime : 2017-09-21 17:28:00
     * freight : 0.0
     * id : 9256292377d54e39bb14809222054ff9
     * localeFlag : 0
     * mcOrderGoodsMappings : [{"count":3,"goods":{"area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","browseCount":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","commentCount":0,"createTime":"2017-08-09 19:22:39","freight":0,"goodsType":{"createTime":"2017-08-09 15:46:15","id":"test","num":0,"shop":{"address":"广西壮族自治区南宁市青秀区建政街道万达广场(青秀店)","aliAccount":"alipay","avgEvaluate":5.3333,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038506953655bdac2.jpg","bankCardAccount":"bank","browseCount":14,"browseCountDay":2,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":0,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":1,"hotFlag":0,"id":"testSHOP","info":"以一流的食材服务广大食客","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1505889753795b92722.jpeg","longitude":108.369102,"name":"whoo后旗舰店","num":0,"parkingLotFlag":0,"phone":"5664","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"shopType":{"decorationFlag":0,"id":"0","imgUrl":"http://s0.meituan.net/bs/?f=fe-web-meituan:img/banner/1.jpg@457b102","location":"st0cv4","num":0,"title":"美食","uid":0},"status":1,"sumCollection":0,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeId":"0","uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","wifiFlag":0,"workingTimeDescription":"全天营业","workingTimeEnd":24,"workingTimeEndM":0,"workingTimeStart":0,"workingTimeStartM":0,"wxAccount":"wx"},"shopId":"testSHOP","title":"热销","uid":-1},"goodsTypeId":"test","hotFlag":1,"id":"c1bfbc9c039343cb83277bfd5f8c4a09","keyword":"护肤液","logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038511310315e975e.jpg","num":4,"price":0.01,"province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shop":{"address":"广西壮族自治区南宁市青秀区建政街道万达广场(青秀店)","aliAccount":"alipay","avgEvaluate":5.3333,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038506953655bdac2.jpg","bankCardAccount":"bank","browseCount":14,"browseCountDay":2,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":0,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":1,"hotFlag":0,"id":"testSHOP","info":"以一流的食材服务广大食客","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1505889753795b92722.jpeg","longitude":108.369102,"name":"whoo后旗舰店","num":0,"parkingLotFlag":0,"phone":"5664","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"shopType":{"decorationFlag":0,"id":"0","imgUrl":"http://s0.meituan.net/bs/?f=fe-web-meituan:img/banner/1.jpg@457b102","location":"st0cv4","num":0,"title":"美食","uid":0},"status":1,"sumCollection":0,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeId":"0","uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","wifiFlag":0,"workingTimeDescription":"全天营业","workingTimeEnd":24,"workingTimeEndM":0,"workingTimeStart":0,"workingTimeStartM":0,"wxAccount":"wx"},"shopId":"testSHOP","status":1,"stock":100,"sumCollection":0,"title":"Whoo后 天气丹花献水乳护肤套装","uid":3},"goodsId":"c1bfbc9c039343cb83277bfd5f8c4a09","id":"ogmc55d68500f1f48fe91ed648cd17db214","orderId":"9256292377d54e39bb14809222054ff9","uid":75}]
     * payType : 0
     * price : 0.03
     * shopId : testSHOP
     * status : 0
     * totalFee : 0.03
     * transportType : 0
     * uid : 220
     * ukId : u646d1d5c2e374c4f9a388dbf31f1af8d
     */

    private String addressInfo;
    private String addressInfoName;
    private String addressInfoPhone;
    private String createTime;
    private String customerIp;
    private String deliveryTime;
    private double freight;
    private String id;
    private int localeFlag;
    private int payType;
    private double price;
    private String shopId;
    private int status;
    private double totalFee;
    private int transportType;
    private int uid;
    private String ukId;
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

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLocaleFlag() {
        return localeFlag;
    }

    public void setLocaleFlag(int localeFlag) {
        this.localeFlag = localeFlag;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
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

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUkId() {
        return ukId;
    }

    public void setUkId(String ukId) {
        this.ukId = ukId;
    }

    public List<McOrderGoodsMappingsBean> getMcOrderGoodsMappings() {
        return mcOrderGoodsMappings;
    }

    public void setMcOrderGoodsMappings(List<McOrderGoodsMappingsBean> mcOrderGoodsMappings) {
        this.mcOrderGoodsMappings = mcOrderGoodsMappings;
    }
}
