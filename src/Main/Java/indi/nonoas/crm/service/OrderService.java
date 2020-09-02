package indi.nonoas.crm.service;

import indi.nonoas.crm.beans.vo.OrderRecordVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-02 15:43
 */
@Transactional
public interface OrderService {

    /**
     * 查询所有商品订单
     * @return 商品订单列表
     */
    List<OrderRecordVO> selectGdsOrds();

    /**
     * 删除一年前的记录
     */
    void delete365dAgo();
}
