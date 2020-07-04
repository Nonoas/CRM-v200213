package indi.nonoas.crm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import indi.nonoas.crm.app.ConsumPane;
import indi.nonoas.crm.app.GoodsManagePane;
import indi.nonoas.crm.app.StaffManagePane;
import indi.nonoas.crm.app.StatPane;
import indi.nonoas.crm.app.VipManagePane;
import indi.nonoas.crm.utils.ImageSrc;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController implements Initializable {

	private static final double IMGSIZE = 30; // ��ͼ��ߴ�

	private String username;

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
		ImageView img_shift = new ImageView(ImageSrc.SHIFT_PATH); // ����ͼ��
		ImageView img_backups = new ImageView(ImageSrc.BACKUPS_PATH); // ����ͼ��
		ImageView img_setting = new ImageView(ImageSrc.SETTING_PATH); // ����ͼ��
		ImageView img_exit = new ImageView(ImageSrc.EXIT_PATH); // �˳�ͼ��
		img_shift.setFitHeight(IMGSIZE);
		img_shift.setFitWidth(IMGSIZE);
		img_backups.setFitHeight(IMGSIZE);
		img_backups.setFitWidth(IMGSIZE);
		img_setting.setFitHeight(IMGSIZE);
		img_setting.setFitWidth(IMGSIZE);
		img_exit.setFitHeight(IMGSIZE-1);
		img_exit.setFitWidth(IMGSIZE-1);
		btn_shift.setGraphic(img_shift);
		btn_backups.setGraphic(img_backups);
		btn_setting.setGraphic(img_setting);
		btn_exit.setGraphic(img_exit);
		toConsumPane();
	}

	@FXML // ��ת�û����ѽ���
	private void toConsumPane() {
		if (currentPane != CenterPane.CONSUMPTION) {
			currentPane = CenterPane.CONSUMPTION;
			bp_root.setCenter(new ConsumPane());
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
		Application app=new Application() {
			@Override
			public void start(Stage primaryStage) throws Exception {
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
	 * ��Stage������
	 * 
	 * @param username
	 */
	public void setInfos(String username) {
		this.username = username;
		label_operator.setText("����Ա��" + this.username);
	}

	/**
	 * ö�٣���������
	 * 
	 * @author Nonoas
	 *
	 */
	private enum CenterPane {
		/** ���ѽ��� */
		CONSUMPTION,
		/** ��Ա������� */
		VIP_MANAGER,
		/** ��Ʒ������� */
		GOODS_MANAGE,
		/** Ա��������� */
		STAFF_MANAGE,
		/** ͳ�Ʊ������ */
		STATISTICS,
	}
}
