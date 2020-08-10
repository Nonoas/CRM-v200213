package indi.nonoas.crm.app;

import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.annotation.FXML;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author : Nonoas
 * @time : 2020-08-10 23:47
 */

@FXML("/fxml/welcome.fxml")
@CSS("css/welcome.css")
public class WelcomeStage extends Stage {

    public WelcomeStage() {
        setTitle("客户管理系统");
        getIcons().add(new Image(ImageSrc.lOGO_PATH));
        setResizable(false);
    }

}
