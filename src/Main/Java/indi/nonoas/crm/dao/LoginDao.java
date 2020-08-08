package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.LoginBean;

public class LoginDao extends MySqlDao<LoginBean> {
	
	private static final String VERIFY = "select * from login_info where id=#{id} and password=#{password}";

	/**
	 * ��֤��¼��Ϣ
	 * 
	 * @param username �û���
	 * @param password ����
	 * @return �ɹ�����bean��ʧ�ܷ���null
	 */
	public LoginBean verify(String username, String password) {
		return selectOne(VERIFY, username,password);
	}

	@Override
	protected Class<LoginBean> getBeanClass() {
		return LoginBean.class;
	}

}
