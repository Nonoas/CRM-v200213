package indi.nonoas.crm.dialog;

import indi.nonoas.crm.table.GoodsInfoTable;
import indi.nonoas.crm.bean.GoodsBean;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class GoodsSelectDialog extends Dialog<GoodsBean> {

    GoodsInfoTable table = new GoodsInfoTable();

    //TODO 设置面板样式,如何获取选中的商品
    public GoodsSelectDialog(){
        DialogPane pane=new DialogPane();
        pane.setContent(table);
        table.showAllInfos();
        pane.getButtonTypes().addAll(ButtonType.OK);
        pane.setPrefSize(600,600);
        setDialogPane(pane);
    }

    /**
     * 获取选中的商品
     * @return 选中的GoodsBean
     */
    public GoodsBean getSelectGoods(){
        return  table.getSelectedData();
    }

}
