package indi.nonoas.crm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import indi.nonoas.crm.bean.LoginBean;
import indi.nonoas.crm.global.ClientSession;
import indi.nonoas.crm.app.ConsumePane;
import indi.nonoas.crm.app.goods.GoodsManagePane;
import indi.nonoas.crm.app.StaffManagePane;
import indi.nonoas.crm.app.StatPane;
import indi.nonoas.crm.app.vip.VipManagePane;
import indi.nonoas.crm.config.ImageSrc;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController implements Initializable {

    private static final double IMG_SIZE = 30; // ��ͼ��ߴ�

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
    private Button btn_consumption;

    @FXML
    private Label label_operator; // ����Ա

    public MainController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            currentPane = CenterPane.CONSUMPTION;
            bp_root.setCenter(new ConsumePane());
        }
    }

    @FXML // ��ת��Ա�������
    private void toVipManagerPane() {
        if (currentPane != CenterPane.VIP_MANAGER) {
            currentPane = CenterPane.VIP_MANAGER;
            bp_root.setCenter(new VipManagePane());
        }
    }

    @FXML // ��ת��Ʒ�������
    private void toGoodsManagePane() {
        if (currentPane != CenterPane.GOODS_MANAGE) {
            currentPane = CenterPane.GOODS_MANAGE;
            bp_root.setCenter(new GoodsManagePane());
        }
    }

    @FXML // ��תԱ���������
    private void toStaffManagePane() {
        if (currentPane != CenterPane.STAFF_MANAGE) {
            currentPane = CenterPane.STAFF_MANAGE;
            bp_root.setCenter(new StaffManagePane());
        }
    }

    @FXML // ��תͳ�Ʊ������
    private void toStatPane() {
        if (currentPane != CenterPane.STATISTICS) {
            currentPane = CenterPane.STATISTICS;
            bp_root.setCenter(new StatPane());
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
     * ö�٣���������
     *
     * @author Nonoas
     */
    private enum CenterPane {
        /**
         * ���ѽ���
         */
        CONSUMPTION,
        /**
         * ��Ա�������
         */
        VIP_MANAGER,
        /**
         * ��Ʒ�������
         */
        GOODS_MANAGE,
        /**
         * Ա���������
         */
        STAFF_MANAGE,
        /**
         * ͳ�Ʊ������
         */
        STATISTICS,
    }
}
