package indi.nonoas.crm;

import indi.jfxmaker.stage.AppStage;
import indi.nonoas.crm.component.CustomSplash;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.view.MainStageView;
import indi.nonoas.crm.view.WelcomeView;
import indi.jfxmaker.AbstractApp;
import java.util.Collection;
import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * crm 启动类
 *
 * @author : Nonoas
 * @time : 2020-09-01 13:53
 */
@MapperScan("indi.nonoas.crm.dao")
@SpringBootApplication
public class ApplicationStarter extends AbstractApp {

    public static void main(String[] args) {
        launch(ApplicationStarter.class, WelcomeView.class, new CustomSplash(), args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
    }

    @Override
    public Collection<Image> loadDefaultIcons() {
        LinkedList<Image> images = new LinkedList<>();
        images.add(new Image(ImageSrc.lOGO_PATH));
        return images;
    }

    /**
     * 跳转到主界面
     */
    public static void toMainStageView() {
        AppStage appStage = getAppStage();
        appStage.close();
        appStage.setMaximized(true);
        appStage.setMinWidth(1200);
        appStage.setMinHeight(800);
        appStage.setResizable(true);
        showView(MainStageView.class);
    }
}
