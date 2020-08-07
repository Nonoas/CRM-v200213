package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.beans.GoodsBean;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;

public class GoodsSelectTable extends GoodsInfoTable {

    public GoodsSelectTable() {
        item_min_discount.setVisible(false);
        item_deduction.setVisible(false);
        item_deduction_rate.setVisible(false);
        item_purchase_price.setVisible(false);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * 获取选中所有商品
     *
     * @return 选中所有商品信息
     */
    public ObservableList<GoodsBean> getSelectedItems() {
        return getSelectionModel().getSelectedItems();
    }
}
