package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.pojo.vo.UsrGdsOdrRecordVO;
import indi.nonoas.crm.dao.UsrGdsOdrMapper;
import indi.nonoas.crm.service.UsrGdsOdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-03 17:29
 */
@Service("UsrGdsOdrServiceImpl")
public class UsrGdsOdrServiceImpl implements UsrGdsOdrService {


    private UsrGdsOdrMapper usrGdsOdrMapper;

    @Override
    public List<UsrGdsOdrRecordVO> selectUserGoodsOrder() {
        return usrGdsOdrMapper.selectUserGoodsOrder();
    }


    @Autowired
    public void setUsrGdsOdrMapper(UsrGdsOdrMapper usrGdsOdrMapper) {
        this.usrGdsOdrMapper = usrGdsOdrMapper;
    }
}
