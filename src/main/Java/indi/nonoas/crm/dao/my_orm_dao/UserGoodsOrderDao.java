package indi.nonoas.crm.dao.my_orm_dao;

import indi.nonoas.crm.beans.UserGoodsOrderBean;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-21 14:15
 */
public class UserGoodsOrderDao extends SqliteDao<UserGoodsOrderBean> {
    private static final String INSERT_INFOS = "insert into user_goods_order" +
            "(user_id,goods_id,amount,order_date,transactor) " +
            "values(#{user_id},#{goods_id},#{amount},#{order_date},#{transactor})";

    /**
     * 单例
     */
    private static volatile UserGoodsOrderDao INSTANCE;

    private UserGoodsOrderDao() {
    }

    /**
     * 批量插入订单内容
     *
     * @param orders 订单列表
     */
    public void insertOrders(List<UserGoodsOrderBean> orders) {
        executeBatch(INSERT_INFOS, orders);
    }

    /**
     * 获取单例实例
     *
     * @return 单例
     */
    public static UserGoodsOrderDao getInstance() {
        if (INSTANCE == null) {
            synchronized (UserGoodsOrderDao.class) {
                if (INSTANCE == null)
                    INSTANCE = new UserGoodsOrderDao();
            }
        }
        return INSTANCE;
    }

    @Override
    protected Class<UserGoodsOrderBean> getBeanClass() {
        return UserGoodsOrderBean.class;
    }
}
