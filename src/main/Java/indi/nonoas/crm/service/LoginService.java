package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.LoginDto;

/**
 * @author : Nonoas
 * @time : 2020-09-01 12:56
 */
public interface LoginService {
    /**
     * ��֤��¼��Ϣ
     *
     * @param username �û���
     * @param password ����
     * @return �ɹ�����bean��ʧ�ܷ���null
     */
    LoginDto verify(String username, String password);
}
