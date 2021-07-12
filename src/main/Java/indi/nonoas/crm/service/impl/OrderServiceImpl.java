package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.dao.GoodsMapper;
import indi.nonoas.crm.dao.OrderMapper;
import indi.nonoas.crm.dao.UsrGdsMapper;
import indi.nonoas.crm.dao.VipMapper;
import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.OrderDto;
import indi.nonoas.crm.pojo.UserGoods;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.pojo.vo.OrderRecordVO;
import indi.nonoas.crm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private VipMapper vipMapper;

    private GoodsMapper goodsMapper;

    @Override
    public List<OrderRecordVO> selectGdsOrds() {
        return odrMapper.selectGdsOrds();
    }

    @Override
    public void delete365dAgo() {
        odrMapper.delete365dAgo();
    }

    /**
     * 商品下单事务
     *
     * @param order        订单
     * @param orderDetails 订单详情
     * @param userGoods    需要更新的 用户-商品 列表
     * @param goodsBeans   商品 列表
     * @param vipBean      用户
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void placeGoodsOrder(OrderDto order,
                                List<OrderDetailBean> orderDetails,
                                List<UserGoods> userGoods,
                                List<GoodsDto> goodsBeans,
                                VipInfoDto vipBean) {
        odrMapper.insertOrder(order);
        odrMapper.insertOrderDetails(orderDetails);

        if (vipBean != VipInfoDto.SANKE) {
            // 更新用户信息
            vipMapper.updateInfo(vipBean);
            // 更新用户商品
            ugMapper.replaceUserGoods(userGoods);
        }
        // 更新商品数量
        goodsMapper.updateGoodsAmount(goodsBeans);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void placePackageOrder(OrderDto order,
                                  List<OrderDetailBean> orderDetails,
                                  List<UserGoods> userGoods,
                                  List<GoodsDto> goodsBeans,
                                  VipInfoDto vipBean) {

        if (vipBean != VipInfoDto.SANKE) {
            vipMapper.updateInfo(vipBean);

            // Fixme h2数据库不支持 replace into 语法
            ugMapper.replaceUserGoods(userGoods);
        }
        odrMapper.insertOrder(order);
        odrMapper.insertOrderDetails(orderDetails);
        goodsMapper.updateGoodsAmount(goodsBeans);
    }


    /**
     * 生商品订单号
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
     * 生成订单号
     * @return 订单号
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
    public void setUserMapper(VipMapper vipMapper) {
        this.vipMapper = vipMapper;
    }

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }
}
