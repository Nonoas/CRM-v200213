package indi.nonoas.crm.component.dialog;

import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.view.goods.GoodsSelectTable;
import indi.nonoas.crm.config.ImageSrc;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GoodsSelectDialog extends Dialog<GoodsDto> {

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
    public ObservableList<GoodsDto> getSelectGoods(){
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
    }

}
