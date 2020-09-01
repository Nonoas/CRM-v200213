package indi.nonoas.crm;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import indi.nonoas.crm.app.WelcomeView;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.view.CustomSplash;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.text.html.ImageView;
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
        stage.setTitle("客户管理系统");
        super.start(stage);
    }

    @Override
    public Collection<Image> loadDefaultIcons() {
        LinkedList<Image> images = new LinkedList<>();
        images.add(new Image(ImageSrc.lOGO_PATH));
        return images;
    }
}
