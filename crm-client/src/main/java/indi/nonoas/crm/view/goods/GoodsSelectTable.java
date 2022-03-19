package indi.nonoas.crm.view.goods;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;

import java.util.Collections;

public class GoodsSelectTable extends GoodsInfoTable {

    public GoodsSelectTable() {
        super(Collections.emptyList());
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public ObservableList<GoodsDto> getSelectedItems() {
        return getSelectionModel().getSelectedItems();
    }
}
