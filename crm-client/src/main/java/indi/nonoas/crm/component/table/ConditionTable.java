package indi.nonoas.crm.component.table;

import indi.nonoas.crm.common.IDict;
import indi.nonoas.crm.pojo.dto.MenuConditionDto;
import indi.nonoas.crm.service.MenuConditionService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.utils.StringUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 可配置化表格，从数据库读取配置
 *
 * @author Nonoas
 * @datetime 2022/3/7 20:37
 */
public class ConditionTable<S> extends TableView<S> {

    private final static MenuConditionService mcService = (MenuConditionService) SpringUtil.getBean("MenuConditionService");

    private S selectedData;

    private final ObservableList<S> items = getItems();

    public ConditionTable(String menuCode, List<S> items, Class<? extends S> clazz) {
        this.items.addAll(items);
        List<MenuConditionDto> mcDtoList = mcService.listMenuConditionById(menuCode, IDict.ConditionType.RESULT, null);
        // 初始化列
        ObservableList<TableColumn<S, ?>> columns = getColumns();
        TableColumn<S, String> tabColTemp;
        for (MenuConditionDto mcDto : mcDtoList) {
            tabColTemp = new TableColumn<>(mcDto.getElementName());
            tabColTemp.setPrefWidth(mcDto.getDataWidth());
            tabColTemp.setCellValueFactory(param -> {
                S value = param.getValue();
                try {
                    Method method = clazz.getMethod("get" + StringUtil.captureName(mcDto.getElementCode()));
                    String s = String.valueOf(method.invoke(value));
                    return new SimpleStringProperty(s);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            });
            columns.add(tabColTemp);
        }
        // 设置监听
        getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> selectedData = newVal);
    }

    /**
     * 获取选中的单个数据
     */
    public S getSelectedData() {
        return selectedData;
    }

    /**
     * 刷新表格数据
     *
     * @param dataList 刷新之后的数据
     */
    public void refreshData(List<S> dataList) {
        items.clear();
        items.addAll(dataList);
    }

    /**
     * 移除指定元素
     *
     * @param e 指定移除的元素
     */
    public void removeData(S e) {
        items.remove(e);
    }

    /**
     * 清空所有数据
     */
    public void clearData() {
        items.clear();
    }
}
