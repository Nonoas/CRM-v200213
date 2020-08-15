package indi.nonoas.crm.app;

import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.annotation.StageProperty;
import indi.nonoas.crm.view.stage.ControllableStage;
import javafx.scene.image.Image;

/**
 * @author : Nonoas
 * @time : 2020-08-10 23:47
 */
@StageProperty(title = "客户管理系统")
@FXML("/fxml/welcome.fxml")
@CSS("css/welcome.css")
public class WelcomeStage extends ControllableStage {

    public WelcomeStage() {
        getIcons().add(new Image(ImageSrc.lOGO_PATH));
        setResizable(false);
    }

}
