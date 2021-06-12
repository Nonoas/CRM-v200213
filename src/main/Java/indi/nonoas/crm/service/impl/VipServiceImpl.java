package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.pojo.dto.VipInfo;
import indi.nonoas.crm.config.UserConfig;
import indi.nonoas.crm.dao.VipMapper;
import indi.nonoas.crm.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-15 22:35
 */
@Service("UserServiceImpl")
public class VipServiceImpl implements VipService {

    private VipMapper vipMapper;

    /**
     * 从配置文件取出最新会员卡号的下一位的36进制表示
     *
     * @return 36进制会员卡号
     */
    @Override
    public String generateVipID() {
        String prefix;
        BigInteger bigInteger;
        synchronized (VipServiceImpl.class) {
            prefix = UserConfig.getPrefix();
            String lastID = UserConfig.getLastID();
            bigInteger = new BigInteger(lastID).add(new BigInteger("1"));
            UserConfig.setLastID(bigInteger.toString());
        }
        String suffix = bigInteger.toString(36).toUpperCase();
        return prefix + suffix;
    }

    @Override
    public VipInfo getInfoByIdOrName(String id, String name) {
        return vipMapper.getInfoByIdOrName(id, name);
    }

    @Override
    public ArrayList<VipInfo> selectAllUser() {
        return vipMapper.selectAllUser();
    }

    @Override
    public ArrayList<VipInfo> selectByFiltrate(String id, String name, String card_level) {
        return vipMapper.selectByFiltrate(id, name, card_level);
    }

    @Override
    public List<VipInfo> selectByDateFiltrate(String id, String name, String level, String dateFrom, String dateTo) {
        return vipMapper.selectByDateFiltrate(id, name, level, dateFrom, dateTo);
    }

    @Override
    public void insertInfo(VipInfo vipBean) {
        vipMapper.insertInfo(vipBean);
    }

    @Override
    public void deleteByID(String id) {
        vipMapper.deleteByID(id);
    }

    @Override
    public void updateInfo(VipInfo vipBean) {
        vipMapper.updateInfo(vipBean);
    }


    @Autowired
    public void setUserMapper(VipMapper vipMapper) {
        this.vipMapper = vipMapper;
    }
}
