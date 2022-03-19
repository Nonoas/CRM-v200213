package indi.nonoas.crm;

import indi.jfxmaker.AbstractApp;
import indi.jfxmaker.stage.AppStage;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.view.MainStageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;
import java.util.LinkedList;

/**
 * crm 启动类
 *
 * @author : Nonoas
 * @time : 2020-09-01 13:53
 */
@MapperScan("indi.nonoas.crm.dao")
@SpringBootApplication
public class App extends AbstractApp {

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
        appStage.setMinHeight(750);
        appStage.setResizable(true);
        showView(MainStageView.class);
    }
}
