package indi.nonoas.crm.controller;

import indi.nonoas.crm.app.stat.OrderTable;
import indi.nonoas.crm.app.stat.UsrGdsOdrTable;
import indi.nonoas.crm.config.ImageSrc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StatController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void goodsOrderRecord() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("商品订单记录");
        dialog.setResizable(true);
        DialogPane pane = dialog.getDialogPane();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
        pane.getButtonTypes().add(ButtonType.OK);
        pane.setContent(new OrderTable());
        dialog.show();
    }

    @FXML
    void pkgConsumeRecord() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("用户库存消费记录");
        dialog.setResizable(true);
        DialogPane pane = dialog.getDialogPane();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
        pane.getButtonTypes().add(ButtonType.OK);
        pane.setContent(new UsrGdsOdrTable());
        dialog.show();
    }

}
