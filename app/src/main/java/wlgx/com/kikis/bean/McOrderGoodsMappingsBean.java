package wlgx.com.kikis.bean;

/**
 * Created by lian on 2017/9/14.
 */
public class McOrderGoodsMappingsBean {

    /**
     * count : 3
     * goods : {"area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","browseCount":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","commentCount":0,"createTime":"2017-08-09 19:22:39","freight":20,"goodsTypeId":"test","hotFlag":1,"id":"c1bfbc9c039343cb83277bfd5f8c4a09","keyword":"护肤液","logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038511310315e975e.jpg","num":4,"price":1080,"province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shop":{"address":"南宁民族大道梦之岛百货","aliAccount":"alipay","avgEvaluate":4.7778,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038506953655bdac2.jpg","bankCardAccount":"bank","browseCount":9,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":0,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":0,"hotFlag":0,"id":"testSHOP","info":"以一流的食材服务广大食客","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1503850695337554777.jpg","longitude":108.369102,"name":"whoo后旗舰店","num":0,"phone":"5425000","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"status":1,"sumCollection":0,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeFlag":0,"uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","workingTimeDescription":"全天营业","workingTimeEnd":24,"workingTimeStart":0,"wxAccount":"wx"},"shopId":"testSHOP","status":1,"stock":100,"sumCollection":0,"title":"Whoo后 天气丹花献水乳护肤套装","uid":3}
     * goodsId : c1bfbc9c039343cb83277bfd5f8c4a09
     * id : 111
     * orderId : 1111
     * uid : 1
     */
    private int count;
    private GoodsBean goods;
    private String goodsId;
    private String id;
    private String orderId;
    private int uid;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
