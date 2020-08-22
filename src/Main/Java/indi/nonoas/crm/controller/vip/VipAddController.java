package indi.nonoas.crm.controller.vip;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

import indi.nonoas.crm.service.UserService;
import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.beans.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import indi.nonoas.crm.dao.VipLevelDao;
import indi.nonoas.crm.config.ImageSrc;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class VipAddController implements Initializable {

    /**
     * 会员信息DAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstance();

    private final ToggleGroup tGroup = new ToggleGroup();

    private Tab parentTab;

    /**
     * 会员照片绝对路径
     */
    private String photoUrl;
    @FXML
    private HBox hbox_root;
    @FXML
    private CheckBox chc_isClose;
    /**
     * 永久按钮
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
    private TextField tf_referrer;
    @FXML
    private TextField tf_integral;
    @FXML
    private ImageView img_photo;
    @FXML
    private TextField tf_idcard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 将单选按钮绑定在一组
        rbtn_man.setToggleGroup(tGroup);
        rbtn_man.setUserData("男");
        rbtn_women.setToggleGroup(tGroup);
        rbtn_women.setUserData("女");
        rbtn_secret.setToggleGroup(tGroup);
        rbtn_secret.setUserData("保密");
        // 设置初始图片
        img_photo.setImage(new Image(ImageSrc.PHOTO_PATH));
        // 初始化CombBox
        LinkedList<String> cbbItems = new VipLevelDao().selectAllNames();
        for (String item : cbbItems)
            cbb_level.getItems().add(item);
        cbb_level.setValue(cbbItems.get(0));
        // 永久选项监听
        cbb_forever.selectedProperty().addListener(isForeverListener);
    }

    /**
     * 监听过期时间是否为永久
     */
    private final ChangeListener<Boolean> isForeverListener = (observable, oldValue, newValue) -> {
        dpick_expiration.setDisable(newValue);
        if (newValue)
            dpick_expiration.setValue(null);
    };

    @FXML // 自动生成会员卡号
    private void autoSetId() {
        if (!tf_id.getText().equals("")) {
            new MyAlert(AlertType.WARNING, "请先清除已经填写的会员卡号！").show();
            return;
        }
        String newID = UserService.generateVipID();
        tf_id.setText(newID);
    }

    @FXML // 上传会员照片
    private void uploadPhoto() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg"));
        File photoFile = chooser.showOpenDialog(null);
        photoUrl = photoFile.getAbsolutePath();
        img_photo.setImage(new Image("file:" + photoUrl));
    }

    @FXML    //关闭当前面板
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
        bean.setPhoto(photoUrl); // 照片

        vipInfoDao.insertInfo(bean);

        new MyAlert(AlertType.CONFIRMATION, "会员信息添加成功！").showAndWait();
        if (chc_isClose.isSelected()) { // 如果选择了提交后关闭，则关闭当前tab
            cancelInfo();
        }
    }

    /**
     * 判断是否可以提交信息
     *
     * @return 可以为true，不可用为false
     */
    private boolean isCommittable() {
        String id = tf_id.getText().trim(); // 卡号
        String name = tf_name.getText().trim(); // 姓名
        String tel = tf_tel.getText().trim(); // 电话号码
        String level = cbb_level.getValue(); // 会员等级
        LocalDate date = dp_addDate.getValue();
        if (id.equals("") || name.equals("") || tel.equals("") || level.equals("") || date == null) {
            new MyAlert(AlertType.WARNING, "会员卡号、会员姓名、联系电话、会员等级、入会时间不能为空！").show();
            return false;
        }
        return true;
    }

    /**
     * 通过外界传递当前的tab引用
     */
    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

}
