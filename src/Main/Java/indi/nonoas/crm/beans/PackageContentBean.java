package indi.nonoas.crm.beans;

/**
 * ��Ŀ������ϢBean
 *
 * @author Nonoas
 */
public class PackageContentBean {

    /**
     * ��Ŀ���
     */
    private String pkg_id;
    /**
     * ��Ʒ���
     */
    private String goods_id;
    /**
     * ��Ʒ����
     */
    private int goods_amount;


    public String getPkg_id() {
        return pkg_id;
    }

    public void setPkg_id(String pkg_id) {
        this.pkg_id = pkg_id;
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

    @Override
    public String toString() {
        return "PackageContentBean{" +
                "pkg_id='" + pkg_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_amount=" + goods_amount +
                '}';
    }
}
