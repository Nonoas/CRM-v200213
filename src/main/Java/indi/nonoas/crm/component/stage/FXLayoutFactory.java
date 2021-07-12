package indi.nonoas.crm.component.stage;

import indi.nonoas.crm.component.annotation.CSS;
import indi.nonoas.crm.component.annotation.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * 布局构建工厂，可以通过注解设置 fxml 和 css
 *
 * @author : Nonoas
 * @time : 2020-08-10 23:00
 */
public class FXLayoutFactory {

    public static Parent createFXLayout(Class<? extends Parent> clazz) {

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
