package indi.nonoas.crm.service;

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
     * @return ��Ʒ�����б�
     */
    List<OrderRecordVO> selectGdsOrds();

    /**
     * ɾ��һ��ǰ�ļ�¼
     */
    void delete365dAgo();
}
