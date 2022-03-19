package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.OrderDto;
import indi.nonoas.crm.pojo.vo.OrderRecordVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author : Nonoas
 * @time : 2020-09-01 22:32
 */
@Repository
public interface OrderMapper {

    /**
     * 查询所有商品订单
     *
     * @return 商品订单列表
     */
    List<OrderRecordVO> selectGdsOrds();

    /**
     * 插入一条订单
     *
     * @param order 订单pojo
     */
    void insertOrder(OrderDto order);

    /**
     * 删除一年前的订单记录
     */
    void delete365dAgo();

    /**
     * 批量插入订单详情
     *
     * @param orderDetails 订单详情集合
     * @return 成功：true
     */
    boolean insertOrderDetails(@Param("orderDetails") List<OrderDetailBean> orderDetails);


    @Select("select * from ORDER_DETAILS where ORDER_ID=#{orderId}")
    List<OrderDetailBean> selectByOrder(String orderId);
}
