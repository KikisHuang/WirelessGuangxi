package wlgx.com.kikis.bean;

/**
 * Created by lian on 2017/9/21.
 */
public class AdminBean {


    /**
     * account : testSHOP-2
     * createDate : 2017-09-06 10:22:10
     * id : ada86e373dd11422ea7a6501e49dd5a2a
     * level : 2
     * mcShop : {"address":"广西壮族自治区南宁市青秀区建政街道万达广场(青秀店)","aliAccount":"alipay","avgEvaluate":5.3333,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038506953655bdac2.jpg","bankCardAccount":"bank","browseCount":12,"browseCountDay":1,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":0,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":1,"hotFlag":0,"id":"testSHOP","info":"以一流的食材服务广大食客","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1505889753795b92722.jpeg","longitude":108.369102,"name":"whoo后旗舰店","num":0,"parkingLotFlag":0,"phone":"5664","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"shopType":{"decorationFlag":0,"id":"0","imgUrl":"http://s0.meituan.net/bs/?f=fe-web-meituan:img/banner/1.jpg@457b102","location":"st0cv4","num":0,"title":"美食","uid":0},"status":1,"sumCollection":0,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeId":"0","uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","wifiFlag":0,"workingTimeDescription":"全天营业","workingTimeEnd":24,"workingTimeStart":0,"wxAccount":"wx"}
     * nickname : testSHOP-2
     * phone : 136077179162
     * shopId : testSHOP
     * status : 1
     * uid : 15
     * user : {"area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","balance":0,"balanceFrozen":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","headImgUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/150373430288640c761.gif","id":"1","idcard":"450111111","loginTime":"2017-09-13 14:20:51","name":"test11","phone":"13878141913","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sex":1,"status":true,"uid":1,"wxNumber":"456"}
     * userId : 1
     * wxOpenid : orXMFwMHpYZD4-qd-jcDKHUBHhVU
     */

    private String account;
    private String createDate;
    private String id;
    private int level;
    private McShopBean mcShop;
    private String nickname;
    private String phone;
    private String shopId;
    private int status;
    private int uid;
    private McUserBean user;
    private String userId;
    private String wxOpenid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public McShopBean getMcShop() {
        return mcShop;
    }

    public void setMcShop(McShopBean mcShop) {
        this.mcShop = mcShop;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public McUserBean getUser() {
        return user;
    }

    public void setUser(McUserBean user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }
}
