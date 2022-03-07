package indi.nonoas.crm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.nonoas.crm.pojo.dto.MenuConditionDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nonoas
 * @datetime 2022/3/7 20:54
 */
@Repository
public interface MenuConditionMapper extends BaseMapper<MenuConditionDto> {
    @Select("select * from menu_condition")
    List<MenuConditionDto> listAll();

    List<MenuConditionDto> listById(@Param("menuCode") String menuCode,
                                    @Param("conditionType") String conditionType,
                                    @Param("elementCode") String elementCode);
}
