package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.app.pkg.PackageContentEditTable;
import indi.nonoas.crm.bean.GoodsBean;
import javafx.beans.value.ChangeListener;

/**
 * @author : Nonoas
 * @time : 2020-08-02 12:05
 */
public class GoodsSingleSelectTable extends GoodsInfoTable {

    private PackageContentEditTable goodsEditTable;

    public GoodsSingleSelectTable(PackageContentEditTable goodsEditTable) {
        super();

        this.goodsEditTable = goodsEditTable;

        item_min_discount.setVisible(false);
        item_deduction.setVisible(false);
        item_deduction_rate.setVisible(false);
        item_purchase_price.setVisible(false);

        ChangeListener<GoodsBean> cl_select = (observable, oldValue, newValue) -> {
            if(newValue!=null){
//                goodsEditTable.addBean();
            }
        };
        getSelectionModel().selectedItemProperty().addListener(cl_select);
    }

}
