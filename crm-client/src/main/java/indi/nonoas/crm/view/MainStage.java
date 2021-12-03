package indi.nonoas.crm.view;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author : Nonoas
 * @time : 2020-09-02 12:10
 */
public class MainStage extends Stage {

    public MainStage() {

        URL url = getClass().getResource("/fxml/main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            StackPane root = new StackPane();

            Pane content = fxmlLoader.load();
            content.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            root.getChildren().add(content);

            Scene scene = new Scene(root);
            initStyle(StageStyle.DECORATED);

            root.setPadding(new Insets(25));
            root.setStyle( "-fx-background-color: rgb(0, 0, 255,)");

            DropShadow dropshadow = new DropShadow();// 阴影向外
            dropshadow.setRadius(20);// 颜色蔓延的距离
            dropshadow.setSpread(0.1);// 颜色变淡的程度
            dropshadow.setColor(Color.rgb(0,0,0,0.2));// 设置颜色
            content.setEffect(dropshadow);// 绑定指定窗口控件
            content.setStyle("-fx-background-color: white");

            scene.setFill(Color.TRANSPARENT);
            setScene(scene);

            setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
