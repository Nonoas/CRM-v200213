package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * 统计报表面板
 * @author Nonoas
 *
 */
public class StatPane extends Pane {
	public StatPane() {
		URL url = getClass().getResource("/fxml/statistics.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(url);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		GridPane pane = null;
		try {
			pane=fxmlLoader.load();
			getChildren().add(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setStyle("-fx-border-color:#DDD");
		pane.prefHeightProperty().bind(this.heightProperty());
		pane.prefWidthProperty().bind(this.widthProperty());
		
	}
}
