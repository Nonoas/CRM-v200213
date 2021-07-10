package indi.nonoas.crm.controller.vip;

import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.service.VipService;
import indi.nonoas.crm.utils.Log;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.vip.VipModifyTab;
import javafx.collections.ObservableList;
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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class VipModifyController implements Initializable {

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    private final ToggleGroup tGroup = new ToggleGroup();

    /**
     * ��ǰ������bean
     */
    private VipInfoDto vipBean;

    private VipModifyTab parentTab;

    @FXML
    private HBox HBox_root;
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
    private TextField tf_discount;
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
    private void cancelInfo() {
        parentTab.close();
    }

    @FXML
    private void commitIfo() {
        if (!isCommittable())
            return;

        vipBean.setId(tf_id.getText().trim());
        vipBean.setName(tf_name.getText().trim());
        vipBean.setSex((String) tGroup.getSelectedToggle().getUserData());

        if (cbb_level.getValue() != null)
            vipBean.setCardLevel(cbb_level.getValue());

        vipBean.setAddress(tf_address.getText());

        String strDiscount = tf_discount.getText().trim();
        double discount = strDiscount.equals("") ? 1 : Double.parseDouble(strDiscount);
        discount = discount > 0 && discount <= 1 ? discount : 1;
        vipBean.setDiscount(discount);

        vipBean.setTelephone(tf_tel.getText());
        vipBean.setIdcard(tf_idcard.getText());
        if (dp_birthday.getValue() != null)
            vipBean.setBirthday(dp_birthday.getValue().toString());
        vipBean.setCareer(tf_career.getText());
        vipBean.setEmail(tf_mail.getText());
        vipBean.setOther(tf_other.getText());
//		vipBean.setPhoto("��Ƭ");

        vipService.updateInfo(vipBean);    //�������ݿ�

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
        if (id.equals("") || name.equals("") || tel.equals("") || level.equals("")) {
            new MyAlert(AlertType.WARNING, "��Ա���š���Ա进价ϵ�绰����Ա�ȼ���Ϊ�գ�").show();
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
    public void setBean(VipInfoDto bean) {
        this.vipBean = bean;
        tf_mail.setText(vipBean.getEmail());
        tf_career.setText(vipBean.getCareer());
        tf_other.setText(vipBean.getOther());
        tf_tel.setText(vipBean.getTelephone());
        tf_address.setText(vipBean.getAddress());
        tf_name.setText(vipBean.getName());
        tf_discount.setText(String.valueOf(vipBean.getDiscount()));
        tf_id.setText(vipBean.getId());
        tf_idcard.setText(vipBean.getIdcard());
        cbb_level.setValue(vipBean.getCardLevel());
        // ������ʾ����Ƭ
        String photoUrl = bean.getPhoto();
        Log.i(this, photoUrl);
        if (photoUrl != null && !photoUrl.equals("")) {
            img_photo.setImage(new Image("file:" + bean.getPhoto()));
        }
        // ������ʾ进价
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
