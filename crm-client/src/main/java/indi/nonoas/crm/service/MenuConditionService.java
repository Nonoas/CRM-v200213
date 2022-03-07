package indi.nonoas.crm.service;

import indi.nonoas.crm.pojo.dto.MenuConditionDto;

import java.util.List;

/**
 * @author Nonoas
 * @datetime 2022/3/7 21:25
 */
public interface MenuConditionService {
    List<MenuConditionDto> listMenuConditionById(String menuCode, String conditionType, String elementCode);
}
