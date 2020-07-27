package indi.nonoas.crm.view.alert;

import indi.nonoas.crm.utils.ImageSrc;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * �Զ���ĵ�����������ͼ���ͷ����
 *
 * @author Nonoas
 */
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
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
    }

}
