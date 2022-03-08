package indi.nonoas.crm.component.alert;

import indi.nonoas.crm.config.ImageSrc;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MyAlert extends Alert {

    @SuppressWarnings("unused")
    public MyAlert(AlertType alertType) {
        super(alertType);
        init();
    }

    public MyAlert(AlertType alertType, String message) {
        super(alertType, message);
        init();
    }

    @SuppressWarnings("unused")
    public MyAlert(AlertType alertType, String message, ButtonType... buttonTypes) {
        super(alertType, message, buttonTypes);
        init();
    }

    private void init() {
        setHeaderText(null);
        Pane pane = getDialogPane();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
        pane.getStylesheets().add("/css/style-normal.css");
    }

}
