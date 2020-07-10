package indi.nonoas.crm.controller;

import indi.nonoas.crm.app.GoodsAddTab;
import indi.nonoas.crm.app.GoodsModifyTab;
import indi.nonoas.crm.app.PackageAddTab;
import indi.nonoas.crm.app.PackageModifyTab;
import indi.nonoas.crm.bean.GoodsBean;
import indi.nonoas.crm.bean.PackageBean;
import indi.nonoas.crm.dao.GoodsDao;
import indi.nonoas.crm.dao.GoodsTypeDao;
import indi.nonoas.crm.dao.PackageDao;
import indi.nonoas.crm.dialog.MyAlert;
import indi.nonoas.crm.table.GoodsInfoTable;
import indi.nonoas.crm.table.PackageContentTable;
import indi.nonoas.crm.table.PackageTable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GoodsManageController implements Initializable {

    private final GoodsInfoTable table = new GoodsInfoTable(); // 商品信息表

    private final GoodsDao goodsDao = GoodsDao.getInstance();

    private PackageBean packageBean;
    @FXML
    private ScrollPane scrollPane; // 表格的父容器
    @FXML
    private TextField tf_id;
    @FXML
    private TabPane tp_rootPane; // 根布局
    @FXML
    private ComboBox<String> cb_type; // 商品类型

    @FXML
    private TextField tf_pkgID;
    @FXML
    private TextField tf_moneyLow;
    @FXML
    private TextField tf_moneyHigh;

    @FXML
    private ScrollPane sp_package; // 项目表格的Pane
    @FXML
    private ScrollPane sp_pkg_content; // 项目内容的Pane

    public GoodsManageController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
        initPkgView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        // 初始化表格
        scrollPane.setContent(table);

        // 初始化“商品分类”下拉列表框
        LinkedList<String> goodsTypes = new GoodsTypeDao().selectAllNames();
        cb_type.getItems().add("所有类型");
        if (goodsTypes != null) {
            for (String str : goodsTypes)
                cb_type.getItems().add(str);
        }
        cb_type.setValue("所有类型");
    }

    /**
     * 初始化套餐界面
     */
    private void initPkgView() {
        // 初始化表格内容
        sp_package.setContent(pkgTable);
        sp_pkg_content.setContent(pkgContTable);
    }

    private void initData() {

    }

    ////////////// 商品管理 ////////////////////

    @FXML    //查询商品
    private void find() {
        String str = "%" + tf_id.getText().trim() + "%";
        String str0 = cb_type.getValue().equals("所有类型") ? "" : cb_type.getValue();
        String type = "%" + str0 + "%";
        ArrayList<GoodsBean> listVipBeans = goodsDao.selectByFiltrate(str, str, type);
        if (listVipBeans != null) {
            table.clearData();
            for (GoodsBean bean : listVipBeans)
                table.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的商品！").show();
        }
    }

    @FXML    //修改商品信息
    private void updateInfo() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "修改商品信息";
        for (Tab tab : obList) { // 遍历判断该tab是否已经添加
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
                return;
            }
        }
        Tab tab = new GoodsModifyTab(table.getSelectedData());
        tab.setUserData(DATA);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    @FXML    //添加商品事件
    private void addGoods() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "添加商品";
        for (Tab tab : obList) { // 遍历判断该tab是否已经添加
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
                return;
            }

        }
        Tab tab = new GoodsAddTab();
        tab.setUserData(DATA);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    @FXML    //删除商品信息
    private void deleteInfo() {
        GoodsBean bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }
        String id = "编号:" + bean.getId();
        String name = "名称:" + bean.getName();
        MyAlert alert = new MyAlert(AlertType.CONFIRMATION, "是否确定删除该商品的信息？\n(" + id + "，" + name + ")");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            goodsDao.deleteByID(bean);
        }
    }


    ///////////////////  套餐项目 /////////////////////////

    private final PackageContentTable pkgContTable = new PackageContentTable(); //套餐商品表格

    private final PackageTable pkgTable = new PackageTable(pkgContTable);   //套餐表格

    @FXML
    private void findPackage() {
        String id = "%" + tf_pkgID.getText().trim() + "%";
        String money1 = tf_moneyLow.getText().trim();
        String money2 = tf_moneyHigh.getText().trim();
        double mLow = money1.equals("") ? 0 : Double.parseDouble(money1);
        double mHigh = money2.equals("") ? 9999999 : Double.parseDouble(money2);

        ArrayList<PackageBean> list = PackageDao.getInstance().findByFilter(id, id, mLow, mHigh);
        if (list == null) {
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的项目信息！").show();
        } else {
            pkgTable.clearData();
            for (PackageBean bean : list)
                pkgTable.addBean(bean);
        }
    }

    @FXML
    private void deletePackage() {
        PackageBean bean = pkgTable.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }
        String id = "编号:" + bean.getId();
        String name = "名称:" + bean.getName();
        MyAlert alert = new MyAlert(AlertType.CONFIRMATION, "是否确定删除该项目的信息？\n(" + id + "，" + name + ")");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            pkgTable.removeData(bean);
            PackageDao.getInstance().deleteByID(bean);
        }
    }

    @FXML
    private void addPackage() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        // 遍历判断该Tab是否已经添加,如果已添加，则直接切换到该Tab
        final String DATA = "添加项目";
        for (Tab tab : obList) {
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
                return;
            }
        }

        PackageAddTab tab = new PackageAddTab();
        tab.setUserData(DATA);
        tp_rootPane.getTabs().add(tab);
        tp_rootPane.getSelectionModel().select(tab);

    }

    //修改套餐信息
    @FXML
    private void modifyPackage() {
        PackageBean bean = pkgTable.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条套餐项目信息！").show();
            return;
        }
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        // 遍历判断该Tab是否已经添加,如果已添加，则直接切换到该Tab
        final String DATA = "修改项目信息";
        for (Tab tab : obList) {
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
                return;
            }
        }

        PackageModifyTab tab = new PackageModifyTab(pkgTable.getSelectedData());
        tab.setUserData(DATA);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }
}
