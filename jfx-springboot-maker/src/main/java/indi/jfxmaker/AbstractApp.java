package indi.jfxmaker;

import de.felixroske.jfxsupport.PropertyReaderHelper;
import indi.jfxmaker.fxml.AbstractFxmlView;
import indi.jfxmaker.splash.Splash;
import indi.jfxmaker.stage.AppStage;
import java.awt.SystemTray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The Class AbstractApp.
 *
 * @author Nonoas
 */
public abstract class AbstractApp extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApp.class);

    private static String[] savedArgs = new String[0];

    static Class<? extends AbstractFxmlView> savedInitialView;
    static Splash splashScreen;
    private static ConfigurableApplicationContext applicationContext;

    private static final List<Image> icons = new ArrayList<>();
    private final List<Image> defaultIcons = new ArrayList<>();

    private final CompletableFuture<Runnable> splashIsShowing;

    protected AbstractApp() {
        splashIsShowing = new CompletableFuture<>();
    }

    public static AppStage getAppStage() {
        return AppState.getAppStage();
    }

    public static Scene getScene() {
        return AppState.getScene();
    }

    public static HostServices getAppHostServices() {
        return AppState.getHostServices();
    }

    public static SystemTray getSystemTray() {
        return AppState.getSystemTray();
    }

    /**
     * @param window The FxmlView derived class that should be shown.
     * @param mode   See {@code javafx.stage.Modality}.
     */
    public static void showView(final Class<? extends AbstractFxmlView> window,
                                final Modality mode) {
        final AbstractFxmlView view = applicationContext.getBean(window);
        Stage newStage = new Stage();

        Scene newScene;
        if (view.getView().getScene() != null) {
            // This view was already shown so
            // we have a scene for it and use this one.
            newScene = view.getView().getScene();
        } else {
            newScene = new Scene(view.getView());
        }

        newStage.setScene(newScene);
        newStage.initModality(mode);
//        newStage.initOwner(getAppStage());
//        newStage.setTitle(view.getDefaultTitle());
//        newStage.initStyle(view.getDefaultStyle());

        newStage.showAndWait();
    }

    private void loadIcons(ConfigurableApplicationContext ctx) {
        try {
            final List<String> fsImages =
                PropertyReaderHelper.get(ctx.getEnvironment(), "javafx.appicons");

            if (!fsImages.isEmpty()) {
                fsImages.forEach((s) ->
                    {
                        Image img = new Image(getClass().getResource(s).toExternalForm());
                        icons.add(img);
                    }
                );
            } else { // add factory images
                icons.addAll(defaultIcons);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load icons: ", e);
        }


    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#init()
     */
    @Override
    public void init() throws Exception {
        // Load in JavaFx Thread and reused by Completable Future, but should no be a big deal.
        defaultIcons.addAll(loadDefaultIcons());
        CompletableFuture.supplyAsync(() ->
            SpringApplication.run(this.getClass(), savedArgs)
        ).whenComplete((ctx, throwable) -> {
            if (throwable != null) {
                LOGGER.error("无法加载 spring 应用程序上下文： ", throwable);
                Platform.runLater(() -> showErrorAlert(throwable));
            } else {
                Platform.runLater(() -> {
                    loadIcons(ctx);
                    launchApplicationView(ctx);
                });
            }
        }).thenAcceptBothAsync(splashIsShowing, (ctx, closeSplash) -> {
            Platform.runLater(closeSplash);
        });
    }


    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage splashStage) throws Exception {

        // 创建一个 AppStage， 主界面在此 AppStage 中展示
        AppState.setAppStage(new AppStage());
        AppState.setHostServices(this.getHostServices());

        // 设置启动屏幕
        splashStage.initStyle(StageStyle.TRANSPARENT);

        if (AbstractApp.splashScreen.visible()) {
            final Scene splashScene = new Scene(splashScreen.getParent(), Color.TRANSPARENT);
            splashStage.setScene(splashScene);
            splashStage.getIcons().addAll(defaultIcons);
            splashStage.initStyle(StageStyle.TRANSPARENT);
            beforeShowingSplash(splashStage);
            splashStage.show();
        }

        splashIsShowing.complete(() -> {
            showInitialView();
            if (AbstractApp.splashScreen.visible()) {
                splashStage.hide();
                splashStage.setScene(null);
            }
        });
    }


    /**
     * 显示初始视图。
     */
    private void showInitialView() {

        beforeInitialView(AppState.getStage(), applicationContext);

        showView(savedInitialView);
    }


    /**
     * Launch application view.
     */
    private void launchApplicationView(final ConfigurableApplicationContext ctx) {
        AbstractApp.applicationContext = ctx;
    }

    /**
     * Show view.
     *
     * @param newView the new view
     */
    public static void showView(final Class<? extends AbstractFxmlView> newView) {
        try {
            final AbstractFxmlView view = applicationContext.getBean(newView);

            AppState.getAppStage().setContentView(view.getView());

            applyEnvPropsToView();

            AppState.getAppStage().addIcons(icons);
            AppState.getAppStage().setResizable(view.getStageResizable());
            AppState.getAppStage().show();
        } catch (Throwable t) {
            LOGGER.error("无法加载应用程序： ", t);
            showErrorAlert(t);
        }
    }

    /**
     * 显示关闭应用程序的错误警报。
     *
     * @param throwable 错误原因
     */
    private static void showErrorAlert(Throwable throwable) {
        Alert alert = new Alert(AlertType.ERROR, "哎呀！发生了不可恢复的错误。\n" +
            "请联系您的软件供应商。\n\n" +
            "该应用程序将立即停止。\n\n" +
            "错误： " + throwable.getMessage());
        alert.showAndWait().ifPresent(response -> Platform.exit());
    }

    /**
     * Apply env props to view.
     */
    private static void applyEnvPropsToView() {
//        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_TITLE, String.class,
//            AppState.getStage()::setTitle);
//
//        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_WIDTH, Double.class,
//            AppState.getStage()::setWidth);
//
//        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_HEIGHT, Double.class,
//            AppState.getStage()::setHeight);
//
//        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_RESIZABLE, Boolean.class,
//            AppState.getStage()::setResizable);
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#stop()
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if (applicationContext != null) {
            applicationContext.close();
        } // else: someone did it already
    }

    /**
     * Sets the title. Allows to overwrite values applied during construction at
     * a later time.
     *
     * @param title the new title
     */
    protected static void setTitle(final String title) {
        AppState.getStage().setTitle(title);
    }

    /**
     * Launch app.
     *
     * @param appClass the app class
     * @param view     the view
     * @param args     the args
     */
    public static void launch(final Class<? extends Application> appClass,
                              final Class<? extends AbstractFxmlView> view, final String[] args) {

        launch(appClass, view, new Splash(), args);
    }

    /**
     * 启动应用程序。
     *
     * @param appClass     应用类
     * @param view         视图
     * @param splashScreen 启动图片
     * @param args         参数
     */
    public static void launch(final Class<? extends Application> appClass,
                              final Class<? extends AbstractFxmlView> view,
                              final Splash splashScreen,
                              final String[] args) {
        savedInitialView = view;
        savedArgs = args;

        if (splashScreen != null) {
            AbstractApp.splashScreen = splashScreen;
        } else {
            AbstractApp.splashScreen = new Splash();
        }

        if (SystemTray.isSupported()) {
           AppState.setSystemTray(SystemTray.getSystemTray());
        }
        Application.launch(appClass, args);
    }


    /**
     * 在 Spring 应用程序上下文和 JavaFX 平台完全初始化之后，在显示初始视图之前调用。
     * 覆盖此方法作为一个钩子，为您的应用程序添加特殊代码。
     * 特别是通过调用 AppState.getSystemTray() 并相应地修改它来添加 AWT 代码以添加系统托盘图标和行为。
     * <p>
     * By default noop.
     *
     * @param stage 可用于自定义显示前的舞台
     * @param ctx   represents spring ctx where you can loog for beans.
     */
    protected void beforeInitialView(final Stage stage, final ConfigurableApplicationContext ctx) {
    }

    protected void beforeShowingSplash(Stage splashStage) {
    }

    public Collection<Image> loadDefaultIcons() {
        return Arrays.asList(
            new Image(getClass().getResource("/icons/gear_16x16.png").toExternalForm()),
            new Image(getClass().getResource("/icons/gear_24x24.png").toExternalForm()),
            new Image(getClass().getResource("/icons/gear_36x36.png").toExternalForm()),
            new Image(getClass().getResource("/icons/gear_42x42.png").toExternalForm()),
            new Image(getClass().getResource("/icons/gear_64x64.png").toExternalForm()));
    }
}
