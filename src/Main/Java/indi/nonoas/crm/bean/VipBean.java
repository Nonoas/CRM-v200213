package indi.nonoas.crm.bean;

import java.io.Serializable;

/**
 * 会员信息Bean
 *
 * @author Nonoas
 */
public class VipBean implements Serializable {
    /**
     * 串行版本
     */
    private static final long serialVersionUID = 1L;
    /**
     * 会员卡号
     */
    private String id;
    /**
     * 会员姓名
     */
    private String name;
    /**
     * 会员性别
     */
    private String sex;
    /**
     * 入会日期
     */
    private String admission_date;
    /**
     * 会员等级
     */
    private String card_level;
    /**
     * 积分
     */
    private int integral;
    /**
     * 消费金额
     */
    private double cumulative;
    /**
     * 卡内余额
     */
    private double balance;
    /**
     * 剩余次数
     */
    private int frequency;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 证件号码
     */
    private String idcard;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 单位职业
     */
    private String career;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 其他
     */
    private String other;
    /**
     * 照片
     */
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public VipBean() {

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdmission_date() {
        return admission_date;
    }

    public void setAdmission_date(String admission_date) {
        this.admission_date = admission_date;
    }

    public String getCard_level() {
        return card_level;
    }

    public void setCard_level(String card_level) {
        this.card_level = card_level;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public double getCumulative() {
        return cumulative;
    }

    public void setCumulative(double cumulative) {
        this.cumulative = cumulative;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + getId() + ",name=" + getName() + ",sex=" + getSex() + "]";
    }

}