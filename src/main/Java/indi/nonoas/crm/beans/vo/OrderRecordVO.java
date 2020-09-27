package indi.nonoas.crm.beans.vo;

/**
 * @author : Nonoas
 * @time : 2020-08-21 20:07
 */
public class OrderRecordVO {

    private String orderId;
    private String userId;
    private String userName;
    private String datetime;
    private String content;
    private double price;
    private int integralCost;
    private int integralGet;
    private String transactor;
    private String payMode;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIntegralCost() {
        return integralCost;
    }

    public void setIntegralCost(int integralCost) {
        this.integralCost = integralCost;
    }

    public int getIntegralGet() {
        return integralGet;
    }

    public void setIntegralGet(int integralGet) {
        this.integralGet = integralGet;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getTransactor() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
