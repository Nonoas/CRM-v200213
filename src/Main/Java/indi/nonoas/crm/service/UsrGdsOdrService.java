package indi.nonoas.crm.service;

import indi.nonoas.crm.beans.vo.UsrOdrRecordVO;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-01 22:47
 */
public interface UsrGdsOdrService {
    /**
     * ��ѯ�����û������Ʒ���Ѽ�¼
     *
     * @return ���Ѽ�¼�б�
     */
    List<UsrOdrRecordVO> selectUserGoodsOrder();
}
