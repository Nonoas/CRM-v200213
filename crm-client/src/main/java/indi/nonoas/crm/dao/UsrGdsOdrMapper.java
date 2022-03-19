package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.UserGoodsDto;
import indi.nonoas.crm.pojo.vo.UsrGdsOdrRecordVO;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author : Nonoas
 * @time : 2020-09-03 16:47
 */
@Repository
public interface UsrGdsOdrMapper {

    List<UsrGdsOdrRecordVO> selectUserGoodsOrder();

    /**
     * 插入 用户-商品 订单信息
     * @param orders 用户-商品 订单信息
     */
     void insertOrders(List<UserGoodsDto> orders);
}
