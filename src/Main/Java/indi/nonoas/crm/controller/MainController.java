package indi.nonoas.crm.controller;

import de.felixroske.jfxsupport.FXMLController;
import indi.nonoas.crm.app.StaffManagePane;
import indi.nonoas.crm.app.consume.ConsumePane;
import indi.nonoas.crm.app.goods.GoodsManagePane;
import indi.nonoas.crm.app.vip.StatPane;
import indi.nonoas.crm.app.vip.VipManagePane;
import indi.nonoas.crm.beans.LoginBean;
import indi.nonoas.crm.common.ClientSession;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.view.alert.MyAlert;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class MainController implements Initializable {

    private final Logger logger = Logger.getLogger(MainController.class);

    private static final double IMG_SIZE = 30; // 按图标尺寸

    @Autowired
    private OrderService odrService;

    private CenterPane currentPane; // 当前界面名称

    @FXML
    private BorderPane bp_root;

    @FXML
    private Button btn_shift;

    @FXML
    private Button btn_setting;

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_backups;

    @FXML
    private Label label_operator; // 操作员

    public MainController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //启动后台任务
        startBackgroundTask();

        LoginBean loginBean = (LoginBean) ClientSession.getAttribute("user");

        label_operator.setText("操作员：" + loginBean.getName());

        ImageView img_shift = new ImageView(ImageSrc.SHIFT_PATH); // 换班图标
        ImageView img_backups = new ImageView(ImageSrc.BACKUPS_PATH); // 换班图标
        ImageView img_setting = new ImageView(ImageSrc.SETTING_PATH); // 设置图标
        ImageView img_exit = new ImageView(ImageSrc.EXIT_PATH); // 退出图标
        img_shift.setFitHeight(IMG_SIZE);
        img_shift.setFitWidth(IMG_SIZE);
        img_backups.setFitHeight(IMG_SIZE);
        img_backups.setFitWidth(IMG_SIZE);
        img_setting.setFitHeight(IMG_SIZE);
        img_setting.setFitWidth(IMG_SIZE);
        img_exit.setFitHeight(IMG_SIZE - 1);
        img_exit.setFitWidth(IMG_SIZE - 1);
        btn_shift.setGraphic(img_shift);
        btn_backups.setGraphic(img_backups);
        btn_setting.setGraphic(img_setting);
        btn_exit.setGraphic(img_exit);
        toConsumePane();
    }

    @FXML // 跳转用户消费界面
    private void toConsumePane() {
        if (currentPane != CenterPane.CONSUMPTION) {
            changePane(CenterPane.CONSUMPTION);
        }
    }

    @FXML // 跳转会员管理界面
    private void toVipManagerPane() {
        if (currentPane != CenterPane.VIP_MANAGER) {
            changePane(CenterPane.VIP_MANAGER);
        }
    }

    @FXML // 跳转商品管理界面
    private void toGoodsManagePane() {
        if (currentPane != CenterPane.GOODS_MANAGE) {
            changePane(CenterPane.GOODS_MANAGE);
        }
    }

    @FXML // 跳转员工管理界面
    private void toStaffManagePane() {
        new MyAlert(Alert.AlertType.INFORMATION, "敬请期待！").show();
//        if (currentPane != CenterPane.STAFF_MANAGE) {
//            changePane(CenterPane.STAFF_MANAGE);
//        }
    }

    @FXML // 跳转统计报表界面
    private void toStatPane() {
        if (currentPane != CenterPane.STATISTICS) {
            changePane(CenterPane.STATISTICS);
        }
    }

    @FXML
    private void linkAuthor() {
        Application app = new Application() {
            @Override
            public void start(Stage primaryStage) {
            }
        };
        app.getHostServices().showDocument("https://me.csdn.net/weixin_44155115");
        try {
            app.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换界面
     *
     * @param centerPane 中间面板枚举
     */
    private void changePane(CenterPane centerPane) {
        currentPane = centerPane;
        bp_root.setCenter(currentPane.pane());
    }

    private void startBackgroundTask() {
//        OrderService odrService = (OrderService) SpringUtil.getBean("OrderServiceImpl");
        //删除旧订单
        new Thread(() -> {
            odrService.delete365dAgo();
            logger.debug("==================\n" +
                    "删除一年前的订单" +
                    "\n===================");
        }).start();
    }

    /**
     * 枚举：界面名称
     *
     * @author Nonoas
     */
    private enum CenterPane {
        /**
         * 消费界面
         */
        CONSUMPTION(new ConsumePane()),
        /**
         * 会员管理界面
         */
        VIP_MANAGER(new VipManagePane()),
        /**
         * 商品管理界面
         */
        GOODS_MANAGE(new GoodsManagePane()),
        /**
         * 员工管理界面
         */
        STAFF_MANAGE(new StaffManagePane()),
        /**
         * 统计报表界面
         */
        STATISTICS(new StatPane());

        private final Pane pane;

        CenterPane(Pane pane) {
            this.pane = pane;
        }

        public Pane pane() {
            return pane;
        }
    }
}
