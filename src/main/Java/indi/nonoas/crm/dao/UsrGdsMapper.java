package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.UserGoods;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 批量插入替换某一列
     * @param userGoodsList 用户-商品集合
     * @return 成功：true
     */
     boolean replaceUserGoods(@Param("userGoodsList") List<UserGoods> userGoodsList);
}
