package indi.nonoas.crm.table;

import indi.nonoas.crm.bean.GoodsBean;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;

public class GoodsSelectTable extends GoodsInfoTable{

    public GoodsSelectTable(){
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * ��ȡѡ��������Ʒ
     * @return ѡ��������Ʒ��Ϣ
     */
    public ObservableList<GoodsBean> getSelectedItems(){
        return getSelectionModel().getSelectedItems();
    }
}
