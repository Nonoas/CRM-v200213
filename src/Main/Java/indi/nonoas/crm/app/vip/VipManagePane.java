package indi.nonoas.crm.app.vip;

import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.stage.FXLayoutFactory;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * VIP管理界面
 *
 * @author Nonoas
 */
@FXML("/fxml/vip_manage.fxml")
public class VipManagePane extends Pane {

    public VipManagePane() {
        TabPane pane = (TabPane) FXLayoutFactory.createFXLayout(VipManagePane.class);
        getChildren().add(pane);
        setStyle("-fx-border-color:#DDD");
        if (pane != null) {
            pane.prefHeightProperty().bind(this.heightProperty());
            pane.prefWidthProperty().bind(this.widthProperty());
        }
    }

}
