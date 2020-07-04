package indi.nonoas.crm.bean;

/**
 * 套餐项目类
 * 
 * @author Nonoas
 *
 */
public class PackageBean {
	/** 编号 */
	private String id;
	/** 名称 */
	private String name;
	/** 积分花费 */
	private int integral_cost;
	/** 金钱花费 */
	private double money_cost;
	/** 持续时间 */
	private String duration;
	/** 最低折扣 */
	private double min_discount;
	/** 备注信息 */
	private String other;

	public PackageBean() {
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

	public int getIntegral_cost() {
		return integral_cost;
	}

	public void setIntegral_cost(int integral_cost) {
		this.integral_cost = integral_cost;
	}

	public double getMoney_cost() {
		return money_cost;
	}

	public void setMoney_cost(double money_cost) {
		this.money_cost = money_cost;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public double getMin_discount() {
		return min_discount;
	}

	public void setMin_discount(double min_discount) {
		this.min_discount = min_discount;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return "PackageBean [id=" + id + ", name=" + name + ", integral_cost=" + integral_cost + ", money_cost="
				+ money_cost + ", duration=" + duration + ", min_discount=" + min_discount + ", other=" + other + "]";
	}

}
