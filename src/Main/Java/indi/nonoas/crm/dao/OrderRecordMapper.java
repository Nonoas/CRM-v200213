package indi.nonoas.crm.dao;

import indi.nonoas.crm.beans.vo.UsrOdrRecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-09-01 22:32
 */
@Repository
public interface OrderRecordMapper {
    /**
     * ��ѯ�����û������Ʒ���Ѽ�¼
     *
     * @return ���Ѽ�¼�б�
     */
    List<UsrOdrRecordVO> selectUserGoodsOrder();
}
