package indi.nonoas.crm.view;

import indi.nonoas.crm.component.annotation.FXML;
import indi.nonoas.crm.component.stage.FXLayoutFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Ա进价�
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
