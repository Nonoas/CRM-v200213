package indi.nonoas.crm.component.stage;

import indi.nonoas.crm.component.annotation.CSS;
import indi.nonoas.crm.component.annotation.FXML;
import indi.nonoas.crm.component.annotation.StageProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * 可以通过注解设置fxml和css的Stage类
 *
 * @author : Nonoas
 * @time : 2020-08-11 0:16
 */
public class ControllableStage extends Stage {

    private Initializable controller;

    public ControllableStage() {

        Class<?> clazz = this.getClass();
        FXML anoFxml = clazz.getAnnotation(FXML.class);

        // 如果没有指定FXML，则直接返回
        if (null == anoFxml) {
            return;
        }

        CSS anoCSS = clazz.getAnnotation(CSS.class);
        StageProperty anoStage = clazz.getAnnotation(StageProperty.class);

        if (anoStage != null) {
            setTitle(anoStage.title());
        }

        String fxml = anoFxml.value();
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

    /**
     * 获取controller
     *
     * @return Initializable 的实现类
     * @see Initializable
     */
    protected Initializable getController() {
        return controller;
    }


}
