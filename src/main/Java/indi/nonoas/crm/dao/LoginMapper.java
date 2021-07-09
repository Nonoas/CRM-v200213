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
     * 验证登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 成功返回bean，失败返回null
     */
    LoginDto verifySelect(@Param("id") String username, @Param("password") String password);
}
