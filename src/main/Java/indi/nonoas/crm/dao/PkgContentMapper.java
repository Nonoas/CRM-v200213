package indi.nonoas.crm.dao;

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
     * 通过项目id查询项目内容
     *
     * @param pkgId 项目id
     * @return 项目id包含的商品信息，可以为null
     */
    List<PackageContentDto> selectById(String pkgId);

    /**
     * 批量插入套餐内商品信息
     *
     * @param dtoList 商品信息集合
     */
    void insertInfos(@Param("dtoList") List<PackageContentDto> dtoList);

    /**
     * '
     * 通过id删除套餐内的商品
     *
     * @param pkgId 套餐id
     */
    void deleteById(String pkgId);

}
