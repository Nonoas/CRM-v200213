package indi.nonoas.crm.app;

import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.dao.OrderDao;
import indi.nonoas.crm.view.annotation.CSS;
import indi.nonoas.crm.view.annotation.FXML;
import indi.nonoas.crm.view.annotation.StageProperty;
import indi.nonoas.crm.view.stage.ControllableStage;
import javafx.scene.image.Image;

@StageProperty(title = "客户管理系统")
@FXML("/fxml/main.fxml")
@CSS("css/application.css")
public class MainStage extends ControllableStage {

    public MainStage() {
        initUI();
        startBackgroundTask();
    }

    private void initUI() {
        getIcons().add(new Image(ImageSrc.lOGO_PATH));
        // 设置最小宽高
        setMinHeight(800);
        setMinWidth(1280);
        show();
        setMaximized(true);
    }

    private void startBackgroundTask() {
        new Thread(() -> {
            OrderDao.getInstance().delete365DaysAgo();
            System.out.println("删除一年前的订单");
        }).start();
    }
}
