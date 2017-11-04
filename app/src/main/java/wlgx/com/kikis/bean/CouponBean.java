package wlgx.com.kikis.bean;

/**
 * Created by lian on 2017/10/21.
 */
public class CouponBean {

    /**
     * createTime : 2017-10-21 10:06:20
     * endTime : 2017-10-25 23:59:59
     * goodsType : {"createTime":"2017-10-20 12:21:11","id":"7a678fe4-980d-4500-b197-a92ed0f2ea0e","num":0,"shop":{"address":"广西壮族自治区南宁市青秀区中山街道七星路一巷131号","aliAccount":"alipay","area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","avgEvaluate":5.3333,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075463966d5c.gif","bankCardAccount":"bank","bankName":"laojj","bankUserName":"j","browseCount":14,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":1,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":1,"hotFlag":1,"id":"testSHOP","info":"品牌于韩国上市，源自宫廷的独秘配方","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075437231d01.png","longitude":108.369102,"name":"whoo后旗舰店","num":-2,"parkingLotFlag":0,"phone":"07715698526","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"shopType":{"childs":[],"decorationFlag":0,"id":"4ba6cb200a704bc0a2cbf71220af047c","location":"st126a84-12893d","num":99999,"shopType":{"childs":[],"decorationFlag":1,"id":"6dc09b8c237849fbab25b2f6c2ccde31","imgUrl":"http://limitless-wifi-zw.oss-cn-hangzhou.aliyuncs.com/15064397784083bff75.png","location":"st126a84","num":7,"title":"零售商铺","uid":134},"shopTypeId":"6dc09b8c237849fbab25b2f6c2ccde31","title":"其他","uid":136},"status":1,"sumCollection":0,"sumMoney":0.01,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeId":"4ba6cb200a704bc0a2cbf71220af047c","uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","wifiFlag":0,"workingTimeDescription":"全天营业","workingTimeEnd":23,"workingTimeEndM":59,"workingTimeStart":0,"workingTimeStartM":0,"wxAccount":"wx"},"shopId":"testSHOP","title":"宠物","uid":78}
     * goodsTypeId : 7a678fe4-980d-4500-b197-a92ed0f2ea0e
     * id : 08d12130a3984234aea56413383d5c0b
     * info : 无
     * price : 10.0
     * shop : {"address":"广西壮族自治区南宁市青秀区中山街道七星路一巷131号","aliAccount":"alipay","area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","avgEvaluate":5.3333,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075463966d5c.gif","bankCardAccount":"bank","bankName":"laojj","bankUserName":"j","browseCount":14,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":1,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":1,"hotFlag":1,"id":"testSHOP","info":"品牌于韩国上市，源自宫廷的独秘配方","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075437231d01.png","longitude":108.369102,"name":"whoo后旗舰店","num":-2,"parkingLotFlag":0,"phone":"07715698526","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"shopType":{"childs":[],"decorationFlag":0,"id":"4ba6cb200a704bc0a2cbf71220af047c","location":"st126a84-12893d","num":99999,"shopType":{"childs":[],"decorationFlag":1,"id":"6dc09b8c237849fbab25b2f6c2ccde31","imgUrl":"http://limitless-wifi-zw.oss-cn-hangzhou.aliyuncs.com/15064397784083bff75.png","location":"st126a84","num":7,"title":"零售商铺","uid":134},"shopTypeId":"6dc09b8c237849fbab25b2f6c2ccde31","title":"其他","uid":136},"status":1,"sumCollection":0,"sumMoney":0.01,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeId":"4ba6cb200a704bc0a2cbf71220af047c","uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","wifiFlag":0,"workingTimeDescription":"全天营业","workingTimeEnd":23,"workingTimeEndM":59,"workingTimeStart":0,"workingTimeStartM":0,"wxAccount":"wx"}
     * shopId : testSHOP
     * status : 1
     * triggerMoney : 200.0
     * uid : 15
     */

    private String createTime;
    private String endTime;
    private GoodsType goodsType;
    private String goodsTypeId;
    private String id;
    private String info;
    private double price;
    private ShopBean shop;
    private String shopId;
    private int status;
    private double triggerMoney;
    private int uid;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
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

    public double getTriggerMoney() {
        return triggerMoney;
    }

    public void setTriggerMoney(double triggerMoney) {
        this.triggerMoney = triggerMoney;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
