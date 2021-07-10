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
     * �����û�ID��ѯ�û���Ʒ���
     *
     * @param userID �û�ID
     * @return �û���Ʒ�б�
     */
    List<UserGoods> selectByUser(String userID);

    /**
     * �����û�ID����ƷID��ѯ
     *
     * @param userId  �û�ID
     * @param goodsId ��ƷID
     * @return �û�-��ƷBean��
     */
    UserGoods selectByUserGoods(@Param("userId") String userId, @Param("goodsId") String goodsId);

    /**
     * 进价�滻ĳһ��
     *
     * @param userGoodsList �û�-��Ʒ����
     * @return �ɹ���true
     */
    boolean replaceUserGoods(@Param("userGoodsList") List<UserGoods> userGoodsList);


}
