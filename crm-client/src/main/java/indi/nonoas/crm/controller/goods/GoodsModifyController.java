package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.config.ImageSrc;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class GoodsModifyController implements Initializable {

    private GoodsDto goodsBean = new GoodsDto();

    private final GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

    /**
     * ��Ա��Ƭ����·��
     */
    private String photoUrl;

    private Tab parentTab; // ��ǰTab
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
        // ��ʼ��CombBox
        cbb_type.getItems().addAll("��Ʒ��", "������");
        cbb_type.setValue("��Ʒ��");
    }

    @FXML // �رյ�ǰtab
    private void cancelInfo() {
        TabPane tabPane = parentTab.getTabPane();
        tabPane.getTabs().remove(parentTab);
    }

    @FXML // �ύ��Ϣ
    private void commitIfo() {
        double sellPrice = Double.parseDouble(tf_sellPrice.getText()); // 单价
        String name = tf_name.getText(); // ��Ʒ����
        String type = cbb_type.getValue();
        double quantity = Double.parseDouble(tf_quantity.getText());
        String unit = tf_unit.getText();
        double purcPrice = Double.parseDouble(tf_puchasPrice.getText());
        double minDiscount = Double.parseDouble(tf_minDiscount.getText());
        double commissionRate = Double.parseDouble(tf_commissionRate.getText());
        double commission = Double.parseDouble(tf_commission.getText());

        goodsBean.setSellPrice(sellPrice);
        goodsBean.setName(name);
        goodsBean.setType(type);
        goodsBean.setQuantity(quantity);
        goodsBean.setBaseUnit(unit);
        goodsBean.setPurchasePrice(purcPrice);
        goodsBean.setMinDiscount(minDiscount);
        goodsBean.setDeductionRate(commissionRate);
        goodsBean.setDeduction(commission);

        goodsService.update(goodsBean);

        if (chc_isClose.isSelected()) {
            cancelInfo();
        }
    }

    @FXML
    private void uploadPhoto() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg"));
        File photoFile = chooser.showOpenDialog(null);
        if (photoFile != null) {
            photoUrl = photoFile.getAbsolutePath();
            img_photo.setImage(new Image("file:" + photoUrl));
            goodsBean.setPhoto(photoUrl);
        }
    }

    /**
     * ���ݵ�ǰtab������
     */
    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

    /**
     * ���ݵ�ǰ�޸ĵ�Bean
     */
    public void setBean(GoodsDto bean) {
        this.goodsBean = bean;

        String id = goodsBean.getId();
        double sellPrice = goodsBean.getSellPrice(); // 单价
        String name = goodsBean.getName(); // ��Ʒ����
        String type = goodsBean.getType();
        double quantity = goodsBean.getQuantity();
        String unit = goodsBean.getBaseUnit();
        double purcPrice = goodsBean.getPurchasePrice();
        double minDiscount = goodsBean.getMinDiscount();
        double commissionRate = goodsBean.getDeductionRate();
        double commission = goodsBean.getDeduction();
        // ��ʾgoodsBean����Ϣ
        tf_id.setText(id);
        tf_sellPrice.setText(String.valueOf(sellPrice));
        tf_name.setText(name);
        cbb_type.setValue(type);
        tf_quantity.setText(String.valueOf(quantity));
        tf_unit.setText(unit);
        tf_puchasPrice.setText(String.valueOf(purcPrice));
        tf_minDiscount.setText(String.valueOf(minDiscount));
        tf_commissionRate.setText(String.valueOf(commissionRate));
        tf_commission.setText(String.valueOf(commission));

    }
}
