package indi.nonoas.crm.controller.goods;

import indi.nonoas.crm.app.goods.GoodsAddTab;
import indi.nonoas.crm.app.goods.GoodsInfoTable;
import indi.nonoas.crm.app.goods.GoodsModifyTab;
import indi.nonoas.crm.app.pkg.PackageAddTab;
import indi.nonoas.crm.app.pkg.PackageContentTable;
import indi.nonoas.crm.app.pkg.PackageModifyTab;
import indi.nonoas.crm.app.pkg.PackageTable;
import indi.nonoas.crm.pojo.dto.GoodsDto;
import indi.nonoas.crm.pojo.PackageBean;
import indi.nonoas.crm.dao.my_orm_dao.PackageContentDao;
import indi.nonoas.crm.dao.my_orm_dao.PackageDao;
import indi.nonoas.crm.service.GoodsService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.alert.MyAlert;
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

    GoodsService goodsService = (GoodsService) SpringUtil.getBean("GoodsServiceImpl");

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
        LinkedList<String> goodsTypes = goodsService.selectGoodsTypes();
        cb_type.getItems().addAll("所有类型", "产品类", "服务类");
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

    //===========================================================================
    //                            商品管理
    //===========================================================================

    @FXML    //查询商品
    private void find() {
        String keyWord = tf_id.getText().trim();
        String type = cb_type.getValue().equals("所有类型") ? "" : cb_type.getValue();
        ArrayList<GoodsDto> listVipBeans = goodsService.selectByFiltrate(keyWord, keyWord, type);
        if (listVipBeans != null)
            table.replaceData(listVipBeans);
        else
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的商品！").show();
    }

    @FXML    //修改商品信息
    private void updateGoods() {

        GoodsDto bean = table.getSelectedData();

        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }

        ObservableList<Tab> tabList = tp_rootPane.getTabs();
        final String DATA = "修改商品信息";
        for (Tab tab : tabList) { // 遍历判断该tab是否已经添加
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
                return;
            }
        }
        Tab tab = new GoodsModifyTab(table.getSelectedData());
        tab.setUserData(DATA);
        tabList.add(tab);
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
        GoodsDto bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }
        String id = bean.getId();      //商品编号
        String name = bean.getName();  //商品名称
        MyAlert alert = new MyAlert(AlertType.CONFIRMATION,
                String.format("是否确定删除该商品的信息？\n[ 编号：%s，名称：%s ]", id, name));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            goodsService.deleteByID(id);
        }
    }

    //===========================================================================
    //                            套餐项目
    //===========================================================================

    private final PackageContentTable pkgContTable = new PackageContentTable(); //套餐商品表格

    private final PackageTable pkgTable = new PackageTable(pkgContTable);   //套餐表格

    @FXML
    private void findPackage() {
        String id = "%" + tf_pkgID.getText().trim() + "%";
        String money1 = tf_moneyLow.getText().trim();
        String money2 = tf_moneyHigh.getText().trim();
        double mLow = money1.equals("") ? 0 : Double.parseDouble(money1);
        double mHigh = money2.equals("") ? 99999999 : Double.parseDouble(money2);

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
            PackageContentDao.getInstance().deleteById(bean.getId());
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
