package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.vo.UsrOdrRecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-01 22:32
 */
@Repository
public interface OrderRecordMapper {
    /**
     * 查询所有用户库存商品消费记录
     *
     * @return 消费记录列表
     */
    List<UsrOdrRecordVO> selectUserGoodsOrder();
}
