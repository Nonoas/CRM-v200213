package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * 员工管理界面
 *
 * @author Nonoas
 */
public class StaffManagePane extends Pane {
    public StaffManagePane() {
        URL url = getClass().getResource("/fxml/staffmanage.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        VBox pane = null;
        try {
            pane = fxmlLoader.load();
            getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setStyle("-fx-border-color:#DDD");
        if (pane != null) {
            pane.prefHeightProperty().bind(this.heightProperty());
            pane.prefWidthProperty().bind(this.widthProperty());
        }
    }
}
