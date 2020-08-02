package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.app.pkg.PackageContentEditTable;
import indi.nonoas.crm.bean.GoodsBean;
import javafx.beans.value.ChangeListener;

/**
 * @author : Nonoas
 * @time : 2020-08-02 12:05
 */
public class GoodsSingleSelectTable extends GoodsInfoTable {

    private final GoodsConsumeTable gc_table;

    public GoodsSingleSelectTable(GoodsConsumeTable gc_table) {
        super();

        this.gc_table = gc_table;

        item_min_discount.setVisible(false);
        item_deduction.setVisible(false);
        item_deduction_rate.setVisible(false);
        item_purchase_price.setVisible(false);

        ChangeListener<GoodsBean> cl_select = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                gc_table.addBean(newValue);
            }
        };
        getSelectionModel().selectedItemProperty().addListener(cl_select);
    }

}
