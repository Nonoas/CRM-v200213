package indi.nonoas.crm.table;

import indi.nonoas.crm.bean.GoodsBean;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;

public class GoodsSelectTable extends GoodsInfoTable{

    public GoodsSelectTable(){
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * 获取选中所有商品
     * @return 选中所有商品信息
     */
    public ObservableList<GoodsBean> getSelectedItems(){
        return getSelectionModel().getSelectedItems();
    }
}
