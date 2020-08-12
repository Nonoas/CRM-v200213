package indi.nonoas.crm.beans;

import java.math.BigDecimal;

/**
 * @author : Nonoas
 * @time : 2020-08-04 14:39
 */
public class OrderBean {

    private String orderId;
    private String userId;
    private String datetime;
    private BigDecimal price;
    private int integral_cost;
    private int integral_get;
    private String transactor;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTransactor() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor;
    }

    public int getIntegral_cost() {
        return integral_cost;
    }

    public void setIntegral_cost(int integral_cost) {
        this.integral_cost = integral_cost;
    }

    public int getIntegral_get() {
        return integral_get;
    }

    public void setIntegral_get(int integral_get) {
        this.integral_get = integral_get;
    }
}
