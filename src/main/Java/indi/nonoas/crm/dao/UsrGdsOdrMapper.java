package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.vo.UsrGdsOdrRecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-03 16:47
 */
@Repository
public interface UsrGdsOdrMapper {
    /**
     * ��ѯ�����û������Ʒ���Ѽ�¼
     *
     * @return ���Ѽ�¼�б�
     */
    List<UsrGdsOdrRecordVO> selectUserGoodsOrder();
}
