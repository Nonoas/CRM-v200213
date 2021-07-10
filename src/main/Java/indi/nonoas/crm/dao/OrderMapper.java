package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.OrderBean;
import indi.nonoas.crm.pojo.OrderDetailBean;
import indi.nonoas.crm.pojo.vo.OrderRecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-01 22:32
 */
@Repository
public interface OrderMapper {

    /**
     * ��ѯ������Ʒ����
     *
     * @return ��Ʒ�����б�
     */
    List<OrderRecordVO> selectGdsOrds();

    /**
     * ����һ������
     *
     * @param order ����pojo
     */
    void insertOrder(OrderBean order);

    /**
     * ɾ��һ��ǰ�Ķ�����¼
     */
    void delete365dAgo();

    /**
     * �������붩������
     *
     * @param orderDetails �������鼯��
     * @return �ɹ���true
     */
    boolean insertOrderDetails(@Param("orderDetails") List<OrderDetailBean> orderDetails);

}
