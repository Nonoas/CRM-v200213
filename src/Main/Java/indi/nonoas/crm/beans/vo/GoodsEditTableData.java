package indi.nonoas.crm.beans.vo;

import java.beans.PropertyChangeSupport;

/**
 * 数据模型类，用于和PackageContentBean相互转换
 */
public class GoodsEditTableData {

    private String id;
    private String name;
    private double price;
    private int amount;
    private double sum_price;

    private final PropertyChangeSupport psc = new PropertyChangeSupport(this);

    public GoodsEditTableData() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public double getSum_price() {
        return sum_price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
        this.sum_price = price * amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.sum_price = price * amount;
    }

    @Override
    public String toString() {
        return "Data{" +
                "goods_id='" + id + '\'' +
                ", goods_name='" + name + '\'' +
                ", goods_price=" + price +
                ", goods_amount=" + amount +
                ", sum_price=" + sum_price +
                '}';
    }
}

