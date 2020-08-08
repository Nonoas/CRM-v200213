package indi.nonoas.crm.beans;

public class GoodsBean {
	/** 编号 */
	private String id;
	/** 名称 */
	private String name;
	/** 售价 */
	private double sellPrice;
	/** 进价 */
	private double purchasePrice;
	/** 数量 */
	private double quantity;
	/** 最低折扣 */
	private double minDiscount;
	/** 提成金额 */
	private double deduction;
	/** 提成比率 */
	private double deductionRate;
	/** 商品种类 */
	private String type;
	/**度量单位*/
	private String baseUnit;
	/**商品图片*/
	private String photo;

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
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

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getMinDiscount() {
		return minDiscount;
	}

	public void setMinDiscount(double minDiscount) {
		this.minDiscount = minDiscount;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getDeductionRate() {
		return deductionRate;
	}

	public void setDeductionRate(double deductionRate) {
		this.deductionRate = deductionRate;
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
