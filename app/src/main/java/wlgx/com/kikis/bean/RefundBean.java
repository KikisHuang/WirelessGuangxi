package wlgx.com.kikis.bean;

/**
 * Created by lian on 2017/10/27.
 */
public class RefundBean {


    /**
     * createTime : 2017-09-30 13:16:04
     * order : {"createTime":"2017-09-30 13:14:43","customerIp":"140.207.54.74","freight":0,"id":"1da6abefaded4876843875c496a93f8e","localeFlag":1,"mcShop":{"address":"广秀店ss","aliAccount":"alipay","area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","avgEvaluate":5.3333,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075463966d5c.gif","bankCardAccount":"bank","bankName":"laojj","bankUserName":"j","browseCount":14,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":2,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":1,"hotFlag":1,"id":"testSHOP","info":"品牌于韩国上市","latitude":22.843055,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075437231d01.png","longitude":108.355949,"name":"whoo后旗舰店","num":-2,"parkingLotFlag":0,"phone":"07715698526","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"shopType":{"childs":[],"decorationFlag":0,"id":"ea443b7fb84d44f2a188d035dbd7057a","location":"st0cv4-87ee3","num":4,"shopType":{"childs":[],"decorationFlag":0,"id":"0","imgUrl":"http://gxct-limitless-wifi-zw.oss-cn-shenzhen.aliyuncs.com/1507687979143aacfef.png","location":"st0cv4","num":0,"title":"餐饮美食","uid":221},"shopTypeId":"0","title":"广西菜系","uid":94},"status":1,"sumCollection":0,"sumMoney":0.01,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeId":"ea443b7fb84d44f2a188d035dbd7057a","uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","wifiFlag":0,"workingTimeDescription":"全天营业","workingTimeEnd":23,"workingTimeEndM":59,"workingTimeStart":0,"workingTimeStartM":0,"wxAccount":"wx"},"orderNumber":"4200000027201709305136468973","outTradeNo":"1da6abefaded4876843875c496a93f8e","payTime":"2017-09-30 13:14:55","payType":0,"price":0.01,"shopId":"testSHOP","status":1,"totalFee":0.01,"transportType":0,"uid":235,"ukId":"ue899dffd39c848a69ee3eb36aab15bf1"}
     * status : 0
     * userMsg : 太便宜了，便宜没好货
     * userName :
     * userPhone : 18078110256
     */

    private String createTime;
    private GoodsDetailsBean order;
    private int status;
    private String userMsg;
    private String userName;
    private String userPhone;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public GoodsDetailsBean getOrder() {
        return order;
    }

    public void setOrder(GoodsDetailsBean order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
