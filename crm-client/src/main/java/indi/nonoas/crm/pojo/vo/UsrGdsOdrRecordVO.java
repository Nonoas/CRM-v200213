package indi.nonoas.crm.pojo.vo;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class UsrGdsOdrRecordVO {

    private final SimpleStringProperty orderDate = new SimpleStringProperty();
    private final SimpleStringProperty userId = new SimpleStringProperty();
    private final SimpleStringProperty userName = new SimpleStringProperty();
    private final SimpleStringProperty goods = new SimpleStringProperty();
    private final SimpleLongProperty amount = new SimpleLongProperty();
    private final SimpleStringProperty transactor = new SimpleStringProperty();

    public String getOrderDate() {
        return orderDate.get();
    }

    public SimpleStringProperty orderDateProperty() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    public String getUserId() {
        return userId.get();
    }

    public SimpleStringProperty userIdProperty() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId.set(userId);
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getGoods() {
        return goods.get();
    }

    public SimpleStringProperty goodsProperty() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods.set(goods);
    }

    public long getAmount() {
        return amount.get();
    }

    public SimpleLongProperty amountProperty() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount.set(amount);
    }

    public String getTransactor() {
        return transactor.get();
    }

    public SimpleStringProperty transactorProperty() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor.set(transactor);
    }
}
