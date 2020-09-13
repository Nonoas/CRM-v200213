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

    private static final double IMG_SIZE = 30; // ��ͼ��ߴ�

    @Autowired
    private OrderService odrService;

    private CenterPane currentPane; // ��ǰ��������

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
    private Label label_operator; // ����Ա

    public MainController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //������̨����
        startBackgroundTask();

        LoginBean loginBean = (LoginBean) ClientSession.getAttribute("user");

        label_operator.setText("����Ա��" + loginBean.getName());

        ImageView img_shift = new ImageView(ImageSrc.SHIFT_PATH); // ����ͼ��
        ImageView img_backups = new ImageView(ImageSrc.BACKUPS_PATH); // ����ͼ��
        ImageView img_setting = new ImageView(ImageSrc.SETTING_PATH); // ����ͼ��
        ImageView img_exit = new ImageView(ImageSrc.EXIT_PATH); // �˳�ͼ��
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

    @FXML // ��ת�û����ѽ���
    private void toConsumePane() {
        if (currentPane != CenterPane.CONSUMPTION) {
            changePane(CenterPane.CONSUMPTION);
        }
    }

    @FXML // ��ת��Ա�������
    private void toVipManagerPane() {
        if (currentPane != CenterPane.VIP_MANAGER) {
            changePane(CenterPane.VIP_MANAGER);
        }
    }

    @FXML // ��ת��Ʒ�������
    private void toGoodsManagePane() {
        if (currentPane != CenterPane.GOODS_MANAGE) {
            changePane(CenterPane.GOODS_MANAGE);
        }
    }

    @FXML // ��תԱ���������
    private void toStaffManagePane() {
        new MyAlert(Alert.AlertType.INFORMATION, "�����ڴ���").show();
//        if (currentPane != CenterPane.STAFF_MANAGE) {
//            changePane(CenterPane.STAFF_MANAGE);
//        }
    }

    @FXML // ��תͳ�Ʊ������
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
     * �л�����
     *
     * @param centerPane �м����ö��
     */
    private void changePane(CenterPane centerPane) {
        currentPane = centerPane;
        bp_root.setCenter(currentPane.pane());
    }

    private void startBackgroundTask() {
//        OrderService odrService = (OrderService) SpringUtil.getBean("OrderServiceImpl");
        //ɾ���ɶ���
        new Thread(() -> {
            odrService.delete365dAgo();
            logger.debug("==================\n" +
                    "ɾ��һ��ǰ�Ķ���" +
                    "\n===================");
        }).start();
    }

    /**
     * ö�٣���������
     *
     * @author Nonoas
     */
    private enum CenterPane {
        /**
         * ���ѽ���
         */
        CONSUMPTION(new ConsumePane()),
        /**
         * ��Ա�������
         */
        VIP_MANAGER(new VipManagePane()),
        /**
         * ��Ʒ�������
         */
        GOODS_MANAGE(new GoodsManagePane()),
        /**
         * Ա���������
         */
        STAFF_MANAGE(new StaffManagePane()),
        /**
         * ͳ�Ʊ������
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
