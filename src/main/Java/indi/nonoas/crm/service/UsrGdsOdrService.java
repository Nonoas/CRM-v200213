package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.vo.UsrGdsOdrRecordVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-03 17:28
 */
@Transactional
public interface UsrGdsOdrService {
    /**
     * ��ѯ�����û������Ʒ���Ѽ�¼
     *
     * @return ���Ѽ�¼�б�
     */
    List<UsrGdsOdrRecordVO> selectUserGoodsOrder();
}
