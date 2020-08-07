package indi.nonoas.crm.beans;

import java.math.BigDecimal;

/**
 * @author : Nonoas
 * @time : 2020-08-04 14:39
 */
public class OrderBean {
    private String order_id;
    private String user_id;
    private String datetime;
    private BigDecimal price;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
