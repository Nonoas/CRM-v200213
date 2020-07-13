package indi.nonoas.crm.controller;

import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageContentBean;
import indi.nonoas.crm.dialog.GoodsSelectDialog;
import indi.nonoas.crm.dialog.MyAlert;
import indi.nonoas.crm.table.PackageContentEditTable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class PackageController implements Initializable {

    protected PackageContentEditTable pkgGoodsTable = new PackageContentEditTable();

    @FXML
    protected Button btn_upload;    //上传照片按钮

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
     * 初始化界面操作
     */
    protected abstract void initView();

    /**
     * 添加商品
     */
    @FXML
    private void addGoods() {
        //添加套餐项目的商品
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
     * 删除商品
     */
    @FXML
    private void deleteGoods() {
        pkgGoodsTable.removeData(pkgGoodsTable.getSelectedData());
    }

    /**
     * 清空商品
     */
    @FXML
    private void clearGoods() {
        pkgGoodsTable.clearData();
    }

    /**
     * 用于提交时候检查是否有未填选项
     *
     * @return 有则返回true，没有则返回false
     */
    protected boolean hasEmpty() {

        if (tf_id.getText().equals("")) {
            new MyAlert(Alert.AlertType.WARNING, "项目编号不能为空！").show();
            return true;
        } else if (tf_name.getText().equals("")) {
            new MyAlert(Alert.AlertType.WARNING, "项目名称不能为空！").show();
            return true;
        } else if (tf_money.getText().equals("")) {
            new MyAlert(Alert.AlertType.WARNING, "套餐售价不能为空！").show();
            return true;
        } else if (tf_integral.getText().equals("")) {
            new MyAlert(Alert.AlertType.WARNING, "套餐积分不能为空！").show();
            return true;
        }
        return false;

    }


}
