package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.vo.OrderRecordVO;
import indi.nonoas.crm.dao.my_orm_dao.SqliteDao;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-21 21:31
 */
public class OrderRecordVODao extends SqliteDao<OrderRecordVO> {

    private static final String SELECT_ALL = "select * from order_record";

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
