package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.controller.ConsumeController;
import indi.nonoas.crm.controller.ConsumeDialogController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author : Nonoas
 * @time : 2020-08-10 17:39
 */
public class ConsumeDialog extends Stage {

    private ConsumeDialogController controller;

    public ConsumeDialog() {
        initView();
    }

    private void initView() {
        setTitle("∂©µ•Ω·À„");
        URL url = getClass().getResource("/fxml/consume_dialog.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) {
            Scene scene = new Scene(root);
            scene.getStylesheets().add("css/application.css");
            setScene(scene);
        }
        getIcons().add(new Image(ImageSrc.lOGO_PATH));
        setResizable(false);
        controller = fxmlLoader.getController();
        controller.setStage(this);
    }

}
