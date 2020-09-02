package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.OrderBean;
import indi.nonoas.crm.beans.vo.OrderRecordVO;
import indi.nonoas.crm.beans.vo.UsrOdrRecordVO;
import indi.nonoas.crm.service.UsrGdsOdrService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-01 22:32
 */
@Repository
public interface OrderMapper {
    /**
     * 查询所有用户库存商品消费记录
     *
     * @return 消费记录列表
     */
    //FIXME 需要移动到别的Mapper
    List<UsrOdrRecordVO> selectUserGoodsOrder();

    /**
     * 查询所有商品订单
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


}
