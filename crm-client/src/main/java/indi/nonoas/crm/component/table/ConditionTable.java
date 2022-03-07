package indi.nonoas.crm.component.table;

import indi.nonoas.crm.common.IDict;
import indi.nonoas.crm.pojo.dto.MenuConditionDto;
import indi.nonoas.crm.service.MenuConditionService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nonoas
 * @datetime 2022/3/7 20:37
 */
public class ConditionTable<S> extends TableView<S> {

    private final static MenuConditionService mcService = (MenuConditionService) SpringUtil.getBean("MenuConditionService");

    public ConditionTable(String menuCode, List<S> items) {
        getItems().addAll(items);
        List<MenuConditionDto> mcDtoList = mcService.listMenuConditionById(menuCode, IDict.ConditionType.RESULT, null);
        // 初始化列
        ObservableList<TableColumn<S, ?>> columns = getColumns();
        TableColumn<S,?> tabColTemp;
        for (MenuConditionDto mcDto : mcDtoList) {
            tabColTemp = new TableColumn<>(mcDto.getElementName());
            tabColTemp.setPrefWidth(mcDto.getDataWidth());
            columns.add(tabColTemp);
        }
    }

    public S getSelectedData() {
        return null;
    }

    public void replaceData(ArrayList<S> listVipBeans) {

    }

    public void removeData(S dto) {

    }
}
