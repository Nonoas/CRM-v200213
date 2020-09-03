package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.vo.UsrOdrRecordVO;
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
    //FIXME 需要移动到别的Mapper
    List<UsrOdrRecordVO> selectUserGoodsOrder();
}
