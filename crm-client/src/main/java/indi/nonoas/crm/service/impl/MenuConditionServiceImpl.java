package indi.nonoas.crm.service.impl;

import indi.nonoas.crm.dao.MenuConditionMapper;
import indi.nonoas.crm.pojo.dto.MenuConditionDto;
import indi.nonoas.crm.service.MenuConditionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nonoas
 * @datetime 2022/3/7 21:27
 */
@Service("MenuConditionService")
public class MenuConditionServiceImpl implements MenuConditionService {

    private final MenuConditionMapper mcMapper;

    public MenuConditionServiceImpl(MenuConditionMapper mcMapper) {
        this.mcMapper = mcMapper;
    }

    @Override
    public List<MenuConditionDto> listMenuConditionById(String menuCode, String conditionType, String elementCode) {
        return mcMapper.listById(menuCode, conditionType, elementCode);
    }
}
