package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.LoginBean;

/**
 * @author : Nonoas
 * @time : 2020-09-01 11:59
 */
public interface LoginMapperTest {
    /**
     * 验证登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 成功返回bean，失败返回null
     */
    LoginBean verify(String username, String password);
}
