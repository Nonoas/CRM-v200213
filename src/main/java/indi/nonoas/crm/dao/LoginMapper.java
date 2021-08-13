package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.LoginDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : Nonoas
 * @time : 2020-09-01 11:59
 */
@Repository
public interface LoginMapper {
    /**
     * ��֤��¼��Ϣ
     *
     * @param username �û���
     * @param password ����
     * @return �ɹ�����bean��ʧ�ܷ���null
     */
    LoginDto verifySelect(@Param("id") String username, @Param("password") String password);
}
