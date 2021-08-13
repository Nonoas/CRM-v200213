package indi.nonoas.crm.pojo.dto;

public class GoodsDto {
    /**
     * ���
     */
    private String id;
    /**
     * ����
     */
    private String name;
    /**
     * �ۼ�
     */
    private double sellPrice;
    /**
     * ����
     */
    private double purchasePrice;
    /**
     * ����
     */
    private double quantity;
    /**
     * ����ۿ�
     */
    private double minDiscount;
    /**
     * ��ɽ��
     */
    private double deduction;
    /**
     * ��ɱ���
     */
    private double deductionRate;
    /**
     * ��Ʒ����
     */
    private String type;
    /**
     * ������λ
     */
    private String baseUnit;
    /**
     * ��ƷͼƬ
     */
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

    public GoodsDto() {
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
