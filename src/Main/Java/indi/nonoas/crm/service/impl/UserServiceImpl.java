package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.beans.UserBean;
import indi.nonoas.crm.config.UserConfig;
import indi.nonoas.crm.dao.UserMapper;
import indi.nonoas.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author : Nonoas
 * @time : 2020-08-15 22:35
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    /**
     * 从配置文件取出最新会员卡号的下一位的36进制表示
     *
     * @return 36进制会员卡号
     */
    public String generateVipID() {
        String prefix;
        BigInteger bigInteger;
        synchronized (UserServiceImpl.class) {
            prefix = UserConfig.getPrefix();
            String lastID = UserConfig.getLastID();
            bigInteger = new BigInteger(lastID).add(new BigInteger("1"));
            UserConfig.setLastID(bigInteger.toString());
        }
        String suffix = bigInteger.toString(36).toUpperCase();
        return prefix + suffix;
    }

    @Override
    public UserBean getInfoByIdOrName(String id, String name) {
        return userMapper.getInfoByIdOrName(id, name);
    }

    @Override
    public ArrayList<UserBean> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public ArrayList<UserBean> selectByFiltrate(String id, String name, String card_level) {
        return userMapper.selectByFiltrate(id, name, card_level);
    }

    @Override
    public ArrayList<UserBean> selectByDateFiltrate(String id, String name, String level, String dateFrom, String dateTo) {
        return userMapper.selectByDateFiltrate(id, name, level, dateFrom, dateTo);
    }

    @Override
    public void insertInfo(UserBean vipBean) {
        userMapper.insertInfo(vipBean);
    }

    @Override
    public void deleteByID(String id) {
        userMapper.deleteByID(id);
    }

    @Override
    public void updateInfo(UserBean vipBean) {
        userMapper.updateInfo(vipBean);
    }


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
