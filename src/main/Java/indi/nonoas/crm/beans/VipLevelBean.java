package indi.nonoas.crm.beans;

/**
 * 会员等级bean
 * 
 * @author Nonoas
 *
 */
public class VipLevelBean {

	/** 编号 */
	private String id;
	/** 名称 */
	private String name;
	/** 每积分需要消费多少元 */
	private double yuanPerIntegral;
	/** 折扣 */
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
