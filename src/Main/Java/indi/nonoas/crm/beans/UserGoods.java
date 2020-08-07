package indi.nonoas.crm.beans;

/**
 * 用户的服务类商品
 *
 * @author : Nonoas
 * @time : 2020-08-07 14:03
 */
public class UserGoods {
    private String user_id;
    private String goods_id;
    private int amount;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
