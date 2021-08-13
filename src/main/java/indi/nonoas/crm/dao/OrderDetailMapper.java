package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.OrderDetailBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-07-13 15:59
 */
@Repository
public interface OrderDetailMapper {

    @Select("select * from order_details where ORDER_ID=#{order_id}")
    List<OrderDetailBean> selectByOrder(String orderID);

}
