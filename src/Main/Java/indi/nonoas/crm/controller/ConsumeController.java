package indi.nonoas.crm.controller;

import indi.nonoas.crm.app.goods.GoodsConsumeTable;
import indi.nonoas.crm.app.goods.GoodsSingleSelectTable;
import indi.nonoas.crm.app.pkg.PackageConsumeTable;
import indi.nonoas.crm.app.pkg.PackageSingleSelectTable;
import indi.nonoas.crm.app.vip.VipAddTab;
import indi.nonoas.crm.app.vip.VipInfoTable;
import indi.nonoas.crm.bean.PackageBean;
import indi.nonoas.crm.bean.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import indi.nonoas.crm.dao.VipLevelDao;
import indi.nonoas.crm.service.OrderService;
import indi.nonoas.crm.view.alert.MyAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ConsumeController implements Initializable {

    /**
     * 会员信息DAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstance();
    @FXML
    private TabPane tp_rootPane;
    @FXML
    private Label lb_cardState;
    @FXML
    private Label lb_id;
    @FXML
    private Label lb_integral;
    @FXML
    private Label lb_name;
    @FXML
    private Label lb_cardLevel;
    @FXML
    private TextField tf_find;
    @FXML
    private TextField tf_findInfo;
    @FXML
    private ScrollPane sp_userInfo;
    @FXML
    private ComboBox<String> cb_disType;

    private final VipInfoTable tv_vipInfo = new VipInfoTable(); // 会员信息表

    public ConsumeController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    @FXML // 查找信息
    private void inquireVIP() {
        String str = tf_find.getText().trim();
        if (str.equals(""))
            return;
        VipBean vipBean = vipInfoDao.getInfoByIdOrName(str, str);
        if (vipBean != null) {
            lb_cardState.setText("可用");
            lb_id.setText(vipBean.getId());
            lb_integral.setText(String.valueOf(vipBean.getIntegral()));
            lb_cardLevel.setText(vipBean.getCard_level());
            lb_name.setText(vipBean.getName());
        } else {
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的会员！").show();
        }
    }

    @FXML
    private void inquireVIPInfo() {
        String str = "%" + tf_findInfo.getText().trim() + "%";
        String str0 = cb_disType.getValue().equals("全部类型") ? "" : cb_disType.getValue();
        String disType = str0 + "%";
        if (str.equals("%%"))
            return;
        ArrayList<VipBean> listVipBeans = vipInfoDao.selectByFiltrate(str, str, disType);
        if (listVipBeans != null) {
            tv_vipInfo.clearData();
            for (VipBean bean : listVipBeans)
                tv_vipInfo.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的会员！").show();
        }
    }

    @FXML // 显示全部信息
    private void showAll() {
        tv_vipInfo.showAllInfos();
    }

    @FXML // 清空展示的信息
    private void clearInfo() {
        tf_find.setText("");
        lb_cardState.setText("--");
        lb_id.setText("--");
        lb_integral.setText("--");
        lb_cardLevel.setText("--");
        lb_name.setText("--");
    }

    @FXML // 添加会员信息
    private void addVip() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "添加会员";
        for (Tab tab : obList) { // 遍历判断该tab是否已经添加
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tab.setClosable(true);
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
                return;
            }
        }
        VipAddTab tab = new VipAddTab();
        tab.setUserData(DATA);
        tp_rootPane.getTabs().add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    private void initData() {
    }

    private void initView() {
        tv_vipInfo.showAllInfos();
        sp_userInfo.setContent(tv_vipInfo);
        // 从数据库读出所用会员等级，并初始化ComboBox
        LinkedList<String> listName = new VipLevelDao().selectAllNames();
        cb_disType.getItems().add("全部类型");
        for (String str : listName) {
            cb_disType.getItems().add(str);
        }
        cb_disType.setValue("全部类型");

        initGoodsTab();        //初始化商品消费面板
        initPackageTab();       //初始化套餐消费面板
    }

    //===========================================================================
    //                             商品消费
    //===========================================================================

    private final GoodsConsumeTable gc_table = new GoodsConsumeTable();

    private final GoodsSingleSelectTable goodsSelectTable = new GoodsSingleSelectTable(gc_table);
    @FXML
    private BorderPane pt_borderPane;
    @FXML
    private ScrollPane pt_sp_goods;
    @FXML
    private Label pt_order_price;
    @FXML
    private Label pt_order_dis_price;
    @FXML
    private Label pt_integral;
    @FXML
    private TextField shp_orderNum;
    @FXML
    private TextField shp_orderDate;

    private void initGoodsTab() {
        pt_borderPane.setCenter(gc_table);
        pt_sp_goods.setContent(goodsSelectTable);

        //设置表格的监听事件
        gc_table.getEventHandler().addEvent(() -> {
            if (shp_orderNum.getText().equals("")) {
                shp_orderNum.setText(OrderService.goodsOrderNum());
            }
            if (shp_orderDate.getText().equals("")) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                shp_orderDate.setText(sdf.format(LocalDateTime.now()));
            }
            pt_order_price.setText(String.format("%.2f", gc_table.getSumPrice()));
            pt_order_dis_price.setText("0.00");
            pt_integral.setText("0");

        });
    }


    @FXML
    private void clearGoodsTable() {
        gc_table.clearData();
    }

    @FXML
    private void orderPay() {
        new MyAlert(AlertType.INFORMATION, "结算成功").show();
    }


    //===========================================================================
    //                            套餐消费
    //===========================================================================

    /**
     * 套餐单选表格
     */
    private final PackageSingleSelectTable pkgSelectTable = new PackageSingleSelectTable();

    /**
     * 套餐消费表格
     */
    private final PackageConsumeTable pcTable = new PackageConsumeTable();
    @FXML
    private BorderPane tc_borderPane;
    @FXML
    private ScrollPane tc_sp_goods;
    @FXML
    private Label tc_order_price;
    @FXML
    private Label tc_order_dis_price;
    @FXML
    private Label tc_integral;
    @FXML
    private TextField tc_orderNum;
    @FXML
    private TextField tc_orderDate;

    private void initPackageTab() {
        tc_sp_goods.setContent(pkgSelectTable);
        tc_borderPane.setCenter(pcTable);
        //设置监听
        pcTable.getEventHandler().addEvent(() -> {
            if (tc_orderNum.getText().equals("")) {
                tc_orderNum.setText(OrderService.packageOrderNum());
            }
            if (tc_orderDate.getText().equals("")) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                tc_orderDate.setText(sdf.format(LocalDateTime.now()));
            }
            tc_order_price.setText(String.format("%.2f", pcTable.getSumPrice()));
            tc_order_dis_price.setText("0.00");
            tc_integral.setText("0");
        });

        pkgSelectTable.getEventHandler().addEvent(() -> {
            PackageBean bean = pkgSelectTable.getSelectedData();
            if (bean != null)
                pcTable.addBean(bean);
        });
    }

    @FXML
    private void clearPackageTable() {
        pcTable.clearData();
    }
}
