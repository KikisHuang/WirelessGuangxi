package wlgx.com.kikis.bean;

/**
 * Created by lian on 2017/9/15.
 */
public class ChartsBean {
    /**
     * browseShop : 6
     * month : 8
     * year : 2017
     * totalFee : 0.0
     */

    private int browseShop;
    private String month;
    private String year;
    private double totalFee;

    public int getBrowseShop() {
        return browseShop;
    }

    public void setBrowseShop(int browseShop) {
        this.browseShop = browseShop;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }
}
