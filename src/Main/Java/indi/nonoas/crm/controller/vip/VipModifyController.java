package indi.nonoas.crm.controller.vip;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import indi.nonoas.crm.app.vip.VipModifyTab;
import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.bean.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import indi.nonoas.crm.utils.ImageSrc;
import indi.nonoas.crm.utils.Log;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class VipModifyController implements Initializable {

    /**
     * ��Ա��ϢDAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstence();

    private final ToggleGroup tGroup = new ToggleGroup();

    /**
     * ��ǰ������bean
     */
    private VipBean vipBean;

    private VipModifyTab parentTab;

    @FXML
    private HBox hbox_root;
    @FXML
    private CheckBox chc_isClose;
    @FXML
    private RadioButton rbtn_expiration;
    @FXML
    private RadioButton rbtn_secret;
    @FXML
    private DatePicker dp_birthday;
    @FXML
    private TextField tf_mail;
    @FXML
    private TextField tf_career;
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
        cbb_level.getItems().addAll("��ͨ��Ա", "������Ա");

    }

    @FXML    //�ϴ���Ƭ
    private void uploadPhoto() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ͼƬ�ļ�", "*.png", "*.jpg"));
        File photoFile = chooser.showOpenDialog(null);
        if (photoFile != null) {
            String photoUrl = photoFile.getAbsolutePath();
            img_photo.setImage(new Image("file:" + photoUrl));
            vipBean.setPhoto(photoUrl);
        }

    }

    @FXML
    private void selectPreferred() {

    }

    @FXML
    private void selectStaff() {

    }

    @FXML
    private void cancelInfo() {
        parentTab.close();
    }

    @FXML
    private void commitIfo() {
        if (!isCommitable())
            return;
        vipBean.setId(tf_id.getText().trim());
        vipBean.setName(tf_name.getText().trim());
        vipBean.setSex((String) tGroup.getSelectedToggle().getUserData());

        if (cbb_level.getValue() != null)
            vipBean.setCard_level(cbb_level.getValue());

        vipBean.setAddress(tf_address.getText());
        vipBean.setTelephone(tf_tel.getText());
        vipBean.setIdcard(tf_idcard.getText());
        if (dp_birthday.getValue() != null)
            vipBean.setBirthday(dp_birthday.getValue().toString());
        vipBean.setCareer(tf_career.getText());
        vipBean.setEmail(tf_mail.getText());
        vipBean.setOther(tf_other.getText());
//		vipBean.setPhoto("��Ƭ");

        vipInfoDao.updateInfo(vipBean);    //�������ݿ�

        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
            cancelInfo();
        }
    }

    /**
     * �ж��Ƿ�����ύ��Ϣ
     *
     * @return ����Ϊtrue��������Ϊfalse
     */
    private boolean isCommitable() {
        String id = tf_id.getText().trim(); // ����
        String name = tf_name.getText().trim(); // ����
        String tel = tf_tel.getText().trim(); // �绰����
        String level = cbb_level.getValue(); // ��Ա�ȼ�
        if (id.equals("") || name.equals("") || tel.equals("") || level.equals("")) {
            new MyAlert(AlertType.WARNING, "��Ա���š���Ա��������ϵ�绰����Ա�ȼ���Ϊ�գ�").show();
            return false;
        }
        return true;
    }

    /**
     * ͨ����紫�ݵ�ǰ��tab����
     */
    public void setPane(VipModifyTab tab) {
        this.parentTab = tab;
    }

    /**
     * ����bean
     *
     * @param bean ��ǰ�û���Ϣ��VipBean
     */
    public void setBean(VipBean bean) {
        this.vipBean = bean;
        tf_mail.setText(vipBean.getEmail());
        tf_career.setText(vipBean.getCareer());
        tf_other.setText(vipBean.getOther());
        tf_tel.setText(vipBean.getTelephone());
        tf_address.setText(vipBean.getAddress());
        tf_name.setText(vipBean.getName());
        tf_id.setText(vipBean.getId());
        tf_idcard.setText(vipBean.getIdcard());
        cbb_level.setValue(vipBean.getCard_level());
        // ������ʾ����Ƭ
        String photoUrl = bean.getPhoto();
        Log.i(this, photoUrl);
        if (photoUrl != null && !photoUrl.equals("")) {
            img_photo.setImage(new Image("file:" + bean.getPhoto()));
        }
        // ������ʾ��������
        String birthday = vipBean.getBirthday();
        if (birthday != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(birthday, fmt);
            dp_birthday.setValue(date);
        }
        String sex = bean.getSex();

        ObservableList<Toggle> toggles = tGroup.getToggles();
        for (Toggle rBtn : toggles) {
            if (sex != null && rBtn.getUserData().equals(sex)) // ������ʾ���Ա�
                rBtn.setSelected(true);
        }
    }

}
