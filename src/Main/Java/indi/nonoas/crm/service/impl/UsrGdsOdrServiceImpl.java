package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.beans.vo.UsrOdrRecordVO;
import indi.nonoas.crm.dao.OrderRecordMapper;
import indi.nonoas.crm.service.UsrGdsOdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-01 22:55
 */
@Service("UsrGdsOdrServiceImpl")
public class UsrGdsOdrServiceImpl implements UsrGdsOdrService {

    private OrderRecordMapper odrMapper;

    @Override
    public List<UsrOdrRecordVO> selectUserGoodsOrder() {
        return odrMapper.selectUserGoodsOrder();
    }

    @Autowired
    public void setOdrMapper(OrderRecordMapper odrMapper) {
        this.odrMapper = odrMapper;
    }


}
