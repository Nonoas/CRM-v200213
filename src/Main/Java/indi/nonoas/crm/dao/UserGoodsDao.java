package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.UserGoods;
import indi.nonoas.crm.dao.my_orm_dao.SqliteDao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-07 18:26
 */
@Deprecated
public class UserGoodsDao extends SqliteDao<UserGoods> {

    private static final String SELECT_BY_USER_GOODS = "select * from user_goods where user_id=#{user_id} " +
            "and goods_id=#{goods_id}";

    private static final String SELECT_BY_USER = "select * from user_goods where user_id=#{user_id}";

    private static final String UPDATE_GOODS_AMOUNT = "update user_goods " +
            "set amount=#{amount} where user_id=#{user_id} and goods_id=#{goods_id}";

    private static final String DELETE_NULL_DATA = "delete from user_goods where amount=0";

    private UserGoodsDao() {

    }

    private final static UserGoodsDao INSTANCE = new UserGoodsDao();

    public static UserGoodsDao getInstance() {
        return INSTANCE;
    }

    /**
     * 根据用户ID和商品ID查询
     *
     * @param userID  用户ID
     * @param goodsID 商品ID
     * @return 用户-商品Bean类
     */
    public UserGoods selectByUserGoods(String userID, String goodsID) {
        return selectOne(SELECT_BY_USER_GOODS, userID, goodsID);
    }

    /**
     * 消费用户商品
     *
     * @param ugoList 消费后的用户商品数据
     */
    public void reduceGoods(List<UserGoods> ugoList) {
        executeBatch(UPDATE_GOODS_AMOUNT, ugoList);
        try {
            execute(DELETE_NULL_DATA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户ID查询用户商品余额
     *
     * @param userID 用户ID
     * @return 用户商品列表
     */
    public List<UserGoods> selectByUser(String userID) {
        return select(SELECT_BY_USER, userID);
    }

    @Override
    protected Class<UserGoods> getBeanClass() {
        return UserGoods.class;
    }

}
