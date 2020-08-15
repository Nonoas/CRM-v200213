package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.OrderBean;
import indi.nonoas.crm.beans.OrderDetailBean;
import per.nonoas.orm.AbstractTransaction;
import per.nonoas.orm.BeanTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-15 13:46
 */
public class OrderDao extends MySqlDao<OrderBean> {

    private static final String INSERT_ORDER = "insert into order_info(order_id,user_id,datetime,price,transactor) " +

            "values(#{order_id},#{user_id},#{datetime},#{price},#{transactor})";

    private static final String INSERT_ORDER_DETAIL = "insert into order_details(order_id,goods_id,goods_amount) " +

            "values(#{order_id},#{goods_id},#{goods_amount})";


    private OrderDao() {
    }

    private static final OrderDao INSTANCE = new OrderDao();

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    /**
     * 商品下单事务
     *
     * @param order        订单
     * @param orderDetails 订单详情
     */
    public boolean placeGoodsOrder(OrderBean order, List<OrderDetailBean> orderDetails) {
        List<AbstractTransaction> transactions = new ArrayList<>();
        transactions.add(new BeanTransaction(INSERT_ORDER, order));
        for (OrderDetailBean orderDetail : orderDetails) {
            transactions.add(new BeanTransaction(INSERT_ORDER_DETAIL, orderDetail));
        }
        return executeTransaction(transactions);
    }

    @Override
    protected Class<OrderBean> getBeanClass() {
        return OrderBean.class;
    }
}
