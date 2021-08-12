package indi.nonoas.crm.controller.vip;

import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.controller.MainController;
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

    private String photoUrl;
    @FXML
    private HBox hbox_root;
    @FXML
    private CheckBox chc_isClose;

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

        rbtn_man.setToggleGroup(tGroup);
        rbtn_man.setUserData("男");
        rbtn_women.setToggleGroup(tGroup);
        rbtn_women.setUserData("女");
        rbtn_secret.setToggleGroup(tGroup);
        rbtn_secret.setUserData("保密");
        img_photo.setImage(new Image(ImageSrc.PHOTO_PATH));
        List<String> cbbItems = vipLvService.listAllNames();
        for (String item : cbbItems) {
            cbb_level.getItems().add(item);
        }
        cbb_level.setValue(cbbItems.get(0));

        cbb_forever.selectedProperty().addListener(isForeverListener);
    }

    /**
     * 永久会员监听
     */
    private final ChangeListener<Boolean> isForeverListener = (observable, oldValue, newValue) -> {
        dpick_expiration.setDisable(newValue);
        if (newValue) {
            dpick_expiration.setValue(null);
        }
    };

    /**
     * 自动生成会员卡号
     */
    @FXML
    private void autoSetId() {
        if (!"".equals(tf_id.getText())) {
            new MyAlert(AlertType.WARNING, "请先清空填写的会员卡号！").show();
            return;
        }
        String newID = vipService.generateVipID();
        tf_id.setText(newID);
    }

    @FXML
    private void uploadPhoto() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg"));
        File photoFile = chooser.showOpenDialog(null);
        photoUrl = photoFile.getAbsolutePath();
        img_photo.setImage(new Image("file:" + photoUrl));
    }

    @FXML
    private void cancelInfo() {
        MainController.removeTab(parentTab);
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
        if (dp_addDate.getValue() != null) {
            bean.setAdmissionDate(dp_addDate.getValue().toString());
        }

        if (cbb_level.getValue() != null) {
            bean.setCardLevel(cbb_level.getValue());
        }

        String strIntegral = tf_integral.getText().trim();
        int iIntegral = "".equals(strIntegral) ? 0 : Integer.parseInt(strIntegral);
        bean.setIntegral(iIntegral);

        String strBalance = tf_balance.getText().trim();
        double dBalance = "".equals(strBalance) ? 0 : Double.parseDouble(strBalance);
        bean.setBalance(dBalance);

        String strDiscount = tf_discount.getText().trim();
        double discount = "".equals(strDiscount) ? Double.parseDouble(strDiscount) : 1;
        discount = discount > 0 && discount <= 1 ? discount : 1;
        bean.setDiscount(discount);

        bean.setAddress(tf_address.getText());
        bean.setTelephone(tf_tel.getText());
        bean.setIdcard(tf_idcard.getText());
        if (dp_birthday.getValue() != null) {
            bean.setBirthday(dp_birthday.getValue().toString());
        }
        bean.setCareer(tf_career.getText());
        bean.setEmail(tf_mail.getText());
        bean.setOther(tf_other.getText());
        bean.setPhoto(photoUrl);

        vipService.insertInfo(bean);

        new MyAlert(AlertType.CONFIRMATION, "会员新增成功！").showAndWait();
        if (chc_isClose.isSelected()) {
            cancelInfo();
        }
    }

    /**
     * 判断是否能提交
     *
     * @return 可以提交：true
     */
    private boolean isCommittable() {
        String id = tf_id.getText().trim();
        String name = tf_name.getText().trim();
        String tel = tf_tel.getText().trim();
        String level = cbb_level.getValue();
        LocalDate date = dp_addDate.getValue();
        if ("".equals(id) || "".equals(name) || "".equals(tel) || "".equals(level) || date == null) {
            new MyAlert(AlertType.WARNING, "会员卡号，姓名，电话，会员等级和入会日期不能为空！").show();
            return false;
        }
        return true;
    }

    /**
     * 传递当前tab到controller
     *
     * @param tab 当前tab
     */
    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

}
