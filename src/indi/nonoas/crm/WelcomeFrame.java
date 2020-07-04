package indi.nonoas.crm;

import java.io.IOException;
import java.net.URL;
import indi.nonoas.crm.controller.WelcomeController;
import indi.nonoas.crm.utils.ImageSrc;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WelcomeFrame extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL location = getClass().getResource("/indi/nonoas/crm/mfxml/welcome.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("�ͻ�����ϵͳ");
        primaryStage.getIcons().add(new Image(ImageSrc.lOGO_PATH));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getScene().getStylesheets().add("indi/nonoas/crm/mcss/welcome.css");

        WelcomeController controller = fxmlLoader.getController(); // ��ȡController��ʵ������
        controller.initialize(location, null);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
