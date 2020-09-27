package indi.nonoas.crm.beans;

/**
 * @author : Nonoas
 * @time : 2020-08-04 14:46
 */
public class OrderDetailBean {
    private String orderId;
    private String productId;
    private int productAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }
}
