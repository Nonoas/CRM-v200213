package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.LoginDto;

/**
 * @author : Nonoas
 * @time : 2020-09-01 12:56
 */
public interface LoginService {
    /**
     * 验证登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 成功返回bean，失败返回null
     */
    LoginDto verify(String username, String password);
}
