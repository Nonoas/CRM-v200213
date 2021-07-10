package indi.nonoas.crm.controller.goods;

import java.net.URL;
import java.util.ResourceBundle;

import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.config.ImageSrc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GoodsAddController implements Initializable {

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    private Tab parentTab;
    @FXML
    private TextField tf_unit;
    @FXML
    private TextField tf_puchasPrice;
    @FXML
    private TextField tf_quantity;
    @FXML
    private TextField tf_minDiscount;
    @FXML
    private TextField tf_commission;
    @FXML
    private Button btn_upload;
    @FXML
    private CheckBox chc_isClose;
    @FXML
    private TextField tf_commissionRate;
    @FXML
    private ComboBox<String> cbb_type;
    @FXML
    private TextField tf_sellPrice;
    @FXML
    private HBox hbox_root;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_id;
    @FXML
    private ImageView img_photo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ���ó�ʼͼƬ
        img_photo.setImage(new Image(ImageSrc.PHOTO_PATH));
        //��ʼ��CombBox
        cbb_type.getItems().addAll("��Ʒ��", "������");
        cbb_type.setValue("��Ʒ��");
    }

    @FXML
    private void uploadPhoto() {

    }

    @FXML
    private void cancelInfo() {
        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
            return;
        }
        tf_sellPrice.setText("");
        tf_quantity.setText("");
        tf_unit.setText("");
        tf_puchasPrice.setText("");
        tf_minDiscount.setText("");
        tf_commissionRate.setText("");
        tf_commission.setText("");
        tf_name.setText("");
        tf_id.setText("");
    }

    @FXML
    private void commitIfo() {
        if (!isCommitable())
            return;
        GoodsDto bean = new GoodsDto();
        bean.setId(tf_id.getText().trim());
        bean.setName(tf_name.getText().trim());

        String sp = tf_sellPrice.getText().trim();    //单价
        bean.setSellPrice(Double.parseDouble(sp));

        String pp = tf_puchasPrice.getText().trim();    //进价
        double pur_price = pp.equals("") ? 0 : Double.parseDouble(pp);
        bean.setPurchasePrice(pur_price);

        String quan = tf_quantity.getText().trim();    //��ʼ���
        double quantity = quan.equals("") ? 0 : Double.parseDouble(quan);
        bean.setQuantity(quantity);

        String md = tf_minDiscount.getText().trim();    //��С�ۿ�
        double min_dis = md.equals("") ? 0 : Double.parseDouble(md);
        bean.setMinDiscount(min_dis);

        String de = tf_commission.getText().trim();    //��ɽ��
        double comison = de.equals("") ? 0 : Double.parseDouble(de);
        bean.setDeduction(comison);

        String deRate = tf_commissionRate.getText().trim();    //��ɱ���
        double com_rate = deRate.equals("") ? 0 : Double.parseDouble(deRate);
        bean.setDeductionRate(com_rate);

        bean.setBaseUnit(tf_unit.getText().trim());    //������λ

        bean.setType(cbb_type.getValue());

//		bean.setPhoto(photo);
        goodsService.insertInfo(bean);

        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }
    }

    /**
     * �ж��Ƿ�����ύ��Ϣ
     *
     * @return ����Ϊtrue进价Ϊfalse
     */
    private boolean isCommitable() {
        String id = tf_id.getText().trim(); // ���
        String name = tf_name.getText().trim(); // ����
        String sellPrice = tf_sellPrice.getText().trim(); // 单价
        String level = cbb_type.getValue(); // ��Ʒ����
        if (id.equals("") || name.equals("") || sellPrice.equals("") || level.equals("")) {
            new MyAlert(AlertType.WARNING, "��Ʒ��š���Ʒ���ơ�Ԥ�۵��ۡ进价��Ϊ�գ�").show();
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
