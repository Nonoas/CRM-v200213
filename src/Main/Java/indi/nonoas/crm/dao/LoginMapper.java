package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.LoginBean;
import org.apache.ibatis.annotations.Param;

/**
 * @author : Nonoas
 * @time : 2020-09-01 11:59
 */
public interface LoginMapper {
    /**
     * ��֤��¼��Ϣ
     *
     * @param username �û���
     * @param password ����
     * @return �ɹ�����bean��ʧ�ܷ���null
     */
    LoginBean verifySelect(@Param("id") String username, @Param("password") String password);
}
