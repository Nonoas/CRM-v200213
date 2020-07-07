package indi.nonoas.crm.dialog;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.table.GoodsSelectTable;
import indi.nonoas.crm.utils.ImageSrc;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GoodsSelectDialog extends Dialog<GoodsBean> {

    GoodsSelectTable table = new GoodsSelectTable();

    public GoodsSelectDialog(){

        setLogo();  //���ô���ͼ��
        setStyle(); //������ʽ

        DialogPane pane=new DialogPane();
        pane.setContent(table);
        table.showAllInfos();
        pane.getButtonTypes().addAll(ButtonType.OK);
        pane.setPrefSize(800,600);

        setDialogPane(pane);

    }

    /**
     * ��ȡѡ�е���Ʒ
     * @return ѡ�е�GoodsBean
     */
    public ObservableList<GoodsBean> getSelectGoods(){
        return  table.getSelectedItems();
    }

    /**
     * ���ô���logo
     */
    private void setLogo(){
        Stage stage= (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
    }

    /**
     * ������ʽ
     */
    private void setStyle(){
        Scene scene=getDialogPane().getScene();
        scene.getStylesheets().add("indi/nonoas/crm/mcss/application.css");
    }

}
