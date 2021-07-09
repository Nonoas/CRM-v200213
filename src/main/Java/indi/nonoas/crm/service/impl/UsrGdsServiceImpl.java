package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.pojo.UserGoods;
import indi.nonoas.crm.dao.UsrGdsMapper;
import indi.nonoas.crm.service.UsrGdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 根据用户ID和商品ID查询
     *
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @return 用户-商品Bean类
     */
    @Override
    public UserGoods selectByUserGoods(String userId, String goodsId) {
        return usrGdsMapper.selectByUserGoods(userId, goodsId);
    }

    //===========================================================================
    //                            setter注入
    //===========================================================================
    @Autowired
    public void setUsrGdsMapper(UsrGdsMapper usrGdsMapper) {
        this.usrGdsMapper = usrGdsMapper;
    }
}
