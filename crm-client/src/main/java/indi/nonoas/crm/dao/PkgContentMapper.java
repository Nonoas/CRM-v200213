package indi.nonoas.crm.dao;

import indi.nonoas.crm.pojo.PackageContentDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : Nonoas
 * @time : 2021-06-14 11:38
 */
@Repository
public interface PkgContentMapper {

    /**
     * ͨ����Ŀid��ѯ��Ŀ����
     *
     * @param pkgId ��Ŀid
     * @return ��Ŀid进价Ʒ��Ϣ������Ϊnull
     */
    List<PackageContentDto> selectById(String pkgId);

    /**
     * 进价�ײ�����Ʒ��Ϣ
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


    /**
     * 通过商品id查询一条套餐id
     *
     * @param goodsId 商品id
     * @return 套餐id
     */
    String selectIdByGoodsId(String goodsId);

}
