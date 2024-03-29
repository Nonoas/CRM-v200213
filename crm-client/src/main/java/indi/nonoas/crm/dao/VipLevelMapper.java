package indi.nonoas.crm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.nonoas.crm.pojo.dto.VipLevelDto;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author : Nonoas
 * @time : 2021-04-27 12:31
 */
@Repository
public interface VipLevelMapper extends BaseMapper<VipLevelDto> {

    @Select("select name from vip_level")
    List<String> listAllNames();
}
