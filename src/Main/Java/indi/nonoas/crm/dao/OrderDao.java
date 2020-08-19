package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.*;
import per.nonoas.orm.AbstractTransaction;
import per.nonoas.orm.BeanTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-15 13:46
 */
public class OrderDao extends SqliteDao<OrderBean> {

    private static final String INSERT_ORDER = "insert into order_info(order_id,user_id,datetime,price,transactor,integral_get,integral_cost,pay_mode) " +

            "values(#{order_id},#{user_id},#{datetime},#{price},#{transactor},#{integral_get},#{integral_cost},#{pay_mode})";

    private static final String INSERT_ORDER_DETAIL = "insert into order_details(order_id,product_id,product_amount) " +

            "values(#{order_id},#{product_id},#{product_amount})";

    private static final String REPLACE_USER_GOODS = "replace into user_goods(user_id,goods_id,amount) " +

            "values(#{user_id},#{goods_id},#{amount})";

    private static final String REDUCE_GOODS = "update goods_info set quantity=#{quantity} where id=#{id}";

    private static final String REDUCE_USER_BALANCE = "update user_info set "
            + "name=#{name},sex=#{sex},birthday=#{birthday},card_level=#{card_level},"
            + "balance=#{balance},frequency=#{frequency},"
            + "cumulative=#{cumulative},address=#{address},integral=#{integral},telephone=#{telephone},"
            + "idcard=#{idcard},career=#{career},email=#{email},other=#{other} " + "where id=#{id}";


    private OrderDao() {
    }

    private static final OrderDao INSTANCE = new OrderDao();

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    /**
     * ��Ʒ�µ�����
     *
     * @param order        ����
     * @param orderDetails ��������
     * @param userGoods    ��Ҫ���µ� �û�-��Ʒ �б�
     * @param goodsBeans   ��Ʒ �б�
     * @param vipBean      �û�
     */
    public boolean placeGoodsOrder(OrderBean order, List<OrderDetailBean> orderDetails, List<UserGoods> userGoods, List<GoodsBean> goodsBeans, VipBean vipBean) {

        List<AbstractTransaction> transactions = new ArrayList<>();

        //�����ɢ��������������
        if (vipBean != VipBean.SANKE) {
            //�û�����
            transactions.add(new BeanTransaction(REDUCE_USER_BALANCE, vipBean));
            //�û�-��Ʒ����
            for (UserGoods ug : userGoods) {
                transactions.add(new BeanTransaction(REPLACE_USER_GOODS, ug));
            }
        }
        //��������
        transactions.add(new BeanTransaction(INSERT_ORDER, order));
        //������������
        for (OrderDetailBean orderDetail : orderDetails) {
            transactions.add(new BeanTransaction(INSERT_ORDER_DETAIL, orderDetail));
        }
        //��Ʒ����
        for (GoodsBean g : goodsBeans) {
            transactions.add(new BeanTransaction(REDUCE_GOODS, g));
        }
        return executeTransaction(transactions);
    }

    /**
     * ��Ʒ�µ�����
     *
     * @param order        ����
     * @param orderDetails ��������
     * @param userGoods    ��Ҫ���µ� �û�-��Ʒ �б�
     * @param goodsBeans   ��Ʒ �б�
     * @param vipBean      �û�
     */
    public boolean placePackageOrder(OrderBean order, List<OrderDetailBean> orderDetails, List<UserGoods> userGoods, List<GoodsBean> goodsBeans, VipBean vipBean) {

        List<AbstractTransaction> transactions = new ArrayList<>();

        //�����ɢ��������������
        if (vipBean != VipBean.SANKE) {
            //�û�����
            transactions.add(new BeanTransaction(REDUCE_USER_BALANCE, vipBean));
            //�û�-��Ʒ����
            for (UserGoods ug : userGoods) {
                transactions.add(new BeanTransaction(REPLACE_USER_GOODS, ug));
            }
        }
        //��������
        transactions.add(new BeanTransaction(INSERT_ORDER, order));
        //������������
        for (OrderDetailBean orderDetail : orderDetails) {
            transactions.add(new BeanTransaction(INSERT_ORDER_DETAIL, orderDetail));
        }
        //��Ʒ����
        for (GoodsBean g : goodsBeans) {
            transactions.add(new BeanTransaction(REDUCE_GOODS, g));
        }
        return executeTransaction(transactions);
    }

    @Override
    protected Class<OrderBean> getBeanClass() {
        return OrderBean.class;
    }
}
