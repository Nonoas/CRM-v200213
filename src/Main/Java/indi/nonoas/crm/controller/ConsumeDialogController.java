package indi.nonoas.crm.controller;


import indi.nonoas.crm.view.alert.MyAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author : Nonoas
 * @time : 2020-08-10 17:59
 */
public class ConsumeDialogController implements Initializable {

    private Stage stage;
    @FXML
    private GridPane gp_rootPane;

    @FXML
    private ComboBox<String> cb_payMode;

    @FXML
    private TextField tf_transactor;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_submit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_payMode.getItems().addAll("积分", "现金");
        cb_payMode.setValue("现金");
    }

    @FXML
    private void submit() {
        new MyAlert(Alert.AlertType.INFORMATION, "结算成功！").show();
        stage.close();
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
