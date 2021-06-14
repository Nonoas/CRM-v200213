package indi.nonoas.crm.dao;

import indi.nonoas.crm.dao.my_orm_dao.PackageContentDao;
import indi.nonoas.crm.pojo.PackageContentDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-06-14 11:38
 */
@Repository
public interface PkgContentMapper {

    /**
     * ���������ײ�����Ʒ��Ϣ
     *
     * @param dtoList ��Ʒ��Ϣ����
     */
    void insertInfos(@Param("dtoList") List<PackageContentDto> dtoList);

    /**
     * '
     * ͨ��idɾ���ײ��ڵ���Ʒ
     *
     * @param pkgId �ײ�id
     */
    void deleteById(String pkgId);

}
