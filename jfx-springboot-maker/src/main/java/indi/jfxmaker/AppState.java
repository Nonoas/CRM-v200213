package indi.jfxmaker;

import indi.jfxmaker.stage.AppStage;
import java.awt.SystemTray;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;

public enum AppState {

    @SuppressWarnings("unused")
    INSTANCE;

    private static Scene scene;

    private static Stage stage;

    private static AppStage appStage;

    private static String title;

    private static HostServices hostServices;

    private static SystemTray systemTray;

    public static String getTitle() {
        return title;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }



    public static void setScene(final Scene scene) {
        AppState.scene = scene;
    }

    public static void setStage(final Stage stage) {
        AppState.stage = stage;
    }

    public static void setTitle(final String title) {
        AppState.title = title;
    }

    public static HostServices getHostServices() {
        return hostServices;
    }

    static void setHostServices(HostServices hostServices) {
        AppState.hostServices = hostServices;
    }

    public static SystemTray getSystemTray() {
        return systemTray;
    }

    static void setSystemTray(SystemTray systemTray) {
        AppState.systemTray = systemTray;
    }

    public static AppStage getAppStage() {
        return appStage;
    }

    public static void setAppStage(AppStage appStage) {
        AppState.appStage = appStage;
    }
}
