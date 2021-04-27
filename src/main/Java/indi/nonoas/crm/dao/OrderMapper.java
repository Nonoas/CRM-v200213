package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.OrderBean;
import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.vo.OrderRecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    void insertOrder(OrderBean order);

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

}
