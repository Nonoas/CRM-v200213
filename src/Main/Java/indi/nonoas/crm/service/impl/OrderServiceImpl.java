package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.beans.*;
import indi.nonoas.crm.beans.vo.OrderRecordVO;
import indi.nonoas.crm.dao.GoodsMapper;
import indi.nonoas.crm.dao.OrderMapper;
import indi.nonoas.crm.dao.UserMapper;
import indi.nonoas.crm.dao.UsrGdsMapper;
import indi.nonoas.crm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-06 12:00
 */
@Service("OrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    private OrderMapper odrMapper;

    private UsrGdsMapper ugMapper;

    private UserMapper userMapper;

    private GoodsMapper goodsMapper;

    @Override
    public List<OrderRecordVO> selectGdsOrds() {
        return odrMapper.selectGdsOrds();
    }

    @Override
    public void delete365dAgo() {
        odrMapper.delete365dAgo();
    }

    @Override
    public boolean placeGoodsOrder(OrderBean order,
                                   List<OrderDetailBean> orderDetails,
                                   List<UserGoods> userGoods,
                                   List<GoodsBean> goodsBeans,
                                   UserBean vipBean) {
        //TODO 捕获异常判断是否提交成功
        //订单事务
        odrMapper.insertOrder(order);
        //订单详情事务
        odrMapper.insertOrderDetails(orderDetails);

        //如果是散客则不做以下事务
        if (vipBean != UserBean.SANKE) {
            //用户事务
            userMapper.updateInfo(vipBean);
            //用户商品
            ugMapper.replaceUserGoods(userGoods);
        }
        //商品事务
        //TODO 数量有问题
        goodsMapper.updateGoodsAmount(goodsBeans);

        return true;
    }


    /**
     * 生成商品订单号
     *
     * @return 商品订单号
     */
    public static synchronized String goodsOrderNum() {
        final String prefix = "SP";
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return prefix + dtf.format(dateTime);
    }

    /**
     * 生成商品订单号
     *
     * @return 商品订单号
     */
    public static synchronized String packageOrderNum() {
        final String prefix = "TC";
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return prefix + dtf.format(dateTime);
    }


    //===========================================================================
    //                            setter注入
    //===========================================================================

    @Autowired
    public void setOdrMapper(OrderMapper odrMapper) {
        this.odrMapper = odrMapper;
    }

    @Autowired
    public void setUgMapper(UsrGdsMapper ugMapper) {
        this.ugMapper = ugMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }
}
