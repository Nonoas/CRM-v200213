package indi.nonoas.crm.dialog;

import indi.nonoas.crm.table.GoodsInfoTable;
import indi.nonoas.crm.bean.GoodsBean;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class GoodsSelectDialog extends Dialog<GoodsBean> {

    GoodsInfoTable table = new GoodsInfoTable();

    //TODO ���������ʽ,��λ�ȡѡ�е���Ʒ
    public GoodsSelectDialog(){
        DialogPane pane=new DialogPane();
        pane.setContent(table);
        table.showAllInfos();
        pane.getButtonTypes().addAll(ButtonType.OK);
        pane.setPrefSize(600,600);
        setDialogPane(pane);
    }

    /**
     * ��ȡѡ�е���Ʒ
     * @return ѡ�е�GoodsBean
     */
    public GoodsBean getSelectGoods(){
        return  table.getSelectedData();
    }

}
