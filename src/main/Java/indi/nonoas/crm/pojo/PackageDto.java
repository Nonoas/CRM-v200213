package indi.nonoas.crm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * �ײ���Ŀ��
 *
 * @author Nonoas
 */
@TableName("package_info")
public class PackageDto {
    /**
     * ���
     */
    @TableId("id")
    private String id;
    /**
     * ����
     */
    private String name;
    /**
     * ���ֻ���
     */
    private int integralCost;
    /**
     * ��Ǯ����
     */
    private double moneyCost;
    /**
     * ����ۿ�
     */
    private double minDiscount;
    /**
     * ��Ŀ����
     */
    private String type;
    /**
     * ��ע��Ϣ
     */
    private String other;

    public PackageDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntegralCost() {
        return integralCost;
    }

    public void setIntegralCost(int integralCost) {
        this.integralCost = integralCost;
    }

    public double getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(double moneyCost) {
        this.moneyCost = moneyCost;
    }

    public double getMinDiscount() {
        return minDiscount;
    }

    public void setMinDiscount(double minDiscount) {
        this.minDiscount = minDiscount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "PackageBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", integral_cost=" + integralCost +
                ", money_cost=" + moneyCost +
                ", min_discount=" + minDiscount +
                ", type='" + type + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}
