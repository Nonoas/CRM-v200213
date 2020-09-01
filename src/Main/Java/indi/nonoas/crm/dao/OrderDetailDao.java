package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.OrderDetailBean;
import indi.nonoas.crm.dao.my_orm_dao.SqliteDao;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-21 21:20
 */
public class OrderDetailDao extends SqliteDao<OrderDetailBean> {

    private static final String SELECT_BY_ORDER = "select * from order_details where order_id=#{order_id}";


    /**
     * 单例
     */
    private static volatile OrderDetailDao INSTANCE;

    private OrderDetailDao() {
    }

    public List<OrderDetailBean> selectByOrder(String orderID) {
        return select(SELECT_BY_ORDER, orderID);
    }

    /**
     * 获取单例实例
     *
     * @return 单例
     */
    public static OrderDetailDao getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderDetailDao.class) {
                if (INSTANCE == null)
                    INSTANCE = new OrderDetailDao();
            }
        }
        return INSTANCE;
    }


    @Override
    protected Class<OrderDetailBean> getBeanClass() {
        return OrderDetailBean.class;
    }
}
