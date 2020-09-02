package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.UserGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-02 16:47
 */
@Repository
public interface UsrGdsMapper {
    /**
     * 根据用户ID查询用户商品余额
     *
     * @param userID 用户ID
     * @return 用户商品列表
     */
     List<UserGoods> selectByUser(String userID);
}
