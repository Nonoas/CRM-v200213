package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.bean.GoodsBean;
import javafx.collections.ObservableList;

public class GoodsSelectTable extends GoodsInfoTable {

    public GoodsSelectTable() {
        item_min_discount.setVisible(false);
        item_deduction.setVisible(false);
        item_deduction_rate.setVisible(false);
        item_purchase_price.setVisible(false);
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
