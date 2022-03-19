package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.UserGoods;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-02 17:08
 */
@Transactional
public interface UsrGdsService {
    /**
     * 根据用户ID查询用户商品余额
     *
     * @param userID 用户ID
     * @return 用户商品列表
     */
    List<UserGoods> selectByUser(String userID);

    UserGoods selectByUserGoods(String userId, String goodsId);


    void reduceGoods(List<UserGoods> ugoList);
}
