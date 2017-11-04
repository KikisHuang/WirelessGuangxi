package wlgx.com.kikis.bean;

/**
 * Created by lian on 2017/9/16.
 */
public class shopTypeBean {

    /**
     * id : 1
     * imgUrl : http://s0.meituan.net/bs/?f=fe-web-meituan:img/banner/1.3.jpg@457b102
     * num : 1
     * title : 零售
     * uid : 1
     */

    private String id;
    private String imgUrl;
    private int num;
    private String title;
    private int uid;
    private String shopTypeId;

    public String getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(String shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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
