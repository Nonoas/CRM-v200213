package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.UserGoods;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
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
     * 根据用户ID和商品ID查询
     *
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @return 用户-商品Bean类
     */
    UserGoods selectByUserGoods(@Param("userId") String userId, @Param("goodsId") String goodsId);


    /**
     * 减少用户商品的数量
     *
     * @param userGoods 用户商品信息
     */
    void reduceGoods(UserGoods userGoods);


    @Delete("delete from USER_GOODS where USER_ID=#{userId} and GOODS_ID=#{goodsId}")
    void deleteById(@Param("userId") String userId, @Param("goodsId") String goodsId);


    @Insert("insert into USER_GOODS(user_id, goods_id, amount) VALUES ( #{userId},#{goodsId},#{amount} )")
    void insert(UserGoods userGoods);


}
