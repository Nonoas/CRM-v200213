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
 * 界面工厂类
 *
 * @author : Nonoas
 * @time : 2020-08-10 23:00
 */
public class FXLayoutFactory {

    public static Object createFXLayout(Class<? extends Parent> clazz) {

        Parent parent = null;

        String fxml = clazz.getAnnotation(FXML.class).value();


        URL location = FXLayoutFactory.class.getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        try {
            parent = fxmlLoader.load();
            CSS anoCSS = clazz.getAnnotation(CSS.class);
            if (anoCSS != null)
                parent.getStylesheets().add(anoCSS.value());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parent;

    }
}
