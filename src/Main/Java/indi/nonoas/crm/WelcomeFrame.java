package indi.nonoas.crm;

import indi.nonoas.crm.app.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class WelcomeFrame extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage = new WelcomeStage();
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(WelcomeFrame.class, args);
    }
}
