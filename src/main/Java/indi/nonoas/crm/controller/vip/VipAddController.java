package indi.nonoas.crm.controller.vip;

import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.service.VipLvService;
import indi.nonoas.crm.service.VipService;
import indi.nonoas.crm.utils.SpringUtil;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class VipAddController implements Initializable {

    private final ToggleGroup tGroup = new ToggleGroup();

    private Tab parentTab;

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    private final VipLvService vipLvService = (VipLvService) SpringUtil.getBean("VipLvServiceImpl");

    /**
     * ��Ա��Ƭ����·��
     */
    private String photoUrl;
    @FXML
    private HBox hbox_root;
    @FXML
    private CheckBox chc_isClose;
    /**
     * ���ð�ť
     */
    @FXML
    private CheckBox cbb_forever;
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
    private TextField tf_discount;
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
        List<String> cbbItems = vipLvService.listAllNames();
        for (String item : cbbItems) {
            cbb_level.getItems().add(item);
        }
        cbb_level.setValue(cbbItems.get(0));
        // ����ѡ�����
        cbb_forever.selectedProperty().addListener(isForeverListener);
    }

    /**
     * 进价ʱ���Ƿ�Ϊ����
     */
    private final ChangeListener<Boolean> isForeverListener = (observable, oldValue, newValue) -> {
        dpick_expiration.setDisable(newValue);
        if (newValue) {
            dpick_expiration.setValue(null);
        }
    };

    @FXML // �Զ����ɻ�Ա����
    private void autoSetId() {
        if (!tf_id.getText().equals("")) {
            new MyAlert(AlertType.WARNING, "进价Ѿ���д�Ļ�Ա���ţ�").show();
            return;
        }
        String newID = vipService.generateVipID();
        tf_id.setText(newID);
    }

    @FXML // �ϴ���Ա��Ƭ
    private void uploadPhoto() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ͼƬ�ļ�", "*.png", "*.jpg"));
        File photoFile = chooser.showOpenDialog(null);
        photoUrl = photoFile.getAbsolutePath();
        img_photo.setImage(new Image("file:" + photoUrl));
    }

    @FXML    //�رյ�ǰ���
    private void cancelInfo() {
        TabPane tabPane = parentTab.getTabPane();
        tabPane.getTabs().remove(parentTab);
    }

    @FXML
    private void commitIfo() {
        if (!isCommittable()) {
            return;
        }
        VipInfoDto bean = new VipInfoDto();
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

        String strDiscount = tf_discount.getText().trim();
        double discount = strDiscount.equals("") ? Double.parseDouble(strDiscount) : 1;
        discount = discount > 0 && discount <= 1 ? discount : 1;
        bean.setDiscount(discount);

        bean.setAddress(tf_address.getText());
        bean.setTelephone(tf_tel.getText());
        bean.setIdcard(tf_idcard.getText());
        if (dp_birthday.getValue() != null)
            bean.setBirthday(dp_birthday.getValue().toString());
        bean.setCareer(tf_career.getText());
        bean.setEmail(tf_mail.getText());
        bean.setOther(tf_other.getText());
        bean.setPhoto(photoUrl); // ��Ƭ

        vipService.insertInfo(bean);

        new MyAlert(AlertType.CONFIRMATION, "��Ա��Ϣ��ӳɹ���").showAndWait();
        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
            cancelInfo();
        }
    }

    /**
     * �ж��Ƿ�����ύ��Ϣ
     *
     * @return ����Ϊtrue进价Ϊfalse
     */
    private boolean isCommittable() {
        String id = tf_id.getText().trim(); // ����
        String name = tf_name.getText().trim(); // ����
        String tel = tf_tel.getText().trim(); // �绰����
        String level = cbb_level.getValue(); // ��Ա�ȼ�
        LocalDate date = dp_addDate.getValue();
        if (id.equals("") || name.equals("") || tel.equals("") || level.equals("") || date == null) {
            new MyAlert(AlertType.WARNING, "��Ա���š���Ա进价ϵ�绰����Ա�ȼ������ʱ�䲻��Ϊ�գ�").show();
            return false;
        }
        return true;
    }

    /**
     * ͨ����紫�ݵ�ǰ��tab����
     */
    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

}
