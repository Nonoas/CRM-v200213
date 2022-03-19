package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.OrderDetailBean;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author : Nonoas
 * @time : 2021-07-13 15:59
 */
@Repository
public interface OrderDetailMapper {

    @Select("select * from order_details where ORDER_ID=#{order_id}")
    List<OrderDetailBean> selectByOrder(String orderID);

}
