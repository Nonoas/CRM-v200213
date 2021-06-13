package indi.nonoas.crm.controller.vip;

import indi.nonoas.crm.component.alert.MyAlert;
import indi.nonoas.crm.controller.MainController;
import indi.nonoas.crm.pojo.dto.VipInfo;
import indi.nonoas.crm.service.VipService;
import indi.nonoas.crm.utils.JXLUtil;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.view.vip.VipAddTab;
import indi.nonoas.crm.view.vip.VipInfoTable;
import indi.nonoas.crm.view.vip.VipModifyTab;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class VipManageController implements Initializable {

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    private VipInfoTable table;

    @FXML
    private ScrollPane scrollPane;
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

        List<VipInfo> listVipBeans = vipService.selectByDateFiltrate(idOrName, idOrName, level, dateFrom, dateTo);
        if (listVipBeans != null) {
            table.clearData();
            for (VipInfo bean : listVipBeans)
                table.addBean(bean);
        } else {
            new MyAlert(AlertType.INFORMATION, "没有找到您查询的会员！").show();
        }
    }

    @FXML // 添加会员信息
    private void addVip() {
        MainController.addTab(new VipAddTab());
    }

    /**
     * 删除会员信息
     */
    @FXML
    private void deleteVip() {
        VipInfo bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }
        String id = "卡号:" + bean.getId();
        String name = "姓名:" + bean.getName();
        MyAlert alert = new MyAlert(AlertType.CONFIRMATION, "是否确定删除该用户的信息？\n[ " + id + "，" + name + " ]");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table.removeData(bean);
            vipService.deleteByID(bean.getId());
        }
    }

    /**
     * 修改会员信息
     */
    @FXML
    private void modifyVip() {
        VipInfo bean = table.getSelectedData();
        if (bean == null) {
            new MyAlert(AlertType.INFORMATION, "请先选择一条数据！").show();
            return;
        }
        MainController.addTab(new VipModifyTab(bean));
    }

    @FXML
    private void printInfo() {
        File file = new File("D:/会员信息.xls");
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<TableColumn<VipInfo, ?>> columns = table.getColumns();
        ObservableList<VipInfo> items = table.getItems();
        List<Object> contentList = new ArrayList<>(items);
        String[] titles = new String[columns.size()];
        String[] fieldNames = {"id", "admissionDate", "name", "sex", "cardLevel", "balance", "cumulative", "address", "integral", "telephone", "idcard", "birthday", "career", "email", "other"};
        for (int i = 0; i < titles.length; i++) {
            titles[i] = columns.get(i).getText();
        }
        boolean hasPrint = JXLUtil.exportExcel(titles, fieldNames, contentList, os);
        if (hasPrint) {
            new MyAlert(AlertType.INFORMATION, "打印成功！\n文件保存位置为：" + file.getAbsolutePath()).show();
        } else {
            new MyAlert(AlertType.INFORMATION, "打印失败！").show();
        }
    }

}
