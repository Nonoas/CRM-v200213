package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.UserGoods;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-07 18:26
 */
public class UserGoodsDao extends MySqlDao<UserGoods> {

    private static final String SELECT_BY_USER = "select * from user_goods where id=#{id}";

    public List<UserGoods> selectByUser(String userID) {
        return select(SELECT_BY_USER, userID);
    }

    @Override
    protected Class<UserGoods> getBeanClass() {
        return UserGoods.class;
    }
}
