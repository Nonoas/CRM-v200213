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
}
