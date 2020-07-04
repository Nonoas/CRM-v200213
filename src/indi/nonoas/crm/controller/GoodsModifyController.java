package indi.nonoas.crm.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.utils.ImageSrc;
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

    private GoodsBean goodsBean = new GoodsBean();

    private GoodsDao goodsDao = GoodsDao.getInstance();

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
        double sellPrice = Double.parseDouble(tf_sellPrice.getText()); // Ԥ�۵���
        String name = tf_name.getText(); // ��Ʒ����
        String type = cbb_type.getValue();
        double quantity = Double.parseDouble(tf_quantity.getText());
        String unit = tf_unit.getText();
        double purcPrice = Double.parseDouble(tf_puchasPrice.getText());
        double minDiscount = Double.parseDouble(tf_minDiscount.getText());
        double commissionRate = Double.parseDouble(tf_commissionRate.getText());
        double commission = Double.parseDouble(tf_commission.getText());

        goodsBean.setSell_price(sellPrice);
        goodsBean.setName(name);
        goodsBean.setType(type);
        goodsBean.setQuantity(quantity);
        goodsBean.setBase_unit(unit);
        goodsBean.setPurchas_price(purcPrice);
        goodsBean.setMin_discount(minDiscount);
        goodsBean.setDeduction_rate(commissionRate);
        goodsBean.setDeduction(commission);

        goodsDao.update(goodsBean);

        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
            cancelInfo();
        }
    }

    @FXML // �ϴ���Ƭ
    private void uploadPhoto() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ͼƬ�ļ�", "*.png", "*.jpg"));
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
    public void setBean(GoodsBean bean) {
        this.goodsBean = bean;

        String id = goodsBean.getId();
        double sellPrice = goodsBean.getSell_price(); // Ԥ�۵���
        String name = goodsBean.getName(); // ��Ʒ����
        String type = goodsBean.getType();
        double quantity = goodsBean.getQuantity();
        String unit = goodsBean.getBase_unit();
        double purcPrice = goodsBean.getPurchas_price();
        double minDiscount = goodsBean.getMin_discount();
        double commissionRate = goodsBean.getDeduction_rate();
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
