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
     * ��ȡѡ��������Ʒ
     *
     * @return ѡ��������Ʒ��Ϣ
     */
    public ObservableList<GoodsBean> getSelectedItems() {
        return getSelectionModel().getSelectedItems();
    }
}
