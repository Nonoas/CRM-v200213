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

    public GoodsSelectDialog() {

        setLogo();  //设置窗口图标
        setStyle(); //设置样式

        DialogPane pane = new DialogPane();
        pane.setContent(table);
        table.showAllInfos();
        pane.getButtonTypes().addAll(ButtonType.OK);
        pane.setPrefSize(800, 600);

        setDialogPane(pane);

    }

    /**
     * 获取选中的商品
     *
     * @return 选中的GoodsBean
     */
    public ObservableList<GoodsDto> getSelectGoods() {
        return table.getSelectedItems();
    }

    /**
     * 设置窗口logo
     */
    private void setLogo() {
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
    }

    /**
     * 设置样式
     */
    private void setStyle() {
        Scene scene = getDialogPane().getScene();
        scene.getStylesheets().add("css/bootstrap3.css");
    }

}
