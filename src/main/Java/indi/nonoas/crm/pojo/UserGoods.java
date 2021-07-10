package indi.nonoas.crm.pojo;

/**
 * 用户-商品
 *
 * @author : Nonoas
 * @time : 2020-08-07 14:03
 */
public class UserGoods {
    private String userId;
    private String goodsId;
    private int amount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
