package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.LoginBean;
import org.apache.ibatis.annotations.Param;

/**
 * @author : Nonoas
 * @time : 2020-09-01 11:59
 */
public interface LoginMapper {
    /**
     * 验证登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 成功返回bean，失败返回null
     */
    LoginBean verifySelect(@Param("id") String username, @Param("password") String password);
}
