package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.OrderDto;
import indi.nonoas.crm.pojo.UserGoods;
import indi.nonoas.crm.pojo.UserGoodsDto;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.pojo.vo.OrderRecordVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-02 15:43
 */
@Transactional
public interface OrderService {

    /**
     * 查询所有商品订单
     *
     * @return 商品订单列表
     */
    List<OrderRecordVO> selectGdsOrds();

    /**
     * 删除一年前的记录
     */
    void delete365dAgo();

    /**
     * 商品下单事务
     *
     * @param order        订单
     * @param orderDetails 订单详情
     * @param userGoods    需要更新的 用户-商品 列表
     * @param goodsBeans   商品 列表
     * @param vipBean      用户
     */
    void placeGoodsOrder(OrderDto order,
                         List<OrderDetailBean> orderDetails,
                         List<UserGoods> userGoods,
                         List<GoodsDto> goodsBeans,
                         VipInfoDto vipBean) ;


    void placePackageOrder(OrderDto order,
                           List<OrderDetailBean> orderDetails,
                           List<UserGoods> userGoods,
                           List<GoodsDto> goodsBeans,
                           VipInfoDto vipBean);

    void placeCountOrder(List<UserGoodsDto> ugoDtoList, List<UserGoods> ugoList);

    List<OrderDetailBean> selectByOrder(String orderId);
}
