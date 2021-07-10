package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.*;
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
     * ��ѯ������Ʒ����
     *
     * @return ��Ʒ�����б�
     */
    List<OrderRecordVO> selectGdsOrds();

    /**
     * ɾ��һ��ǰ�ļ�¼
     */
    void delete365dAgo();

    /**
     * ��Ʒ�µ�����
     *
     * @param order        ����
     * @param orderDetails 进价
     * @param userGoods    ��Ҫ���µ� �û�-��Ʒ �б�
     * @param goodsBeans   ��Ʒ �б�
     * @param vipBean      �û�
     */
    void placeGoodsOrder(OrderDto order,
                         List<OrderDetailBean> orderDetails,
                         List<UserGoods> userGoods,
                         List<GoodsDto> goodsBeans,
                         VipInfoDto vipBean) throws Exception;

    /**
     * ��Ʒ�µ�����
     *
     * @param order        ����
     * @param orderDetails 进价
     * @param userGoods    ��Ҫ���µ� �û�-��Ʒ �б�
     * @param goodsBeans   ��Ʒ �б�
     * @param vipBean      �û�
     */
    void placePackageOrder(OrderDto order,
                           List<OrderDetailBean> orderDetails,
                           List<UserGoods> userGoods,
                           List<GoodsDto> goodsBeans,
                           VipInfoDto vipBean) ;
}
