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
     * �����û�ID��ѯ�û���Ʒ���
     *
     * @param userID �û�ID
     * @return �û���Ʒ�б�
     */
    List<UserGoods> selectByUser(String userID);
}
