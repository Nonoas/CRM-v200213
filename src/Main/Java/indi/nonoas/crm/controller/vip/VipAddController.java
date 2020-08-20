package indi.nonoas.crm.controller.vip;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.beans.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import indi.nonoas.crm.dao.VipLevelDao;
import indi.nonoas.crm.config.ImageSrc;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class VipAddController implements Initializable {

	/** ��Ա��ϢDAO*/
	private VipInfoDao vipInfoDao= VipInfoDao.getInstance();
	
	private final ToggleGroup tGroup = new ToggleGroup();

	private Tab parentTab;

	/** ��Ա��Ƭ����·�� */
	private String photoUrl;
	@FXML
	private HBox hbox_root;
	@FXML
	private CheckBox chc_isClose;
	/** ���ð�ť */
	@FXML
	private CheckBox cbb_foever;
	@FXML
	private RadioButton rbtn_expiration;
	@FXML
	private TextField tf_operator;
	@FXML
	private TextField tf_fee;
	@FXML
	private RadioButton rbtn_secret;
	@FXML
	private DatePicker dp_birthday;
	@FXML
	private TextField tf_mail;
	@FXML
	private TextField tf_balance;
	@FXML
	private TextField tf_career;
	@FXML
	private DatePicker dp_addDate;
	@FXML
	private RadioButton rbtn_man;
	@FXML
	private TextField tf_other;
	@FXML
	private TextField tf_tel;
	@FXML
	private RadioButton rbtn_women;
	@FXML
	private TextField tf_address;
	@FXML
	private ComboBox<String> cbb_level;
	@FXML
	private TextField tf_name;
	@FXML
	private DatePicker dpick_expiration;
	@FXML
	private TextField tf_id;
	@FXML
	private TextField tf_referrer;
	@FXML
	private TextField tf_integral;
	@FXML
	private ImageView img_photo;
	@FXML
	private TextField tf_idcard;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ����ѡ��ť����һ��
		rbtn_man.setToggleGroup(tGroup);
		rbtn_man.setUserData("��");
		rbtn_women.setToggleGroup(tGroup);
		rbtn_women.setUserData("Ů");
		rbtn_secret.setToggleGroup(tGroup);
		rbtn_secret.setUserData("����");
		// ���ó�ʼͼƬ
		img_photo.setImage(new Image(ImageSrc.PHOTO_PATH));
		// ��ʼ��CombBox
		LinkedList<String> cbbItems = new VipLevelDao().selectAllNames();
		for (String item : cbbItems)
			cbb_level.getItems().add(item);
		cbb_level.setValue(cbbItems.get(0));
		// ����ѡ�����
		cbb_foever.selectedProperty().addListener(isForeverListener);
	}

	/**
	 * ��������ʱ���Ƿ�Ϊ����
	 */
	private ChangeListener<Boolean> isForeverListener = (observable, oldValue, newValue) -> {
		dpick_expiration.setDisable(newValue);
		if (newValue)
			dpick_expiration.setValue(null);
	};

	@FXML // �Զ����ɻ�Ա����
	private void autoSetId() {
		String maxId = vipInfoDao.selectMaxId();
		int len = maxId.length();
		int intID = Integer.parseInt(maxId) + 1;
		String strID = String.format("%0" + len + "d", intID);
		tf_id.setText(strID);
	}

	@FXML // �ϴ���Ա��Ƭ
	private void uploadPhoto() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ͼƬ�ļ�", "*.png", "*.jpg"));
		File photoFile = chooser.showOpenDialog(null);
		photoUrl = photoFile.getAbsolutePath();
		img_photo.setImage(new Image("file:" + photoUrl));
	}

	@FXML	//�رյ�ǰ���
	private void cancelInfo() {
		TabPane tabPane = parentTab.getTabPane();
		tabPane.getTabs().remove(parentTab);
	}

	@FXML
	private void commitIfo() {
		if (!isCommittable())
			return;
		VipBean bean = new VipBean();
		bean.setId(tf_id.getText().trim());
		bean.setName(tf_name.getText().trim());
		bean.setSex((String) tGroup.getSelectedToggle().getUserData());
		if (dp_addDate.getValue() != null)
			bean.setAdmissionDate(dp_addDate.getValue().toString());

		if (cbb_level.getValue() != null)
			bean.setCardLevel(cbb_level.getValue());

		String strIntegral = tf_integral.getText().trim();
		int iIntegral = strIntegral.equals("") ? 0 : Integer.parseInt(strIntegral);
		bean.setIntegral(iIntegral);

		String strBalance = tf_balance.getText().trim();
		double dBalance = strBalance.equals("") ? 0 : Double.parseDouble(strBalance);
		bean.setBalance(dBalance);

		bean.setAddress(tf_address.getText());
		bean.setTelephone(tf_tel.getText());
		bean.setIdcard(tf_idcard.getText());
		if (dp_birthday.getValue() != null)
			bean.setBirthday(dp_birthday.getValue().toString());
		bean.setCareer(tf_career.getText());
		bean.setEmail(tf_mail.getText());
		bean.setOther(tf_other.getText());
		bean.setPhoto(photoUrl); // ��Ƭ

		vipInfoDao.insertInfo(bean);

		new MyAlert(AlertType.CONFIRMATION, "��Ա��Ϣ��ӳɹ���").showAndWait();
		if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
			cancelInfo();
		}
	}

	/**
	 * �ж��Ƿ�����ύ��Ϣ
	 * 
	 * @return ����Ϊtrue��������Ϊfalse
	 */
	private boolean isCommittable() {
		String id = tf_id.getText().trim(); // ����
		String name = tf_name.getText().trim(); // ����
		String tel = tf_tel.getText().trim(); // �绰����
		String level = cbb_level.getValue(); // ��Ա�ȼ�
		// TODO ����Ƿ�����ύ
		if (id.equals("") || name.equals("") || tel.equals("") || level.equals("")) {
			new MyAlert(AlertType.WARNING, "��Ա���š���Ա��������ϵ�绰����Ա�ȼ�����Ϊ�գ�").show();
			return false;
		}
		return true;
	}

	/** ͨ����紫�ݵ�ǰ��tab���� */
	public void setPane(Tab tab) {
		this.parentTab = tab;
	}

}
