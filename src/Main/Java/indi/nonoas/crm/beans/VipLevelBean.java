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
	private double yuan_per_integral;
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

	public double getYuan_per_integral() {
		return yuan_per_integral;
	}

	public void setYuan_per_integral(double yuan_per_integral) {
		this.yuan_per_integral = yuan_per_integral;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "VipLevelBean [id=" + id + ", name=" + name + ", yuan_per_integral=" + yuan_per_integral + ", discount="
				+ discount + "]";
	}

}
