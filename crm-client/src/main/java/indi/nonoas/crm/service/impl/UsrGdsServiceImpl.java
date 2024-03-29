package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.dao.UsrGdsMapper;
import indi.nonoas.crm.pojo.UserGoods;
import indi.nonoas.crm.service.UsrGdsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Nonoas
 * @time : 2020-09-02 17:09
 */
@Service("UsrGdsServiceImpl")
public class UsrGdsServiceImpl implements UsrGdsService {

    private UsrGdsMapper usrGdsMapper;

    @Override
    public List<UserGoods> selectByUser(String userID) {
        return usrGdsMapper.selectByUser(userID);
    }


    @Override
    public UserGoods selectByUserGoods(String userId, String goodsId) {
        return usrGdsMapper.selectByUserGoods(userId, goodsId);
    }

    @Override
    public void reduceGoods(List<UserGoods> ugoList) {
        for (UserGoods ug : ugoList) {
            usrGdsMapper.reduceGoods(ug);
        }
    }

    //===========================================================================
    //                            setterע��
    //===========================================================================
    @Autowired
    public void setUsrGdsMapper(UsrGdsMapper usrGdsMapper) {
        this.usrGdsMapper = usrGdsMapper;
    }
}
