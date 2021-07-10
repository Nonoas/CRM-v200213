package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.LoginDto;

/**
 * @author : Nonoas
 * @time : 2020-09-01 11:59
 */
public interface LoginMapperTest {
    /**
     * ��֤��¼��Ϣ
     *
     * @param username �û���
     * @param password ����
     * @return �ɹ�����bean��ʧ�ܷ���null
     */
    LoginDto verify(String username, String password);
}
