package indi.nonoas.crm.view.goods;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.view.consume.GoodsConsumeTable;
import javafx.beans.value.ChangeListener;

import java.util.Collections;

/**
 * @author : Nonoas
 * @time : 2020-08-02 12:05
 */
public class GoodsSingleSelectTable extends GoodsInfoTable {

    public GoodsSingleSelectTable(GoodsConsumeTable gc_table) {
        super(Collections.emptyList());


        ChangeListener<GoodsDto> cl_select = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                gc_table.addBean(newValue);
            }
        };
        getSelectionModel().selectedItemProperty().addListener(cl_select);
    }

}
