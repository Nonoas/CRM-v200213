package indi.nonoas.crm.beans;

/**
 * ��Ա�ȼ�bean
 * 
 * @author Nonoas
 *
 */
public class VipLevelBean {

	/** ��� */
	private String id;
	/** ���� */
	private String name;
	/** ÿ������Ҫ���Ѷ���Ԫ */
	private double yuanPerIntegral;
	/** �ۿ� */
	private double discount;

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

	public double getYuanPerIntegral() {
		return yuanPerIntegral;
	}

	public void setYuanPerIntegral(double yuanPerIntegral) {
		this.yuanPerIntegral = yuanPerIntegral;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "VipLevelBean [id=" + id + ", name=" + name + ", yuan_per_integral=" + yuanPerIntegral + ", discount="
				+ discount + "]";
	}

}
