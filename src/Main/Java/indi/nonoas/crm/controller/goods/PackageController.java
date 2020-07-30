package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageContentBean;
import indi.nonoas.crm.view.dialog.GoodsSelectDialog;
import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.app.table.PackageContentEditTable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class PackageController implements Initializable {

    /**
     * ��Ʒ�б�����
     */
    protected PackageContentEditTable pkgGoodsTable = new PackageContentEditTable();

    /**
     * �ϴ���ť
     */
    @FXML
    protected Button btn_upload;

    @FXML
    protected TextField tf_other;

    @FXML
    protected CheckBox chc_isClose;

    @FXML
    protected HBox hBox_root;

    @FXML
    protected TextField tf_money;

    @FXML
    protected TextField tf_name;

    @FXML
    protected ScrollPane sp_goods;

    @FXML
    protected TextField tf_id;

    @FXML
    protected TextField tf_integral;

    @FXML
    protected TextField tf_min_discount;

    @FXML
    protected ImageView img_photo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    /**
     * ��ʼ���������
     */
    protected abstract void initView();

    /**
     * ������Ʒ
     */
    @FXML
    protected void addGoods() {
        //�����ײ���Ŀ����Ʒ
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

    /**
     * ɾ����Ʒ
     */
    @FXML
    protected void deleteGoods() {
        pkgGoodsTable.removeData(pkgGoodsTable.getSelectedData());
    }

    /**
     * �����Ʒ
     */
    @FXML
    protected void clearGoods() {
        pkgGoodsTable.clearData();
    }

    /**
     * �����ύʱ�����Ƿ���δ��ѡ��
     *
     * @return ���򷵻�true��û���򷵻�false
     */
    protected boolean hasEmpty() {

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

}