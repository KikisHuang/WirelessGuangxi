package wlgx.com.kikis.bean;

/**
 * Created by lian on 2017/9/14.
 */
public class McUserBean {
        /**
         * area : {"city":"南宁市","id":"450103","name":"青秀区"}
         * areaId : 450103
         * balance : 0.0
         * balanceFrozen : 0.0
         * city : {"id":"450100","name":"南宁市","province":"广西壮族自治区"}
         * cityId : 450100
         * headImgUrl : http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/150373430288640c761.gif
         * id : 1
         * idcard : 450111111
         * loginTime : 2017-09-13 14:20:51
         * name : test
         * phone : 13878141913
         * province : {"id":"450000","name":"广西壮族自治区"}
         * provinceId : 450000
         * sex : 1
         * status : true
         * uid : 1
         * wxNumber : 456
         */

        private AreaBean area;
        private String areaId;
        private double balance;
        private double balanceFrozen;
        private CityBean city;
        private String cityId;
        private String headImgUrl;
        private String id;
        private String idcard;
        private String loginTime;
        private String name;
        private String phone;
        private ProvinceBean province;
        private String provinceId;
        private int sex;
        private boolean status;
        private int uid;
        private String wxNumber;

        public AreaBean getArea() {
            return area;
        }

        public void setArea(AreaBean area) {
            this.area = area;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getBalanceFrozen() {
            return balanceFrozen;
        }

        public void setBalanceFrozen(double balanceFrozen) {
            this.balanceFrozen = balanceFrozen;
        }

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public ProvinceBean getProvince() {
            return province;
        }

        public void setProvince(ProvinceBean province) {
            this.province = province;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getWxNumber() {
            return wxNumber;
        }

        public void setWxNumber(String wxNumber) {
            this.wxNumber = wxNumber;
        }
}
