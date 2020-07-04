package indi.nonoas.crm.app;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * 商品管理面板
 *
 * @author Nonoas
 */
public class GoodsManagePane extends Pane {
    public GoodsManagePane() {
        URL url = getClass().getResource("/indi/nonoas/crm/mfxml/goodsmanage.fxml");
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

        if (pane != null) {
            pane.prefHeightProperty().bind(this.heightProperty());
            pane.prefWidthProperty().bind(this.widthProperty());
        }
    }
}
