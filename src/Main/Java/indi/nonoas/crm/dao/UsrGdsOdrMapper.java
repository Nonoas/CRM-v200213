package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.vo.UsrGdsOdrRecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-03 16:47
 */
@Repository
public interface UsrGdsOdrMapper {
    /**
     * 查询所有用户库存商品消费记录
     *
     * @return 消费记录列表
     */
    List<UsrGdsOdrRecordVO> selectUserGoodsOrder();
}
