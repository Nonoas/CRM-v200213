package indi.nonoas.crm.service;

import indi.nonoas.crm.beans.vo.UsrOdrRecordVO;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-01 22:47
 */
public interface UsrGdsOdrService {
    /**
     * 查询所有用户库存商品消费记录
     *
     * @return 消费记录列表
     */
    List<UsrOdrRecordVO> selectUserGoodsOrder();
}
