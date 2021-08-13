package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.dao.VipLevelMapper;
import indi.nonoas.crm.service.VipLvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-04-27 12:40
 */
@Service("VipLvServiceImpl")
public class VipLvServiceImpl implements VipLvService {

    @Autowired
    private VipLevelMapper vipLevelMapper;

    @Override
    public List<String> listAllNames() {
        return vipLevelMapper.listAllNames();
    }
}
