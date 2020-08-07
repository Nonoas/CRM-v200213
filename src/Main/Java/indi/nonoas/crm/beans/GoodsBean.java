package indi.nonoas.crm.beans;

public class GoodsBean {
	/** 编号 */
	private String id;
	/** 名称 */
	private String name;
	/** 售价 */
	private double sell_price;
	/** 进价 */
	private double purchase_price;
	/** 数量 */
	private double quantity;
	/** 最低折扣 */
	private double min_discount;
	/** 提成金额 */
	private double deduction;
	/** 提成比率 */
	private double deduction_rate;
	/** 商品种类 */
	private String type;
	/**度量单位*/
	private String base_unit;
	/**商品图片*/
	private String photo;

	public String getBase_unit() {
		return base_unit;
	}

	public void setBase_unit(String base_unit) {
		this.base_unit = base_unit;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public GoodsBean() {
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

	public double getSell_price() {
		return sell_price;
	}

	public void setSell_price(double sell_price) {
		this.sell_price = sell_price;
	}

	public double getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(double purchase_price) {
		this.purchase_price = purchase_price;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getMin_discount() {
		return min_discount;
	}

	public void setMin_discount(double min_discount) {
		this.min_discount = min_discount;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getDeduction_rate() {
		return deduction_rate;
	}

	public void setDeduction_rate(double deduction_rate) {
		this.deduction_rate = deduction_rate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + getId() + ",name=" + getName() + ",type=" + getType() + "]";
	}

}
