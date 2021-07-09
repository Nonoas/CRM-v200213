package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.pojo.LoginDto;
import indi.nonoas.crm.dao.LoginMapper;
import indi.nonoas.crm.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Nonoas
 * @time : 2020-09-01 13:04
 */
@Service("LoginServiceImpl")
public class LoginServiceImpl implements LoginService {

    private LoginMapper loginMapper;

    @Override
    public LoginDto verify(String username, String password) {
        return loginMapper.verifySelect(username, password);
    }

    @Autowired
    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }
}
