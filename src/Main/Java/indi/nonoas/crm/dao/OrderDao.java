package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.*;
import indi.nonoas.crm.dao.my_orm_dao.SqliteDao;
import per.nonoas.orm.AbstractTransaction;
import per.nonoas.orm.BeanTransaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-15 13:46
 */
public class OrderDao extends SqliteDao<OrderBean> {

    private static final String SELECT_ALL = "select * from order_info";

    private static final String INSERT_ORDER = "insert into order_info(order_id,user_id,datetime,price,transactor,integral_get,integral_cost,pay_mode) " +

            "values(#{order_id},#{user_id},#{datetime},#{price},#{transactor},#{integral_get},#{integral_cost},#{pay_mode})";

    private static final String INSERT_ORDER_DETAIL = "insert into order_details(order_id,product_id,product_amount) " +

            "values(#{order_id},#{product_id},#{product_amount})";

    private static final String REPLACE_USER_GOODS = "replace into user_goods(user_id,goods_id,amount) " +

            "values(#{user_id},#{goods_id},#{amount})";

    private static final String REDUCE_GOODS = "update goods_info set quantity=#{quantity} where id=#{id}";

    private static final String REDUCE_USER_BALANCE = "update user_info set "
            + "name=#{name},sex=#{sex},birthday=#{birthday},card_level=#{card_level},"
            + "balance=#{balance},"
            + "cumulative=#{cumulative},address=#{address},integral=#{integral},telephone=#{telephone},"
            + "idcard=#{idcard},career=#{career},email=#{email},other=#{other} "
            + "where id=#{id}";

    private static final String DELETE_OLD_DATA = "delete from order_info where date('now', '-365 day') >= date(datetime)";


    private OrderDao() {
    }

    private static final OrderDao INSTANCE = new OrderDao();

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    public List<OrderBean> selectAll() {
        return select(SELECT_ALL);
    }

    /**
     * 商品下单事务
     *
     * @param order        订单
     * @param orderDetails 订单详情
     * @param userGoods    需要更新的 用户-商品 列表
     * @param goodsBeans   商品 列表
     * @param vipBean      用户
     */
    public boolean placeGoodsOrder(OrderBean order, List<OrderDetailBean> orderDetails, List<UserGoods> userGoods, List<GoodsBean> goodsBeans, VipBean vipBean) {

        List<AbstractTransaction> transactions = new ArrayList<>();

        //如果是散客则不做以下事务
        if (vipBean != VipBean.SANKE) {
            //用户事务
            transactions.add(new BeanTransaction(REDUCE_USER_BALANCE, vipBean));
            //用户-商品事务
            for (UserGoods ug : userGoods) {
                transactions.add(new BeanTransaction(REPLACE_USER_GOODS, ug));
            }
        }
        //订单事务
        transactions.add(new BeanTransaction(INSERT_ORDER, order));
        //订单详情事务
        for (OrderDetailBean orderDetail : orderDetails) {
            transactions.add(new BeanTransaction(INSERT_ORDER_DETAIL, orderDetail));
        }
        //商品事务
        for (GoodsBean g : goodsBeans) {
            transactions.add(new BeanTransaction(REDUCE_GOODS, g));
        }
        return executeTransaction(transactions);
    }

    /**
     * 商品下单事务
     *
     * @param order        订单
     * @param orderDetails 订单详情
     * @param userGoods    需要更新的 用户-商品 列表
     * @param goodsBeans   商品 列表
     * @param vipBean      用户
     */
    public boolean placePackageOrder(OrderBean order, List<OrderDetailBean> orderDetails, List<UserGoods> userGoods, List<GoodsBean> goodsBeans, VipBean vipBean) {

        List<AbstractTransaction> transactions = new ArrayList<>();

        //如果是散客则不做以下事务
        if (vipBean != VipBean.SANKE) {
            //用户事务
            transactions.add(new BeanTransaction(REDUCE_USER_BALANCE, vipBean));
            //用户-商品事务
            for (UserGoods ug : userGoods) {
                transactions.add(new BeanTransaction(REPLACE_USER_GOODS, ug));
            }
        }
        //订单事务
        transactions.add(new BeanTransaction(INSERT_ORDER, order));
        //订单详情事务
        for (OrderDetailBean orderDetail : orderDetails) {
            transactions.add(new BeanTransaction(INSERT_ORDER_DETAIL, orderDetail));
        }
        //商品事务
        for (GoodsBean g : goodsBeans) {
            transactions.add(new BeanTransaction(REDUCE_GOODS, g));
        }
        return executeTransaction(transactions);
    }

    /**
     * 删除一年前的记录
     */
    public void delete365DaysAgo() {
        try {
            execute(DELETE_OLD_DATA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<OrderBean> getBeanClass() {
        return OrderBean.class;
    }
}
