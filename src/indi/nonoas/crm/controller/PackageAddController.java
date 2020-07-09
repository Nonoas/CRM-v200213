package indi.nonoas.crm.controller;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageBean;
import indi.nonoas.crm.dao.PackageContentDao;
import indi.nonoas.crm.dao.PackageDao;
import indi.nonoas.crm.dialog.GoodsSelectDialog;
import indi.nonoas.crm.dialog.MyAlert;
import indi.nonoas.crm.table.PackageContentEditTable;
import indi.nonoas.crm.bean.PackageContentBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PackageAddController implements Initializable {

    private final PackageContentEditTable pkgGoodsTable = new PackageContentEditTable();    //�ײ�����Ʒ��Ϣ��

    private Tab parentTab;  //��ǰtab������
    @FXML
    private Button btn_upload;    //�ϴ���Ƭ��ť

    @FXML
    private TextField tf_other;

    @FXML
    private CheckBox chc_isClose;

    @FXML
    private HBox hbox_root;

    @FXML
    private TextField tf_money;

    @FXML
    private TextField tf_name;

    @FXML
    private ScrollPane sp_goods;

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_integral;

    @FXML
    private TextField tf_min_discount;

    @FXML
    private ImageView img_photo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sp_goods.setContent(pkgGoodsTable);
    }

    /**
     * ȡ����ǰ��д����Ϣ
     */
    @FXML
    private void cancelInfo() {
        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }
        //TODO û�����������������
    }


    /**
     * �ύ��Ϣ
     */
    @FXML
    private void commitIfo() {

        if (hasEmpty())  //����Ƿ���δ��д�ı�����Ŀ
            return;

        //�ײ���Ϣ
        PackageBean packageBean = new PackageBean();
        packageBean.setId(tf_id.getText());
        packageBean.setName(tf_name.getText());
        packageBean.setMoney_cost(Double.parseDouble(tf_money.getText()));
        packageBean.setIntegral_cost(Integer.parseInt(tf_integral.getText()));
        String minDiscount = tf_min_discount.getText();
        if (!minDiscount.equals("")) {
            packageBean.setMin_discount(Double.parseDouble(minDiscount));
        }
        packageBean.setOther(tf_other.getText());
        //�����ײ���Ϣ�����ݿ�
        PackageDao.getInstance().insert(packageBean);
        //�ײ�������Ϣ
        ArrayList<PackageContentBean> packageContentBeans = pkgGoodsTable.getAllData();
        for (PackageContentBean p : packageContentBeans) {
            p.setPkg_id(packageBean.getId());
        }
        PackageContentDao.getInstance().insertInfos(packageContentBeans);

        if (chc_isClose.isSelected()) { // ���ѡ�����ύ��رգ���رյ�ǰtab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }
    }

    @FXML
    private void addGoods() {
        //����ײ���Ŀ����Ʒ
        GoodsSelectDialog dialog = new GoodsSelectDialog();
        dialog.showAndWait();
        ObservableList<GoodsBean> beans = dialog.getSelectGoods();
        if (beans != null && beans.size() != 0) {
            for (GoodsBean goodsBean : beans) {
                PackageContentBean packageContentBean = new PackageContentBean();
                packageContentBean.setPkg_id(tf_id.getText());
                packageContentBean.setGoods_id(goodsBean.getId());
                packageContentBean.setGoods_amount(1);
                pkgGoodsTable.addBean(packageContentBean);
            }
        }
    }

    @FXML
    private void deleteGoods() {
        pkgGoodsTable.removeData(pkgGoodsTable.getSelectedData());
    }

    @FXML
    private void clearGoods() {

    }

    /**
     * �����ύʱ�����Ƿ���δ��ѡ��
     *
     * @return ���򷵻�true��û���򷵻�false
     */
    private boolean hasEmpty() {
        if (tf_id.getText().equals("")) {
            new MyAlert(Alert.AlertType.WARNING, "��Ŀ��Ų���Ϊ�գ�").show();
            return true;
        } else if (tf_name.getText().equals("")) {
            new MyAlert(Alert.AlertType.WARNING, "��Ŀ���Ʋ���Ϊ�գ�").show();
            return true;
        } else if (tf_money.getText().equals("")) {
            new MyAlert(Alert.AlertType.WARNING, "�ײ��ۼ۲���Ϊ�գ�").show();
            return true;
        } else if (tf_integral.getText().equals("")) {
            new MyAlert(Alert.AlertType.WARNING, "�ײͻ��ֲ���Ϊ�գ�").show();
            return true;
        }
        return false;
    }

    /**
     * ���ݵ�ǰtab������
     */
    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

}
