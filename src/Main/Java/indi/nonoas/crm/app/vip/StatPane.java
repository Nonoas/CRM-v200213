package indi.nonoas.crm.app.vip;

import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.stage.FXLayoutFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * 统计报表面板
 *
 * @author Nonoas
 */
@FXML("/fxml/statistics.fxml")
public class StatPane extends Pane {
    public StatPane() {
        GridPane pane = (GridPane) FXLayoutFactory.createFXLayout(StatPane.class);
        getChildren().add(pane);
        setStyle("-fx-border-color:#DDD");
        if (pane != null) {
            pane.prefHeightProperty().bind(this.heightProperty());
            pane.prefWidthProperty().bind(this.widthProperty());
        }
    }
}
