package indi.nonoas.crm.dao.my_orm_dao;

import indi.nonoas.crm.beans.UserGoods;

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
     * �����û���Ʒ
     *
     * @param ugoList ���Ѻ���û���Ʒ����
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