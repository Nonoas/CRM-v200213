package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * VIP管理界面
 * 
 * @author Nonoas
 *
 */
public class VipManagePane extends Pane {

	public VipManagePane() {
		
		URL url = getClass().getResource("/indi/nonoas/crm/mfxml/vipmanage.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(url);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		TabPane pane = null;
		try {
			pane = fxmlLoader.load();
			getChildren().add(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setStyle("-fx-border-color:#DDD");
		pane.prefHeightProperty().bind(this.heightProperty());
		pane.prefWidthProperty().bind(this.widthProperty());
	}
	
}
