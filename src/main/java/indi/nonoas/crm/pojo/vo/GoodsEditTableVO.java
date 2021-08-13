package indi.nonoas.crm.pojo.vo;


public class GoodsEditTableVO {

    private String id;
    private String name;
    private double price;
    private int amount;
    private double sum_price;

    public GoodsEditTableVO() {
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

