package indi.nonoas.crm.controller.consume;

import indi.nonoas.crm.view.vip.VipInfoTable;
import indi.nonoas.crm.pojo.dto.VipInfo;
import indi.nonoas.crm.service.UserService;
import indi.nonoas.crm.service.VipLvService;
import indi.nonoas.crm.utils.SpringUtil;
import indi.nonoas.crm.component.alert.MyAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author : Nonoas
 * @time : 2021-06-08 21:59
 */
public class VipQueryController implements Initializable {

    private final UserService userService = (UserService) SpringUtil.getBean("UserServiceImpl");

    private final VipInfoTable tv_vipInfo = new VipInfoTable(); // 会员信息表

    private final VipLvService vipLvService = (VipLvService) SpringUtil.getBean("VipLvServiceImpl");

    @FXML
    private TextField tf_findInfo;

    @FXML
    private ComboBox<String> cb_disType;

    @FXML
    private ScrollPane sp_userInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sp_userInfo.setContent(new VipInfoTable());
        cb_disType.getItems().add("全部类型");
        // 从数据库读出所用会员等级，并初始化ComboBox
        List<String> listName = vipLvService.listAllNames();
        for (String str : listName) {
            cb_disType.getItems().add(str);
        }
        cb_disType.setValue("全部类型");
    }

    @FXML
    private void inquireVIPInfo() {
        String str = tf_findInfo.getText().trim();
        String disType = cb_disType.getValue().equals("全部类型") ? "" : cb_disType.getValue();
        if (str.equals(""))
            return;
        ArrayList<VipInfo> listVipBeans = userService.selectByFiltrate(str, str, disType);
        if (listVipBeans != null) {
            tv_vipInfo.clearData();
            for (VipInfo bean : listVipBeans)
                tv_vipInfo.addBean(bean);
        } else {
            new MyAlert(Alert.AlertType.INFORMATION, "没有找到您查询的会员！").show();
        }
    }

    @FXML // 显示全部信息
    private void showAll() {
        tv_vipInfo.showAllInfos();
    }



}
