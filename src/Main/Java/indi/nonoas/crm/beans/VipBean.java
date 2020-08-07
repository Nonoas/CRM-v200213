package indi.nonoas.crm.beans;

import java.io.Serializable;

/**
 * ��Ա��ϢBean
 *
 * @author Nonoas
 */
public class VipBean implements Serializable {
    /**
     * ���а汾
     */
    private static final long serialVersionUID = 1L;
    /**
     * ��Ա����
     */
    private String id;
    /**
     * ��Ա����
     */
    private String name;
    /**
     * ��Ա�Ա�
     */
    private String sex;
    /**
     * �������
     */
    private String admission_date;
    /**
     * ��Ա�ȼ�
     */
    private String card_level;
    /**
     * ����
     */
    private int integral;
    /**
     * ���ѽ��
     */
    private double cumulative;
    /**
     * �������
     */
    private double balance;
    /**
     * ʣ�����
     */
    private int frequency;
    /**
     * ��ϵ��ַ
     */
    private String address;
    /**
     * ��ϵ�绰
     */
    private String telephone;
    /**
     * ֤������
     */
    private String idcard;
    /**
     * ��������
     */
    private String birthday;
    /**
     * ��λְҵ
     */
    private String career;
    /**
     * �����ʼ�
     */
    private String email;
    /**
     * ����
     */
    private String other;
    /**
     * ��Ƭ
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