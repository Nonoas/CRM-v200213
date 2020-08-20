package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.stage.FXLayoutFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * 员工管理界面
 *
 * @author Nonoas
 */
@FXML("/fxml/staff_manage.fxml")
public class StaffManagePane extends Pane {
    public StaffManagePane() {
        VBox pane = (VBox) FXLayoutFactory.createFXLayout(StaffManagePane.class);
        getChildren().add(pane);
        setStyle("-fx-border-color:#DDD");
        if (pane != null) {
            pane.prefHeightProperty().bind(this.heightProperty());
            pane.prefWidthProperty().bind(this.widthProperty());
        }
    }
}
