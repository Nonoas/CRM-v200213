package indi.nonoas.crm.controller.vip;

import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.service.VipService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.vip.VipModifyTab;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class VipModifyController implements Initializable {

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    private final ToggleGroup tGroup = new ToggleGroup();

    private final Logger logger = LoggerFactory.getLogger(VipModifyController.class);

    /**
     * 当前操作的bean
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
        cbb_level.getItems().addAll("普通会员", "超级会员");

    }

    /**
     * 上传照片
     */
    @FXML
    private void uploadPhoto() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg"));
        File photoFile = chooser.showOpenDialog(null);
        if (photoFile != null) {
            String photoUrl = photoFile.getAbsolutePath();
            img_photo.setImage(new Image("file:" + photoUrl));

            logger.debug("图片地址：" + img_photo.getImage().toString());

            vipBean.setPhoto(photoUrl);
        }

    }

    @FXML
    private void cancelInfo() {
        parentTab.close();
    }

    @FXML
    private void commitIfo() {
        if (!isCommittable()) {
            return;
        }

        vipBean.setId(tf_id.getText().trim());
        vipBean.setName(tf_name.getText().trim());
        vipBean.setSex((String) tGroup.getSelectedToggle().getUserData());

        if (cbb_level.getValue() != null) {
            vipBean.setCardLevel(cbb_level.getValue());
        }

        vipBean.setAddress(tf_address.getText());

        String strDiscount = tf_discount.getText().trim();
        double discount = "".equals(strDiscount) ? 1 : Double.parseDouble(strDiscount);
        discount = discount > 0 && discount <= 1 ? discount : 1;
        vipBean.setDiscount(discount);

        vipBean.setTelephone(tf_tel.getText());
        vipBean.setIdcard(tf_idcard.getText());
        if (dp_birthday.getValue() != null) {
            vipBean.setBirthday(dp_birthday.getValue().toString());
        }
        vipBean.setCareer(tf_career.getText());
        vipBean.setEmail(tf_mail.getText());
        vipBean.setOther(tf_other.getText());

//		vipBean.setPhoto("照片");

        vipService.updateInfo(vipBean);    //更新数据库

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
        if ("".equals(id) || "".equals(name) || "".equals(tel) || "".equals(level)) {
            new MyAlert(AlertType.WARNING, "会员卡号、会员姓名、联系电话、会员等级不为空！").show();
            return false;
        }
        return true;
    }

    /**
     * 通过外界传递当前的tab引用
     */
    public void setPane(VipModifyTab tab) {
        this.parentTab = tab;
    }

    /**
     * 传递bean
     *
     * @param bean 当前用户信息的VipBean
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
        // 设置显示的照片
        String photoUrl = bean.getPhoto();

        if (!StringUtils.isEmpty(photoUrl)) {
            img_photo.setImage(new Image("file:" + bean.getPhoto()));
        }
        // 设置显示出生日期
        String birthday = vipBean.getBirthday();
        if (birthday != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(birthday, fmt);
            dp_birthday.setValue(date);
        }
        String sex = bean.getSex();

        ObservableList<Toggle> toggles = tGroup.getToggles();
        for (Toggle rBtn : toggles) {
            if (sex != null && rBtn.getUserData().equals(sex)) // 设置显示的性别
            {
                rBtn.setSelected(true);
            }
        }
    }

}
