package wlgx.com.kikis.bean;

import java.util.List;

/**
 * Created by lian on 2017/9/16.
 */
public class BankBean {


    /**
     * address : 南宁民族大道梦之岛百货
     * aliAccount : alipay
     * avgEvaluate : 5.3333
     * avgPrice : 230.0
     * backgroundUrl : http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038506953655bdac2.jpg
     * bankCardAccount : bank
     * browseCount : 9
     * browseCountDay : 0
     * cityId : 450100
     * couponCount : 0
     * createTime : 2017-08-09 16:33:22
     * decorationFlag : 0
     * homeFlag : 0
     * hotFlag : 0
     * id : testSHOP
     * info : 以一流的食材服务广大食客
     * latitude : 22.817071
     * level : 0
     * logoUrl : http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1503850695337554777.jpg
     * longitude : 108.369102
     * name : whoo后旗舰店
     * num : 0
     * phone : 5425000
     * provinceId : 450000
     * sellCount : 0
     * shopImgs : []
     * status : 1
     * sumCollection : 0
     * sumMoney : 10.0
     * sumMoneyDay : 0.0
     * sumOrderCountMonth : 0
     * typeId : 1
     * uid : -1
     * wifiEquipmentId : 0b24f5e86a174b4fbcea49fa1676b16b
     * workingTimeDescription : 全天营业
     * workingTimeEnd : 78070
     * workingTimeStart : 32560
     * wxAccount : wx
     */

    private String address;
    private String aliAccount;
    private double avgEvaluate;
    private double avgPrice;
    private String backgroundUrl;
    private String bankCardAccount;
    private int browseCount;
    private int browseCountDay;
    private String cityId;
    private int couponCount;
    private String createTime;
    private int decorationFlag;
    private int homeFlag;
    private int hotFlag;
    private String id;
    private String info;
    private double latitude;
    private int level;
    private String logoUrl;
    private double longitude;
    private String name;
    private int num;
    private String phone;
    private String provinceId;
    private int sellCount;
    private int status;
    private int sumCollection;
    private double sumMoney;
    private double sumMoneyDay;
    private int sumOrderCountMonth;
    private String typeId;
    private int uid;
    private String wifiEquipmentId;
    private String workingTimeDescription;
    private int workingTimeEnd;
    private int workingTimeStart;
    private String wxAccount;
    private List<shopImgs> shopImgs;
    private shopTypeBean shopType;

    public shopTypeBean getShopType() {
        return shopType;
    }

    public void setShopType(shopTypeBean shopType) {
        this.shopType = shopType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    /**
     * 开户行名称
     */
    private String bankName;
    /**
     * 开户人姓名
     */
    private String bankUserName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAliAccount() {
        return aliAccount;
    }

    public void setAliAccount(String aliAccount) {
        this.aliAccount = aliAccount;
    }

    public double getAvgEvaluate() {
        return avgEvaluate;
    }

    public void setAvgEvaluate(double avgEvaluate) {
        this.avgEvaluate = avgEvaluate;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getBankCardAccount() {
        return bankCardAccount;
    }

    public void setBankCardAccount(String bankCardAccount) {
        this.bankCardAccount = bankCardAccount;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public int getBrowseCountDay() {
        return browseCountDay;
    }

    public void setBrowseCountDay(int browseCountDay) {
        this.browseCountDay = browseCountDay;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDecorationFlag() {
        return decorationFlag;
    }

    public void setDecorationFlag(int decorationFlag) {
        this.decorationFlag = decorationFlag;
    }

    public int getHomeFlag() {
        return homeFlag;
    }

    public void setHomeFlag(int homeFlag) {
        this.homeFlag = homeFlag;
    }

    public int getHotFlag() {
        return hotFlag;
    }

    public void setHotFlag(int hotFlag) {
        this.hotFlag = hotFlag;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSumCollection() {
        return sumCollection;
    }

    public void setSumCollection(int sumCollection) {
        this.sumCollection = sumCollection;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public double getSumMoneyDay() {
        return sumMoneyDay;
    }

    public void setSumMoneyDay(double sumMoneyDay) {
        this.sumMoneyDay = sumMoneyDay;
    }

    public int getSumOrderCountMonth() {
        return sumOrderCountMonth;
    }

    public void setSumOrderCountMonth(int sumOrderCountMonth) {
        this.sumOrderCountMonth = sumOrderCountMonth;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getWifiEquipmentId() {
        return wifiEquipmentId;
    }

    public void setWifiEquipmentId(String wifiEquipmentId) {
        this.wifiEquipmentId = wifiEquipmentId;
    }

    public String getWorkingTimeDescription() {
        return workingTimeDescription;
    }

    public void setWorkingTimeDescription(String workingTimeDescription) {
        this.workingTimeDescription = workingTimeDescription;
    }

    public int getWorkingTimeEnd() {
        return workingTimeEnd;
    }

    public void setWorkingTimeEnd(int workingTimeEnd) {
        this.workingTimeEnd = workingTimeEnd;
    }

    public int getWorkingTimeStart() {
        return workingTimeStart;
    }

    public void setWorkingTimeStart(int workingTimeStart) {
        this.workingTimeStart = workingTimeStart;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public List<shopImgs> getShopImgs() {
        return shopImgs;
    }

    public void setShopImgs(List<shopImgs> shopImgs) {
        this.shopImgs = shopImgs;
    }
}
