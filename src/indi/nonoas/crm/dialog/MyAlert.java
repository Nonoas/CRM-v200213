package indi.nonoas.crm.dialog;

import indi.nonoas.crm.utils.ImageSrc;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 自定义的弹窗，设置了图标和头文字
 * 
 * @author Nonoas
 *
 */
public class MyAlert extends Alert {

	public MyAlert(AlertType alertType) {
		super(alertType);
		init();
	}

	public MyAlert(AlertType alertType, String message) {
		super(alertType, message);
		init();
	}

	public MyAlert(AlertType alertType,String message,ButtonType...buttonTypes) {
		super(alertType, message, buttonTypes);
		init();
	}

	private void init() {
		setHeaderText(null);
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
	}

}
