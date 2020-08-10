package indi.nonoas.crm.view.stage;

import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.annotation.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Stageπ§≥ß¿‡
 *
 * @author : Nonoas
 * @time : 2020-08-10 23:00
 */
public class StageFactory {

    public static Stage getStage(Class<? extends Stage> clazz) {
        Stage o = null;
        try {
            o = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        String fxml = clazz.getAnnotation(FXML.class).value();
        String css = clazz.getAnnotation(CSS.class).value();

        URL location = StageFactory.class.getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (o != null && root != null) {
            o.setScene(new Scene(root));
            o.getScene().getStylesheets().add(css);
        }
        return o;

    }
}
