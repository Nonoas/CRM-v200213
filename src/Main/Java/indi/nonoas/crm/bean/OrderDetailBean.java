package indi.nonoas.crm.bean;

/**
 * @author : Nonoas
 * @time : 2020-08-04 14:46
 */
public class OrderDetailBean {
    private String order_id;
    private String goods_id;
    private int goods_amount;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(int goods_amount) {
        this.goods_amount = goods_amount;
    }
}
