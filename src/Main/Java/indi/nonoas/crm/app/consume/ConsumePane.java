package indi.nonoas.crm.app.consume;

import java.io.IOException;
import java.net.URL;

import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.stage.FXLayoutFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * 消费界面
 *
 * @author Nonoas
 */
@FXML("/fxml/consumption.fxml")
public class ConsumePane extends Pane {

    public ConsumePane() {
        TabPane pane = (TabPane) FXLayoutFactory.createFXLayout(ConsumePane.class);
        getChildren().add(pane);
        setStyle("-fx-border-color:#DDD");
        if (pane != null) {
            pane.prefHeightProperty().bind(this.heightProperty());
            pane.prefWidthProperty().bind(this.widthProperty());
        }


    }
}
