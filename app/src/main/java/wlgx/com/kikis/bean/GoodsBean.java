package wlgx.com.kikis.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lian on 2017/9/14.
 */
public class GoodsBean {

    /**
     * area : {"city":"南宁市","id":"450103","name":"青秀区"}
     * areaId : 450103
     * browseCount : 0
     * city : {"id":"450100","name":"南宁市","province":"广西壮族自治区"}
     * cityId : 450100
     * commentCount : 0
     * createTime : 2017-08-09 19:22:39
     * freight : 20.0
     * goodsTypeId : test
     * hotFlag : 1
     * id : c1bfbc9c039343cb83277bfd5f8c4a09
     * keyword : 护肤液
     * logoUrl : http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038511310315e975e.jpg
     * num : 4
     * price : 1080.0
     * province : {"id":"450000","name":"广西壮族自治区"}
     * provinceId : 450000
     * sellCount : 0
     * shop : {"address":"南宁民族大道梦之岛百货","aliAccount":"alipay","avgEvaluate":4.7778,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/15038506953655bdac2.jpg","bankCardAccount":"bank","browseCount":9,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":0,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":0,"hotFlag":0,"id":"testSHOP","info":"以一流的食材服务广大食客","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1503850695337554777.jpg","longitude":108.369102,"name":"whoo后旗舰店","num":0,"phone":"5425000","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"status":1,"sumCollection":0,"sumMoney":0,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeFlag":0,"uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","workingTimeDescription":"全天营业","workingTimeEnd":24,"workingTimeStart":0,"wxAccount":"wx"}
     * shopId : testSHOP
     * status : 1
     * stock : 100
     * sumCollection : 0
     * title : Whoo后 天气丹花献水乳护肤套装
     * uid : 3
     */

    private AreaBean area;
    private String areaId;
    private int browseCount;
    private CityBean city;
    private String cityId;
    private int commentCount;
    private String createTime;
    private double freight;
    private String goodsTypeId;
    private int hotFlag;
    private String id;
    private String keyword;
    private String logoUrl;
    private int num;
    private double price;
    private String provinceId;
    private int sellCount;
    private ShopBean shop;
    private String shopId;
    private int status;
    private int stock;
    private int sumCollection;
    private String title;
    private int uid;
    private GoodsType goodsType;
    private String content;
    /**
     * shop : {"address":"广西壮族自治区南宁市青秀区中山街道七星路一巷131号","aliAccount":"alipay","area":{"city":"南宁市","id":"450103","name":"青秀区"},"areaId":"450103","avgEvaluate":5.3333,"avgPrice":230,"backgroundUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075463966d5c.gif","bankCardAccount":"bank","bankName":"laojj","bankUserName":"j","browseCount":14,"browseCountDay":0,"city":{"id":"450100","name":"南宁市","province":"广西壮族自治区"},"cityId":"450100","couponCount":0,"createTime":"2017-08-09 16:33:22","decorationFlag":0,"homeFlag":1,"hotFlag":1,"id":"testSHOP","info":"品牌于韩国上市，源自宫廷的独秘配方","latitude":22.817071,"level":0,"logoUrl":"http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075437231d01.png","longitude":108.369102,"name":"whoo后旗舰店","num":-2,"parkingLotFlag":0,"phone":"07715698526","province":{"id":"450000","name":"广西壮族自治区"},"provinceId":"450000","sellCount":0,"shopImgs":[],"shopType":{"childs":[],"decorationFlag":0,"id":"4ba6cb200a704bc0a2cbf71220af047c","location":"st126a84-12893d","num":99999,"shopType":{"childs":[],"decorationFlag":1,"id":"6dc09b8c237849fbab25b2f6c2ccde31","imgUrl":"http://limitless-wifi-zw.oss-cn-hangzhou.aliyuncs.com/15064397784083bff75.png","location":"st126a84","num":7,"title":"零售商铺","uid":134},"shopTypeId":"6dc09b8c237849fbab25b2f6c2ccde31","title":"其他","uid":136},"status":1,"sumCollection":0,"sumMoney":0.01,"sumMoneyDay":0,"sumOrderCountMonth":0,"typeId":"4ba6cb200a704bc0a2cbf71220af047c","uid":-1,"wifiEquipmentId":"0b24f5e86a174b4fbcea49fa1676b16b","wifiFlag":0,"workingTimeDescription":"全天营业","workingTimeEnd":23,"workingTimeEndM":59,"workingTimeStart":0,"workingTimeStartM":0,"wxAccount":"wx"}
     */

    @SerializedName("shop")
    private ShopBean shopX;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

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

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSumCollection() {
        return sumCollection;
    }

    public void setSumCollection(int sumCollection) {
        this.sumCollection = sumCollection;
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

    public ShopBean getShopX() {
        return shopX;
    }

    public void setShopX(ShopBean shopX) {
        this.shopX = shopX;
    }

    public static class ShopBean {
        /**
         * address : 广西壮族自治区南宁市青秀区中山街道七星路一巷131号
         * aliAccount : alipay
         * area : {"city":"南宁市","id":"450103","name":"青秀区"}
         * areaId : 450103
         * avgEvaluate : 5.3333
         * avgPrice : 230.0
         * backgroundUrl : http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075463966d5c.gif
         * bankCardAccount : bank
         * bankName : laojj
         * bankUserName : j
         * browseCount : 14
         * browseCountDay : 0
         * city : {"id":"450100","name":"南宁市","province":"广西壮族自治区"}
         * cityId : 450100
         * couponCount : 0
         * createTime : 2017-08-09 16:33:22
         * decorationFlag : 0
         * homeFlag : 1
         * hotFlag : 1
         * id : testSHOP
         * info : 品牌于韩国上市，源自宫廷的独秘配方
         * latitude : 22.817071
         * level : 0
         * logoUrl : http://limitless-wifi-info.oss-cn-hangzhou.aliyuncs.com/1506680075437231d01.png
         * longitude : 108.369102
         * name : whoo后旗舰店
         * num : -2
         * parkingLotFlag : 0
         * phone : 07715698526
         * province : {"id":"450000","name":"广西壮族自治区"}
         * provinceId : 450000
         * sellCount : 0
         * shopImgs : []
         * shopType : {"childs":[],"decorationFlag":0,"id":"4ba6cb200a704bc0a2cbf71220af047c","location":"st126a84-12893d","num":99999,"shopType":{"childs":[],"decorationFlag":1,"id":"6dc09b8c237849fbab25b2f6c2ccde31","imgUrl":"http://limitless-wifi-zw.oss-cn-hangzhou.aliyuncs.com/15064397784083bff75.png","location":"st126a84","num":7,"title":"零售商铺","uid":134},"shopTypeId":"6dc09b8c237849fbab25b2f6c2ccde31","title":"其他","uid":136}
         * status : 1
         * sumCollection : 0
         * sumMoney : 0.01
         * sumMoneyDay : 0.0
         * sumOrderCountMonth : 0
         * typeId : 4ba6cb200a704bc0a2cbf71220af047c
         * uid : -1
         * wifiEquipmentId : 0b24f5e86a174b4fbcea49fa1676b16b
         * wifiFlag : 0
         * workingTimeDescription : 全天营业
         * workingTimeEnd : 23
         * workingTimeEndM : 59
         * workingTimeStart : 0
         * workingTimeStartM : 0
         * wxAccount : wx
         */

        private String address;
        private String aliAccount;
        @SerializedName("area")
        private AreaBean areaX;
        @SerializedName("areaId")
        private String areaIdX;
        private double avgEvaluate;
        private double avgPrice;
        private String backgroundUrl;
        private String bankCardAccount;
        private String bankName;
        private String bankUserName;
        @SerializedName("browseCount")
        private int browseCountX;
        private int browseCountDay;
        @SerializedName("city")
        private CityBean cityX;
        @SerializedName("cityId")
        private String cityIdX;
        private int couponCount;
        @SerializedName("createTime")
        private String createTimeX;
        private int decorationFlag;
        private int homeFlag;
        @SerializedName("hotFlag")
        private int hotFlagX;
        @SerializedName("id")
        private String idX;
        private String info;
        private double latitude;
        private int level;
        @SerializedName("logoUrl")
        private String logoUrlX;
        private double longitude;
        private String name;
        @SerializedName("num")
        private int numX;
        private int parkingLotFlag;
        private String phone;
        private CityBean province;
        @SerializedName("provinceId")
        private String provinceIdX;
        @SerializedName("sellCount")
        private int sellCountX;
        private ShopTypeBeanX shopType;
        @SerializedName("status")
        private int statusX;
        @SerializedName("sumCollection")
        private int sumCollectionX;
        private double sumMoney;
        private double sumMoneyDay;
        private int sumOrderCountMonth;
        private String typeId;
        @SerializedName("uid")
        private int uidX;
        private String wifiEquipmentId;
        private int wifiFlag;
        private String workingTimeDescription;
        private int workingTimeEnd;
        private int workingTimeEndM;
        private int workingTimeStart;
        private int workingTimeStartM;
        private String wxAccount;
        private List<?> shopImgs;

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

        public AreaBean getAreaX() {
            return areaX;
        }

        public void setAreaX(AreaBean areaX) {
            this.areaX = areaX;
        }

        public String getAreaIdX() {
            return areaIdX;
        }

        public void setAreaIdX(String areaIdX) {
            this.areaIdX = areaIdX;
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

        public int getBrowseCountX() {
            return browseCountX;
        }

        public void setBrowseCountX(int browseCountX) {
            this.browseCountX = browseCountX;
        }

        public int getBrowseCountDay() {
            return browseCountDay;
        }

        public void setBrowseCountDay(int browseCountDay) {
            this.browseCountDay = browseCountDay;
        }

        public CityBean getCityX() {
            return cityX;
        }

        public void setCityX(CityBean cityX) {
            this.cityX = cityX;
        }

        public String getCityIdX() {
            return cityIdX;
        }

        public void setCityIdX(String cityIdX) {
            this.cityIdX = cityIdX;
        }

        public int getCouponCount() {
            return couponCount;
        }

        public void setCouponCount(int couponCount) {
            this.couponCount = couponCount;
        }

        public String getCreateTimeX() {
            return createTimeX;
        }

        public void setCreateTimeX(String createTimeX) {
            this.createTimeX = createTimeX;
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

        public int getHotFlagX() {
            return hotFlagX;
        }

        public void setHotFlagX(int hotFlagX) {
            this.hotFlagX = hotFlagX;
        }

        public String getIdX() {
            return idX;
        }

        public void setIdX(String idX) {
            this.idX = idX;
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

        public String getLogoUrlX() {
            return logoUrlX;
        }

        public void setLogoUrlX(String logoUrlX) {
            this.logoUrlX = logoUrlX;
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

        public int getNumX() {
            return numX;
        }

        public void setNumX(int numX) {
            this.numX = numX;
        }

        public int getParkingLotFlag() {
            return parkingLotFlag;
        }

        public void setParkingLotFlag(int parkingLotFlag) {
            this.parkingLotFlag = parkingLotFlag;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public CityBean getProvince() {
            return province;
        }

        public void setProvince(CityBean province) {
            this.province = province;
        }

        public String getProvinceIdX() {
            return provinceIdX;
        }

        public void setProvinceIdX(String provinceIdX) {
            this.provinceIdX = provinceIdX;
        }

        public int getSellCountX() {
            return sellCountX;
        }

        public void setSellCountX(int sellCountX) {
            this.sellCountX = sellCountX;
        }

        public ShopTypeBeanX getShopType() {
            return shopType;
        }

        public void setShopType(ShopTypeBeanX shopType) {
            this.shopType = shopType;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public int getSumCollectionX() {
            return sumCollectionX;
        }

        public void setSumCollectionX(int sumCollectionX) {
            this.sumCollectionX = sumCollectionX;
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

        public int getUidX() {
            return uidX;
        }

        public void setUidX(int uidX) {
            this.uidX = uidX;
        }

        public String getWifiEquipmentId() {
            return wifiEquipmentId;
        }

        public void setWifiEquipmentId(String wifiEquipmentId) {
            this.wifiEquipmentId = wifiEquipmentId;
        }

        public int getWifiFlag() {
            return wifiFlag;
        }

        public void setWifiFlag(int wifiFlag) {
            this.wifiFlag = wifiFlag;
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

        public int getWorkingTimeEndM() {
            return workingTimeEndM;
        }

        public void setWorkingTimeEndM(int workingTimeEndM) {
            this.workingTimeEndM = workingTimeEndM;
        }

        public int getWorkingTimeStart() {
            return workingTimeStart;
        }

        public void setWorkingTimeStart(int workingTimeStart) {
            this.workingTimeStart = workingTimeStart;
        }

        public int getWorkingTimeStartM() {
            return workingTimeStartM;
        }

        public void setWorkingTimeStartM(int workingTimeStartM) {
            this.workingTimeStartM = workingTimeStartM;
        }

        public String getWxAccount() {
            return wxAccount;
        }

        public void setWxAccount(String wxAccount) {
            this.wxAccount = wxAccount;
        }

        public List<?> getShopImgs() {
            return shopImgs;
        }

        public void setShopImgs(List<?> shopImgs) {
            this.shopImgs = shopImgs;
        }

        public static class ShopTypeBeanX {
            /**
             * childs : []
             * decorationFlag : 0
             * id : 4ba6cb200a704bc0a2cbf71220af047c
             * location : st126a84-12893d
             * num : 99999
             * shopType : {"childs":[],"decorationFlag":1,"id":"6dc09b8c237849fbab25b2f6c2ccde31","imgUrl":"http://limitless-wifi-zw.oss-cn-hangzhou.aliyuncs.com/15064397784083bff75.png","location":"st126a84","num":7,"title":"零售商铺","uid":134}
             * shopTypeId : 6dc09b8c237849fbab25b2f6c2ccde31
             * title : 其他
             * uid : 136
             */

            private int decorationFlag;
            @SerializedName("id")
            private String idX;
            private String location;
            @SerializedName("num")
            private int numX;
            private ShopTypeBean shopType;
            private String shopTypeId;
            @SerializedName("title")
            private String titleX;
            @SerializedName("uid")
            private int uidX;
            private List<?> childs;

            public int getDecorationFlag() {
                return decorationFlag;
            }

            public void setDecorationFlag(int decorationFlag) {
                this.decorationFlag = decorationFlag;
            }

            public String getIdX() {
                return idX;
            }

            public void setIdX(String idX) {
                this.idX = idX;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getNumX() {
                return numX;
            }

            public void setNumX(int numX) {
                this.numX = numX;
            }

            public ShopTypeBean getShopType() {
                return shopType;
            }

            public void setShopType(ShopTypeBean shopType) {
                this.shopType = shopType;
            }

            public String getShopTypeId() {
                return shopTypeId;
            }

            public void setShopTypeId(String shopTypeId) {
                this.shopTypeId = shopTypeId;
            }

            public String getTitleX() {
                return titleX;
            }

            public void setTitleX(String titleX) {
                this.titleX = titleX;
            }

            public int getUidX() {
                return uidX;
            }

            public void setUidX(int uidX) {
                this.uidX = uidX;
            }

            public List<?> getChilds() {
                return childs;
            }

            public void setChilds(List<?> childs) {
                this.childs = childs;
            }

            public static class ShopTypeBean {
                /**
                 * childs : []
                 * decorationFlag : 1
                 * id : 6dc09b8c237849fbab25b2f6c2ccde31
                 * imgUrl : http://limitless-wifi-zw.oss-cn-hangzhou.aliyuncs.com/15064397784083bff75.png
                 * location : st126a84
                 * num : 7
                 * title : 零售商铺
                 * uid : 134
                 */

                private int decorationFlag;
                @SerializedName("id")
                private String idX;
                private String imgUrl;
                private String location;
                @SerializedName("num")
                private int numX;
                @SerializedName("title")
                private String titleX;
                @SerializedName("uid")
                private int uidX;
                private List<?> childs;

                public int getDecorationFlag() {
                    return decorationFlag;
                }

                public void setDecorationFlag(int decorationFlag) {
                    this.decorationFlag = decorationFlag;
                }

                public String getIdX() {
                    return idX;
                }

                public void setIdX(String idX) {
                    this.idX = idX;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public int getNumX() {
                    return numX;
                }

                public void setNumX(int numX) {
                    this.numX = numX;
                }

                public String getTitleX() {
                    return titleX;
                }

                public void setTitleX(String titleX) {
                    this.titleX = titleX;
                }

                public int getUidX() {
                    return uidX;
                }

                public void setUidX(int uidX) {
                    this.uidX = uidX;
                }

                public List<?> getChilds() {
                    return childs;
                }

                public void setChilds(List<?> childs) {
                    this.childs = childs;
                }
            }
        }
    }
}
