package indi.nonoas.crm.pojo;

/**
 * ��Ŀ������ϢBean
 *
 * @author Nonoas
 */
public class PackageContentDto {

    /**
     * ��Ŀ���
     */
    private String pkgId;
    /**
     * ��Ʒ���
     */
    private String goodsId;
    /**
     * ��Ʒ����
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
