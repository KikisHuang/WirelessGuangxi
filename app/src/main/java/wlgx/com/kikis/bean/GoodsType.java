package wlgx.com.kikis.bean;

import java.io.Serializable;

/**
 * Created by lian on 2017/9/14.
 */
public class GoodsType implements Serializable {

    /**
     * createTime : 2017-08-14 01:44:24
     * id : b1753088a574442195e149fc32337493
     * num : 0
     * shop : {"address":"南宁青秀万达广场","avgEvaluate":0,"avgPrice":0,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1503849531286bb58d4.jpg","browseCount":7,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":1,"createTime":"2017-08-14 01:37:05","homeFlag":0,"hotFlag":0,"id":"48b019f4eaf94f529276e273be8f9e57","info":"耐克专卖店主营运动产品","level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/150384953126437b480.jpg","name":"耐克专卖","phone":"5888888","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","shopImgs":[],"status":1,"sumCollection":1,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeFlag":0,"uid":1,"wifiEquipmentId":"ed009a9046774a9cb83b1533858e8058","workingTimeDescription":"全天24小时营业","workingTimeEnd":24,"workingTimeStart":0}
     * shopId : 48b019f4eaf94f529276e273be8f9e57
     * title : 运动鞋
     * uid : 9
     */

    private String createTime;
    private String id;
    private int num;
    private ShopBean shop;
    private String shopId;
    private String title;
    private int uid;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
