package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.OrderBean;
import indi.nonoas.crm.beans.OrderDetailBean;
import indi.nonoas.crm.beans.vo.OrderRecordVO;
import indi.nonoas.crm.beans.vo.UsrOdrRecordVO;
import indi.nonoas.crm.service.UsrGdsOdrService;
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
