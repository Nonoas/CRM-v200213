package indi.nonoas.crm.beans.vo;


public class UsrOdrRecordVO {

  private String orderDate;
  private String userId;
  private String userName;
  private String goods;
  private long amount;
  private String transactor;


  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getGoods() {
    return goods;
  }

  public void setGoods(String goods) {
    this.goods = goods;
  }


  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }


  public String getTransactor() {
    return transactor;
  }

  public void setTransactor(String transactor) {
    this.transactor = transactor;
  }

}
