package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.app.consume.GoodsConsumeTable;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import javafx.beans.value.ChangeListener;

/**
 * @author : Nonoas
 * @time : 2020-08-02 12:05
 */
public class GoodsSingleSelectTable extends GoodsInfoTable {

    public GoodsSingleSelectTable(GoodsConsumeTable gc_table) {
        
        super();
        item_min_discount.setVisible(false);
        item_deduction.setVisible(false);
        item_deduction_rate.setVisible(false);
        item_purchase_price.setVisible(false);

        ChangeListener<GoodsDto> cl_select = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                gc_table.addBean(newValue);
            }
        };
        getSelectionModel().selectedItemProperty().addListener(cl_select);
    }

}
