package indi.nonoas.crm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.nonoas.crm.pojo.PackageDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Nonoas
 * @time : 2021-06-11 15:16
 */
@Repository
public interface PackageMapper extends BaseMapper<PackageDto> {

    /**
     * 通过过滤查找项目信息
     *
     * @param id     编号
     * @param name   名称
     * @param money1 金钱下限
     * @param money2 金钱上限
     * @return 所有满足过滤条件的项目信息, 可以为null
     */
    List<PackageDto> findByFilter(@Param("id") String id,
                                  @Param("name") String name,
                                  @Param("money1") double money1,
                                  @Param("money2") double money2);

}
