package indi.nonoas.crm.app;

import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.stage.ControllableStage;
import javafx.scene.image.Image;

@FXML("/fxml/main.fxml")
@CSS("css/application.css")
public class MainStage extends ControllableStage {

    public MainStage() {
        initUI();
    }

    private void initUI() {
        setTitle("�ͻ�����ϵͳ");
        getIcons().add(new Image(ImageSrc.lOGO_PATH));
        // ������С���
        setMinHeight(800);
        setMinWidth(1280);
        setMaximized(true);
        show();
    }
}
