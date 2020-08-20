package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.UserGoods;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-07 18:26
 */
public class UserGoodsDao extends SqliteDao<UserGoods> {

    private static final String SELECT_BY_USER_GOODS = "select * from user_goods where user_id=#{user_id} " +
            "and goods_id=#{goods_id}";

    private static final String SELECT_BY_USER = "select * from user_goods where user_id=#{user_id}";

    private UserGoodsDao() {

    }

    private final static UserGoodsDao INSTANCE = new UserGoodsDao();

    public static UserGoodsDao getInstance() {
        return INSTANCE;
    }

    /**
     * �����û�ID����ƷID��ѯ
     *
     * @param userID  �û�ID
     * @param goodsID ��ƷID
     * @return �û�-��ƷBean��
     */
    public UserGoods selectByUserGoods(String userID, String goodsID) {
        return selectOne(SELECT_BY_USER_GOODS, userID, goodsID);
    }

    /**
     * �����û�ID��ѯ�û���Ʒ���
     *
     * @param userID �û�ID
     * @return �û���Ʒ�б�
     */
    public List<UserGoods> selectByUser(String userID) {
        return select(SELECT_BY_USER, userID);
    }

    @Override
    protected Class<UserGoods> getBeanClass() {
        return UserGoods.class;
    }


}
