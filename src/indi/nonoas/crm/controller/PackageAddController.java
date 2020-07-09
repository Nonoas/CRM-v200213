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

    private final PackageContentEditTable pkgGoodsTable = new PackageContentEditTable();    //套餐内商品信息表

    private Tab parentTab;  //当前tab的引用
    @FXML
    private Button btn_upload;    //上传照片按钮

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
     * 取消当前填写的信息
     */
    @FXML
    private void cancelInfo() {
        if (chc_isClose.isSelected()) { // 如果选择了提交后关闭，则关闭当前tab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }
        //TODO 没勾上则清空所有内容
    }


    /**
     * 提交信息
     */
    @FXML
    private void commitIfo() {

        if (hasEmpty())  //检查是否有未填写的必填项目
            return;

        //套餐信息
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
        //插入套餐信息到数据库
        PackageDao.getInstance().insert(packageBean);
        //套餐内容信息
        ArrayList<PackageContentBean> packageContentBeans = pkgGoodsTable.getAllData();
        for (PackageContentBean p : packageContentBeans) {
            p.setPkg_id(packageBean.getId());
        }
        PackageContentDao.getInstance().insertInfos(packageContentBeans);

        if (chc_isClose.isSelected()) { // 如果选择了提交后关闭，则关闭当前tab
            TabPane tabPane = parentTab.getTabPane();
            tabPane.getTabs().remove(parentTab);
        }
    }

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

    @FXML
    private void deleteGoods() {
        pkgGoodsTable.removeData(pkgGoodsTable.getSelectedData());
    }

    @FXML
    private void clearGoods() {

    }

    /**
     * 用于提交时候检查是否有未填选项
     *
     * @return 有则返回true，没有则返回false
     */
    private boolean hasEmpty() {
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

    /**
     * 传递当前tab的引用
     */
    public void setPane(Tab tab) {
        this.parentTab = tab;
    }

}
