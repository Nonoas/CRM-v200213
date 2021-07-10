package indi.nonoas.crm.controller.consume;

import indi.nonoas.crm.view.vip.VipInfoTable;
import indi.nonoas.crm.pojo.dto.VipInfoDto;
import indi.nonoas.crm.service.VipService;
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

    private final VipService vipService = (VipService) SpringUtil.getBean("UserServiceImpl");

    private final VipInfoTable tv_vipInfo = new VipInfoTable(); // ��Ա��Ϣ��

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
        cb_disType.getItems().add("ȫ������");
        // �����ݿ�������û�Ա�ȼ�������ʼ��ComboBox
        List<String> listName = vipLvService.listAllNames();
        for (String str : listName) {
            cb_disType.getItems().add(str);
        }
        cb_disType.setValue("ȫ������");
    }

    @FXML
    private void inquireVIPInfo() {
        String str = tf_findInfo.getText().trim();
        String disType = cb_disType.getValue().equals("ȫ������") ? "" : cb_disType.getValue();
        if (str.equals(""))
            return;
        ArrayList<VipInfoDto> listVipBeans = vipService.selectByFiltrate(str, str, disType);
        if (listVipBeans != null) {
            tv_vipInfo.clearData();
            for (VipInfoDto bean : listVipBeans)
                tv_vipInfo.addBean(bean);
        } else {
            new MyAlert(Alert.AlertType.INFORMATION, "û���ҵ�����ѯ�Ļ�Ա��").show();
        }
    }

    @FXML // ��ʾȫ����Ϣ
    private void showAll() {
        tv_vipInfo.showAllInfos();
    }



}
