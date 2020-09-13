package indi.nonoas.crm.service;

import indi.nonoas.crm.beans.*;
import indi.nonoas.crm.beans.vo.OrderRecordVO;
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
     * @param orderDetails ��������
     * @param userGoods    ��Ҫ���µ� �û�-��Ʒ �б�
     * @param goodsBeans   ��Ʒ �б�
     * @param vipBean      �û�
     */
    boolean placeGoodsOrder(OrderBean order,
                            List<OrderDetailBean> orderDetails,
                            List<UserGoods> userGoods,
                            List<GoodsBean> goodsBeans,
                            UserBean vipBean);
}
