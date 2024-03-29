package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.UserGoodsDto;
import indi.nonoas.crm.pojo.vo.UsrGdsOdrRecordVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-03 17:28
 */
@Transactional
public interface UsrGdsOdrService {
    /**
     * 查询所有用户库存商品消费记录
     *
     * @return 消费记录列表
     */
    List<UsrGdsOdrRecordVO> selectUserGoodsOrder();


    /**
     * 插入 用户-商品 订单信息
     * @param orders 用户-商品 订单信息
     */
    void insertOrders(List<UserGoodsDto> orders);
}
