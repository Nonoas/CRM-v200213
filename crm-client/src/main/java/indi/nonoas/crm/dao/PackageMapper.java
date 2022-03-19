package indi.nonoas.crm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.nonoas.crm.pojo.PackageDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : Nonoas
 * @time : 2021-06-11 15:16
 */
@Repository
public interface PackageMapper extends BaseMapper<PackageDto> {

    /**
     * 筛选查询套餐信息
     *
     * @param id  套餐id
     * @param name   套餐名称
     * @param money1 价格下限
     * @param money2 价格上限
     * @return 套餐信息
     */
    List<PackageDto> findByFilter(@Param("id") String id,
                                  @Param("name") String name,
                                  @Param("money1") double money1,
                                  @Param("money2") double money2);

}
