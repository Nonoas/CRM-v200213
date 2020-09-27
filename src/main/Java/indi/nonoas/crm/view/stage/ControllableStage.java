package indi.nonoas.crm.view.stage;

import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.annotation.StageProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Nonoas
 * @time : 2020-08-11 0:16
 */
public class ControllableStage extends Stage {

    private Object controller;

    public ControllableStage() {

        Class<?> clazz = this.getClass();
        String fxml = clazz.getAnnotation(FXML.class).value();

        CSS anoCSS = clazz.getAnnotation(CSS.class);
        StageProperty anoStage = clazz.getAnnotation(StageProperty.class);

        if (anoStage != null) {
            setTitle(anoStage.title());
        }

        URL location = getClass().getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root;
        try {
            root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            setScene(new Scene(root));
            if (anoCSS != null) {
                String css = anoCSS.value();
                getScene().getStylesheets().add(css);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Object getController() {
        return controller;
    }


}
