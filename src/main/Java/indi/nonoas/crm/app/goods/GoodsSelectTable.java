package indi.nonoas.crm.app.goods;

import indi.nonoas.crm.pojo.dto.GoodsDto;
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
     * ��ȡѡ��������Ʒ
     *
     * @return ѡ��������Ʒ��Ϣ
     */
    public ObservableList<GoodsDto> getSelectedItems() {
        return getSelectionModel().getSelectedItems();
    }
}
