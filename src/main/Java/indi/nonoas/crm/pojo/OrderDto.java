package indi.nonoas.crm.pojo;

/**
 * @author : Nonoas
 * @time : 2020-08-04 14:39
 */
public class OrderDto {

    private String orderId;
    private String userId;
    private String datetime;
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

    public String getTransactor() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor;
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
}
