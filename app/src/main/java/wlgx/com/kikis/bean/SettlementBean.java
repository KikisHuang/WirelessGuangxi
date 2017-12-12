package wlgx.com.kikis.bean;

/**
 * Created by lian on 2017/11/30.
 */
public class SettlementBean {

    /**
     * id : 71a32254a69848708e4e27bec6ae37d0
     * outTradeNo : 71a32254a69848708e4e27bec6ae37d0
     * payTime : 2017-11-22 11:02:11
     * payType : 0
     * totalFee : 0.01
     */

    private String id;
    private String outTradeNo;
    private String payTime;
    private int payType;
    private double totalFee;
    /**
     * adminName : 超级管理员test
     * createTime : 2017-11-30 11:52:33
     * settlementPrice : 3.49
     */

    private String adminName;
    private String createTime;
    private double settlementPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(double settlementPrice) {
        this.settlementPrice = settlementPrice;
    }
}
