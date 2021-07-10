package indi.nonoas.crm.dao.my_orm_dao;

import indi.nonoas.crm.pojo.UserGoodsOrderBean;

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
     * ����
     */
    private static volatile UserGoodsOrderDao INSTANCE;

    private UserGoodsOrderDao() {
    }

    /**
     * �������붩������
     *
     * @param orders �����б�
     */
    public void insertOrders(List<UserGoodsOrderBean> orders) {
        executeBatch(INSERT_INFOS, orders);
    }

    /**
     * ��ȡ����ʵ��
     *
     * @return ����
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
