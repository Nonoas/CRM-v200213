package indi.nonoas.crm.beans;

public class GoodsBean {
	/** ��� */
	private String id;
	/** ���� */
	private String name;
	/** �ۼ� */
	private double sell_price;
	/** ���� */
	private double purchase_price;
	/** ���� */
	private double quantity;
	/** ����ۿ� */
	private double min_discount;
	/** ��ɽ�� */
	private double deduction;
	/** ��ɱ��� */
	private double deduction_rate;
	/** ��Ʒ���� */
	private String type;
	/**������λ*/
	private String base_unit;
	/**��ƷͼƬ*/
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
