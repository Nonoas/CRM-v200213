package indi.nonoas.crm.beans;

/**
 * @author : Nonoas
 * @time : 2020-08-04 14:46
 */
public class OrderDetailBean {
    private String orderId;
    private String goodsId;
    private int goodsAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(int goodsAmount) {
        this.goodsAmount = goodsAmount;
    }
}
