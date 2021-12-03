package indi.nonoas.crm.view.pkg;

import indi.nonoas.crm.common.delegate.EventHandler;
import indi.nonoas.crm.pojo.PackageDto;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.SelectionMode;

/**
 * @author : Nonoas
 * @time : 2020-08-05 13:04
 */
public class PackageSingleSelectTable extends PackageTable {

    private final EventHandler eventHandler = new EventHandler();

    public PackageSingleSelectTable() {
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ChangeListener<PackageDto> listener = (observable, oldValue, newValue) -> eventHandler.execute();
        getSelectionModel().selectedItemProperty().addListener(listener);
    }

    /**
     * 获取事件处理类
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }


}
