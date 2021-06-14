package indi.nonoas.crm.pojo;

/**
 * 项目内容信息Bean
 *
 * @author Nonoas
 */
public class PackageContentDto {

    /**
     * 项目编号
     */
    private String pkgId;
    /**
     * 商品编号
     */
    private String goodsId;
    /**
     * 商品数量
     */
    private int goodsAmount;


    public String getPkgId() {
        return pkgId;
    }

    public void setPkgId(String pkgId) {
        this.pkgId = pkgId;
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

    @Override
    public String toString() {
        return "PackageContentBean{" +
                "pkg_id='" + pkgId + '\'' +
                ", goods_id='" + goodsId + '\'' +
                ", goods_amount=" + goodsAmount +
                '}';
    }
}
