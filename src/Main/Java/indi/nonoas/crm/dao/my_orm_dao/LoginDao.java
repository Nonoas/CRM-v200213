package indi.nonoas.crm.dao.my_orm_dao;

import indi.nonoas.crm.beans.LoginBean;

public class LoginDao extends SqliteDao<LoginBean> {

    private static final String VERIFY = "select * from login_info where id=#{id} and password=#{password}";

    /**
     * 验证登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 成功返回bean，失败返回null
     */
    public LoginBean verify(String username, String password) {
        return selectOne(VERIFY, username, password);
    }

    @Override
    protected Class<LoginBean> getBeanClass() {
        return LoginBean.class;
    }

}
