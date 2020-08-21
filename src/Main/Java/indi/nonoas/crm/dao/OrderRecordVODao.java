package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.vo.OrderRecordVO;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-21 21:31
 */
public class OrderRecordVODao extends SqliteDao<OrderRecordVO> {

    private static final String SELECT_ALL = "select oi.order_id,oi.user_id, ui.name user_name,oi.datetime, oi.price,oi.integral_cost," +
            "oi.integral_get,oi.transactor, oi.pay_mode " +
            "from order_info oi, user_info ui " +
            "where ui.id = oi.user_id";

    private static final OrderRecordVODao INSTANCE = new OrderRecordVODao();

    private OrderRecordVODao() {
    }

    public static OrderRecordVODao getInstance() {
        return INSTANCE;
    }

    public List<OrderRecordVO> selectAll() {
        return select(SELECT_ALL);
    }


    @Override
    protected Class<OrderRecordVO> getBeanClass() {
        return OrderRecordVO.class;
    }
}
