package indi.nonoas.crm.app.consume;

import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.controller.ConsumeDialogController;
import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.stage.ControllableStage;
import indi.nonoas.crm.view.annotation.FXML;
import javafx.scene.image.Image;

/**
 * @author : Nonoas
 * @time : 2020-08-10 17:39
 */
@FXML("/fxml/consume_dialog.fxml")
@CSS("css/application.css")
public class ConsumeDialog extends ControllableStage {

    public ConsumeDialog() {
        initView();
    }

    private void initView() {
        setTitle("∂©µ•Ω·À„");
        getIcons().add(new Image(ImageSrc.lOGO_PATH));
        ConsumeDialogController controller = (ConsumeDialogController) getController();
        controller.setStage(this);
        setResizable(false);
    }

}
