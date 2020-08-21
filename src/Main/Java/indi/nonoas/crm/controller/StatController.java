package indi.nonoas.crm.controller;

import indi.nonoas.crm.app.stat.OrderTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StatController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void goodsOrderRecord() {
        Dialog<String> dialog = new Dialog<>();
        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().add(ButtonType.OK);
        pane.setContent(new OrderTable());
        dialog.show();
    }

    @FXML
    void pkgConsumeRecord() {

    }

    @FXML
    void pkgPurchaseRecord() {

    }

}
