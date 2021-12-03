package indi.nonoas.crm;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import indi.nonoas.crm.view.WelcomeView;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.component.CustomSplash;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author : Nonoas
 * @time : 2020-09-01 13:53
 */
@MapperScan("indi.nonoas.crm.dao")
@SpringBootApplication
public class ApplicationStarter extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(ApplicationStarter.class, WelcomeView.class, new CustomSplash(), args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("店铺管理系统");
        stage.setResizable(false);
        super.start(stage);
    }

    @Override
    public Collection<Image> loadDefaultIcons() {
        LinkedList<Image> images = new LinkedList<>();
        images.add(new Image(ImageSrc.lOGO_PATH));
        return images;
    }

//    /**
//     * 跳转到主界面
//     */
//    public static void toMainStageView() {
//        Stage stage = getStage();
//        stage.close();
//        stage.setMaximized(true);
//        stage.setMinWidth(1280);
//        stage.setMinHeight(820);
//        stage.setResizable(true);
//        showView(MainStageView.class);
//    }
}
