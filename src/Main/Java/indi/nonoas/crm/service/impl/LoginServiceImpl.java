package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.beans.LoginBean;
import indi.nonoas.crm.dao.LoginMapper;
import indi.nonoas.crm.service.LoginService;
import indi.nonoas.crm.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * @author : Nonoas
 * @time : 2020-09-01 13:04
 */
public class LoginServiceImpl implements LoginService {

    @Override
    public LoginBean verify(String username, String password) {
        SqlSession session = MybatisUtil.getSqlSession(true);
        LoginMapper mapper = session.getMapper(LoginMapper.class);
        LoginBean loginBean = mapper.verifySelect(username, password);
        session.close();
        return loginBean;
    }
}
