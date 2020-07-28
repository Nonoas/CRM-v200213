package indi.nonoas.crm.controller.vip;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import indi.nonoas.crm.view.alert.MyAlert;
import indi.nonoas.crm.app.vip.VipAddTab;
import indi.nonoas.crm.table.VipInfoTable;
import indi.nonoas.crm.app.vip.VipModifyTab;
import indi.nonoas.crm.bean.VipBean;
import indi.nonoas.crm.dao.VipInfoDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class VipManageController implements Initializable {

    /**
     * 会员信息DAO
     */
    private final VipInfoDao vipInfoDao = VipInfoDao.getInstence();

    private VipInfoTable table;

    @FXML
    private Tab tab_manage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TabPane tp_rootPane;
    @FXML
    private TextField tf_findInfo;
    @FXML
    private ComboBox<String> cbb_level;    //会员等级
    @FXML
    private DatePicker dpk_from;    //入会时间范围（起始）
    @FXML
    private DatePicker dpk_to;    //入会时间范围（结束）

    public VipManageController() {
        initData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        table = new VipInfoTable();
        scrollPane.setContent(table);

        cbb_level.getItems().addAll("所有等级", "普通会员", "超级会员");
        cbb_level.setValue("所有等级");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String str_from = "1900-01-01";
        LocalDate from = LocalDate.parse(str_from, df);
        LocalDate to = LocalDate.now();
        dpk_from.setValue(from);    //入会时间上限
        dpk_to.setValue(to);    //入会时间下限

//        		FadeTransition ft = new FadeTransition();
//        		ft.setNode(tp_rootPane);
//        		ft.setDuration(Duration.seconds(1));
//        		ft.setFromValue(0);
//        		ft.setToValue(1);
//        		ft.setAutoReverse(true);
//        		ft.play();
    }

    private void initData() {

    }

    @FXML
    private void findVIP() {

        String idOrName = "%" + tf_findInfo.getText().trim() + "%";    //卡号或姓名
        String str0 = cbb_level.getValue().equals("所有等级") ? "" : cbb_level.getValue();
        String level = str0 + "%";    //会员等级
        String dateFrom = dpk_from.getValue().toString();    //时间上限
        String dateTo = dpk_to.getValue().toString();    //时间下限

        ArrayList<VipBean> listVipBeans = vipInfoDao.selectByFiltrate(idOrName, idOrName, level, dateFrom, dateTo);
        if (listVipBeans != null) {
            table.clearData();
            for (VipBean bean : listVipBeans)
                table.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的会员！").show();
        }
    }

    @FXML // 添加会员信息
    private void addVip() {
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "添加会员";
        for (Tab tab : obList) { // 遍历判断该tab是否已经添加
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
                return;
            }

        }
        Tab tab = new VipAddTab();
        tab.setUserData(DATA);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

    @FXML
    /** 删除会员信息 */
    private void deleteVip() {
        VipBean bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }
        String id = "卡号:" + bean.getId();
        String name = "姓名:" + bean.getName();
        MyAlert alert = new MyAlert(AlertType.CONFIRMATION, "是否确定删除该用户的信息？\n(" + id + "，" + name + ")");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            vipInfoDao.deleteByID(bean);
        }
    }

    /**
     * 修改会员信息
     */
    @FXML
    private void modifyVip() {
        VipBean bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }
        ObservableList<Tab> obList = tp_rootPane.getTabs();
        final String DATA = "修改会员信息";
        for (Tab tab : obList) { // 遍历判断该tab是否已经添加
            String userDate = (String) tab.getUserData();
            if (userDate != null && userDate.equals(DATA)) {
                tp_rootPane.getSelectionModel().select(tab); // 如果已经添加则显示该tab并返回
                return;
            }

        }
        VipModifyTab tab = new VipModifyTab(bean);
        tab.setUserData(DATA);
        tab.setPreTab(tab_manage);
        obList.add(tab);
        tp_rootPane.getSelectionModel().select(tab);
    }

}
